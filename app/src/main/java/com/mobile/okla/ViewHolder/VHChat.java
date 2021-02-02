package com.mobile.okla.ViewHolder;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mobile.okla.R;

public class VHChat extends RecyclerView.ViewHolder {

    public TextView tv_namapengirim;
    public TextView tv_pesan;
    public TextView tv_waktu;
    public LinearLayout container;

    public VHChat(@NonNull View itemView) {
        super(itemView);

        tv_namapengirim = itemView.findViewById(R.id.ci_tv_namapengirim);
        tv_pesan = itemView.findViewById(R.id.ci_tv_pesan);
        tv_waktu = itemView.findViewById(R.id.ci_tv_waktu);
        container = itemView.findViewById(R.id.ci_container);

    }
}
