package com.mobile.okla.Fragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mobile.okla.Adapter.ARoom;
import com.mobile.okla.Model.MRoom;
import com.mobile.okla.Model.MUser;
import com.mobile.okla.R;
import com.mobile.okla.ViewHolder.VHRoom;

/**
 * A simple {@link Fragment} subclass.
 */
public class Home extends Fragment {


    public Home() {
        // Required empty public constructor
    }


    //firebase
    FirebaseAuth auth;
    DatabaseReference databaseReference;
    FirebaseRecyclerOptions<MRoom> options;
    FirebaseRecyclerAdapter<MRoom, VHRoom> adapter;
    RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        //auth
        auth = FirebaseAuth.getInstance();
        //database reference root
        databaseReference = FirebaseDatabase.getInstance().getReference()
                .child(auth.getCurrentUser().getUid());
        //set option
        options = new FirebaseRecyclerOptions.Builder<MRoom>()
                .setQuery(databaseReference.child("Room"),MRoom.class).build();
        //set adapter
        adapter = new ARoom(options,getContext(),databaseReference);
        //recycler view
        recyclerView = rootView.findViewById(R.id.fh_rv);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        //start listening data
        adapter.startListening();

        return rootView;
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
