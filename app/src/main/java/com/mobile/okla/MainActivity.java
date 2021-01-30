package com.mobile.okla;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.internal.NavigationMenu;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    //view
    Toolbar toolbar;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle drawerToggle;
    NavigationView navigationView;

    //firebase
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_drawer);

        //auth
        auth = FirebaseAuth.getInstance();

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
                    case R.id.drawer_menu_setting1:
                        Toast.makeText(MainActivity.this, "Gak tau buat apa", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.drawer_menu_setting2:
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

    }
}
