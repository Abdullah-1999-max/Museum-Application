package com.example.smartmuseumapp.Ticketing;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.smartmuseumapp.R;
import com.example.smartmuseumapp.UserModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class TicketHelper {
    public  static  UserModel userModel;
    public static DatabaseReference ref = FirebaseDatabase.getInstance().getReference();

    public static String UID = FirebaseAuth.getInstance().getCurrentUser().getUid();

    public static UserModel getUserModel() {
        return userModel;
    }

    public static void setUserModel(UserModel userModel) {
        TicketHelper.userModel = userModel;
    }

    public static void changeFragment(Context mContext, Fragment fragment)
    {
        AppCompatActivity activity = (AppCompatActivity) mContext;
        Fragment myFragment = fragment;
        activity.getSupportFragmentManager().beginTransaction().replace(R.id.f_container, myFragment).addToBackStack(null).commit();
    }
}
