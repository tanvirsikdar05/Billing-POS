package com.kaiser.kaiserinvoice;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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

public class monthly_report extends AppCompatActivity {
    TextView date_show,grandttl;
    Button print;
    ImageView left,right,delete_record;
    ArrayList<print_model_class> datalist=new ArrayList<>();
    print_adapter adapter;
    Dbhelper dbhelper;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout._monthly_report);
        date_show=findViewById(R.id.tv_date_show);
        grandttl=findViewById(R.id.tv_grand_total);
        print=findViewById(R.id.btn_print_ff);
        left=findViewById(R.id.ivleft2);
        delete_record=findViewById(R.id.delete_record);
        right=findViewById(R.id.ivright2);

        recyclerView=findViewById(R.id.res_id2);
        Build_recyclerview();
        dbhelper=new Dbhelper(monthly_report.this);
        String currentDate = new SimpleDateFormat("MMM-yyyy", Locale.getDefault()).format(new Date());
       date_show.setText(currentDate);
       Load_data(currentDate);
       delete_record.setVisibility(View.GONE);
       //delete previous month record
       delete_record.setOnClickListener(view -> {
           String perin="0";
           if (datalist.size() > 0){
               for (int cn=0;cn<datalist.size();cn++){
                   String id=datalist.get(cn).getId();
                   String uniq_id=datalist.get(cn).getUni_id();
                   dbhelper.delete_invoice(id);
                   if (!perin.equals(uniq_id)){
                       dbhelper.delete_per_invoice(uniq_id);
                       perin=uniq_id;
                   }
               }
               datalist.clear();
               adapter.notifyDataSetChanged();
               ctoats("Delete successful");
           }else {
               ctoats("no record found");
           }
       });

       right.setOnClickListener(view -> {
           try {
               String month=up_month(date_show.getText().toString());
               date_show.setText(month);
               Load_data(month);
               month_compare(currentDate);
           } catch (ParseException e) {
               e.printStackTrace();
           }
       });

       left.setOnClickListener(view -> {

           try {
               String month=previous_month(date_show.getText().toString());
               date_show.setText(month);
               Load_data(month);
               month_compare(currentDate);
           } catch (ParseException e) {
               e.printStackTrace();
           }

       });

       print.setOnClickListener(view -> {

           try {
               print_report();
           } catch (EscPosEncodingException | EscPosBarcodeException | EscPosParserException | EscPosConnectionException e) {
               e.printStackTrace();
           }
       });


    }

    private void month_compare(String currentDate) throws ParseException {

        SimpleDateFormat sdf = new SimpleDateFormat("MMM-yyyy");
        Date strDate = sdf.parse(date_show.getText().toString());
        if (System.currentTimeMillis() > strDate.getTime()) {
          if (currentDate.equals(date_show.getText().toString())){
              //false
              delete_record.setVisibility(View.GONE);
          }else {
              //true
              //delete record here
              delete_record.setVisibility(View.VISIBLE);
          }
        }
        else{

            delete_record.setVisibility(View.GONE);
        }

    }

    private void print_report() throws EscPosEncodingException, EscPosBarcodeException, EscPosParserException, EscPosConnectionException {

        if (datalist.size() > 0){

            BluetoothAdapter mblutooth=BluetoothAdapter.getDefaultAdapter();
            if (mblutooth.isEnabled()){
                EscPosPrinter printer = new EscPosPrinter(BluetoothPrintersConnections.selectFirstPaired(), 203, 56f, 32);
                ctoats("Printing...");

                printer.printFormattedText("[C]<font size='big'>Monthly report</font>\n" +
                        "[L]"+date_show.getText().toString()+"\n"+
                        "[C]--------------------------------\n"+
                        "[L]  price |  quan  |  dis |  total\n",2f);
                for (int ll=0;ll<datalist.size();ll++){
                    int counter=ll+1;
                    printer.printFormattedText(
                            "[L]"+counter+"."+datalist.get(ll).getName()+"\n"+
                                    "[L]   "+datalist.get(ll).getPrice()+"   "+datalist.get(ll).getTotal_uom()+
                                    "      "+datalist.get(ll).getDiscount()+
                                    "[R]"+datalist.get(ll).getTotal_price(),1f);
                }
                printer.printFormattedText("[R]"+grandttl.getText().toString(),3f);
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
            if (uniqid.contains(date)){
                String name = cursor.getString(cursor.getColumnIndex(Dbhelper.INVOICE_NAME_KEY));
                String id = cursor.getString(cursor.getColumnIndex(Dbhelper.INVOICE_ID));
                String price = cursor.getString(cursor.getColumnIndex(Dbhelper.INVOICE_PRICE_KEY));
                String discount = cursor.getString(cursor.getColumnIndex(Dbhelper.INVOICE_DISCOUNT_KEY));
                String totalprice = cursor.getString(cursor.getColumnIndex(Dbhelper.INVOICE_TOTAL_PRICE_KEY));
                String uniq_id = cursor.getString(cursor.getColumnIndex(Dbhelper.INVOICE_UNIQ_INV));
                String uom = cursor.getString(cursor.getColumnIndex(Dbhelper.INVOICE_UOM_KEY));
                datalist.add(new print_model_class(id,name,price,totalprice,uom,discount,uniq_id));
                ttl=ttl+Double.parseDouble(totalprice);
            }

        }
        cursor.close();
        adapter.notifyDataSetChanged();
        grandttl.setText("grand Total: "+new DecimalFormat("##.###").format(ttl));

    }

    private void Build_recyclerview() {
        adapter=new print_adapter(datalist,monthly_report.this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }
    public static String up_month(String current) throws ParseException {

        SimpleDateFormat dtfrm = new SimpleDateFormat("MMM-yyyy");
        Date d = dtfrm.parse(current);
        Calendar cal = Calendar.getInstance();
        cal.setTime(d);
        cal.add(Calendar.MONTH, 1);
        String nxt_date=dtfrm.format(cal.getTime());
        return nxt_date;
    }
    public static String previous_month(String current) throws ParseException {

        SimpleDateFormat dtfrm = new SimpleDateFormat("MMM-yyyy");
        Date d = dtfrm.parse(current);
        Calendar cal = Calendar.getInstance();
        cal.setTime(d);
        cal.add(Calendar.MONTH, -1);
        String nxt_date=dtfrm.format(cal.getTime());
        return nxt_date;
    }
    public void ctoats(String txt){
        Toast.makeText(this,txt,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent=new Intent(monthly_report.this,all_report.class);
        startActivity(intent);
    }
}