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

public class categroy_adapter extends RecyclerView.Adapter<categroy_adapter.ViewHolder> {
    Context context;
    ArrayList<categroy_model_class> items;


 private categroy_edit click_categroy_edit;
  public interface categroy_edit{
      void onclick(int position);
  }
  public void set_categroy_edit(categroy_edit Categroy_edit){
      this.click_categroy_edit=Categroy_edit;
  }

    public categroy_adapter(Context context,ArrayList<categroy_model_class> items) {
        this.context=context;
        this.items=items;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.uom_ex_recy,parent,false);
        return new ViewHolder(view,click_categroy_edit);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        categroy_model_class data=items.get(position);
        //holder.p_name.setText(items.getP_name());
        holder.counter.setText(data.getNumber());
        holder.name.setText(data.getName());

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView counter,name;
        ImageView edit;
        public ViewHolder(@NonNull View itemView,categroy_edit categroy_edit) {
            super(itemView);
            counter=itemView.findViewById(R.id.uomcounter_txtview);
            name=itemView.findViewById(R.id.uomname_txtview);
            edit=itemView.findViewById(R.id.uomedit_imageview);
            edit.setOnClickListener(view -> categroy_edit.onclick(getAdapterPosition()));
        }
    }
}
