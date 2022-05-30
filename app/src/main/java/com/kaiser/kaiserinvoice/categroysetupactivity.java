package com.kaiser.kaiserinvoice;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class categroysetupactivity extends AppCompatActivity {
    RecyclerView ctgrecyclerview;
    ArrayList<categroy_model_class> items=new ArrayList<>();
    categroy_adapter adapter;
    ImageButton addbtn;
    Dbhelper dbhelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.categroysetupactivity);

        ctgrecyclerview=findViewById(R.id.categroy_recyclerview);
        addbtn=findViewById(R.id.addctgbtn);
        dbhelper=new Dbhelper(categroysetupactivity.this);
        build_recyclerview();
        loaddata_from_database();

        addbtn.setOnClickListener(view -> {
            adddata_in_recyclerview();
        });


        //update catagroy
        adapter.set_categroy_edit(position -> {
            Dialog dialog=new Dialog(this);
            dialog.setContentView(R.layout.uom_update_dialog);
            EditText data=dialog.findViewById(R.id.updatedialog_data);
            Button update_btn=dialog.findViewById(R.id.updatedialog_update);
            Button delete_btn=dialog.findViewById(R.id.updatedialog_delete);

            data.setText(items.get(position).getName());


            //uom update
            update_btn.setOnClickListener(view -> {
                if (!data.getText().toString().isEmpty()){
                    dbhelper.update_categroy(items.get(position).getNumber(),data.getText().toString());
                    items.get(position).setName(data.getText().toString());
                    adapter.notifyItemChanged(position);
                    dialog.dismiss();
                }else {
                    ctoast("Field cannot be Empty");
                }
            });
            //uom delete
            delete_btn.setOnClickListener(view -> {
                dbhelper.delete_categroy(items.get(position).getNumber());
                items.remove(position);
                adapter.notifyItemRemoved(position);
                dialog.dismiss();
            });

            dialog.show();
        });


    }

    private void loaddata_from_database() {

        Cursor cursor = dbhelper.get_all_categroy();
        items.clear();
        while (cursor.moveToNext()){
            @SuppressLint("Range") String id = cursor.getString(cursor.getColumnIndex(Dbhelper.CATEGROY_ID));
            @SuppressLint("Range") String categroy = cursor.getString(cursor.getColumnIndex(Dbhelper.CATEGROY_NAME_KEY));
            adapter.notifyDataSetChanged();
            items.add(new categroy_model_class(id,categroy));
        }
        cursor.close();
    }

    private void adddata_in_recyclerview() {
        Dialog dialog=new Dialog(this);
        dialog.setContentView(R.layout.uom_add_dialog);
        dialog.setCancelable(true);
        TextView header=dialog.findViewById(R.id.adddialog_header);
        EditText data=dialog.findViewById(R.id.adddialog_data);
        Button savebtn=dialog.findViewById(R.id.adddialog_savebtn);
        header.setText("Add Categroy");
        data.setHint("Enter Categroy name");
        savebtn.setOnClickListener(view -> {
            if(!data.getText().toString().isEmpty()){
                long id=dbhelper.add_categroy(data.getText().toString());
                items.add(new categroy_model_class(String.valueOf(id),data.getText().toString()));
                adapter.notifyDataSetChanged();
                dialog.dismiss();
            }else {
                ctoast("Field cannot be empty");
            }
        });
        dialog.show();
    }
    public void ctoast(String txt){
        Toast.makeText(this,txt,Toast.LENGTH_SHORT).show();
    }

    private void build_recyclerview() {
        adapter=new categroy_adapter(this,items);
        ctgrecyclerview.setHasFixedSize(true);
        ctgrecyclerview.setLayoutManager(new LinearLayoutManager(this));
        ctgrecyclerview.setAdapter(adapter);
    }
}