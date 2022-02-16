package com.example.smartmuseumapp.Ticketing.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.smartmuseumapp.Ticketing.TicketHelper;
import com.example.smartmuseumapp.Ticketing.fragments.Purchase_ticket;
import android.widget.TextView;

import com.example.smartmuseumapp.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Ticketing#newInstance} factory method to
 * create an instance of this fragment.
 */

public class Ticketing extends Fragment {
    private Button purchase;

    private Button history;
     String user_id;
     String user_name;
    public static Ticketing newInstance() {
        Ticketing fragment = new Ticketing();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_ticketing, container, false);
        purchase= view.findViewById(R.id.purchase);
        purchase.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                Bundle bundle=getArguments();
                if (bundle!= null) {
                    user_name = bundle.getString("user_name");
                    user_id = bundle.getString("user_id");
                }
                Bundle bundle1=new Bundle();
                bundle1.putString("user_id",user_id);
                bundle1.putString("user_name",user_name);
                Fragment someFragment = new Purchase_ticket();
                someFragment.setArguments(bundle);
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.f_container, someFragment ); // give your fragment container id in first parameter
                transaction.addToBackStack(null);  // if written, this transaction will be added to backstack
                transaction.commit();
            }

        });
        history= view.findViewById(R.id.history);
        history.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                Fragment someFragment = new History();
                Bundle bundle1=new Bundle();
                bundle1.putString("user_id", TicketHelper.UID);
                someFragment.setArguments(bundle1);
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.f_container, someFragment ); // give your fragment container id in first parameter
                transaction.addToBackStack(null);  // if written, this transaction will be added to backstack
                transaction.commit();
            }
        });
        return view;
    }
}