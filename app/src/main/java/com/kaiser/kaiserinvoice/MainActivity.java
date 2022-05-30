package com.kaiser.kaiserinvoice;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.app.Dialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    TextView tolbartxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tolbartxt=findViewById(R.id.toolbartxtview);
        tolbartxt.setText("Home");
        getSupportActionBar().hide();

        limit_uses();

        bottomNavigationView=findViewById(R.id.bottom_navigationbar_id);
        bottomNavigationView.setOnItemSelectedListener(navListener);
        bottomNavigationView.setSelectedItemId(R.id.menu_home);
        getSupportFragmentManager().beginTransaction().replace(R.id.nav_container, new home()).commit();
    }

    private void limit_uses() {
        SharedPreferences prefs = getSharedPreferences("f2", MODE_PRIVATE);
        int name = prefs.getInt("cn", 0);




        if (name == 0){
            SharedPreferences.Editor editor = getSharedPreferences("f2", MODE_PRIVATE).edit();
            editor.putInt("cn", 1);
            editor.apply();

        }else if (name == 10){

            Dialog dialog=new Dialog(getApplicationContext());
            dialog.setContentView(R.layout.add_balance_dialogue);
            dialog.setCancelable(false);
            EditText blnc=dialog.findViewById(R.id.et_balance);
            blnc.setText("Contact with developer");
        }else {
            SharedPreferences.Editor editor = getSharedPreferences("f2", MODE_PRIVATE).edit();
            editor.putInt("cn", name + 1);
            editor.apply();

        }
    }

    private NavigationBarView.OnItemSelectedListener navListener = item -> {

        Fragment selectedFragment = null;
        switch (item.getItemId()) {
            case R.id.menu_home:
                selectedFragment = new home();
                break;
            case R.id.menu_menu:
                selectedFragment = new report();
                break;
            case R.id.menu_setting:
                selectedFragment=new setting();
                break;

        }
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.nav_container, selectedFragment)
                .commit();
        return true;
    };
}