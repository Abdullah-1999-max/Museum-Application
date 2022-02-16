package com.example.smartmuseumapp.Ticketing.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.smartmuseumapp.R;
import com.example.smartmuseumapp.Ticketing.TicketHelper;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PaymentMethod#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PaymentMethod extends Fragment {
    Button get_ticket;
    String name1="";
    String t_id;
    String user_id;
    String time1;
    String date1;
    String member1;
    String adult1;
    String student1;
    String amount1;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public PaymentMethod() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PaymentMethod.
     */
    // TODO: Rename and change types and number of parameters
    public static PaymentMethod newInstance(String param1, String param2) {
        PaymentMethod fragment = new PaymentMethod();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
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
        View view= inflater.inflate(R.layout.fragment_payment_method, container, false);
        get_ticket=view.findViewById(R.id.btn6);
        DatabaseReference ticket= FirebaseDatabase.getInstance().getReference();
        get_ticket.setOnClickListener(new View.OnClickListener() {
            @Override


            public void onClick(View v) {
                Bundle bundle = getArguments();
                if (bundle != null) {
                    t_id = bundle.getString("user_ticket");
                    user_id = bundle.getString("user_id");
                    name1 = bundle.getString("name");
                    date1= bundle.getString("Date");
                    time1= bundle.getString("Time");
                    adult1 = bundle.getString("adult");
                    student1= bundle.getString("student");
                    member1= bundle.getString("member");
                    amount1 = bundle.getString("amount");
                }
                Bundle bundle1= new Bundle();
                bundle1.putString("user_ticket", t_id);
                bundle1.putString("user_id", user_id);
                bundle1.putString("name", name1);
                bundle1.putString("Date", date1);
                bundle1.putString("Time", time1);
                bundle1.putString("adult", adult1);
                bundle1.putString("student", student1);
                bundle1.putString("member",member1);
                bundle1.putString("amount", amount1);
               ticket.child("Ticket")
                        .child(TicketHelper.UID)
                        .child(t_id).child("Payment").setValue("paid");

                Fragment someFragment = new GenerateTicket();
                someFragment.setArguments(bundle1);
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.f_container, someFragment);
                transaction.addToBackStack(null);
                transaction.commit();

               // TicketHelper.changeFragment(getContext(),new GenerateTicket());


            }});

                return view;
    }
}
