package com.kaiser.kaiserinvoice;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class card_activity extends AppCompatActivity {
    RecyclerView recyclerView;
    ArrayList<product_model_class> datalist=new ArrayList<>();
    card_adapter adapter;
    Dbhelper dbhelper;
    TextView totalammount;
    ImageView add_product;
    Button cancel,checkut;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout._card);
        recyclerView=findViewById(R.id.card_recycler_view);
        totalammount=findViewById(R.id.total_amount);
        cancel=findViewById(R.id.buttonc);
        checkut=findViewById(R.id.button2);
        add_product=findViewById(R.id.imageView4);
        dbhelper=new Dbhelper(this);
        Load_data_fromdb();
        build_recyclerview();


        //edit product
        adapter.product_edit(position -> {
            edit_product(position);
        });
        add_product.setOnClickListener(view -> {
            Intent intent=new Intent(card_activity.this,sales_main.class);
            startActivity(intent);
        });
        checkut.setOnClickListener(view -> {
            Intent intent=new Intent(card_activity.this,print_activity.class);
            startActivity(intent);
        });
        cancel.setOnClickListener(view -> {
            dbhelper.clear_card();
            Intent intent=new Intent(card_activity.this,MainActivity.class);
            startActivity(intent);

            SharedPreferences.Editor editor = getSharedPreferences("addbl", MODE_PRIVATE).edit();
            editor.putString("blnc", "0");
            editor.apply();

        });
    }

    private void edit_product(int position) {

        Dialog dialog=new Dialog(card_activity.this);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.select_product_dialogue);
        TextView item_name,price_uom,preview_bl,card_bl,first_uom,second_uom;
        EditText kg,gram,discount;
        ImageView up_kg,down_kg,up_gram,down_gram,canceliv;
        CheckBox checkBox;
        Button add,delete;
        item_name=dialog.findViewById(R.id.tv_item_name);
        price_uom=dialog.findViewById(R.id.tv_price_and_um);
        preview_bl=dialog.findViewById(R.id.tv_preview_balance);
        card_bl=dialog.findViewById(R.id.tv_cart_balance);
        first_uom=dialog.findViewById(R.id.tv_first_uom);
        second_uom=dialog.findViewById(R.id.tv_second_uom);

        kg=dialog.findViewById(R.id.et_kg);
        gram=dialog.findViewById(R.id.et_gram);
        discount=dialog.findViewById(R.id.et_discount);

        up_kg=dialog.findViewById(R.id.iv_up_kg);
        down_kg=dialog.findViewById(R.id.iv_down_kg);
        up_gram=dialog.findViewById(R.id.iv_up_gram);
        down_gram=dialog.findViewById(R.id.iv_down_gram);
        canceliv=dialog.findViewById(R.id.cancell_iv);
        checkBox=dialog.findViewById(R.id.cb_percent);

        add=dialog.findViewById(R.id.bt_add);
        delete=dialog.findViewById(R.id.bt_delete);

        //end find section
        delete.setVisibility(View.VISIBLE);
        card_bl.setVisibility(View.GONE);
        //price and uom setup
        String uom=datalist.get(position).getUom();
        String[] uom_name=uom.split("\\.");
        String priceand_uom=datalist.get(position).getPrice()+"tk/"+uom_name[2];
        price_uom.setText(priceand_uom);
        kg.setText(uom_name[0]);
        gram.setText(uom_name[1]);
        first_uom.setText(uom_name[2]);
        item_name.setText(datalist.get(position).getName());
        preview_bl.setText(String.valueOf(datalist.get(position).getDb_id()));
        discount.setText(datalist.get(position).getCategroy());
        add.setText("Update");


        //kg up
        up_kg.setOnClickListener(view -> {
            ///////////////
            int curr_kg=Integer.parseInt(kg.getText().toString());
            kg.setText(String.valueOf(curr_kg+1));


        });
        //kg to price genarate
        meserment mm=new meserment();
        kg.addTextChangedListener(new TextWatcher() {


            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String st_curr_kg=String.valueOf(charSequence);
                if (!st_curr_kg.isEmpty()){
                    double currnt_kg=Double.parseDouble(st_curr_kg);
                    double price=Double.parseDouble(datalist.get(position).getPrice());
                    int increase_val=mm.second_mm_increase(uom_name[2]);
                    double curr_gram2=Double.parseDouble(gram.getText().toString());
                    if (increase_val == 100){
                        double per_gram_price=price / 1000.0;
                        double total_price=(currnt_kg * price) + (curr_gram2 * per_gram_price);
                        if (!discount.getText().toString().isEmpty()){
                            boolean check_bx=checkBox.isChecked();
                            double get_discoutn=Double.parseDouble(discount.getText().toString());
                            if (String.valueOf(check_bx).equals("true")){
                                // yes price get discount with percentage
                                double total_discount=total_price * get_discoutn / 100.0;
                                total_price=total_price - total_discount;
                            }else {
                                // no price get discount with percentage
                                total_price=total_price - get_discoutn;
                            }
                        }
                        preview_bl.setText(String.valueOf(total_price));

                    }else {
                        gram.setEnabled(false);
                        double total_price=(currnt_kg * price);
                        if (!discount.getText().toString().isEmpty()){
                            boolean check_bx=checkBox.isChecked();
                            double get_discoutn=Double.parseDouble(discount.getText().toString());
                            if (String.valueOf(check_bx).equals("true")){
                                // yes price get discount with percentage
                                double total_discount=total_price * get_discoutn / 100.0;
                                total_price=total_price - total_discount;
                            }else {
                                // no price get discount with percentage
                                total_price=total_price - get_discoutn;
                            }
                        }
                        preview_bl.setText(String.valueOf(total_price));
                    }
                }



            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        gram.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String st_curr_gram=String.valueOf(charSequence);
                if (!st_curr_gram.isEmpty()){
                    double currnt_gram=Double.parseDouble(st_curr_gram);
                    double price=Double.parseDouble(datalist.get(position).getPrice());
                    int increase_val=mm.second_mm_increase(uom_name[2]);

                    double currnt_kg=Double.parseDouble(kg.getText().toString());

                    if (increase_val == 100){
                        double per_gram_price=price / 1000.0;
                        double total_price=(currnt_kg * price) + (currnt_gram * per_gram_price);
                        if (!discount.getText().toString().isEmpty()){
                            boolean check_bx=checkBox.isChecked();
                            double get_discoutn=Double.parseDouble(discount.getText().toString());
                            if (String.valueOf(check_bx).equals("true")){
                                // yes price get discount with percentage
                                double total_discount=total_price * get_discoutn / 100.0;
                                total_price=total_price - total_discount;
                            }else {
                                // no price get discount with percentage
                                total_price=total_price - get_discoutn;
                            }
                        }
                        preview_bl.setText(String.valueOf(total_price));

                    }
                }


            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        discount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String st_curr_discount=String.valueOf(charSequence);
                if (!st_curr_discount.isEmpty()){
                    double currnt_discount=Double.parseDouble(st_curr_discount);
                    double price=Double.parseDouble(datalist.get(position).getPrice());
                    int increase_val=mm.second_mm_increase(uom_name[2]);

                    double currnt_kg=Double.parseDouble(kg.getText().toString());
                    double currnt_gram=Double.parseDouble(gram.getText().toString());

                    if (increase_val == 100){
                        double per_gram_price=price / 1000.0;
                        double total_price=(currnt_kg * price) + (currnt_gram * per_gram_price);
                        if (!discount.getText().toString().isEmpty()){
                            boolean check_bx=checkBox.isChecked();
                            //double get_discoutn=Double.parseDouble(discount.getText().toString());
                            if (String.valueOf(check_bx).equals("true")){
                                // yes price get discount with percentage
                                double total_discount=total_price * currnt_discount / 100.0;
                                total_price=total_price - total_discount;
                            }else {
                                // no price get discount with percentage
                                total_price=total_price - currnt_discount;
                            }
                        }
                        preview_bl.setText(String.valueOf(total_price));

                    }else {
                        double total_price=(currnt_kg * price);
                        if (!discount.getText().toString().isEmpty()){
                            boolean check_bx=checkBox.isChecked();
                            //double get_discoutn=Double.parseDouble(discount.getText().toString());
                            if (String.valueOf(check_bx).equals("true")){
                                // yes price get discount with percentage
                                double total_discount=total_price * currnt_discount / 100.0;
                                total_price=total_price - total_discount;
                            }else {
                                // no price get discount with percentage
                                total_price=total_price - currnt_discount;
                            }
                        }
                        preview_bl.setText(String.valueOf(total_price));
                    }
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        //kg down
        down_kg.setOnClickListener(view -> {
            ///////////
            int curr_kg=Integer.parseInt(kg.getText().toString());
            if (curr_kg >= 1){
                kg.setText(String.valueOf(curr_kg-1));

            }
        });



        String second_mm=mm.second_mm(uom_name[2]);
        if (!second_mm.equals("0")){
            second_uom.setText(second_mm);
            //up gram
            up_gram.setOnClickListener(view -> {
                //////////
                int increase_val=mm.second_mm_increase(uom_name[2]);
                int curr_gram=Integer.parseInt(gram.getText().toString());
                gram.setText(String.valueOf(curr_gram+increase_val));

            });
            //down gram
            down_gram.setOnClickListener(view -> {
                ////////////
                int increase_val=mm.second_mm_increase(uom_name[2]);
                int curr_gram=Integer.parseInt(gram.getText().toString());
                if (curr_gram >= increase_val){
                    gram.setText(String.valueOf(curr_gram - increase_val));
                }
            });
        }
        //end meserment

        canceliv.setOnClickListener(view -> {
            dialog.dismiss();
        });
        //add button here
        add.setOnClickListener(view -> {
            String decoroded_uom=kg.getText().toString()+"."+gram.getText().toString()+"."+first_uom.getText().toString();
            String decoroded_discount="0.00";
            if (String.valueOf(checkBox.isChecked()).equals("true")){
                decoroded_discount= discount.getText().toString();
            }else decoroded_discount=discount.getText().toString();
          /////////////////update database///
            String prev_blnc=new DecimalFormat("##.###").format(Double.parseDouble(preview_bl.getText().toString()));
            dbhelper.update_card(datalist.get(position).getId(),item_name.getText().toString(),decoroded_uom,datalist.get(position)
            .getPrice(),prev_blnc,discount.getText().toString());

            datalist.get(position).setUom(decoroded_uom);
            datalist.get(position).setDb_id(preview_bl.getText().toString());
            datalist.get(position).setCategroy(discount.getText().toString());
            adapter.notifyDataSetChanged();


            dialog.dismiss();
        });
        delete.setOnClickListener(view -> {
            dbhelper.delete_one_data_card(datalist.get(position).getId());
            adapter.notifyItemRemoved(position);
            dialog.dismiss();
        });
        dialog.show();
    }

    private void Load_data_fromdb() {
        double total_amount=0.0;
        Cursor cursor=dbhelper.get_all_card_data();
        datalist.clear();
        while (cursor.moveToNext()){
            @SuppressLint("Range") String id = cursor.getString(cursor.getColumnIndex(Dbhelper.CARD_ID));
            @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex(Dbhelper.CARD_NAME_KEY));
            @SuppressLint("Range") String price = cursor.getString(cursor.getColumnIndex(Dbhelper.CARD_PRICE_KEY));
            @SuppressLint("Range") String discount = cursor.getString(cursor.getColumnIndex(Dbhelper.CARD_DISCOUNT_KEY));
            @SuppressLint("Range") String totalprice = cursor.getString(cursor.getColumnIndex(Dbhelper.CARD_PRODUCT_PRICE_KEY));
            @SuppressLint("Range") String uom = cursor.getString(cursor.getColumnIndex(Dbhelper.CARD_UOM_KEY));
            datalist.add(new product_model_class(id,totalprice,name,discount,uom,price));
            total_amount=total_amount + Double.parseDouble(totalprice);
        }
        cursor.close();
        totalammount.setText(new DecimalFormat("##.###").format(total_amount));


    }

    private void build_recyclerview() {
        adapter=new card_adapter(this,datalist);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

    }
}