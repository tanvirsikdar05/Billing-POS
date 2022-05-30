package com.kaiser.kaiserinvoice;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class uomsetupactivity extends AppCompatActivity {
    RecyclerView uomrecyclerview;
    ArrayList<uom_model_class> items=new ArrayList<>();
    uom_adapter adapter;
    ImageButton addbtn;
    Dbhelper dbhelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.uomsetupactivity);
        uomrecyclerview=findViewById(R.id.uom_recyclerview);
        addbtn=findViewById(R.id.adduombtn);
        dbhelper=new Dbhelper(uomsetupactivity.this);
        build_recyclerview();
        Loaduom_from_db();

        addbtn.setOnClickListener(view -> {
            adddata_in_recyclerview();
        });

        //edit uom data here
        adapter.Set_uomedit_click(position -> {
            Dialog dialog=new Dialog(this);
            dialog.setContentView(R.layout.uom_update_dialog);

            EditText data=dialog.findViewById(R.id.updatedialog_data);
            Button update_btn=dialog.findViewById(R.id.updatedialog_update);
            Button delete_btn=dialog.findViewById(R.id.updatedialog_delete);

            data.setText(items.get(position).getName());


            //uom update
            update_btn.setOnClickListener(view -> {
                if (!data.getText().toString().isEmpty()){

                        dbhelper.update_uom(items.get(position).getNumber(),data.getText().toString());
                        items.get(position).setName(data.getText().toString());
                        adapter.notifyItemChanged(position);
                        dialog.dismiss();
                }else {
                    ctoast("Field cannot be Empty");
                }
            });
            //uom delete
            delete_btn.setOnClickListener(view -> {
                dbhelper.delete_uom(items.get(position).getNumber());
                items.remove(position);
                adapter.notifyItemRemoved(position);
                dialog.dismiss();
            });

            dialog.show();
        });
    }

    private void Loaduom_from_db() {
        Cursor cursor = dbhelper.get_all_uom();
        items.clear();
        while (cursor.moveToNext()){
            @SuppressLint("Range") String id = cursor.getString(cursor.getColumnIndex(Dbhelper.UOM_ID));
            @SuppressLint("Range") String uom_name = cursor.getString(cursor.getColumnIndex(Dbhelper.UOM_NAME_KEY));
            items.add(new uom_model_class(id,uom_name));
            adapter.notifyDataSetChanged();
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
        savebtn.setOnClickListener(view -> {
            if(!data.getText().toString().isEmpty()){
                //add uom data here
                long id=dbhelper.add_uom(data.getText().toString());
                items.add(new uom_model_class(String.valueOf(id),data.getText().toString()));
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
        adapter=new uom_adapter(this,items);
        uomrecyclerview.setHasFixedSize(true);
        uomrecyclerview.setLayoutManager(new LinearLayoutManager(this));
        uomrecyclerview.setAdapter(adapter);
    }
}