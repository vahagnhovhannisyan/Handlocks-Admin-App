package com.example.handlocksadminapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.handlocksadminapp.R;
import com.example.handlocksadminapp.models.OrdersModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class OrdersAdapter extends RecyclerView.Adapter<OrdersAdapter.ViewHolder> {
    Context context;
    List<OrdersModel> OrdersModelList;
    int totalPrice = 0;
    FirebaseAuth auth;
    FirebaseFirestore firestore;


    public OrdersAdapter(Context context, List<OrdersModel> OrdersModelList) {
        this.context = context;
        this.OrdersModelList = OrdersModelList;
        firestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.orders_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        int a = position;
        holder.name.setText(OrdersModelList.get(position).getProductName());
        holder.price.setText(OrdersModelList.get(position).getProductPrice());
        holder.date.setText(OrdersModelList.get(position).getCurrentDate());
        holder.time.setText(OrdersModelList.get(position).getCurrentTime());
        holder.quantity.setText(OrdersModelList.get(position).getTotalQuantity());
        holder.totalPrice.setText(String.valueOf(OrdersModelList.get(position).getTotalPrice()));
        holder.deliveryType.setText(OrdersModelList.get(position).getDeliveryType());


    }

    @Override
    public int getItemCount() {
        return OrdersModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, price, date, time, quantity, totalPrice, deliveryType;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.product_name);
            price = itemView.findViewById(R.id.product_price);
            date = itemView.findViewById(R.id.current_date);
            time = itemView.findViewById(R.id.current_time);
            quantity = itemView.findViewById(R.id.total_quantity);
            totalPrice = itemView.findViewById(R.id.total_price);
            deliveryType = itemView.findViewById(R.id.delivery_type);



        }
    }
}