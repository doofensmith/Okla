package com.mobile.okla.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.mobile.okla.ChatRoom;
import com.mobile.okla.Model.MRoom;
import com.mobile.okla.R;
import com.mobile.okla.ViewHolder.VHRoom;

public class ARoom extends FirebaseRecyclerAdapter<MRoom, VHRoom> {

    Context context;
    DatabaseReference databaseReference;

    public ARoom(@NonNull FirebaseRecyclerOptions<MRoom> options, Context context, DatabaseReference databaseReference) {
        super(options);
        this.context = context;
        this.databaseReference = databaseReference;
    }

    @Override
    protected void onBindViewHolder(@NonNull VHRoom holder, int position, @NonNull final MRoom model) {

        //ui
        holder.namachatroom.setText(model.getNamaroom());
        holder.lastchat.setText(model.getChatterakhir());
        if (model.getBelumdibaca()==0) {
            holder.badge.setVisibility(View.GONE);
        }else {
            holder.badge.setText(String.valueOf(model.getBelumdibaca()));
        }

        //container click
        holder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("keyroom",model.getKeyroom());
                Intent intent = new Intent(context, ChatRoom.class);
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });

        //container long click
        holder.container.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                final AlertDialog alertDialog = new AlertDialog.Builder(context).create();
                alertDialog.setTitle("Hapus Room");
                alertDialog.setMessage("Ingin menghapus room?");
                alertDialog.setCancelable(false);
                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Hapus", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        databaseReference.child("Room").child(model.getKeyroom()).removeValue();
                    }
                });
                alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Batal", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        alertDialog.dismiss();
                    }
                });
                alertDialog.show();
                return true;
            }
        });

    }

    @NonNull
    @Override
    public VHRoom onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fh_item,parent,false);
        return new VHRoom(view);
    }
}
