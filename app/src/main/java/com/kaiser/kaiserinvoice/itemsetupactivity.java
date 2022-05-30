package com.kaiser.kaiserinvoice;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class itemsetupactivity extends AppCompatActivity {
    FloatingActionButton addproduct;
    RecyclerView recyclerView;
    product_adapter adapter;
    ArrayList<product_model_class> datalist=new ArrayList<>();
    ArrayList<String> categroy=new ArrayList<>();
    ArrayList<String> type=new ArrayList<>();
    ArrayAdapter<String> spiner_categroy_adapter,spiner_uom_adapter;
    Dbhelper dbhelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.temsetupactivity);
        addproduct=findViewById(R.id.product_add_flot);
        recyclerView=findViewById(R.id.add_product_recyclerview);
        dbhelper=new Dbhelper(itemsetupactivity.this);
        load_product();
        buildrecyclerview();
       // load_categroy();
        //Load_uom();



        //update product
        adapter.set_product_edit(position -> {
            Dialog dialog=new Dialog(this);
            dialog.setContentView(R.layout.add_product_dialog);
            EditText id=dialog.findViewById(R.id.addproduct_id);
            EditText name=dialog.findViewById(R.id.addproduct_name);
            EditText price=dialog.findViewById(R.id.addproduct_price);
            Button save_button=dialog.findViewById(R.id.addproduct_save_up);
            Button delete_button=dialog.findViewById(R.id.addproduct_delete);
            Spinner categroy_txtview = dialog.findViewById(R.id.addproduct_categroy);
            Spinner autocomplete_txtview = dialog.findViewById(R.id.addproduct_uom);
            //load type
            Cursor cursor=dbhelper.get_all_uom();
            type.clear();
            while (cursor.moveToNext()){
                @SuppressLint("Range") String type_name = cursor.getString(cursor.getColumnIndex(Dbhelper.UOM_NAME_KEY));
                type.add(type_name);
            }
            cursor.close();
            //end

            // load categroy
            Cursor cursor2=dbhelper.get_all_categroy();
            categroy.clear();
            while (cursor2.moveToNext()){
                @SuppressLint("Range") String ct_name = cursor2.getString(cursor2.getColumnIndex(Dbhelper.CATEGROY_NAME_KEY));
                categroy.add(ct_name);
            }
            cursor.close();
            //end

            meserment Mesarment=new meserment();

            //decoration button
            save_button.setText("Update");
            delete_button.setVisibility(View.VISIBLE);

            id.setText(datalist.get(position).getId());
            name.setText(datalist.get(position).getName());
            price.setText(datalist.get(position).getPrice());









            spiner_uom_adapter=new ArrayAdapter<>(itemsetupactivity.this,R.layout.spinnerexdesign,
                    R.id.txtshower,type);
            autocomplete_txtview.setAdapter(spiner_uom_adapter);
            autocomplete_txtview.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });




            //dropdown categroy
            spiner_categroy_adapter=new ArrayAdapter<>(itemsetupactivity.this,R.layout.spinnerexdesign,
                    R.id.txtshower,categroy);
            categroy_txtview.setAdapter(spiner_categroy_adapter);

            //initial spinner data
            autocomplete_txtview.setSelection(type.indexOf(datalist.get(position).getUom()));
            categroy_txtview.setSelection(categroy.indexOf(datalist.get(position).getCategroy()));

            //update data here **
            save_button.setOnClickListener(view1 ->{
                if (!id.getText().toString().isEmpty() || name.getText().toString().isEmpty() || price.getText().toString().isEmpty()){
                 
                      dbhelper.update_product(datalist.get(position).getDb_id(),name.getText().toString(),autocomplete_txtview.getSelectedItem().toString(),
                              price.getText().toString(),categroy_txtview.getSelectedItem().toString());
                    datalist.get(position).setId(id.getText().toString());
                    datalist.get(position).setName(name.getText().toString());
                    datalist.get(position).setPrice(price.getText().toString());

                    datalist.get(position).setCategroy(categroy_txtview.getSelectedItem().toString());
                    datalist.get(position).setUom(autocomplete_txtview.getSelectedItem().toString());
                     adapter.notifyItemChanged(position);
                    dialog.dismiss();
                }else {
                    c_toast("field cannot be Empty");
                }
            } );
            //delete product
            delete_button.setOnClickListener(view -> {

                dbhelper.delete_product(datalist.get(position).getDb_id());
                datalist.remove(position);
                adapter.notifyItemRemoved(position);
                dialog.dismiss();
            });

            dialog.show();

        });

        addproduct.setOnClickListener(view -> {

            Dialog dialog=new Dialog(this);
            dialog.setContentView(R.layout.add_product_dialog);
            EditText id=dialog.findViewById(R.id.addproduct_id);

            EditText name=dialog.findViewById(R.id.addproduct_name);
            EditText price=dialog.findViewById(R.id.addproduct_price);
            Button save_button=dialog.findViewById(R.id.addproduct_save_up);
            Button delete_button=dialog.findViewById(R.id.addproduct_delete);
            Spinner categroy_txtview = dialog.findViewById(R.id.addproduct_categroy);
            Spinner autocomplete_txtview = dialog.findViewById(R.id.addproduct_uom);

            //load type
            Cursor cursor=dbhelper.get_all_uom();
            type.clear();
            while (cursor.moveToNext()){
                @SuppressLint("Range") String type_name = cursor.getString(cursor.getColumnIndex(Dbhelper.UOM_NAME_KEY));
                type.add(type_name);
            }
            cursor.close();

            // load categroy
            Cursor cursor2=dbhelper.get_all_categroy();
            categroy.clear();
            while (cursor2.moveToNext()){
                @SuppressLint("Range") String ct_name = cursor2.getString(cursor2.getColumnIndex(Dbhelper.CATEGROY_NAME_KEY));
                categroy.add(ct_name);
            }
            cursor.close();

            //decoration button
             save_button.setText("Save");
            delete_button.setVisibility(View.GONE);

            id.setText(String.valueOf(datalist.size()+1));
            meserment Mesarment=new meserment();




            //dropdown uom
            spiner_uom_adapter=new ArrayAdapter<>(itemsetupactivity.this,R.layout.spinnerexdesign,
                    R.id.txtshower,type);
            autocomplete_txtview.setAdapter(spiner_uom_adapter);

            autocomplete_txtview.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });




            //dropdown categroy
            spiner_categroy_adapter=new ArrayAdapter<>(itemsetupactivity.this,R.layout.spinnerexdesign,
                    R.id.txtshower,categroy);
            categroy_txtview.setAdapter(spiner_categroy_adapter);





            save_button.setOnClickListener(view1 ->{
                if (!id.getText().toString().isEmpty() && !name.getText().toString().isEmpty() && !price.getText().toString().isEmpty()){



                    long db_id=dbhelper.add_product_data(name.getText().toString(),autocomplete_txtview.getSelectedItem().toString(),
                            price.getText().toString(), categroy_txtview.getSelectedItem().toString());
                    datalist.add(new product_model_class(id.getText().toString(),String.valueOf(db_id),name.getText().toString(),
                            categroy_txtview.getSelectedItem().toString(),autocomplete_txtview.getSelectedItem().toString(),price.getText().toString()
                            ));
                    adapter.notifyDataSetChanged();
                    dialog.dismiss();
                }else {
                    c_toast("field cannot be Empty");
                }
            } );
            dialog.show();
        });

    }

    private void load_product() {
        Cursor cursor=dbhelper.get_all_product();
        datalist.clear();
        while (cursor.moveToNext()){
            @SuppressLint("Range") String db_id = cursor.getString(cursor.getColumnIndex(Dbhelper.PRODUCT_ID));
            @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex(Dbhelper.PRODUCT_NAME_KEY));
            @SuppressLint("Range") String categroy = cursor.getString(cursor.getColumnIndex(Dbhelper.PRODUCT_CATEGROY_KEY));
            @SuppressLint("Range") String price = cursor.getString(cursor.getColumnIndex(Dbhelper.PRODUCT_PRICE_KEY));
            @SuppressLint("Range") String Uom = cursor.getString(cursor.getColumnIndex(Dbhelper.PRODUCT_UOM_KEY));

            datalist.add(new product_model_class(String.valueOf(datalist.size()+1),db_id,name,
                    categroy,Uom,price));

        }
        cursor.close();

    }

    private void Load_uom() {

        Cursor cursor=dbhelper.get_all_uom();
        type.clear();
        while (cursor.moveToNext()){
            @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex(Dbhelper.UOM_NAME_KEY));
            type.add(name);
        }
        cursor.close();


    }

    private void load_categroy() {
        //load categroy from database
        Cursor cursor=dbhelper.get_all_categroy();
        categroy.clear();
        while (cursor.moveToNext()){
            @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex(Dbhelper.CATEGROY_NAME_KEY));
            categroy.add(name);
        }
        cursor.close();
    }

    private void buildrecyclerview() {
        adapter=new product_adapter(datalist,this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }
    public void c_toast(String txt){
        Toast.makeText(this,txt,Toast.LENGTH_SHORT).show();
    }
}