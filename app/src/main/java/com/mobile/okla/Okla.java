package com.mobile.okla;

import android.app.Application;

import com.google.firebase.database.FirebaseDatabase;

public class Okla extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        //enable offline
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        FirebaseDatabase.getInstance().getReference().keepSynced(true);

    }
}
