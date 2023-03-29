package com.example.handlocksadminapp.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.handlocksadminapp.R;
import com.example.handlocksadminapp.adapters.OffersBrnAdapter;
import com.example.handlocksadminapp.adapters.ProductAdapter;
import com.example.handlocksadminapp.models.OffersModel;
import com.example.handlocksadminapp.models.ProductModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class AboutOfferedProductActivity extends AppCompatActivity {
    ImageView aboutProductImg;
    TextView oldPrice, newPrice, brand, type, description;
    Toolbar toolbar;
    AppCompatButton deleteProduct;

    OffersBrnAdapter offersBrnAdapter;
    List<OffersModel> offersModelList;


    FirebaseFirestore firestore;
    FirebaseAuth auth;

    OffersModel offersModel = null;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_offered_product);
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
        if(object instanceof OffersModel){
            offersModel = (OffersModel) object;

        }


        String position = getIntent().getStringExtra("position");
        aboutProductImg = findViewById(R.id.about_product_img);
        oldPrice = findViewById(R.id.about_product_old_price);
        newPrice = findViewById(R.id.about_product_new_price);
        brand = findViewById(R.id.about_product_brand);
        type = findViewById(R.id.about_product_type);
        description = findViewById(R.id.about_product_des);
        deleteProduct = findViewById(R.id.deleteProduct);

        offersModelList = new ArrayList<>();
        offersBrnAdapter = new OffersBrnAdapter(getApplicationContext(), offersModelList);

        firestore.collection("OffersBrn").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for(DocumentSnapshot documentSnapshot : task.getResult().getDocuments()){

                        String documentId = documentSnapshot.getId();
                        OffersModel offersModel = documentSnapshot.toObject(OffersModel.class);
                        offersModel.setDocumentId(documentId);
                        offersModelList.add(offersModel);
                        offersBrnAdapter.notifyDataSetChanged();

                    }
                }



            }
        });

        deleteProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firestore.collection("OffersBrn").document(offersModelList.get(Integer.parseInt(position)).getDocumentId())
                        .delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                                if(task.isSuccessful()){
                                    offersModelList.remove(offersModelList.get(Integer.parseInt(position)));

                                    offersBrnAdapter.notifyDataSetChanged();
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

        if(offersModel != null){
            Glide.with(getApplicationContext()).load(offersModel.getImg_url()).into(aboutProductImg);
            brand.setText(offersModel.getBrand());
            type.setText(offersModel.getType());
            description.setText(offersModel.getDescription());
            oldPrice.setText("Հին արժեք։ " + offersModel.getOldPrice() + "֏");
            newPrice.setText("Նոր արժեք։ " + offersModel.getNewPrice() + "֏");

        }

    }
}
