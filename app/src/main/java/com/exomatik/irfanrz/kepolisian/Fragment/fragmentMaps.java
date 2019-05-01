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

import com.exomatik.irfanrz.kepolisian.Activity.Kontak;
import com.exomatik.irfanrz.kepolisian.ModelClass.Data.ModelPlace;
import com.exomatik.irfanrz.kepolisian.ModelClass.ItemClickSupport;
import com.exomatik.irfanrz.kepolisian.R;
import com.exomatik.irfanrz.kepolisian.RecyclerView.RecyclerKontak;

import java.util.ArrayList;


/**
 * Created by IrfanRZ on 21/09/2018.
 */

public class fragmentMaps extends Fragment implements ItemClickSupport.OnItemClickListener {
    private View view;
    private RecyclerView rcKontak;
    private ArrayList<ModelPlace> listKontak = new ArrayList<ModelPlace>();

    public fragmentMaps() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_maps, container, false);

        rcKontak = (RecyclerView) view.findViewById(R.id.rc_kontak);

        getDataKontak();

        return view;
    }

    private void getDataKontak(){
        listKontak.removeAll(listKontak);
        listKontak = new ArrayList<ModelPlace>();

        listKontak.add(new ModelPlace("Kementerian Kelautan dan Perikanan", "Jalan Raya Angkasa No.17, Landasan Ulin Utara, Liang Anggang, Kota Banjar Baru, Kalimantan Selatan 70724",
                "(0511) 4705720", "https://www.kkp.go.id/", "-3.43835026", "114.7470206", 3
                ));
        listKontak.add(new ModelPlace("KORPRI Sub Unit Dinas Kelautan Dan Perikanan", "Jl. Jenderal Ahmad Yani, Melayu, Tenggarong, Kabupaten Kutai Kartanegara, Kalimantan Timur 75512",
                "(0541) 661008", "https://www.kkp.go.id/", "-0.41912361", "116.98882967", 0
        ));
        listKontak.add(new ModelPlace("Balai Karantina Ikan (BKIPM) Kelas I Balikpapan", "Jl. Syarifuddin Yoes, Sepinggan, Balikpapan Sel., Kota Balikpapan, Kalimantan Timur 76114",
                "(0542) 876348", "https://www.kkp.go.id/", "-1.24433986", "116.89016193", 0
        ));
        listKontak.add(new ModelPlace("Balai Perikanan Budidaya Air Tawar Mandiangin", "Cempaka, Kota Banjar Baru, Kalimantan Selatan 70661",
                "0822-5060-4658", "-", "-3.49771129", "114.93280917", Float.parseFloat("4.5")
        ));
        listKontak.add(new ModelPlace("Balai Perikanan Budi Daya Air Tawar Mandiangin", "Jl. Tahura Sultan Adam Km 14, Mandiangin Barat, Karang Intan, Cempaka, Banjar, Kalimantan Selatan 70661",
                "0811-5004-658", "https://www.kkp.go.id/", "-3.4978987", "114.93228883", Float.parseFloat("4.5")
        ));
        listKontak.add(new ModelPlace("Kantor Dinas Pelabuhan Perikanan Banjarmasin", "Pelabuhan Perikanan Jl. Barito Hulu No.47, Pelambuan, Banjarmasin Bar., Kota Banjarmasin, Kalimantan Selatan 70129",
                "(0511) 3353026", "https://dislautkan.kalbarprov.go.id/", "-3.32037765", "114.562895", Float.parseFloat("3.5")
        ));
        listKontak.add(new ModelPlace("Kementerian Kelautan Dan Perikanan", "Jl. Husein Hamzah, Pal Lima, Pontianak Bar., Kota Pontianak, Kalimantan Barat 78114",
                "(0561) 796691", "https://jdih.kkp.go.id/", "-0.04119873", "109.28274393", 0
        ));
        listKontak.add(new ModelPlace("Dinas Kelautan dan Perikanan Provinsi Kalbar", "Jl. Sutan Syahrir No.16, Sungai Bangkong, Pontianak Kota, Kota Pontianak, Kalimantan Barat 78116",
                "(0561) 732521", "https://dislautkan.kalbarprov.go.id/", "-0.04034042", "109.32587385", Float.parseFloat("4")
        ));

        RecyclerKontak adapterAgenda = new RecyclerKontak(listKontak);
        RecyclerView.LayoutManager layoutAgenda = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rcKontak.setLayoutManager(layoutAgenda);
        rcKontak.setNestedScrollingEnabled(false);
        rcKontak.setAdapter(adapterAgenda);

        ItemClickSupport.addTo(rcKontak)
                .setOnItemClickListener(this);
    }


    private void sendDataMaps(ModelPlace kontak){
        Kontak.dataKontak = kontak;
        startActivity(new Intent(getActivity(), Kontak.class));
        getActivity().finish();
    }

    @Override
    public void onItemClicked(RecyclerView recyclerView, int position, View v) {
        sendDataMaps(new ModelPlace(listKontak.get(position).name, listKontak.get(position).address, listKontak.get(position).phone,
                listKontak.get(position).web, listKontak.get(position).latitude, listKontak.get(position).longitude, listKontak.get(position).rating
                ));
    }
}
