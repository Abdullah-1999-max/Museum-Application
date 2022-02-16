package com.example.smartmuseumapp.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.smartmuseumapp.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Calander#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Calander extends Fragment {

    public static Calander newInstance() {
        Calander fragment = new Calander();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_calander, container, false);
    }
}