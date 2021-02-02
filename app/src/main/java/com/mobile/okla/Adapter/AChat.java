package com.mobile.okla.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.mobile.okla.Model.MChat;
import com.mobile.okla.R;
import com.mobile.okla.ViewHolder.VHChat;

public class AChat extends FirebaseRecyclerAdapter<MChat, VHChat> {

    Context context;
    DatabaseReference databaseReference;
    String keyroom;

    public AChat(@NonNull FirebaseRecyclerOptions<MChat> options, Context context, DatabaseReference databaseReference, String keyroom) {
        super(options);
        this.context = context;
        this.databaseReference = databaseReference;
        this.keyroom = keyroom;
    }

    @Override
    protected void onBindViewHolder(@NonNull VHChat holder, int position, @NonNull final MChat model) {

        //chat box
        holder.tv_namapengirim.setText(model.getNamapengirim());
        holder.tv_pesan.setText(model.getPesan());
        holder.tv_waktu.setText(model.getWaktu());

        //container long click
        holder.container.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                final AlertDialog alertDialog = new AlertDialog.Builder(context).create();
                alertDialog.setTitle("Hapus Pesan");
                alertDialog.setMessage("Ingin menghapus pesan?");
                alertDialog.setCancelable(false);
                alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "Hapus", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //hapus pesan
                        databaseReference.child("Room").child(keyroom).child("Messages").child(model.getKeychat())
                                .removeValue();
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
    public VHChat onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_item,parent,false);
        return new VHChat(view);
    }
}
