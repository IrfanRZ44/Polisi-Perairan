package com.exomatik.irfanrz.kepolisian.RecyclerView;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.exomatik.irfanrz.kepolisian.ModelClass.Data.ModelHotspot;
import com.exomatik.irfanrz.kepolisian.ModelClass.Data.ModelHukum;
import com.exomatik.irfanrz.kepolisian.R;

import java.util.ArrayList;

/**
 * Created by IrfanRZ on 17/09/2018.
 */

public class RecyclerHotspot extends RecyclerView.Adapter<RecyclerHotspot.bidangViewHolder> {
    private ArrayList<ModelHotspot> dataList;
    private Context context;

    public RecyclerHotspot(ArrayList<ModelHotspot> dataList) {
        this.dataList = dataList;
    }

    @Override
    public bidangViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.list_hotspot, parent, false);
        this.context = parent.getContext();
        return new bidangViewHolder(view);
    }

    @Override
    public void onBindViewHolder(bidangViewHolder holder, int position) {
        holder.txtNama.setText(dataList.get(position).danpal);
        holder.txtDesc.setText(dataList.get(position).telp);
        holder.txtKapal.setText(dataList.get(position).provinsi + " / " +dataList.get(position).alamat);
    }

    @Override
    public int getItemCount() {
        return (dataList != null) ? dataList.size() : 0;
    }

    public class bidangViewHolder extends RecyclerView.ViewHolder{
        private TextView txtNama, txtDesc, txtKapal;

        public bidangViewHolder(View itemView) {
            super(itemView);
            txtNama = (TextView) itemView.findViewById(R.id.text_title);
            txtDesc = (TextView) itemView.findViewById(R.id.text_desc);
            txtKapal = (TextView) itemView.findViewById(R.id.text_kapal);
        }
    }
}