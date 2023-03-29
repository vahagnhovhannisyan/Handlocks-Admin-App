package com.example.handlocksadminapp.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.handlocksadminapp.R;
import com.example.handlocksadminapp.adapters.NewProductsAdapter;
import com.example.handlocksadminapp.adapters.OrdersAdapter;
import com.example.handlocksadminapp.adapters.ProductAdapter;
import com.example.handlocksadminapp.adapters.UsersAdapter;
import com.example.handlocksadminapp.models.OrdersModel;
import com.example.handlocksadminapp.models.ProductModel;
import com.example.handlocksadminapp.models.UsersModel;
import com.example.handlocksadminapp.ui.ProductsActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class AboutUserActivity extends AppCompatActivity {

    Toolbar toolbar;
    TextView name, email;

    UsersAdapter usersAdapter;
    List<UsersModel> usersModelList;


    FirebaseFirestore firestore;
    FirebaseAuth auth;

    UsersModel usersModel = null;

    RecyclerView rec;
    OrdersAdapter ordersAdapter;
    List<OrdersModel> ordersModelList;


    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_user);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        progressBar = findViewById(R.id.progressbar);
        rec = findViewById(R.id.users_rec);
        rec.setVisibility(View.GONE);
        rec.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        progressBar.setVisibility(View.VISIBLE);

        firestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        final Object object = getIntent().getSerializableExtra("detail");
        if(object instanceof UsersModel){
            usersModel = (UsersModel) object;
        }
        name = findViewById(R.id.about_user_name);
        email = findViewById(R.id.about_user_email);

        if(usersModel != null){
            name.setText(usersModel.getName());
            email.setText(usersModel.getEmail());
        }
        ordersModelList = new ArrayList<>();
        ordersAdapter = new OrdersAdapter(getApplicationContext(), ordersModelList);
        rec.setAdapter(ordersAdapter);

        firestore.collection("CurrentUser").document(email.getText().toString()).collection("MyOrder").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for(DocumentSnapshot documentSnapshot : task.getResult().getDocuments()){

                        String documentId = documentSnapshot.getId();
                        OrdersModel ordersModel = documentSnapshot.toObject(OrdersModel.class);
                        ordersModelList.add(ordersModel);
                        ordersAdapter.notifyDataSetChanged();
                        progressBar.setVisibility(View.GONE);
                        rec.setVisibility(View.VISIBLE);
                    }
                }
                else{
                    Toast.makeText(AboutUserActivity.this, "Սխալ։" + task.getException(),Toast.LENGTH_SHORT).show();
                }

            }
        });


    }
}