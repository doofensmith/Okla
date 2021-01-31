package com.mobile.okla;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.mobile.okla.Adapter.ARoom;
import com.mobile.okla.Model.MRoom;

public class ChatRoom extends AppCompatActivity {

    //view
    ImageButton bt_send;

    //static model


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);

        final String keyroom = getIntent().getExtras().getString("keyroom","");
        //test
        bt_send = findViewById(R.id.acr_bt_send);
        bt_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(ChatRoom.this)
                        .setTitle("Test")
                        .setMessage(keyroom)
                        .show();
            }
        });

    }
}