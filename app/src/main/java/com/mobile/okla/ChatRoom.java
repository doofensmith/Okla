package com.mobile.okla;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.badge.BadgeUtils;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mobile.okla.Adapter.AChat;
import com.mobile.okla.Adapter.ARoom;
import com.mobile.okla.Model.MChat;
import com.mobile.okla.Model.MRoom;
import com.mobile.okla.Model.MUser;
import com.mobile.okla.ViewHolder.VHChat;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ChatRoom extends AppCompatActivity {

    //view
    ImageButton bt_send;
    Toolbar toolbar;
    EditText ed_message;

    //firebase
    FirebaseAuth auth;
    DatabaseReference databaseReference;
    FirebaseRecyclerOptions<MChat> options;
    FirebaseRecyclerAdapter<MChat, VHChat> adapter;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);

        //auth
        auth = FirebaseAuth.getInstance();
        //root database reference
        databaseReference = FirebaseDatabase.getInstance().getReference();

        //get key room
        final String keyroom = getIntent().getExtras().getString("keyroom","");

        //update UI
        updateUI(databaseReference,keyroom);

        //view message
        ed_message = findViewById(R.id.acr_ed_message);

        //get nama pengguna
        databaseReference.child(auth.getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                final MUser mUser = snapshot.getValue(MUser.class);
                //button send
                bt_send = findViewById(R.id.acr_bt_send);
                bt_send.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //get text
                        String message = ed_message.getText().toString();
                        String namapengirim = mUser.getNamapengguna();

                        //kondisi
                        if (!message.isEmpty()) {
                            //get keychat
                            String keychat = databaseReference.child("Room").child(keyroom).child("Messages").push().getKey();

                            //get timestamp
                            DateFormat dateFormat = new SimpleDateFormat("EEE, dd/MM/yyyy HH:mm");
                            String waktu = dateFormat.format(Calendar.getInstance().getTime());

                            //prepare data
                            MChat mChat = new MChat(keychat,namapengirim,message,waktu);
                            databaseReference.child("Room").child(keyroom).child("Messages").child(keychat)
                                    .setValue(mChat).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        //clear text
                                        ed_message.setText("");
                                        ed_message.clearFocus();
                                        //hide softkeyboard
                                        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                                        inputMethodManager.hideSoftInputFromWindow(ed_message.getWindowToken(),0);
                                        //scroll
                                        recyclerView.smoothScrollToPosition(adapter.getItemCount());
                                    }else {
                                        new AlertDialog.Builder(ChatRoom.this)
                                                .setTitle("Error")
                                                .setMessage(task.getException().getMessage())
                                                .show();
                                    }
                                }
                            });
                        }

                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //button send
        bt_send = findViewById(R.id.acr_bt_send);
        bt_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //get text
                String message = ed_message.getText().toString();

                //kondisi
                if (!message.isEmpty()) {
                    //get keychat
                    String keychat = databaseReference.child("Room").child(keyroom).child("Messages").push().getKey();

                    //get timestamp
                    DateFormat dateFormat = new SimpleDateFormat("EEE, dd/MM/yyyy HH:mm");
                    String waktu = dateFormat.format(Calendar.getInstance().getTime());

                    //prepare data
                    MChat mChat = new MChat(keychat,"",message,waktu);
                    databaseReference.child("Room").child(keyroom).child("Messages").child(keychat)
                            .setValue(mChat).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                //clear text
                                ed_message.setText("");
                                ed_message.clearFocus();
                                //hide soft keyboard
                                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                                inputMethodManager.hideSoftInputFromWindow(ed_message.getWindowToken(),0);
                                //scroll
                                recyclerView.smoothScrollToPosition(adapter.getItemCount());
                            }else {
                                new AlertDialog.Builder(ChatRoom.this)
                                        .setTitle("Error")
                                        .setMessage(task.getException().getMessage())
                                        .show();
                            }
                        }
                    });
                }

            }
        });

        //show chat
        //option
        options = new FirebaseRecyclerOptions.Builder<MChat>()
                .setQuery(databaseReference.child("Room").child(keyroom).child("Messages"),MChat.class).build();
        //adapter
        adapter = new AChat(options,ChatRoom.this,databaseReference,keyroom);
        //recycler view
        recyclerView = findViewById(R.id.acr_recycleview);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(ChatRoom.this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        layoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        recyclerView.scrollToPosition(adapter.getItemCount());
        //start listening
        adapter.startListening();

    }

    void updateUI(DatabaseReference databaseReference, String keyroom) {
        databaseReference.child("Room").orderByChild("keyroom").equalTo(keyroom)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            for (final DataSnapshot data : snapshot.getChildren()) {
                                toolbar = findViewById(R.id.acr_toolbar);
                                toolbar.setTitle(data.child("namaroom").getValue().toString());
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

    @Override
    public void onStart() {
        super.onStart();

        if (adapter!=null){
            adapter.startListening();
        }

    }

    @Override
    public void onResume() {
        super.onResume();

        if (adapter!=null){
            adapter.startListening();
        }

    }

    @Override
    public void onStop() {

        if (adapter!=null){
            adapter.stopListening();
        }

        super.onStop();
    }

}