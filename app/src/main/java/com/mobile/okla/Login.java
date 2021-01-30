package com.mobile.okla;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {

    //view
    Button bt_login;
    TextView tv_register;
    ProgressBar progressBar;
    EditText ed_email;
    EditText ed_password;

    //firebase
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //auth
        auth = FirebaseAuth.getInstance();

        //init view
        bt_login = findViewById(R.id.al_bt_login);
        tv_register = findViewById(R.id.al_tv_buatakun);
        progressBar = findViewById(R.id.al_progressbar);
        ed_email = findViewById(R.id.al_ed_email);
        ed_password = findViewById(R.id.al_ed_password);

        //buat akun
        tv_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this, Register.class));
                finish();
            }
        });

        //login
        bt_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //get text
                String email = ed_email.getText().toString();
                String password = ed_password.getText().toString();

                //condition
                if (email.isEmpty()) {
                    ed_email.setError("Wajib diisi!");
                }else {
                    //turn on progress bar
                    progressBar.setVisibility(View.VISIBLE);
                    bt_login.setVisibility(View.GONE);

                    //proses login
                    auth.signInWithEmailAndPassword(email,password)
                            .addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                //turn off progress bar
                                progressBar.setVisibility(View.GONE);
                                bt_login.setVisibility(View.VISIBLE);

                                //pindah activity
                                startActivity(new Intent(Login.this, MainActivity.class));
                                finish();
                            }else {
                                //turn off progress bar
                                progressBar.setVisibility(View.GONE);
                                bt_login.setVisibility(View.VISIBLE);

                                //show alert
                                new AlertDialog.Builder(Login.this)
                                        .setTitle("Login Gagal")
                                        .setMessage(task.getException().toString())
                                        .show();
                            }

                        }
                    });
                }
            }
        });

    }
}
