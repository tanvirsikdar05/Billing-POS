package com.kaiser.kaiserinvoice;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;


public class per_invoice_adapter extends RecyclerView.Adapter<per_invoice_adapter.ViewHolder>  {
    private ArrayList<perinvoice_model_class> exampleList;
    private onClicklistener clicklistener;
    Context context;


    public interface onClicklistener{
        void onclick(int position);
    }

    public per_invoice_adapter(Context context,ArrayList<perinvoice_model_class> exampleList) {
        this.exampleList = exampleList;

        this.context=context;
    }
    public void select_invoice(onClicklistener clicklistener){
        this.clicklistener=clicklistener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.per_invoice_ex,parent,false);
        return new ViewHolder(view,clicklistener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        perinvoice_model_class data=exampleList.get(position);
        //holder.p_name.setText(items.getP_name());
        holder.id.setText(data.getId());
        holder.total.setText(data.getTotal());

    }

    @Override
    public int getItemCount() {
        return exampleList.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout per_inv_ll;
        TextView id,total;
        public ViewHolder(@NonNull View itemView,onClicklistener click) {
            super(itemView);
            id=itemView.findViewById(R.id.inv_id);
            total=itemView.findViewById(R.id.inv_total);
            per_inv_ll=itemView.findViewById(R.id.per_inv_ll);
            per_inv_ll.setOnClickListener(view -> {
                click.onclick(getAdapterPosition());
            });
        }
    }

    public void filterlist(ArrayList<perinvoice_model_class> filteredlist){
        exampleList = filteredlist;
        notifyDataSetChanged();

    }

}
