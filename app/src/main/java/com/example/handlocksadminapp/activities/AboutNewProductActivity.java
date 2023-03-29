package com.example.handlocksadminapp.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.example.handlocksadminapp.R;
import com.example.handlocksadminapp.adapters.NewProductsAdapter;
import com.example.handlocksadminapp.adapters.ProductAdapter;
import com.example.handlocksadminapp.models.ProductModel;
import com.example.handlocksadminapp.ui.NewProductsActivity;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class AboutNewProductActivity extends AppCompatActivity {

    ImageView aboutProductImg;
    TextView price, brand, type, description;
    Toolbar toolbar;
    AppCompatButton deleteProduct;

    NewProductsAdapter newProductsAdapter;
    List<ProductModel> productModelList;


    FirebaseFirestore firestore;
    FirebaseAuth auth;

    ProductModel productModel = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_product);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        firestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        final Object object = getIntent().getSerializableExtra("detail");
        if(object instanceof ProductModel){
            productModel = (ProductModel) object;

        }


        String position = getIntent().getStringExtra("position");
        aboutProductImg = findViewById(R.id.about_product_img);
        price = findViewById(R.id.about_product_price);
        brand = findViewById(R.id.about_product_brand);
        type = findViewById(R.id.about_product_type);
        description = findViewById(R.id.about_product_des);
        deleteProduct = findViewById(R.id.deleteProduct);

        productModelList = new ArrayList<>();
        newProductsAdapter = new NewProductsAdapter(getApplicationContext(), productModelList);


        firestore.collection("NewProducts").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for(DocumentSnapshot documentSnapshot : task.getResult().getDocuments()){

                        String documentId = documentSnapshot.getId();
                        ProductModel productModel = documentSnapshot.toObject(ProductModel.class);
                        productModel.setDocumentId(documentId);
                        productModelList.add(productModel);
                        newProductsAdapter.notifyDataSetChanged();

                    }
                }



            }
        });

        deleteProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firestore.collection("NewProducts").document(productModelList.get(Integer.parseInt(position)).getDocumentId())
                        .delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                                if(task.isSuccessful()){
                                    productModelList.remove(productModelList.get(Integer.parseInt(position)));

                                    newProductsAdapter.notifyDataSetChanged();
                                    Toast.makeText(getApplicationContext(), "Ապրանքը Ջնջված Է", Toast.LENGTH_SHORT).show();
                                }
                                else {
                                    Toast.makeText(getApplicationContext(), "Սխալ" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }

                            }
                        });
                finish();
            }
        });

        if(productModel != null){
            Glide.with(getApplicationContext()).load(productModel.getImg_url()).into(aboutProductImg);
            brand.setText(productModel.getBrand());
            type.setText(productModel.getType());
            description.setText(productModel.getDescription());
            price.setText("Արժեք։ " + productModel.getPrice() + "֏");

        }

    }
}
