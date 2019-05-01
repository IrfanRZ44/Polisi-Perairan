package com.exomatik.irfanrz.kepolisian.RecyclerView;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.exomatik.irfanrz.kepolisian.ModelClass.Data.ModelHukum;
import com.exomatik.irfanrz.kepolisian.ModelClass.Data.ModelPlace;
import com.exomatik.irfanrz.kepolisian.R;

import java.util.ArrayList;

/**
 * Created by IrfanRZ on 17/09/2018.
 */

public class RecyclerKontak extends RecyclerView.Adapter<RecyclerKontak.bidangViewHolder> {
    private ArrayList<ModelPlace> dataList;
    private Context context;

    public RecyclerKontak(ArrayList<ModelPlace> dataList) {
        this.dataList = dataList;
    }

    @Override
    public bidangViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.list_kontak, parent, false);
        this.context = parent.getContext();
        return new bidangViewHolder(view);
    }

    @Override
    public void onBindViewHolder(bidangViewHolder holder, int position) {

        holder.textNama.setText(dataList.get(position).name);
        holder.textPhone.setText("Kontak : " + dataList.get(position).phone);
        holder.textAlamat.setText(dataList.get(position).address);
        holder.ratingBar.setRating(dataList.get(position).rating);

    }

    @Override
    public int getItemCount() {
        return (dataList != null) ? dataList.size() : 0;
    }

    public class bidangViewHolder extends RecyclerView.ViewHolder{
        private TextView textNama, textPhone, textAlamat;
        private RatingBar ratingBar;

        public bidangViewHolder(View itemView) {
            super(itemView);

            textNama = (TextView) itemView.findViewById(R.id.text_title);
            textPhone = (TextView) itemView.findViewById(R.id.text_phone);
            textAlamat = (TextView) itemView.findViewById(R.id.text_alamat);
            ratingBar = (RatingBar) itemView.findViewById(R.id.rating_kontak);
        }
    }
}