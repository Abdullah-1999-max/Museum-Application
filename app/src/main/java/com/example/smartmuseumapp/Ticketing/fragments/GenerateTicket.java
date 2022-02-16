package com.example.smartmuseumapp.Ticketing.fragments;

import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.smartmuseumapp.R;
import com.example.smartmuseumapp.Ticketing.TicketHelper;
import com.example.smartmuseumapp.UserModel;

import androidmads.library.qrgenearator.QRGEncoder;
import com.google.zxing.WriterException;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;
import static android.content.Context.WINDOW_SERVICE;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GenerateTicket#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GenerateTicket extends Fragment {
    String name1;
    String t_id;
    String user_id;
    String time1;
    String date1;
    String member1;
    String adult1;
    String student1;
    String amount1;
    TextView ticket_id;
    TextView  date;
    TextView  time;
    TextView  name;
    TextView  cat1;
    TextView  cat2;
    TextView  cat3;
    TextView  amount;
    Bitmap bmp;
    QRGEncoder qrgEncoder;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public GenerateTicket() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment GenerateTicket.
     */
    // TODO: Rename and change types and number of parameters
    public static GenerateTicket newInstance(String param1, String param2) {
        GenerateTicket fragment = new GenerateTicket();
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
        View view= inflater.inflate(R.layout.fragment_generate_ticket, container, false);
        UserModel model = TicketHelper.getUserModel();
        Button newbutton=view.findViewById(R.id.btnnew);
        newbutton.setOnClickListener(new View.OnClickListener() {
            @Override


            public void onClick(View v) {
                Fragment someFragment = new Ticketing();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.f_container, someFragment);
                transaction.addToBackStack(null);
                transaction.commit();

            }});
        Bundle bundle = getArguments();
        if (bundle != null) {
            t_id= bundle.getString("user_ticket");
            user_id = bundle.getString("user_id");
            name1 = bundle.getString("name");
            date1 = bundle.getString("Date");
            time1 = bundle.getString("Time");
            adult1 = bundle.getString("adult");
            student1 = bundle.getString("student");
            member1 = bundle.getString("member");
            amount1 = bundle.getString("amount");
        }

        TextView ticket_id=view.findViewById(R.id.txtticket_id);

        ticket_id.setText(t_id);
        date=view.findViewById(R.id.txtdate);
        time=view.findViewById(R.id.txttime1);
         name=view.findViewById(R.id.txtname1);
         cat1=view.findViewById(R.id.txtaudult);
      cat2=view.findViewById(R.id.txtstudent);
         cat3=view.findViewById(R.id.txtmember);
       amount=view.findViewById(R.id.txtamount1);
        name.setText(name1);
        date.setText(date1);
        time.setText(time1);
        amount.setText(amount1);
        if(student1.equals("0"))
            cat2.setText("");
        if(adult1.equals("0"))
            cat1.setText("");
        if(member1.equals("0"))
            cat3.setText("");
        WindowManager manager = (WindowManager) getActivity().getSystemService(WINDOW_SERVICE);

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
        qrgEncoder = new QRGEncoder( t_id+name.getText().toString()+time1+date1+amount1+user_id, null, QRGContents.Type.TEXT, dimen);
        try {
            // getting our qrcode in the form of bitmap.
          Bitmap  bitmap = qrgEncoder.encodeAsBitmap();

            // the bitmap is set inside our image
            // view using .setimagebitmap method.
            ImageView qrCodeIV=view.findViewById(R.id.imageView1);qrCodeIV.setImageBitmap(bitmap);

        } catch (WriterException ex) {
            ex.printStackTrace();
        }
        return view;

    }
}