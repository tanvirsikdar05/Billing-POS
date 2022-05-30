package com.kaiser.kaiserinvoice;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class product_adapter extends RecyclerView.Adapter<product_adapter.ViewHolder> {
    ArrayList<product_model_class> itemlist;
    Context context;
    private product_edit_click product_edit;
    public product_adapter(ArrayList<product_model_class> itemlist, Context context) {
        this.itemlist = itemlist;
        this.context = context;
    }


    public interface product_edit_click{
        void onclick(int position);
    }
    public void set_product_edit(product_edit_click product_edit){
        this.product_edit=product_edit;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.product_ex_rec,parent,false);
        return new product_adapter.ViewHolder(view,product_edit);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        product_model_class data=itemlist.get(position);
        //holder.p_name.setText(items.getP_name());
        holder.id.setText(data.getId());
        holder.name.setText(data.getName());
        holder.uom.setText(data.getUom());
        holder.categroy.setText(data.getCategroy());
        holder.price.setText(data.getPrice());


    }

    @Override
    public int getItemCount() {
        return itemlist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name,categroy,uom,price,id;
        ImageView pedit;
        public ViewHolder(@NonNull View itemView,product_edit_click product_edit) {
            super(itemView);
            name=itemView.findViewById(R.id.product_name);
            categroy=itemView.findViewById(R.id.product_categroy);
            uom=itemView.findViewById(R.id.product_uom);
            price=itemView.findViewById(R.id.productprice);
            id=itemView.findViewById(R.id.product_counter);
            pedit=itemView.findViewById(R.id.product_edit_btn);

            pedit.setOnClickListener(view -> {
                product_edit.onclick(getAdapterPosition());
            });
        }
    }
}
