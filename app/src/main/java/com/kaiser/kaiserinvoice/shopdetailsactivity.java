package com.kaiser.kaiserinvoice;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class shopdetailsactivity extends AppCompatActivity {

    EditText name,address,vat;
    Button save;
    Dbhelper dbhelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shopdetailsactivity);
        name=findViewById(R.id.sname);
        address=findViewById(R.id.saddress);
        vat=findViewById(R.id.snumber);
        save=findViewById(R.id.ssave);
        dbhelper=new Dbhelper(shopdetailsactivity.this);


        save.setOnClickListener(view -> {
            int res=dbhelper.update_shop_details("1",name.getText().toString(),address.getText().toString(),vat.getText().toString());
            Log.i("res",String.valueOf(res));
           if (res == 0){
               dbhelper.add_shop_details(name.getText().toString(),address.getText().toString(),vat.getText().toString());
               Toast.makeText(this,"Saved",Toast.LENGTH_SHORT).show();
           }
        });
    }


}