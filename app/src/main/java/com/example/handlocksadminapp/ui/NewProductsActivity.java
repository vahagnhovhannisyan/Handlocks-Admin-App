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
import com.example.handlocksadminapp.activities.AddNewProductActivity;
import com.example.handlocksadminapp.activities.AddProductActivity;
import com.example.handlocksadminapp.adapters.NewProductsAdapter;
import com.example.handlocksadminapp.adapters.ProductAdapter;
import com.example.handlocksadminapp.models.ProductModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class NewProductsActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    ImageView imageMenu;
    NavigationView navigationView;
    TextView textTitle;

    FirebaseFirestore firestore;

    Button addNewProduct;

    RecyclerView recyclerView;
    NewProductsAdapter newProductsAdapter;
    List<ProductModel> productModelList;

    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_products);
        drawerLayout = findViewById(R.id.drawerLayout);
        imageMenu = findViewById(R.id.imageMenu);
        navigationView = findViewById(R.id.navigationView);
        textTitle = findViewById(R.id.textTitle);
        progressBar = findViewById(R.id.progressbar);
        recyclerView = findViewById(R.id.new_product_rec);
        recyclerView.setVisibility(View.GONE);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        progressBar.setVisibility(View.VISIBLE);

        firestore = FirebaseFirestore.getInstance();

        addNewProduct = findViewById(R.id.addNewProduct);
        addNewProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(NewProductsActivity.this, AddNewProductActivity.class));

            }
        });

        productModelList = new ArrayList<>();
        newProductsAdapter = new NewProductsAdapter(getApplicationContext(), productModelList);
        recyclerView.setAdapter(newProductsAdapter);

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
                        progressBar.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.VISIBLE);
                    }
                }
                else{
                    Toast.makeText(NewProductsActivity.this, "Սխալ։" + task.getException(),Toast.LENGTH_SHORT).show();
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
                            R.id.menuProducts:
                        drawerLayout.closeDrawer(GravityCompat.START);
                        finish();
                        startActivity(new Intent(NewProductsActivity.this, ProductsActivity.class));

                        break;
                    case
                            R.id.menuNewProducts:
                        drawerLayout.closeDrawer(GravityCompat.START);

                        break;
                    case
                            R.id.menuOffers:
                        drawerLayout.closeDrawer(GravityCompat.START);
                        finish();
                        startActivity(new Intent(NewProductsActivity.this, OffersActivity.class));

                        break;
                    case
                            R.id.menuUsers:
                        drawerLayout.closeDrawer(GravityCompat.START);
                        finish();
                        startActivity(new Intent(NewProductsActivity.this, UsersActivity.class));

                        break;
                }

                return true;
            }
        });

    }
}