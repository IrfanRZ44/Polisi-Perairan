package com.exomatik.irfanrz.kepolisian.RecyclerView;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.exomatik.irfanrz.kepolisian.ModelClass.Data.ModelPengaduan;
import com.exomatik.irfanrz.kepolisian.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by IrfanRZ on 17/09/2018.
 */

public class RecyclerPengaduan extends RecyclerView.Adapter<RecyclerPengaduan.bidangViewHolder> {
    private ArrayList<ModelPengaduan> dataList;
    private Context context;

    public RecyclerPengaduan(ArrayList<ModelPengaduan> dataList) {
        this.dataList = dataList;
    }

    @Override
    public bidangViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.list_pengaduan, parent, false);
        this.context = parent.getContext();
        return new bidangViewHolder(view);
    }

    @Override
    public void onBindViewHolder(bidangViewHolder holder, int position) {
        Uri uri = Uri.parse(dataList.get(position).fotoKejadian);
        Picasso.with(context).load(uri).into(holder.img);

        holder.txtNama.setText(dataList.get(position).subjectPengaduan);
        holder.txtDesc.setText(dataList.get(position).descKejadian);
        holder.textTanggal.setText(dataList.get(position).infoKejadian);
    }

    @Override
    public int getItemCount() {
        return (dataList != null) ? dataList.size() : 0;
    }

    public class bidangViewHolder extends RecyclerView.ViewHolder{
        private TextView txtNama, txtDesc, textTanggal;
        private ImageView img;

        public bidangViewHolder(View itemView) {
            super(itemView);
            img = (ImageView) itemView.findViewById(R.id.listImage);
            txtNama = (TextView) itemView.findViewById(R.id.text_title);
            txtDesc = (TextView) itemView.findViewById(R.id.text_desc);
            textTanggal = (TextView) itemView.findViewById(R.id.text_tanggal);
        }
    }
}