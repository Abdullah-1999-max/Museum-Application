package com.example.smartmuseumapp.Ticketing.fragments;

import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.smartmuseumapp.R;
import com.example.smartmuseumapp.Ticketing.Model.TicketModel;
import com.example.smartmuseumapp.UserModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.WriterException;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;

import static android.content.Context.WINDOW_SERVICE;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Show_ticket#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Show_ticket extends Fragment {
    TextView date;
    TextView  time;
    TextView  name;
    TextView  cat1;
    TextView  cat2;
    TextView  cat3;
    TextView  amount;
    TextView ticket_id;
    DatabaseReference firebase;
    DatabaseReference firebase1;
    Bitmap bitmap;
    QRGEncoder qrgEncoder;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Show_ticket() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Show_ticket.
     */
    // TODO: Rename and change types and number of parameters
    public static Show_ticket newInstance(String param1, String param2) {
        Show_ticket fragment = new Show_ticket();
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
String ticetid;
 String user_id;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_show_ticket, container, false);
        firebase= FirebaseDatabase.getInstance().getReference();
        firebase1= FirebaseDatabase.getInstance().getReference();

        ticket_id=view.findViewById(R.id.txtticket_id);
        date=view.findViewById(R.id.txtdate);
        time=view.findViewById(R.id.txttime1);
        name=view.findViewById(R.id.txtname1);
        cat1=view.findViewById(R.id.txtaudult);
        cat2=view.findViewById(R.id.txtstudent);
        cat3=view.findViewById(R.id.txtmember);
        amount=view.findViewById(R.id.txtamount1);
        ImageView qrCodeIV=view.findViewById(R.id.imageView1);

        Bundle bundle= getArguments();
        if(bundle!=null)
        {
            ticetid=bundle.getString("user_ticket_id");
            user_id=bundle.getString("user_id");

        }
        ticket_id.setText(ticetid);
        firebase.child("Ticket").child(user_id).child(ticetid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){

                    TicketModel model=snapshot.getValue(TicketModel.class);

                    if(model!=null){
                        date.setText(model.getDate());
                        time.setText(model.getTime());
                        name.setText( FirebaseAuth.getInstance().getCurrentUser().getDisplayName());
                        if(model.getAdult().equals("0"))
                            cat1.setText("");
                        if(model.getStudent().equals("0"))
                            cat2.setText("");
                        if(model.getMember().equals("0"))
                            cat3.setText("");

                        amount.setText(model.getAmount());



                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        firebase1.child("Users").child(user_id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){

                    UserModel model1=snapshot.getValue(UserModel.class);

                    if(model1!=null){

                            name.setText(model1.getName());




                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        WindowManager manager = (WindowManager)getActivity(). getSystemService(WINDOW_SERVICE);

        // initializing a variable for default display.
        Display display = manager.getDefaultDisplay();

        // creating a variable for point which
        // is to be displayed in QR Code.
        Point point = new Point();
        display.getSize(point);

        // getting width and
        // height of a point
        int width = point.x;
        int height = point.y;

        // generating dimension from width and height.
        int dimen = width < height ? width : height;
        dimen = dimen * 3 / 4;

        // setting this dimensions inside our qr code
        // encoder to generate our qr code.
        qrgEncoder = new QRGEncoder(ticetid+name.getText().toString()+time.getText()+date.getText()+amount.getText()+user_id, null, QRGContents.Type.TEXT, dimen);
        try {
            // getting our qrcode in the form of bitmap.
            bitmap = qrgEncoder.encodeAsBitmap();

            // the bitmap is set inside our image
            // view using .setimagebitmap method.
            qrCodeIV.setImageBitmap(bitmap);

        } catch (WriterException ex) {
            ex.printStackTrace();
        }

        return view;
    }
}