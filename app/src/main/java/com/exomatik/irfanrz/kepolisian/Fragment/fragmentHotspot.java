package com.exomatik.irfanrz.kepolisian.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.exomatik.irfanrz.kepolisian.Activity.DetailHotspot;
import com.exomatik.irfanrz.kepolisian.Activity.Kontak;
import com.exomatik.irfanrz.kepolisian.Activity.MenuAdmin;
import com.exomatik.irfanrz.kepolisian.ModelClass.Data.ModelHotspot;
import com.exomatik.irfanrz.kepolisian.ModelClass.Data.ModelPlace;
import com.exomatik.irfanrz.kepolisian.ModelClass.ItemClickSupport;
import com.exomatik.irfanrz.kepolisian.R;
import com.exomatik.irfanrz.kepolisian.RecyclerView.RecyclerHotspot;
import com.exomatik.irfanrz.kepolisian.RecyclerView.RecyclerPengaduan;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


/**
 * Created by IrfanRZ on 21/09/2018.
 */

public class fragmentHotspot extends Fragment implements ItemClickSupport.OnItemClickListener {
    private View view;
    private RecyclerView rcHostpot;
    private ArrayList<ModelHotspot> listHostpot = new ArrayList<ModelHotspot>();

    public fragmentHotspot() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_hotspot, container, false);

        rcHostpot = (RecyclerView) view.findViewById(R.id.rc_hotspot);

        getDataHotspot();

        ItemClickSupport.addTo(rcHostpot)
                .setOnItemClickListener(this);

        return view;
    }

    private void getDataHotspot(){
        listHostpot.removeAll(listHostpot);
        listHostpot = new ArrayList<ModelHotspot>();
        DatabaseReference db = FirebaseDatabase.getInstance().getReference("hotspot");
        db.addListenerForSingleValueEvent(valueEventListener);
    }

    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            if (dataSnapshot.exists()){
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    ModelHotspot user = snapshot.getValue(ModelHotspot.class);
                    listHostpot.add(new ModelHotspot(user.provinsi, user.location, user.alamat, user.kapal
                            , user.danpal, user.telp, user.idHotspot
                    ));
                }
            }
            RecyclerHotspot adapterAgenda = new RecyclerHotspot(listHostpot);
            RecyclerView.LayoutManager layoutAgenda = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
            rcHostpot.setLayoutManager(layoutAgenda);
            rcHostpot.setNestedScrollingEnabled(false);
            rcHostpot.setAdapter(adapterAgenda);
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {
            Toast.makeText(getActivity(), databaseError.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
    };


    private void sendDataMaps(ModelPlace kontak){
        Kontak.dataKontak = kontak;
        startActivity(new Intent(getActivity(), Kontak.class));
        getActivity().finish();
    }

    @Override
    public void onItemClicked(RecyclerView recyclerView, int position, View v) {
        DetailHotspot.dataHotspot = new ModelHotspot(listHostpot.get(position).provinsi, listHostpot.get(position).location,
                listHostpot.get(position).alamat, listHostpot.get(position).kapal, listHostpot.get(position).danpal,
                listHostpot.get(position).telp, listHostpot.get(position).idHotspot
                );
        startActivity(new Intent(getActivity(), DetailHotspot.class));
        getActivity().finish();
    }
}
