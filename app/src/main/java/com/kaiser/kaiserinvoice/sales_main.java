package com.kaiser.kaiserinvoice;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class sales_main extends AppCompatActivity {
    RecyclerView recyclerView;
    ArrayList<uom_model_class> datalist=new ArrayList<>();
    categroy_select_adapter adapter;
    Dbhelper dbhelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sales_main);
        recyclerView=findViewById(R.id.categroy_select_recyclerview);
        dbhelper=new Dbhelper(sales_main.this);

        /*SharedPreferences prefs = getSharedPreferences("addbl", MODE_PRIVATE);
        String name = prefs.getString("blnc", "0");*/



        /*if (name.equals("0")){
            Dialog dialog=new Dialog(sales_main.this);
            dialog.setContentView(R.layout.add_balance_dialogue);
            dialog.setCancelable(false);
            EditText blnc=dialog.findViewById(R.id.et_balance);
            Button skip=dialog.findViewById(R.id.btn_skip);
            Button addbl=dialog.findViewById(R.id.btn_add);

            skip.setOnClickListener(view -> {
                dialog.dismiss();
            });
            addbl.setOnClickListener(view -> {

                if (! blnc.getText().toString().isEmpty()){

                    SharedPreferences.Editor editor = getSharedPreferences("addbl", MODE_PRIVATE).edit();
                    editor.putString("blnc", blnc.getText().toString());
                    editor.apply();
                    dialog.dismiss();

                }else {
                    cttoast("cant empty");
                }

            });

            dialog.show();

        }*/



        loaddata();

        adapter=new categroy_select_adapter(sales_main.this,datalist);
        recyclerView.setLayoutManager(new GridLayoutManager(sales_main.this,2));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);


        adapter.click_categroy(position -> {
            Intent n=new Intent(sales_main.this,salesmain_2.class);
            n.putExtra("ct",datalist.get(position).getName());
            startActivity(n);

        });
    }

    private void cttoast(String cant_empty) {
        Toast.makeText(this,cant_empty,Toast.LENGTH_SHORT).show();
    }

    private void loaddata() {
        Cursor cursor=dbhelper.get_all_categroy();
        datalist.clear();
        while (cursor.moveToNext()){
            @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex(Dbhelper.CATEGROY_NAME_KEY));
            @SuppressLint("Range") String id = cursor.getString(cursor.getColumnIndex(Dbhelper.CATEGROY_ID));
            datalist.add(new uom_model_class(id,name));
        }
        cursor.close();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent=new Intent(this,MainActivity.class);
        startActivity(intent);
    }
}