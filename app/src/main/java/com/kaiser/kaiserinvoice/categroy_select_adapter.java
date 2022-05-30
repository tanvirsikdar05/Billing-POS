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

public class categroy_select_adapter extends RecyclerView.Adapter<categroy_select_adapter.ViewHolder> {

    Context context;
    ArrayList<uom_model_class> datalist;
    private  categroySelect selectcategroy;


    public interface categroySelect{
        void onclick(int position);
    }
    public void click_categroy(categroySelect ctselect){
        this.selectcategroy=ctselect;
    }
    public categroy_select_adapter(Context context, ArrayList<uom_model_class> datalist){
        this.context=context;
        this.datalist=datalist;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.categroy_select_ex,parent,false);
        return new ViewHolder(view,selectcategroy);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        uom_model_class data=datalist.get(position);
        //holder.p_name.setText(items.getP_name());
        holder.header.setText(data.getName());

    }

    @Override
    public int getItemCount() {
        return datalist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView header;
        LinearLayout rootlayout;
        public ViewHolder(@NonNull View itemView,categroySelect select) {
            super(itemView);
            header=itemView.findViewById(R.id.ct_name);
            rootlayout=itemView.findViewById(R.id.root_layout);
            rootlayout.setOnClickListener(view -> {
                select.onclick(getAdapterPosition());
            });
        }
    }
}
