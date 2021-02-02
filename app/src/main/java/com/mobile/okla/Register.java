package com.mobile.okla;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mobile.okla.Model.MUser;

public class Register extends AppCompatActivity {

    //view
    Button bt_register;
    TextView tv_login;
    EditText ed_email, ed_password, ed_confirmpassword;
    ProgressBar progressBar;

    //firebase
    FirebaseAuth auth;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //auth
        auth = FirebaseAuth.getInstance();

        //init view
        bt_register = findViewById(R.id.ar_bt_register);
        tv_login = findViewById(R.id.ar_tv_login);
        progressBar = findViewById(R.id.ar_progressbar);
        ed_email = findViewById(R.id.ar_ed_email);
        ed_password = findViewById(R.id.ar_ed_password);
        ed_confirmpassword = findViewById(R.id.ar_ed_confirmpassword);

        //login
        tv_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Register.this, Login.class));
                finish();
            }
        });

        //register
        bt_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //get text
                final String email = ed_email.getText().toString();
                final String password = ed_password.getText().toString();
                String confirmpassword = ed_confirmpassword.getText().toString();

                //kondisi
                if (email.isEmpty()) {
                    ed_email.setError("Tidak boleh kosong!");
                }else if (password.isEmpty()) {
                    ed_password.setError("Tidak boleh kosong!");
                }else if (confirmpassword.isEmpty()) {
                    ed_confirmpassword.setError("Tidak boleh kosong!");
                }else if (!password.equals(confirmpassword)) {
                    ed_confirmpassword.setError("Konfirmasi password harus sama!");
                }else if (password.length()<6) {
                    ed_password.setError("Password terlalu pendek!");
                }else {
                    //on progress bar
                    progressBar.setVisibility(View.VISIBLE);
                    bt_register.setVisibility(View.GONE);

                    //proses registrasi
                    auth.createUserWithEmailAndPassword(email,password)
                            .addOnCompleteListener(Register.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        //progress bar off
                                        progressBar.setVisibility(View.GONE);
                                        bt_register.setVisibility(View.VISIBLE);

                                        //save data
                                        databaseReference = FirebaseDatabase.getInstance().getReference().child(auth.getCurrentUser().getUid());
                                        //prepare data
                                        MUser mUser = new MUser(email,password,"Pengguna","","User");
                                        //proses save data
                                        databaseReference.setValue(mUser);

                                        //change activity
                                        startActivity(new Intent(Register.this, MainActivity.class));
                                        finish();

                                    }else {
                                        //progress bar off
                                        progressBar.setVisibility(View.GONE);
                                        bt_register.setVisibility(View.VISIBLE);

                                        //show error
                                        new AlertDialog.Builder(Register.this)
                                                .setTitle("Registrasi Gagal")
                                                .setMessage(task.getException().getMessage())
                                                .show();
                                    }
                                }
                            });

                }
            }
        });
    }
}
