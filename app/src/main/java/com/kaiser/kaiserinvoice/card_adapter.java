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

public class card_adapter extends RecyclerView.Adapter<card_adapter.ViewHolder> {
    Context context;
    ArrayList<product_model_class> datalist;
    private p_edit pEdit;
    public card_adapter(Context context, ArrayList<product_model_class> datalist) {
        this.context = context;
        this.datalist = datalist;
    }
    public interface p_edit{
        void onclick(int position);
    }
    public void product_edit(p_edit pEdit){
        this.pEdit=pEdit;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.checkout_ex_rec,parent,false);
        return new ViewHolder(view,pEdit);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        product_model_class data=datalist.get(position);
        //holder.p_name.setText(items.getP_name());
        holder.name.setText(data.getName());
        holder.uom.setText(data.getUom());
        holder.pc_tk.setText(data.getPrice());
        holder.discount.setText(data.getCategroy());
        holder.total_tk.setText(data.getDb_id());

    }

    @Override
    public int getItemCount() {
        return datalist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name,uom,pc_tk,discount,total_tk;
        ImageView edit_btn;
        public ViewHolder(@NonNull View itemView,p_edit editp) {
            super(itemView);
            name=itemView.findViewById(R.id.p_name);
            uom=itemView.findViewById(R.id.total_uom);
            pc_tk=itemView.findViewById(R.id.pcs_tk);
            discount=itemView.findViewById(R.id.p_discount);
            total_tk=itemView.findViewById(R.id.total_p_tk);
            edit_btn=itemView.findViewById(R.id.p_edit_btn);
            edit_btn.setOnClickListener(view -> {
                editp.onclick(getAdapterPosition());
            });
        }
    }
}
