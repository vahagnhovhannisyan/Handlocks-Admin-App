package com.example.handlocksadminapp.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.handlocksadminapp.R;
import com.example.handlocksadminapp.activities.AddOfferedProductActivity;
import com.example.handlocksadminapp.activities.AddProductActivity;
import com.example.handlocksadminapp.adapters.OffersBrnAdapter;
import com.example.handlocksadminapp.adapters.OffersCxnAdapter;
import com.example.handlocksadminapp.adapters.OffersKoxAdapter;
import com.example.handlocksadminapp.adapters.OffersMijAdapter;
import com.example.handlocksadminapp.adapters.OffersMxlAdapter;
import com.example.handlocksadminapp.adapters.OffersPakAdapter;
import com.example.handlocksadminapp.adapters.OffersPakMexAdapter;
import com.example.handlocksadminapp.models.OffersModel;
import com.example.handlocksadminapp.models.ProductModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class OffersActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    ImageView imageMenu;
    NavigationView navigationView;
    TextView textTitle;

    FirebaseFirestore db;

    TextView addOfferedProduct;

    RecyclerView brnRec, pakRec, koxRec, mxlRec, cxnRec, mijRec, pakMexRec;
    OffersBrnAdapter offersBrnAdapter;
    List<OffersModel> offersModelList;

    List<OffersModel> offersPakModelList;
    OffersPakAdapter offersPakAdapter;

    List<OffersModel> offersKoxModelList;
    OffersKoxAdapter offersKoxAdapter;

    List<OffersModel> offersMxlModelList;
    OffersMxlAdapter offersMxlAdapter;

    List<OffersModel> offersCxnModelList;
    OffersCxnAdapter offersCxnAdapter;

    List<OffersModel> offersMijModelList;
    OffersMijAdapter offersMijAdapter;

    List<OffersModel> offersPakMexModelList;
    OffersPakMexAdapter offersPakMexAdapter;

    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offers);
        drawerLayout = findViewById(R.id.drawerLayout);
        imageMenu = findViewById(R.id.imageMenu);
        navigationView = findViewById(R.id.navigationView);
        textTitle = findViewById(R.id.textTitle);
        progressBar = findViewById(R.id.progressbar);
        progressBar.setVisibility(View.VISIBLE);

        db = FirebaseFirestore.getInstance();

        addOfferedProduct = findViewById(R.id.add_offered_product);
        addOfferedProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(OffersActivity.this, AddOfferedProductActivity.class));

            }
        });

        brnRec = findViewById(R.id.brnRec);
        brnRec.setLayoutManager(new LinearLayoutManager(getApplicationContext(), RecyclerView.HORIZONTAL,false));
        offersModelList = new ArrayList<>();
        offersBrnAdapter = new OffersBrnAdapter(getApplicationContext(),offersModelList);
        brnRec.setAdapter(offersBrnAdapter);

        db.collection("OffersBrn").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for(DocumentSnapshot documentSnapshot : task.getResult().getDocuments()){

                        String documentId = documentSnapshot.getId();
                        OffersModel offersModel = documentSnapshot.toObject(OffersModel.class);
                        offersModel.setDocumentId(documentId);
                        offersModelList.add(offersModel);
                        offersBrnAdapter.notifyDataSetChanged();
                        progressBar.setVisibility(View.GONE);

                    }
                }
                else{
                    Toast.makeText(OffersActivity.this, "Սխալ։" + task.getException(),Toast.LENGTH_SHORT).show();
                }

            }
        });

        pakRec = findViewById(R.id.pakRec);
        pakRec.setLayoutManager(new LinearLayoutManager(getApplicationContext(), RecyclerView.HORIZONTAL,false));
        offersPakModelList = new ArrayList<>();
        offersPakAdapter = new OffersPakAdapter(getApplicationContext(),offersPakModelList);
        pakRec.setAdapter(offersPakAdapter);

        db.collection("OffersPak")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String documentId = document.getId();
                                OffersModel offersModel = document.toObject(OffersModel.class);
                                offersModel.setDocumentId(documentId);
                                offersPakModelList.add(offersModel);
                                offersPakAdapter.notifyDataSetChanged();

                                progressBar.setVisibility(View.GONE);


                            }
                        } else {

                            Toast.makeText(getApplicationContext(), "Սխալ։" + task.getException(), Toast.LENGTH_SHORT).show();

                        }
                    }
                });


        koxRec = findViewById(R.id.koxRec);
        koxRec.setLayoutManager(new LinearLayoutManager(getApplicationContext(), RecyclerView.HORIZONTAL,false));
        offersKoxModelList = new ArrayList<>();
        offersKoxAdapter = new OffersKoxAdapter(getApplicationContext(),offersKoxModelList);
        koxRec.setAdapter(offersKoxAdapter);

        db.collection("OffersKox").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for(DocumentSnapshot documentSnapshot : task.getResult().getDocuments()){

                        String documentId = documentSnapshot.getId();
                        OffersModel offersModel = documentSnapshot.toObject(OffersModel.class);
                        offersModel.setDocumentId(documentId);
                        offersKoxModelList.add(offersModel);
                        offersKoxAdapter.notifyDataSetChanged();
                        progressBar.setVisibility(View.GONE);

                    }
                }
                else{
                    Toast.makeText(OffersActivity.this, "Սխալ։" + task.getException(),Toast.LENGTH_SHORT).show();
                }

            }
        });

        mxlRec = findViewById(R.id.mxlRec);
        mxlRec.setLayoutManager(new LinearLayoutManager(getApplicationContext(), RecyclerView.HORIZONTAL,false));
        offersMxlModelList = new ArrayList<>();
        offersMxlAdapter = new OffersMxlAdapter(getApplicationContext(),offersMxlModelList);
        mxlRec.setAdapter(offersMxlAdapter);

        db.collection("OffersMxl").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for(DocumentSnapshot documentSnapshot : task.getResult().getDocuments()){

                        String documentId = documentSnapshot.getId();
                        OffersModel offersModel = documentSnapshot.toObject(OffersModel.class);
                        offersModel.setDocumentId(documentId);
                        offersMxlModelList.add(offersModel);
                        offersMxlAdapter.notifyDataSetChanged();
                        progressBar.setVisibility(View.GONE);

                    }
                }
                else{
                    Toast.makeText(OffersActivity.this, "Սխալ։" + task.getException(),Toast.LENGTH_SHORT).show();
                }

            }
        });

        cxnRec = findViewById(R.id.cxnRec);
        cxnRec.setLayoutManager(new LinearLayoutManager(getApplicationContext(), RecyclerView.HORIZONTAL,false));
        offersCxnModelList = new ArrayList<>();
        offersCxnAdapter = new OffersCxnAdapter(getApplicationContext(),offersCxnModelList);
        cxnRec.setAdapter(offersCxnAdapter);

        db.collection("OffersCxn").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for(DocumentSnapshot documentSnapshot : task.getResult().getDocuments()){

                        String documentId = documentSnapshot.getId();
                        OffersModel offersModel = documentSnapshot.toObject(OffersModel.class);
                        offersModel.setDocumentId(documentId);
                        offersCxnModelList.add(offersModel);
                        offersCxnAdapter.notifyDataSetChanged();
                        progressBar.setVisibility(View.GONE);

                    }
                }
                else{
                    Toast.makeText(OffersActivity.this, "Սխալ։" + task.getException(),Toast.LENGTH_SHORT).show();
                }

            }
        });


        mijRec = findViewById(R.id.mijRec);
        mijRec.setLayoutManager(new LinearLayoutManager(getApplicationContext(), RecyclerView.HORIZONTAL,false));
        offersMijModelList = new ArrayList<>();
        offersMijAdapter = new OffersMijAdapter(getApplicationContext(),offersMijModelList);
        mijRec.setAdapter(offersMijAdapter);

        db.collection("OffersMij").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for(DocumentSnapshot documentSnapshot : task.getResult().getDocuments()){

                        String documentId = documentSnapshot.getId();
                        OffersModel offersModel = documentSnapshot.toObject(OffersModel.class);
                        offersModel.setDocumentId(documentId);
                        offersMijModelList.add(offersModel);
                        offersMijAdapter.notifyDataSetChanged();
                        progressBar.setVisibility(View.GONE);

                    }
                }
                else{
                    Toast.makeText(OffersActivity.this, "Սխալ։" + task.getException(),Toast.LENGTH_SHORT).show();
                }

            }
        });


        pakMexRec = findViewById(R.id.pakMexRec);
        pakMexRec.setLayoutManager(new LinearLayoutManager(getApplicationContext(), RecyclerView.HORIZONTAL,false));
        offersPakMexModelList = new ArrayList<>();
        offersPakMexAdapter = new OffersPakMexAdapter(getApplicationContext(),offersPakMexModelList);
        pakMexRec.setAdapter(offersPakMexAdapter);

        db.collection("OffersPakMex").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for(DocumentSnapshot documentSnapshot : task.getResult().getDocuments()){

                        String documentId = documentSnapshot.getId();
                        OffersModel offersModel = documentSnapshot.toObject(OffersModel.class);
                        offersModel.setDocumentId(documentId);
                        offersPakMexModelList.add(offersModel);
                        offersPakMexAdapter.notifyDataSetChanged();
                        progressBar.setVisibility(View.GONE);

                    }
                }
                else{
                    Toast.makeText(OffersActivity.this, "Սխալ։" + task.getException(),Toast.LENGTH_SHORT).show();
                }

            }
        });

        imageMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        navigationView.setItemIconTintList(null);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case
                            R.id.menuOffers:
                        drawerLayout.closeDrawer(GravityCompat.START);

                        break;
                    case
                            R.id.menuProducts:
                        drawerLayout.closeDrawer(GravityCompat.START);
                        finish();
                        startActivity(new Intent(OffersActivity.this, ProductsActivity.class));

                        break;
                    case
                            R.id.menuNewProducts:
                        drawerLayout.closeDrawer(GravityCompat.START);
                        finish();
                        startActivity(new Intent(OffersActivity.this, NewProductsActivity.class));

                        break;
                    case
                            R.id.menuUsers:
                        drawerLayout.closeDrawer(GravityCompat.START);
                        finish();
                        startActivity(new Intent(OffersActivity.this, UsersActivity.class));

                        break;
                }

                return true;
            }
        });



    }
}