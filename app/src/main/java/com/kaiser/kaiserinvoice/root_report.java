package com.kaiser.kaiserinvoice;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class root_report extends AppCompatActivity {
    TextView date_show,Total_show;
    ImageView ivleft,ivright;
    ArrayList<print_model_class> datalist=new ArrayList<>();
    print_adapter adapter;
    Dbhelper dbhelper;
    RecyclerView recyclerView;
    Button printff;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout._root_report);
        date_show=findViewById(R.id.date_show);
        Total_show=findViewById(R.id.tt);
        ivleft=findViewById(R.id.ivleft);
        ivright=findViewById(R.id.ivright);
        printff=findViewById(R.id.print_ff);
        recyclerView=findViewById(R.id.res_id);

        Build_recyclerview();
        dbhelper=new Dbhelper(root_report.this);
        String currentDate = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault()).format(new Date());
        date_show.setText(currentDate);
        Load_data(currentDate);

        //String[] date=currentDate.split("-");


      // String check=getIntent().getStringExtra("cc");

      // if (check.equals("d")){
      //     //daily report


      // }else if (check.equals("m")){
      //     //monthly report
      // }
      // Build_recyclerview();
      // Load_data(check);


        ivright.setOnClickListener(view -> {

            //increase


            try {
                String date2 = up_date(date_show.getText().toString());
                date_show.setText(date2);
                Load_data(date2);
            } catch (ParseException e) {
                e.printStackTrace();
            }


        });
        ivleft.setOnClickListener(view -> {
            //decrease
            try {
                String date2 = previous_date(date_show.getText().toString());
                date_show.setText(date2);
                Load_data(date2);
            } catch (ParseException e) {
                e.printStackTrace();
            }


        });

        printff.setOnClickListener(view -> {
            try {
                print_data();
            } catch (EscPosConnectionException | EscPosEncodingException | EscPosBarcodeException | EscPosParserException e) {
                e.printStackTrace();
            }
        });







    }

    private void print_data() throws EscPosConnectionException, EscPosEncodingException, EscPosBarcodeException, EscPosParserException {

        if (datalist.size() > 0){

            BluetoothAdapter mblutooth=BluetoothAdapter.getDefaultAdapter();
            if (mblutooth.isEnabled()){
                EscPosPrinter printer = new EscPosPrinter(BluetoothPrintersConnections.selectFirstPaired(), 203, 56f, 32);
                ctoats("Printing...");


                printer.printFormattedText("[C]<font size='big'>Daily report</font>\n" +
                        "[L]"+date_show.getText().toString()+"\n"+
                        "[C]--------------------------------\n"+
                        "[L]  price |  quan  |  dis |  total\n",2f);
                for (int ll=0;ll<datalist.size();ll++){
                    int counter=ll+1;
                    printer.printFormattedText(
                            "[L]"+counter+"."+datalist.get(ll).getName()+"\n"+
                                    "[L]   "+datalist.get(ll).getPrice()+"    "+datalist.get(ll).getTotal_uom()+
                                    "     "+datalist.get(ll).getDiscount()+
                                    "[R]"+datalist.get(ll).getTotal_price(),1f);
                }
                printer.printFormattedText("[R]"+Total_show.getText().toString(),3f);
                ctoats("done");

            }else {
                ctoats("Enable blutooth and connect printer");
            }

        }else {
            ctoats("No record found");
        }
    }

    @SuppressLint("Range")
    private void Load_data(String date) {
        Cursor cursor=dbhelper.get_all_invoice();
        double ttl=0.0;
        datalist.clear();
        while (cursor.moveToNext()){
            String uniqid = cursor.getString(cursor.getColumnIndex(Dbhelper.INVOICE_DATE_KEY));
            if (uniqid.equals(date)){
                String name = cursor.getString(cursor.getColumnIndex(Dbhelper.INVOICE_NAME_KEY));
                String id = cursor.getString(cursor.getColumnIndex(Dbhelper.INVOICE_ID));
                String price = cursor.getString(cursor.getColumnIndex(Dbhelper.INVOICE_PRICE_KEY));
                String discount = cursor.getString(cursor.getColumnIndex(Dbhelper.INVOICE_DISCOUNT_KEY));
                String totalprice = cursor.getString(cursor.getColumnIndex(Dbhelper.INVOICE_TOTAL_PRICE_KEY));
                String uom = cursor.getString(cursor.getColumnIndex(Dbhelper.INVOICE_UOM_KEY));
                String uniq_id = cursor.getString(cursor.getColumnIndex(Dbhelper.INVOICE_UNIQ_INV));
                datalist.add(new print_model_class(id,name,price,totalprice,uom,discount,uniq_id));
                ttl=ttl+Double.parseDouble(totalprice);
            }

        }
        cursor.close();
        adapter.notifyDataSetChanged();
        Total_show.setText("grand Total: "+new DecimalFormat("##.###").format(ttl));



    }

    private void Build_recyclerview() {
        adapter=new print_adapter(datalist,root_report.this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }
    public static String up_date(String current) throws ParseException {

        SimpleDateFormat dtfrm = new SimpleDateFormat("dd-MMM-yyyy");
        Date d = dtfrm.parse(current);
       Calendar cal = Calendar.getInstance();
       cal.setTime(d);
       cal.add(Calendar.DAY_OF_MONTH, 1);
        String nxt_date=dtfrm.format(cal.getTime());
       return nxt_date;
    }
    public static String previous_date(String current) throws ParseException {

        SimpleDateFormat dtfrm = new SimpleDateFormat("dd-MMM-yyyy");
        Date d = dtfrm.parse(current);
        Calendar cal = Calendar.getInstance();
        cal.setTime(d);
        cal.add(Calendar.DAY_OF_MONTH, -1);
        String nxt_date=dtfrm.format(cal.getTime());
        return nxt_date;
    }
    public void ctoats(String txt){
        Toast.makeText(this,txt,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent=new Intent(root_report.this,all_report.class);
        startActivity(intent);

    }
}