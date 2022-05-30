package com.kaiser.kaiserinvoice;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class uom_adapter extends RecyclerView.Adapter<uom_adapter.ViewHolder> {
    Context context;
    ArrayList<uom_model_class> items;


    private uom_edit_click uomeditclicklistener;
    public interface uom_edit_click{
        void onclick(int position);
    }

    public void Set_uomedit_click(uom_edit_click uomeditclicklistener){
        this.uomeditclicklistener=uomeditclicklistener;
    }



    public uom_adapter(Context context,ArrayList<uom_model_class> items) {
        this.context=context;
        this.items=items;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.uom_ex_recy,parent,false);
        return new ViewHolder(view,uomeditclicklistener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        uom_model_class data=items.get(position);
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

        public ViewHolder(@NonNull View itemView,uom_edit_click uoeditclicklistener) {
            super(itemView);
            counter=itemView.findViewById(R.id.uomcounter_txtview);
            name=itemView.findViewById(R.id.uomname_txtview);
            edit=itemView.findViewById(R.id.uomedit_imageview);
            edit.setOnClickListener(view -> {
                uoeditclicklistener.onclick(getAdapterPosition());
            });
        }
    }
}
