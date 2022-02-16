package com.example.smartmuseumapp.Ticketing.Adapter;

import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;
import com.example.smartmuseumapp.R;
import androidx.appcompat.app.AppCompatActivity;

import com.example.smartmuseumapp.Ticketing.TicketHelper;
import com.example.smartmuseumapp.Ticketing.fragments.*;
import com.example.smartmuseumapp.Ticketing.fragments.Purchase_ticket;
import com.example.smartmuseumapp.Ticketing.fragments.Show_ticket;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.List;

import static android.app.PendingIntent.getActivity;

public class TicketsAdapter extends RecyclerView.Adapter<TicketsAdapter.ViewHolder> {
    DatabaseReference databaseReference;
    List<String> list;
    Context context;
    String id;
    public TicketsAdapter(List<String> list, Context context,String id) {
        this.list = list;
        this.context = context;
        this.id=id;
        databaseReference=FirebaseDatabase.getInstance().getReference();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.ticket_history_item, parent, false);

        return new TicketsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.ticketids.setText(list.get(position));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Toast.makeText(context,list.get(position),Toast.LENGTH_SHORT).show();
                Bundle bundle1=new Bundle();
                bundle1.putString("user_id",id);
                bundle1.putString("user_ticket_id",list.get(position));
                Fragment someFragment = new Show_ticket();
                someFragment.setArguments(bundle1);
                TicketHelper.changeFragment(context,someFragment);


            }


        });

        holder.cross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                databaseReference = FirebaseDatabase.getInstance().getReference();
                databaseReference.child("Ticket").child(id).child(list.get(position)).removeValue();
                //notifyItemChanged(position);
                notifyDataSetChanged();
                Log.i("tickD", "onCreate: ");
                // notify();
                //TicketsAdapter.this.notify();

                Toast.makeText(context, "Item deleted", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView ticketids;
        ImageButton cross;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ticketids = itemView.findViewById(R.id.ticketsids);
            cross=itemView.findViewById(R.id.cross);
        }
    }
}
