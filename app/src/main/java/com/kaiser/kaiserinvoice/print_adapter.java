package com.kaiser.kaiserinvoice;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class print_adapter extends RecyclerView.Adapter<print_adapter.ViewHolder> {
    ArrayList<print_model_class> datalist;
    Context context;


    public print_adapter(ArrayList<print_model_class> datalist, Context context) {
        this.datalist = datalist;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.print_ex,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        print_model_class data=datalist.get(position);
        holder.name.setText(data.getName());
        holder.totaluom.setText(data.getTotal_uom());
        holder.price.setText(data.getPrice());
        holder.totalprice.setText(data.getTotal_price());
        holder.discount.setText(data.getDiscount());

    }

    @Override
    public int getItemCount() {
        return datalist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name,totaluom,price,totalprice,discount;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.name);
            totaluom=itemView.findViewById(R.id.tuom);
            price=itemView.findViewById(R.id.perp);
            totalprice=itemView.findViewById(R.id.tp);
            discount=itemView.findViewById(R.id.dis);
        }
    }
}
