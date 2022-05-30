package com.kaiser.kaiserinvoice;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Login_ extends AppCompatActivity {
    EditText usernm,pass;
    Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        usernm=findViewById(R.id.et_user_name);
        pass=findViewById(R.id.et_password);
        submit=findViewById(R.id.submit);

        SharedPreferences prefs = getSharedPreferences("login", MODE_PRIVATE);
        int name = prefs.getInt("log", 0);
        if (name == 1){
            Intent n=new Intent(Login_.this,MainActivity.class);
            startActivity(n);
        }

        submit.setOnClickListener(view -> {
            if (!usernm.getText().toString().isEmpty() && !pass.getText().toString().isEmpty()){
                   loginResponse();
            }else {
                ctoast("can't empty");
            }
        });



    }
    private void loginResponse() {
        ctoast("Loging....");
        Call<String> call = ((api_response) new Retrofit.Builder().baseUrl("https://websheba.work/project/").addConverterFactory(GsonConverterFactory.create()).build().create(api_response.class)).sendLoginRequest("demo", "123");
        call.enqueue(new Callback<String>() {
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    int value = Integer.parseInt(response.body().toString());
                    if (value == 1) {
                       //login success
                        ctoast("Login successful");
                        SharedPreferences.Editor editor = getSharedPreferences("login", MODE_PRIVATE).edit();
                        editor.putInt("log", 1);
                        editor.apply();

                        Intent n=new Intent(Login_.this,MainActivity.class);
                        startActivity(n);
                    } else if (value == 2) {
                        Toast.makeText(Login_.this, "Limit over contact with wear house", Toast.LENGTH_SHORT).show();
                    } else if (value == 3) {

                        Toast.makeText(Login_.this, "wrong input", Toast.LENGTH_SHORT).show();
                    } else if (value == 4) {
                        Toast.makeText(Login_.this, "wrong carrier", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(Login_.this, "Something went wrong", Toast.LENGTH_SHORT).show();

            }
        });
    }
public void ctoast(String txt){
    Toast.makeText(Login_.this, txt, Toast.LENGTH_SHORT).show();
}
}