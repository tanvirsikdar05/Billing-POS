package com.kaiser.kaiserinvoice;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;

import java.util.ArrayList;
import java.util.List;

public class per_invoice extends AppCompatActivity {
    per_invoice_adapter adapter;
    ArrayList<perinvoice_model_class> datalist=new ArrayList<>();
    RecyclerView recyclerView;
    Dbhelper dbhelper;
    androidx.appcompat.widget.SearchView serchbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout._per_invoice);
        recyclerView=findViewById(R.id.per_inv_recyclerview);
        serchbar=findViewById(R.id.serachbar);
        dbhelper=new Dbhelper(per_invoice.this);
        build_recyclerview();
        Load_data();


        serchbar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filter(newText);
                return true;
            }
        });

        adapter.select_invoice(position -> {
            Intent intent=new Intent(per_invoice.this,per_invoice_2.class);
            intent.putExtra("id",datalist.get(position).getId());
            intent.putExtra("date",datalist.get(position).getDate());
            intent.putExtra("total",datalist.get(position).getTotal());
            startActivity(intent);
        });

    }

    private void filter(String s) {

        ArrayList<perinvoice_model_class> filterlist=new ArrayList<>();
        for (perinvoice_model_class item : datalist){
            if (item.getId().toLowerCase().contains(s.toLowerCase()) || item.getDate().toLowerCase().contains(s.toLowerCase())){
                filterlist.add(item);
            }
            adapter.filterlist(filterlist);
        }
    }

    @SuppressLint("Range")
    private void Load_data() {

        Cursor cursor=dbhelper.get_all_perinvoice();
        datalist.clear();
        while (cursor.moveToNext()){
           String id = cursor.getString(cursor.getColumnIndex(Dbhelper.PERINVOICE_ID));
           String date = cursor.getString(cursor.getColumnIndex(Dbhelper.PERINVOICE_DATE_KEY));
           String total = cursor.getString(cursor.getColumnIndex(Dbhelper.PERINVOICE_TOTAL_KEY));
            datalist.add(new perinvoice_model_class(id,date,total));
        }
        cursor.close();
    }

    private void build_recyclerview() {
        adapter=new per_invoice_adapter(this,datalist);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }


}