package com.kaiser.kaiserinvoice;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;


public class home extends Fragment {
    CardView card_setting,card_report,card_serch,card_sales;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View root_view=inflater.inflate(R.layout.home, container, false);
       card_setting=root_view.findViewById(R.id.card_setting);
       card_sales=root_view.findViewById(R.id.card_inventory);
       card_report=root_view.findViewById(R.id.card_report);
       card_serch=root_view.findViewById(R.id.card_serch_view);





       //all click handle here
        card_setting.setOnClickListener(view -> {
            FragmentTransaction fr = getParentFragmentManager().beginTransaction();
            fr.replace(R.id.nav_container,new setting());
            fr.commit();
        });
        card_sales.setOnClickListener(view -> {
            Intent intent=new Intent(getActivity(),sales_main.class);
           startActivity(intent);

        });
        card_report.setOnClickListener(view -> {
           Intent intent=new Intent(getActivity(),all_report.class);
           startActivity(intent);

        });
        card_serch.setOnClickListener(view -> {
            Intent intent=new Intent(getActivity(),per_invoice.class);
            startActivity(intent);
        });
        return root_view;
    }
    public void ctoast(String txt){
        Toast.makeText(getActivity(),txt,Toast.LENGTH_SHORT).show();
    }

}