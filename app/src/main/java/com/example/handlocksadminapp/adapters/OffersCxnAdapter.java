package com.example.handlocksadminapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.handlocksadminapp.R;
import com.example.handlocksadminapp.activities.AboutOfferedProductActivity;
import com.example.handlocksadminapp.models.OffersModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class OffersCxnAdapter extends RecyclerView.Adapter<OffersCxnAdapter.ViewHolder> {
    Context context;
    List<OffersModel> list;
    FirebaseFirestore firestore;

    public OffersCxnAdapter(Context context, List<OffersModel> list) {
        this.context = context;
        this.list = list;
        firestore = FirebaseFirestore.getInstance();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.offers_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        int a = position;
        Glide.with(context).load(list.get(position).getImg_url()).into(holder.imageView);
        holder.name.setText(list.get(position).getName());
        holder.brand.setText(list.get(position).getBrand());
        holder.oldPrice.setText(list.get(position).getOldPrice() + "֏");
        holder.newPrice.setText(list.get(position).getNewPrice() + "֏");
        holder.deleteItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                firestore.collection("OffersCxn").document(list.get(a).getDocumentId())
                        .delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                                if(task.isSuccessful()){
                                    list.remove(list.get(a));

                                    notifyDataSetChanged();
                                    Toast.makeText(context, "Ապրանքը Ջնջված Է", Toast.LENGTH_SHORT).show();
                                }
                                else {
                                    Toast.makeText(context, "Սխալ" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }

                            }
                        });
            }
        });



        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, AboutOfferedProductActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("detail", list.get(a));
                intent.putExtra("position", a + "");
                context.startActivity(intent);


            }
        });



    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView name, brand, oldPrice, newPrice;
        ImageView deleteItem;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.offers_product_img);
            name = itemView.findViewById(R.id.offers_product_name);
            brand = itemView.findViewById(R.id.offers_product_brand);
            oldPrice = itemView.findViewById(R.id.offers_old_price);
            newPrice = itemView.findViewById(R.id.offers_new_price);
            deleteItem = itemView.findViewById(R.id.deleteItem);

        }
    }
}