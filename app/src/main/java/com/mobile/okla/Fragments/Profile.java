package com.mobile.okla.Fragments;


import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mobile.okla.Model.MUser;
import com.mobile.okla.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Profile extends Fragment {


    public Profile() {
        // Required empty public constructor
    }

    //view
    TextView tv_nama, tv_alamat, tv_jk, tv_nohp, tv_email;
    TextView bt_editprofil;

    //firebase
    FirebaseAuth auth;
    DatabaseReference databaseReference;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);

        //auth
        auth = FirebaseAuth.getInstance();

        //root reference
        databaseReference = FirebaseDatabase.getInstance().getReference()
                .child(auth.getCurrentUser().getUid());

        //init view
        tv_nama = rootView.findViewById(R.id.fp_tv_nama);
        tv_alamat = rootView.findViewById(R.id.fp_tv_alamat);
        tv_jk = rootView.findViewById(R.id.fp_tv_jk);
        tv_nohp = rootView.findViewById(R.id.fp_tv_nohp);
        tv_email = rootView.findViewById(R.id.fp_tv_email);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    MUser user = snapshot.getValue(MUser.class);
                    tv_nama.setText(user.getNamapengguna());
                    tv_alamat.setText("Alamat : "+user.getAlamat());
                    tv_jk.setText("Jenis Kelamin : "+user.getJeniskelamin());
                    tv_nohp.setText("No HP : "+user.getNohp());
                    tv_email.setText("Email : "+user.getEmail());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //edit profil
        bt_editprofil = rootView.findViewById(R.id.fp_bt_editprofil);
        bt_editprofil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog dialog = new AlertDialog.Builder(getContext()).create();
                dialog.setTitle("Edit Profile");
                dialog.setContentView(R.layout.activity_edit_profile);
                dialog.setCancelable(false);
                dialog.setButton(DialogInterface.BUTTON_POSITIVE, "Simpan", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                dialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Batal", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                dialog.show();
            }
        });


        return rootView;
    }

}
