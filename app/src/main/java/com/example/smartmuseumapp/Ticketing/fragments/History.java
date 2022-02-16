package com.example.smartmuseumapp.Ticketing.fragments;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartmuseumapp.R;
import com.example.smartmuseumapp.Ticketing.Adapter.TicketsAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import androidmads.library.qrgenearator.QRGEncoder;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link History#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class History extends Fragment {
    TextView date;
    TextView  time;
    TextView  name;
    TextView  cat1;
    TextView  cat2;
    TextView  cat3;
    TextView  amount;
    TextView ticket_id;
    DatabaseReference firebase;
    Bitmap bitmap;
    QRGEncoder qrgEncoder;
    RecyclerView recyclerView;
    DatabaseReference ref;
    List<String> mylist;
    String id="";

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment History.
     */
    // TODO: Rename and change types and number of parameters
    public static History newInstance(String param1, String param2) {
        History fragment = new History();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }


    public History() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         View view=inflater.inflate(R.layout.fragment_history, container, false);
        firebase= FirebaseDatabase.getInstance().getReference();

        ticket_id=view.findViewById(R.id.txtticket_id);
        date=view.findViewById(R.id.txtdate);
        time=view.findViewById(R.id.txttime1);
        name=view.findViewById(R.id.txtname1);
        cat1=view.findViewById(R.id.txtaudult);
        cat2=view.findViewById(R.id.txtstudent);
        cat3=view.findViewById(R.id.txtmember);
        amount=view.findViewById(R.id.txtamount1);
        mylist=new ArrayList<>();
        recyclerView=view.findViewById(R.id.ticket_histroy_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        ref= FirebaseDatabase.getInstance().getReference();
         getData();
         return view;
    }
    private void getData() {
        Log.i("TAG", "getData: ");
        Bundle bundle = getArguments();

        if (bundle!= null) {
            id=bundle.getString("user_id");
        }
        ref.child("Ticket").child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    mylist.clear();
                    Log.i("snapshot", "onDataChange: "+snapshot.getKey());
                    Log.i("snapshotvalue", "onDataChange: "+snapshot.getValue());
                    Log.i("snapshot", "onDataChange: "+snapshot.getChildren());

                    for(DataSnapshot snapshot1:snapshot.getChildren()){
                        String ids=snapshot1.getKey();
                        Log.i("ids", "onDataChange: "+ids);
                        mylist.add(ids);

                    }

                    TicketsAdapter adapter=new TicketsAdapter(mylist,getContext(),id);
                    recyclerView.setAdapter(adapter);

                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}