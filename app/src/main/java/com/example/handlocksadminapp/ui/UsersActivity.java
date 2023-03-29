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
import com.example.handlocksadminapp.adapters.ProductAdapter;
import com.example.handlocksadminapp.adapters.UsersAdapter;
import com.example.handlocksadminapp.models.ProductModel;
import com.example.handlocksadminapp.models.UsersModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class UsersActivity extends AppCompatActivity {
    DrawerLayout drawerLayout;
    ImageView imageMenu;
    NavigationView navigationView;
    TextView textTitle;

    FirebaseFirestore firestore;


    RecyclerView recyclerView;
    UsersAdapter usersAdapter;
    List<UsersModel> usersModelList;

    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);
        drawerLayout = findViewById(R.id.drawerLayout);
        imageMenu = findViewById(R.id.imageMenu);
        navigationView = findViewById(R.id.navigationView);
        textTitle = findViewById(R.id.textTitle);
        progressBar = findViewById(R.id.progressbar);
        recyclerView = findViewById(R.id.users_rec);
        recyclerView.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);

        firestore = FirebaseFirestore.getInstance();

        usersModelList = new ArrayList<>();
        usersAdapter = new UsersAdapter(getApplicationContext(), usersModelList);
        recyclerView.setAdapter(usersAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        firestore.collection("Users").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for(DocumentSnapshot documentSnapshot : task.getResult().getDocuments()){

                        UsersModel usersModel = documentSnapshot.toObject(UsersModel.class);
                        usersModelList.add(usersModel);
                        usersAdapter.notifyDataSetChanged();
                        progressBar.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.VISIBLE);
                    }
                }
                else{
                    Toast.makeText(UsersActivity.this, "Սխալ։" + task.getException(),Toast.LENGTH_SHORT).show();
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
                            R.id.menuUsers:
                        drawerLayout.closeDrawer(GravityCompat.START);

                        break;

                    case
                            R.id.menuProducts:
                        drawerLayout.closeDrawer(GravityCompat.START);
                        finish();
                        startActivity(new Intent(UsersActivity.this, ProductsActivity.class));

                        break;
                    case
                            R.id.menuNewProducts:
                        drawerLayout.closeDrawer(GravityCompat.START);
                        finish();
                        startActivity(new Intent(UsersActivity.this, NewProductsActivity.class));

                        break;

                    case
                            R.id.menuOffers:
                        drawerLayout.closeDrawer(GravityCompat.START);
                        finish();
                        startActivity(new Intent(UsersActivity.this, OffersActivity.class));

                        break;
                }

                return true;
            }
        });




    }
}