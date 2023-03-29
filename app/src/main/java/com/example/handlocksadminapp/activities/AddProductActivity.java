package com.example.handlocksadminapp.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.handlocksadminapp.R;
import com.example.handlocksadminapp.adapters.ProductAdapter;
import com.example.handlocksadminapp.models.ProductModel;
import com.example.handlocksadminapp.ui.ProductsActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class AddProductActivity extends AppCompatActivity {



    FirebaseStorage storage;
    FirebaseAuth auth;
    FirebaseDatabase database;
    FirebaseFirestore firestore;

    ImageView productImg;
    EditText productPrice, productBrand, productDescription, productName, productType;
    Button createProduct;
    Toolbar toolbar;

    ProgressBar progressBar;

    Spinner spinnerType;
    Spinner spinnerBrand;
    String spinnerTypeText;
    String spinnerBrandText;

    Uri imageUri;
    StorageReference storageReference;
    String imgUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
        firestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        storage = FirebaseStorage.getInstance();
        database = FirebaseDatabase.getInstance();

        productImg = findViewById(R.id.product_img);

        productName = findViewById(R.id.productName);
        productDescription = findViewById(R.id.productDes);
        productPrice = findViewById(R.id.productPrice);
        spinnerBrand = findViewById(R.id.productBrand);
        ArrayAdapter<CharSequence> adapterBrands = ArrayAdapter.createFromResource(this, R.array.brands, android.R.layout.simple_spinner_item);
        adapterBrands.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerBrand.setAdapter(adapterBrands);
        spinnerBrand.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                spinnerBrandText = adapterView.getItemAtPosition(i).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinnerType = findViewById(R.id.productType);
        ArrayAdapter<CharSequence> adapterTypes = ArrayAdapter.createFromResource(this, R.array.types, android.R.layout.simple_spinner_item);
        adapterTypes.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerType.setAdapter(adapterTypes);
        spinnerType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                spinnerTypeText = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        createProduct = findViewById(R.id.createProduct);

        progressBar = findViewById(R.id.progressbar);
        progressBar.setVisibility(View.GONE);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        createProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createProducts();
                startActivity(new Intent(AddProductActivity.this, ProductsActivity.class));
                finish();


            }
        });

        productImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, 100);
            }
        });
    }

    private void createProducts() {
        String name = productName.getText().toString();
        String description = productDescription.getText().toString();
        int price = Integer.parseInt(productPrice.getText().toString());
        String type = spinnerTypeText;
        String brand = spinnerBrandText;

        final HashMap<String,Object> cartMap = new HashMap<>();

        cartMap.put("name", name);
        cartMap.put("description", description);
        cartMap.put("price", price);
        cartMap.put("brand", brand);
        cartMap.put("type", type);
        cartMap.put("img_url", imgUrl);


        firestore.collection("AllProducts").add(cartMap).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
            @Override
            public void onComplete(@NonNull Task<DocumentReference> task) {
                if(task.isSuccessful()) {

                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(AddProductActivity.this, "Ապրանքը Ստեղծված է", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(AddProductActivity.this, "Սխալ։" + task.getException(), Toast.LENGTH_SHORT).show();

                }

            }
        });

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 100 && data != null && data.getData() != null ){
            imageUri = data.getData();
            productImg.setImageURI(imageUri);


            SimpleDateFormat formatter = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", Locale.CANADA);
            Date now = new Date();
            String fileName = formatter.format(now);
            storageReference = FirebaseStorage.getInstance().getReference("products/" + fileName);
            storageReference.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    Toast.makeText(getApplicationContext(),"Ներբեռնված է", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);

                    storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            imgUrl = uri.toString();

                        }
                    });





                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getApplicationContext(),"Սխալ։" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                }
            });
        }




    }


}