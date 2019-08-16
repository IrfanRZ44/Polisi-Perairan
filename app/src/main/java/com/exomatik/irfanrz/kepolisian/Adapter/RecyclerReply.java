package com.exomatik.irfanrz.kepolisian.Adapter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.exomatik.irfanrz.kepolisian.Activity.DetailLaporan;
import com.exomatik.irfanrz.kepolisian.Model.ModelReply;
import com.exomatik.irfanrz.kepolisian.ModelClass.Data.ModelPengaduan;
import com.exomatik.irfanrz.kepolisian.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


/**
 * Created by IrfanRZ on 17/09/2018.
 */

public class RecyclerReply extends RecyclerView.Adapter<RecyclerReply.bidangViewHolder> {
    private ArrayList<ModelReply> dataList;
    private Context context;
    private ProgressDialog progressDialog = null;
    private Activity activity;
    private static final int REQUEST_PHONE_CALL = 1;

    public RecyclerReply(ArrayList<ModelReply> dataList, Context context, Activity activity) {
        this.dataList = dataList;
        this.context = context;
        this.activity = activity;
    }

    @Override
    public bidangViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.list_reply, parent, false);

        this.context = parent.getContext();
        return new bidangViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(bidangViewHolder holder, final int position) {
        holder.textTanggapan.setText("Tanggapan : " + dataList.get(position).getTanggapan());

        getDataMuballigh(position, holder.imgLaporan, holder.textNama);
    }

    private void getDataMuballigh(final int position, final ImageView imgLaporan, final TextView textNama) {
        Query query = FirebaseDatabase.getInstance()
                .getReference("pengaduan")
                .orderByChild("token")
                .equalTo(dataList.get(position).getTokenPenerima());
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        final ModelPengaduan data = snapshot.getValue(ModelPengaduan.class);

                        Uri uri = Uri.parse(data.fotoKejadian);
                        Picasso.with(context).load(uri).into(imgLaporan);
                        textNama.setText("Subject Pengaduan : " + data.subjectPengaduan);

                    }
                } else {
                    Toast.makeText(context, "Terjadi error yang tidak diketahui", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(context, databaseError.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return (dataList != null) ? dataList.size() : 0;
    }

    public class bidangViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgLaporan;
        private TextView textNama, textTanggapan;

        public bidangViewHolder(View itemView) {
            super(itemView);

            imgLaporan = (ImageView) itemView.findViewById(R.id.imgLaporan);
            textTanggapan = (TextView) itemView.findViewById(R.id.textTanggapan);
            textNama = (TextView) itemView.findViewById(R.id.textNama);
        }
    }
}
