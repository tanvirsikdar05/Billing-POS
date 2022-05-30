package com.kaiser.kaiserinvoice;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dantsu.escposprinter.EscPosPrinter;
import com.dantsu.escposprinter.connection.bluetooth.BluetoothPrintersConnections;
import com.dantsu.escposprinter.exceptions.EscPosBarcodeException;
import com.dantsu.escposprinter.exceptions.EscPosConnectionException;
import com.dantsu.escposprinter.exceptions.EscPosEncodingException;
import com.dantsu.escposprinter.exceptions.EscPosParserException;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class print_activity extends AppCompatActivity {
    ImageView add,cancel;
    TextView total,vat,grandtotal;
    Button print;
    RecyclerView recyclerView;
    print_adapter adapter;
    ArrayList<print_model_class> datalist=new ArrayList<>();
    Dbhelper dbhelper;
    String shopname="empty",address="empty",vat2 ="0.0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout._print);
        add=findViewById(R.id.add_btn_p);
        cancel=findViewById(R.id.iv_cancel_print);
        total=findViewById(R.id.tv_total);
        vat=findViewById(R.id.tv_vat);
        grandtotal=findViewById(R.id.tv_grandtotal);
        dbhelper=new Dbhelper(print_activity.this);
        print=findViewById(R.id.bt_print);
        recyclerView=findViewById(R.id.print_recyclerview);
        build_recyclerview();
        load_data();




        //handle click here
        add.setOnClickListener(view -> {
            Intent intent=new Intent(print_activity.this,sales_main.class);
            startActivity(intent);
        });
        print.setOnClickListener(view -> {
            try {
                print_here();
            } catch (EscPosConnectionException | EscPosParserException | EscPosBarcodeException | EscPosEncodingException e) {
                e.printStackTrace();
            }
        });
        cancel.setOnClickListener(view -> {
            dbhelper.clear_card();
            Intent intent=new Intent(print_activity.this,MainActivity.class);
            startActivity(intent);

            SharedPreferences.Editor editor = getSharedPreferences("addbl", MODE_PRIVATE).edit();
            editor.putString("blnc", "0");
            editor.apply();
        });




    }

    private void print_here() throws EscPosConnectionException, EscPosEncodingException, EscPosBarcodeException, EscPosParserException {

        BluetoothAdapter mblutooth=BluetoothAdapter.getDefaultAdapter();
        if (mblutooth.isEnabled()){




            EscPosPrinter printer = new EscPosPrinter(BluetoothPrintersConnections.selectFirstPaired(), 203, 56f, 32);
            c_toast("Printing...");
            String currentTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
            String currentDate = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault()).format(new Date());


                printer.printFormattedText("[C]<font size='big'>Invoice</font>\n" +
                                "[L]"+currentDate+
                                "[R]"+currentTime+"\n"+
                                "[C]--------------------------------\n"+
                                "[L]  price |  quan  |  dis |  total\n",2f);
                for (int ll=0;ll<datalist.size();ll++){
                    int counter=ll+1;
                    printer.printFormattedText( "[L]"+counter+"."+datalist.get(ll).getName()+"\n"+
                            "[L]   "+datalist.get(ll).getPrice()+"    "+datalist.get(ll).getTotal_uom()+
                            "     "+datalist.get(ll).getDiscount()+
                            "[R]"+datalist.get(ll).getTotal_price(),1f);
                }

                printer.printFormattedText(
                        "[R]"+total.getText().toString()+"\n"+
                        "[R]"+vat.getText().toString()+"\n"+
                        "[R]"+grandtotal.getText().toString()+"\n"+
                                "[C]"+shopname+"\n"+
                                "[C]"+address+"\n",3f);
                c_toast("done");

                sava_invoice(currentDate,grandtotal.getText().toString());
                dbhelper.clear_card();
                //clear share pep
            SharedPreferences.Editor editor = getSharedPreferences("addbl", MODE_PRIVATE).edit();
            editor.putString("blnc", "0");
            editor.apply();

            Intent intent=new Intent(print_activity.this,MainActivity.class);
            startActivity(intent);

        }else {
                c_toast("Enable bluetooth and connect printer");
        }

    }

    private void sava_invoice(String date,String total) {
        long per_invoice_id=dbhelper.add_data_per_invoice(date,total);
        for (int position=0;position<datalist.size();position++){
           dbhelper.add_invoice_data(date,datalist.get(position).getName(),datalist.get(position).getTotal_uom(),
                   datalist.get(position).getPrice(),datalist.get(position).getDiscount(),datalist.get(position).getTotal_price(),
                   String.valueOf(per_invoice_id));
        }



    }

    public void c_toast(String txt){
        Toast.makeText(print_activity.this,txt,Toast.LENGTH_SHORT).show();
    }
    @SuppressLint("Range")
    private void load_data() {
        double total_amount=0.0;

        Cursor cursor=dbhelper.get_all_card_data();
        datalist.clear();
        while (cursor.moveToNext()){
            String name = cursor.getString(cursor.getColumnIndex(Dbhelper.CARD_NAME_KEY));
            String id = cursor.getString(cursor.getColumnIndex(Dbhelper.CARD_ID));
            String price = cursor.getString(cursor.getColumnIndex(Dbhelper.CARD_PRICE_KEY));
            String discount = cursor.getString(cursor.getColumnIndex(Dbhelper.CARD_DISCOUNT_KEY));
            String totalprice = cursor.getString(cursor.getColumnIndex(Dbhelper.CARD_PRODUCT_PRICE_KEY));
            String uom = cursor.getString(cursor.getColumnIndex(Dbhelper.CARD_UOM_KEY));
            String product_id = cursor.getString(cursor.getColumnIndex(Dbhelper.CARD_ID_p_KEY));
            datalist.add(new print_model_class(id,name,price,totalprice,uom,discount,product_id));
            total_amount=total_amount + Double.parseDouble(totalprice);
        }
        cursor.close();

        //
        Cursor cursor1=dbhelper.get_shop_data();
        while (cursor1.moveToNext()){
            shopname = cursor1.getString(cursor1.getColumnIndex(Dbhelper.SHOP_NAME_KEY));
            address = cursor1.getString(cursor1.getColumnIndex(Dbhelper.SHOP_ADDRESS_KEY));
            vat2 = cursor1.getString(cursor1.getColumnIndex(Dbhelper.SHOP_VAT_KEY));
        }
        cursor1.close();

        // finalvatt=total_p * finalvat / 100;
        Log.i("vat",vat2);
        if (vat2== null || vat2.isEmpty()){
            vat2="0.0";
        }
        double c_vat=Double.parseDouble(vat2);
        double finalvat=total_amount * c_vat /100;


        total.setText("Total: "+ new DecimalFormat("##.###").format(total_amount));
        vat.setText("Vat("+vat2+"%): "+ finalvat);
        grandtotal.setText("grand total: "+new DecimalFormat("##.###").format(total_amount +finalvat));
    }

    private void build_recyclerview() {
        adapter=new print_adapter(datalist,this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(print_activity.this));
        recyclerView.setAdapter(adapter);
    }
}