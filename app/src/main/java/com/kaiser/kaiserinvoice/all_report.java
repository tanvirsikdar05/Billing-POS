package com.kaiser.kaiserinvoice;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;

public class all_report extends AppCompatActivity {
    ConstraintLayout daily,monthly;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout._all_report);
        daily=findViewById(R.id.daily_report);
        monthly=findViewById(R.id.monthly_report);
        monthly.setOnClickListener(view -> {

            Intent intent=new Intent(this,monthly_report.class);
            startActivity(intent);

        });
        daily.setOnClickListener(view -> {
            Intent intent=new Intent(this,root_report.class);
            startActivity(intent);

        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent n=new Intent(all_report.this,MainActivity.class);
        startActivity(n);
    }
}