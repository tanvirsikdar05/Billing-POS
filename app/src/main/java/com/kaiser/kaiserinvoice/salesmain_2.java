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
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class salesmain_2 extends AppCompatActivity {
    RecyclerView recyclerView;
    ArrayList<product_model_class> datalist=new ArrayList<>();
    product_select_adapter adapter;
    Dbhelper dbhelper;
    TextView card_counter,card_bl,remain_bl;
    ImageView select_categroy;
    LinearLayout checkout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.salesmain2);
        String categroy_name=getIntent().getStringExtra("ct");
        dbhelper=new Dbhelper(salesmain_2.this);
        card_counter=findViewById(R.id.card_product_counter);
        card_bl=findViewById(R.id.card_bl);
        remain_bl=findViewById(R.id.remain_bl);
        checkout=findViewById(R.id.checkout);
        select_categroy=findViewById(R.id.imageView2);
        build_recyclerview();
        Load_card_number();
        add_data_inrecyclerview(categroy_name);


        SharedPreferences prefs = getSharedPreferences("addbl", MODE_PRIVATE);
        String name = prefs.getString("blnc", "0");
        if ( name.equals("0")){
            card_bl.setVisibility(View.GONE);
            remain_bl.setVisibility(View.GONE);
        }else {
            settle_blnc(name);
        }


        adapter.select_onclick(position -> {
              // item click here
            build_dialog(position);
        });
        select_categroy.setOnClickListener(view -> {
            Intent intent=new Intent(salesmain_2.this,sales_main.class);
            startActivity(intent);
        });
        checkout.setOnClickListener(view -> {
            if (card_counter.getText().toString().equals("0")){
                ctoast("no item selected");
            }else {
                Intent intent=new Intent(salesmain_2.this,card_activity.class);
                startActivity(intent);

            }

        });
    }

    private void settle_blnc(String name) {
        card_bl.setVisibility(View.VISIBLE);
        remain_bl.setVisibility(View.VISIBLE);
        double total_amount=0.0;
        Cursor cursor=dbhelper.get_all_card_data();
        while (cursor.moveToNext()){

            @SuppressLint("Range") String totalprice = cursor.getString(cursor.getColumnIndex(Dbhelper.CARD_PRODUCT_PRICE_KEY));
            total_amount=total_amount + Double.parseDouble(totalprice);
        }
        cursor.close();
        card_bl.setText(new DecimalFormat("##.###").format(total_amount));
        double add_bl=Double.parseDouble(name);
        double ttl=add_bl - total_amount;
        remain_bl.setText(new DecimalFormat("##.###").format(ttl));
    }

    private void Load_card_number() {
        long number=dbhelper.card_numbers();
        card_counter.setText(String.valueOf(number));
    }

    private void build_dialog(int position) {

        Dialog dialog=new Dialog(salesmain_2.this);
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
        delete.setVisibility(View.GONE);
        card_bl.setVisibility(View.GONE);
        //price and uom setup
        String priceand_uom=datalist.get(position).getPrice()+"tk/"+datalist.get(position).getUom();
        price_uom.setText(priceand_uom);

        item_name.setText(datalist.get(position).getName());
        preview_bl.setText("0");
        add.setText("Add");


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
                    int increase_val=mm.second_mm_increase(datalist.get(position).getUom());
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
                    int increase_val=mm.second_mm_increase(datalist.get(position).getUom());

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
                    int increase_val=mm.second_mm_increase(datalist.get(position).getUom());

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
        //mesurment
        first_uom.setText(datalist.get(position).getUom());

        meserment mm1=new meserment();
        String second_mm=mm.second_mm(datalist.get(position).getUom());
        if (!second_mm.equals("0")){
            second_uom.setText(second_mm);
            //up gram
            up_gram.setOnClickListener(view -> {
                //////////
                int increase_val=mm.second_mm_increase(datalist.get(position).getUom());
                int curr_gram=Integer.parseInt(gram.getText().toString());
                gram.setText(String.valueOf(curr_gram+increase_val));

            });
            //down gram
            down_gram.setOnClickListener(view -> {
                ////////////
                int increase_val=mm.second_mm_increase(datalist.get(position).getUom());
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
            String decoroded_uom;
            if (gram.getText().toString().equals("000")){
                 decoroded_uom=kg.getText().toString()+"."+"00"+"."+datalist.get(position).getUom();
            }else {
                decoroded_uom=kg.getText().toString()+"."+gram.getText().toString()+"."+datalist.get(position).getUom();
            }

            String decoroded_discount="0.00";
            if (String.valueOf(checkBox.isChecked()).equals("true")){
                decoroded_discount= discount.getText().toString();
            }else decoroded_discount=discount.getText().toString();

            /////////

            String prev_blnc=new DecimalFormat("##.###").format(Double.parseDouble(preview_bl.getText().toString()));
            dbhelper.add_card_data(datalist.get(position).getName(),decoroded_uom,datalist.get(position).getPrice(),
                    decoroded_discount,prev_blnc,datalist.get(position).getId());
            int counter=Integer.parseInt(card_counter.getText().toString());
            card_counter.setText(String.valueOf(counter + 1));
            update_blance(preview_bl.getText().toString());
            adapter.notifyDataSetChanged();

            dialog.dismiss();
        });
        dialog.show();
    }

    private void update_blance(String jj) {

        SharedPreferences prefs = getSharedPreferences("addbl", MODE_PRIVATE);
        String name = prefs.getString("blnc", "0");

        if (name.equals("0")){

        }else {
            double up_blance=Double.parseDouble(jj);
            double add_bl=Double.parseDouble(name);
            double card_tt=Double.parseDouble(card_bl.getText().toString());

            double updt_card=card_tt + up_blance;
            card_bl.setText(new DecimalFormat("##.###").format(updt_card) );
            double remainbln=add_bl - updt_card;
            remain_bl.setText(new DecimalFormat("##.###").format(remainbln));
        }
    }

    @SuppressLint("Range")
    private void add_data_inrecyclerview(String categroy_name) {
        Cursor cursor= dbhelper.get_all_product();
        datalist.clear();
        while (cursor.moveToNext()){
            String categroy = cursor.getString(cursor.getColumnIndex(Dbhelper.PRODUCT_CATEGROY_KEY));
            if (categroy.equals(categroy_name)){
                String id = cursor.getString(cursor.getColumnIndex(Dbhelper.PRODUCT_ID));
                String name = cursor.getString(cursor.getColumnIndex(Dbhelper.PRODUCT_NAME_KEY));
                String uom = cursor.getString(cursor.getColumnIndex(Dbhelper.PRODUCT_UOM_KEY));
                String price = cursor.getString(cursor.getColumnIndex(Dbhelper.PRODUCT_PRICE_KEY));
                datalist.add(new product_model_class(String.valueOf(datalist.size()+1),id,name,
                        categroy,uom,price));
            }
        }
        cursor.close();

    }

    private void build_recyclerview() {
        recyclerView=findViewById(R.id.item_select_recyclerview);
        adapter=new product_select_adapter(this,datalist);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(salesmain_2.this,3));
        recyclerView.setAdapter(adapter);
    }

    public void ctoast(String txt){
        Toast.makeText(salesmain_2.this,txt,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent=new Intent(this,sales_main.class);
        startActivity(intent);
    }
}