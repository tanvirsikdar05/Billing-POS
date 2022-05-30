package com.kaiser.kaiserinvoice;

import android.content.Intent;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class setting extends Fragment {
    ConstraintLayout itemsetup,catgroysetup,shopdetails,uomsetup;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root_view=inflater.inflate(R.layout.setting, container, false);

        //find section
        uomsetup=root_view.findViewById(R.id.uom_setup);
        itemsetup=root_view.findViewById(R.id.item_setup);
        catgroysetup=root_view.findViewById(R.id.catgroy_setup);
        shopdetails=root_view.findViewById(R.id.shopdetails_setup);


        uomsetup.setOnClickListener(view -> {
            startActivity(new Intent(getContext(),uomsetupactivity.class));
        });
        itemsetup.setOnClickListener(view -> {
            startActivity(new Intent(getContext(),itemsetupactivity.class));
        });
        catgroysetup.setOnClickListener(view -> {
            startActivity(new Intent(getContext(),categroysetupactivity.class));
        });
        shopdetails.setOnClickListener(view -> {
            startActivity(new Intent(getContext(),shopdetailsactivity.class));
        });


        return root_view;
    }

}