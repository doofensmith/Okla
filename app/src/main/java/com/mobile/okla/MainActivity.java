package com.mobile.okla;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mobile.okla.Fragments.Home;
import com.mobile.okla.Fragments.Profile;
import com.mobile.okla.Model.MRoom;

public class MainActivity extends AppCompatActivity {

    //view
    Toolbar toolbar;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle drawerToggle;
    NavigationView navigationView;
    BottomNavigationView bottomNavigationView;

    //firebase
    FirebaseAuth auth;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_drawer);

        //auth
        auth = FirebaseAuth.getInstance();
        //database reference root
        databaseReference = FirebaseDatabase.getInstance().getReference()
                .child(auth.getCurrentUser().getUid());

        //default fragment
        changeFragment(new Home());

        //toolbar
        toolbar = findViewById(R.id.am_toolbar);
        setSupportActionBar(toolbar);

        //add drawer to toolbar
        drawerLayout = findViewById(R.id.am_main_drawer);
        drawerToggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.Open,R.string.Close);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();

        //drawer menu
        navigationView = findViewById(R.id.amd_nview);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.drawer_menu_buatroom:
                        drawerLayout.closeDrawers();
                        buatRoom(databaseReference);
                        break;
                    case R.id.drawer_menu_cariroom:
                        Toast.makeText(MainActivity.this, "Gak tau buat apa", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.drawer_menu_logout:
                        AuthUI.getInstance().signOut(getApplicationContext())
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        startActivity(new Intent(MainActivity.this,Login.class));
                                        finish();
                                    }
                                });
                }
                return true;
            }
        });

        //change fragment
        bottomNavigationView = findViewById(R.id.am_bottomnavigationview);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment = null;
                switch (item.getItemId()) {
                    case R.id.am_bn_menu_home:
                        fragment = new Home();
                        break;
                    case R.id.am_bn_menu_profile:
                        fragment = new Profile();
                        break;
                }
                return changeFragment(fragment);
            }
        });

    }

    public boolean changeFragment(Fragment fragment) {
        if (fragment!=null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.am_framelayout,fragment)
                    .commit();
            return true;
        }
        return false;
    }

    public void buatRoom(final DatabaseReference databaseReference) {
        //inflate dialog view
        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.dialog_buat_room,null);
        //view dialog buat room
        final EditText ed_namaroom = view.findViewById(R.id.dbm_ed_namaroom);
        final EditText ed_pwroom = view.findViewById(R.id.dbm_ed_pwroom);
        ed_namaroom.setError("Wajib diisi!");
        ed_pwroom.setError("Berisi minimal 4 digit angka!");

        //show dialog
        AlertDialog dialog = new AlertDialog.Builder(this).create();
        dialog.setTitle("Buat Room");
        dialog.setView(view);
        dialog.setCancelable(false);
        dialog.setButton(AlertDialog.BUTTON_POSITIVE, "Buat", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //gettext
                String namaroom = ed_namaroom.getText().toString();
                String pwroom = ed_pwroom.getText().toString();
                //kondisi
                if (namaroom.isEmpty()||pwroom.isEmpty()||pwroom.length()<4) {
                    new AlertDialog.Builder(MainActivity.this)
                            .setTitle("Gagal Membuat")
                            .setMessage("Nama Room atau Password tidak memenuhi syarat.")
                            .show();
                }else {
                    //loading
                    final Dialog loading = new Dialog(MainActivity.this);
                    loading.setContentView(R.layout.dialog_loading);
                    loading.setCancelable(false);
                    loading.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                    //show loading
                    loading.show();

                    //prepare data
                    String keyroom = databaseReference.child("Room").push().getKey();
                    MRoom mRoom = new MRoom(keyroom,namaroom,pwroom,"",0);
                    //save data
                    databaseReference.child("Room").child(keyroom).setValue(mRoom).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                //dismiss loading
                                loading.dismiss();
                            }else {
                                loading.dismiss();
                                new AlertDialog.Builder(MainActivity.this)
                                        .setTitle("Gagal Membuat")
                                        .setMessage(task.getException().getMessage())
                                        .show();
                            }
                        }
                    });
                }
            }
        });
        dialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Batal", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}
