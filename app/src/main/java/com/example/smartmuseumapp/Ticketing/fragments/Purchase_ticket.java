package com.example.smartmuseumapp.Ticketing.fragments;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.smartmuseumapp.R;
import com.example.smartmuseumapp.Ticketing.TicketHelper;
import com.example.smartmuseumapp.UserModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import java.io.ByteArrayOutputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import androidmads.library.qrgenearator.QRGEncoder;
import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;

import static android.content.Context.WINDOW_SERVICE;
import static androidx.core.content.ContextCompat.getSystemService;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Purchase_ticket#newInstance} factory method to
 * create an instance of this fragment.
 */
@RequiresApi(api = Build.VERSION_CODES.N)
public class Purchase_ticket extends Fragment implements View.OnClickListener{
    String user_id1;
    String user_name1;
    DatabaseReference ticket;
    String value_paid;
    int c5=0;
    EditText selectDate,selectTime;
    private ImageView qrCodeIV;
    private Button generateQrBtn;
    String name1;
    String id1;
    LocalDateTime d;
    private ArrayList<String> arrayList;
    Bitmap bitmap;
    QRGEncoder qrgEncoder;
    private int mYear, mMonth, mDay, mHour, mMinute;
    int total=0;
    String newDate;
    int total2=0;
    private Button adult_minus;
    private Button adult_plus;
    private Button student_minus;
    private Button student_plus;
    private EditText total1;

    private TextView user_name;
    int grand_total=0;
    public     int c=0;
    public  int c1=0;
    private Button payment;
    Button membermiunus;
    Button memberplus;
    public Calendar c3 = Calendar.getInstance();

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Purchase_ticket() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Purchase_ticket.
     */
    // TODO: Rename and change types and number of parameters
    public static Purchase_ticket newInstance(String param1, String param2) {
        Purchase_ticket fragment = new Purchase_ticket();
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

    UserModel model;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_purchase_ticket, container, false);


        Bundle bundle = getArguments();
        if (bundle != null) {
            user_name1 = bundle.getString("user_name");
            user_id1 = bundle.getString("user_id");
        }


        TextView user_name = view.findViewById(R.id.uname);

        model = TicketHelper.getUserModel();
        user_name.setText(model.getName());

        ticket = FirebaseDatabase.getInstance().getReference();
        selectDate = view.findViewById(R.id.date);
        selectTime = view.findViewById(R.id.time);
        total1 = view.findViewById(R.id.total_price);
        adult_plus = view.findViewById(R.id.btn2);
        adult_minus = view.findViewById(R.id.btn1);
        payment = view.findViewById(R.id.btn6);
        membermiunus = view.findViewById(R.id.btn7);
        memberplus = view.findViewById(R.id.btn8);


        payment = view.findViewById(R.id.btn6);
        payment.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {

                int num = 2000;
                Random random = new Random();
                num = random.nextInt(num);
                value_paid = "unpaid";
                HashMap<String, Object> map = new HashMap<>();
                map.put("user_id", TicketHelper.UID);
                map.put("ticket_id", String.valueOf(num));
                map.put("Date", selectDate.getText().toString());
                map.put("Time", selectTime.getText().toString());
                map.put("ExpiryDate", newDate);
                map.put("adult", String.valueOf(c));
                map.put("student", String.valueOf(c1));
                map.put("member", String.valueOf(c5));
                map.put("amount", total1.getText().toString());
                map.put("Payment", value_paid);

                ticket.child("Ticket")
                        .child(TicketHelper.UID)
                        .child(String.valueOf(num))
                        .updateChildren(map)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
                Bundle bundle= new Bundle();
                bundle.putString("user_ticket",String.valueOf(num));
                bundle.putString("user_id", TicketHelper.UID);
                bundle.putString("name", user_name.getText().toString());
                bundle.putString("Date", selectDate.getText().toString());
                bundle.putString("Time", selectTime.getText().toString());
                bundle.putString("adult", String.valueOf(c));
                bundle.putString("student", String.valueOf(c1));
                bundle.putString("member", String.valueOf(c5));
                bundle.putString("amount", total1.getText().toString());

                Fragment someFragment = new PaymentMethod();
                someFragment.setArguments(bundle);
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.f_container, someFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
        memberplus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                c5 = c5 + 1;
                total = total + 100;
                grand_total = grand_total + total;
                total1.setText("Rs." + total);


            }

        });

        membermiunus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (grand_total <= 0)
                    total1.setText("Rs.00");
                else {
                    if (c5 > 0) {
                        total = total - 100;
                        grand_total = grand_total + total;
                        total1.setText("Rs." + total);
                        c5 = c5 - 1;
                        ;
                    }
                }


            }

        });

        adult_minus.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {

                if (grand_total <= 0)
                    total1.setText("Rs.00");

                else {
                    if (c > 0) {
                        total = total - 500;
                        grand_total = grand_total + total;
                        total1.setText("Rs." + total);
                        c = c - 1;
                        ;
                    }
                }


            }

        });

        adult_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                c = c + 1;
                total = total + 500;
                grand_total = grand_total + total;
                total1.setText("Rs." + total);


            }

        });
        student_minus = view.findViewById(R.id.btn3);
        student_minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (grand_total <= 0)
                    total1.setText("Rs.00");
                else {
                    if (c1 > 0) {
                        total = total - 300;
                        grand_total = grand_total + total;
                        total1.setText("Rs." + total);
                        c1 = c1 - 1;
                        ;
                    }
                }


            }

        });
        student_plus = view.findViewById(R.id.btn4);
        student_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                c1 = c1 + 1;
                total = total + 300;
                grand_total = grand_total + total;
                total1.setText("Rs." + total);
            }
        });


        selectDate.setOnClickListener(this);
        selectTime.setOnClickListener(this);





        return view;}
    DatePickerDialog datePickerDialog=null;

    public void onClick(View view) {


        if (view == selectDate) {



            mYear = c3.get(Calendar.YEAR);
            mMonth = c3.get(Calendar.MONTH);
            mDay = c3.get(Calendar.DAY_OF_MONTH);
            c3.set(mYear,mMonth,mDay);



            datePickerDialog = new DatePickerDialog(getActivity(),
                    new DatePickerDialog.OnDateSetListener() {


                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {


                            selectDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
                            c3.set(year, monthOfYear,dayOfMonth);

                            c3.add(Calendar.DAY_OF_MONTH, 1);
                            newDate = sdf.format(c3.getTime());


                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());

            datePickerDialog.show();
        }
        if (view == selectTime) {

            // Get Current Time
            final Calendar c4 = Calendar.getInstance();
            mHour = c4.get(Calendar.HOUR_OF_DAY);
            mMinute = c4.get(Calendar.MINUTE);

            // Launch Time Picker Dialog
            TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(),
                    new TimePickerDialog.OnTimeSetListener() {

                        @Override
                        public void onTimeSet(TimePicker view, int hours,
                                              int mins) {

                            String timeSet = "";
                            if (hours > 12) {
                                hours -= 12;
                                timeSet = "PM";
                            } else if (hours == 0) {
                                hours += 12;
                                timeSet = "AM";
                            } else if (hours == 12)
                                timeSet = "PM";
                            else
                                timeSet = "AM";


                            String minutes = "";
                            if (mins < 10)
                                minutes = "0" + mins;
                            else
                                minutes = String.valueOf(mins);

                            // Append in a StringBuilder
                            String aTime = new StringBuilder().append(hours).append(':')
                                    .append(minutes).append(" ").append(timeSet).toString();


                            selectTime.setText(aTime);
                        }
                    }, mHour, mMinute, false);
            timePickerDialog.show();
        }
    }
}