package com.kaiser.kaiserinvoice;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

public class report extends Fragment {
    ConstraintLayout logout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root_view = inflater.inflate(R.layout.report, container, false);
        logout=root_view.findViewById(R.id.cl_logout);

        logout.setOnClickListener(view -> {
            SharedPreferences.Editor editor = getActivity().getSharedPreferences("login", MODE_PRIVATE).edit();
            editor.putInt("log", 0);
            editor.apply();
            startActivity(new Intent(getActivity(),Login_.class));
        });
        return root_view;
    }

}
