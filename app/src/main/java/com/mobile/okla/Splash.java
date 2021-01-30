package com.mobile.okla;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Splash extends AppCompatActivity {

    //delay handler
    Handler handler = new Handler();
    FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        //delay
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //intent
                Intent intent;

                //cek user login
                if (FirebaseAuth.getInstance().getCurrentUser()==null) {
                    intent = new Intent(Splash.this, Login.class);
                }else {
                    intent = new Intent(Splash.this, MainActivity.class);
                }

                //pindah aktiviti
                startActivity(intent);
                finish();

            }
        },1250);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
    }
}
