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

public class product_select_adapter extends RecyclerView.Adapter<product_select_adapter.ViewHolder> {
    Context context;
    ArrayList<product_model_class> datalist;
    private product_select selectproduct;



    public interface product_select{
        void onclicl(int position);
    }
    public void select_onclick(product_select slt){
        this.selectproduct=slt;
    }

    public product_select_adapter(Context context, ArrayList<product_model_class> datalist) {
        this.context = context;
        this.datalist = datalist;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.item_ex,parent,false);
        return new ViewHolder(view,selectproduct);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        product_model_class data=datalist.get(position);
        //holder.p_name.setText(items.getP_name());
        holder.number.setText(data.getId());
        holder.name.setText(data.getName());


    }

    @Override
    public int getItemCount() {
        return datalist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView number,name;
        LinearLayout root_layout;
        public ViewHolder(@NonNull View itemView,product_select selectpro) {
            super(itemView);
            number=itemView.findViewById(R.id.item_number_ex);
            name=itemView.findViewById(R.id.item_name_ex);
            root_layout=itemView.findViewById(R.id.item_ex_layout);
            root_layout.setOnClickListener(view -> {
                selectpro.onclicl(getAdapterPosition());
            });
        }
    }
}
