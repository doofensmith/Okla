package com.mobile.okla.ViewHolder;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mobile.okla.R;

public class VHRoom extends RecyclerView.ViewHolder {

    public TextView namachatroom;
    public TextView lastchat;
    public TextView badge;
    public LinearLayout container;

    public VHRoom(@NonNull View itemView) {
        super(itemView);

        //init view
        namachatroom = itemView.findViewById(R.id.fhi_tv_namachatroom);
        lastchat = itemView.findViewById(R.id.fhi_tv_lastchat);
        badge = itemView.findViewById(R.id.fhi_badge);
        container = itemView.findViewById(R.id.fhi_container);
    }
}
