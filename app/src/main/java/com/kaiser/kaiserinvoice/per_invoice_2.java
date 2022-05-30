package com.kaiser.kaiserinvoice;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.dantsu.escposprinter.EscPosPrinter;
import com.dantsu.escposprinter.connection.bluetooth.BluetoothPrintersConnections;
import com.dantsu.escposprinter.exceptions.EscPosBarcodeException;
import com.dantsu.escposprinter.exceptions.EscPosConnectionException;
import com.dantsu.escposprinter.exceptions.EscPosEncodingException;
import com.dantsu.escposprinter.exceptions.EscPosParserException;

import java.util.ArrayList;

public class per_invoice_2 extends AppCompatActivity {
    TextView no,dat,totl;
    RecyclerView recyclerView;
    ArrayList<print_model_class> datalist=new ArrayList<>();
    print_adapter adapter;
    Dbhelper dbhelper;
    Button print;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout._per_invoice2);
        no=findViewById(R.id.inv_no);
        recyclerView=findViewById(R.id.perinvoice2_rec);
        dat=findViewById(R.id.inv_date);
        print=findViewById(R.id.print_f);
        dbhelper=new Dbhelper(per_invoice_2.this);
        totl=findViewById(R.id.inv_totalf);

        String id=getIntent().getStringExtra("id");
        String date=getIntent().getStringExtra("date");
        String total=getIntent().getStringExtra("total");
        no.setText(id);
        dat.setText(date);
        totl.setText(total);

        build_recyclerview();
        load_data(id);

        print.setOnClickListener(view -> {
            try {
                print_pvoice();
            } catch (EscPosConnectionException | EscPosEncodingException | EscPosBarcodeException | EscPosParserException e) {
                e.printStackTrace();
            }

        });

    }

    private void print_pvoice() throws EscPosConnectionException, EscPosEncodingException, EscPosBarcodeException, EscPosParserException {
        ///print here

        if (datalist.size() > 0){
            BluetoothAdapter mblutooth=BluetoothAdapter.getDefaultAdapter();
            if (mblutooth.isEnabled()){
                EscPosPrinter printer = new EscPosPrinter(BluetoothPrintersConnections.selectFirstPaired(), 203, 56f, 32);
                ctoats("Printing...");


                printer.printFormattedText("[C]<font size='big'>per invoice</font>\n" +
                        "[L] #inv"+no.getText().toString()+"\n"+
                        "[R]Date: "+dat.getText().toString()+"\n"+
                        "[C]--------------------------------\n"+
                        "[L]  price |  quan  |  dis |  total\n",2f);
                for (int ll=0;ll<datalist.size();ll++){
                    int counter=ll+1;
                    printer.printFormattedText(
                            "[L]"+counter+"."+datalist.get(ll).getName()+"\n"+
                                    "[L]   "+datalist.get(ll).getPrice()+"    "+datalist.get(ll).getTotal_uom()+
                                    "     "+datalist.get(ll).getDiscount()+
                                    "[R]"+datalist.get(ll).getTotal_price(),2f);
                }
                printer.printFormattedText("[R]"+totl.getText().toString(),3f);
                ctoats("done");

            }else {
                ctoats("Enable blutooth and connect printer");
            }

        }else {
            ctoats("No record found");
        }
    }

    private void ctoats(String s) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }

    @SuppressLint("Range")
    private void load_data(String id) {
        Cursor cursor=dbhelper.get_all_invoice();
        datalist.clear();
        while (cursor.moveToNext()){
             String uniqid = cursor.getString(cursor.getColumnIndex(Dbhelper.INVOICE_UNIQ_INV));
             if (uniqid.equals(id)){
                  String name = cursor.getString(cursor.getColumnIndex(Dbhelper.INVOICE_NAME_KEY));
                  String id2 = cursor.getString(cursor.getColumnIndex(Dbhelper.INVOICE_ID));
                  String price = cursor.getString(cursor.getColumnIndex(Dbhelper.INVOICE_PRICE_KEY));
                  String discount = cursor.getString(cursor.getColumnIndex(Dbhelper.INVOICE_DISCOUNT_KEY));
                  String totalprice = cursor.getString(cursor.getColumnIndex(Dbhelper.INVOICE_TOTAL_PRICE_KEY));
                  String uom = cursor.getString(cursor.getColumnIndex(Dbhelper.INVOICE_UOM_KEY));
                  String uniq_id = cursor.getString(cursor.getColumnIndex(Dbhelper.INVOICE_UNIQ_INV));
                  datalist.add(new print_model_class(id2,name,price,totalprice,uom,discount,uniq_id));
             }

        }
        cursor.close();
    }

    private void build_recyclerview() {
        adapter=new print_adapter(datalist,per_invoice_2.this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(per_invoice_2.this));
        recyclerView.setAdapter(adapter);
    }
}