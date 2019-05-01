package com.exomatik.irfanrz.kepolisian.Fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.exomatik.irfanrz.kepolisian.ModelClass.Data.ModelHukum;
import com.exomatik.irfanrz.kepolisian.R;
import com.exomatik.irfanrz.kepolisian.RecyclerView.MyAdapter;

import java.util.ArrayList;

/**
 * Created by IrfanRZ on 21/09/2018.
 */

public class fragmentHukum extends Fragment {
    private View view;
    private ArrayList<ModelHukum> listHukum = new ArrayList<ModelHukum>();
    private RecyclerView rcHukum;
    RecyclerView.LayoutManager layoutManager;

    public fragmentHukum() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_hukum, container, false);

        rcHukum = (RecyclerView) view.findViewById(R.id.rc_news);
        rcHukum.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        rcHukum.setLayoutManager(layoutManager);

        getDataBerita();

        return view;
    }


    private void getDataBerita() {
        listHukum.removeAll(listHukum);
        listHukum = new ArrayList<ModelHukum>();

        listHukum.add(new ModelHukum(getResources().getString(R.string.judul1), getResources().getString(R.string.hukum1), true));
        listHukum.add(new ModelHukum(getResources().getString(R.string.judul2), getResources().getString(R.string.hukum2), true));
        listHukum.add(new ModelHukum(getResources().getString(R.string.judul3), getResources().getString(R.string.hukum3), true));
        listHukum.add(new ModelHukum(getResources().getString(R.string.judul4), getResources().getString(R.string.hukum4), true));
        listHukum.add(new ModelHukum(getResources().getString(R.string.judul5), getResources().getString(R.string.hukum5), true));
        listHukum.add(new ModelHukum(getResources().getString(R.string.judul6), getResources().getString(R.string.hukum6), true));
        listHukum.add(new ModelHukum(getResources().getString(R.string.judul7), getResources().getString(R.string.hukum7), true));
        listHukum.add(new ModelHukum(getResources().getString(R.string.judul8), getResources().getString(R.string.hukum8), true));

        MyAdapter adapter = new MyAdapter(listHukum);
        rcHukum.setAdapter(adapter);
    }
}
