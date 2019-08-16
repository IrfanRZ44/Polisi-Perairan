package com.exomatik.irfanrz.kepolisian.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.exomatik.irfanrz.kepolisian.ModelClass.Data.ModelPengaduan;
import com.exomatik.irfanrz.kepolisian.ModelClass.ItemClickSupport;
import com.exomatik.irfanrz.kepolisian.R;
import com.exomatik.irfanrz.kepolisian.RecyclerView.RecyclerPengaduan;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MenuAdmin extends AppCompatActivity implements ItemClickSupport.OnItemClickListener {
    private ImageView back;
    private RecyclerView rcLaporan;
    private ArrayList<ModelPengaduan> listPengaduan = new ArrayList<ModelPengaduan>();
    private RelativeLayout rlHotspot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_admin);

        back = (ImageView) findViewById(R.id.back);
        rcLaporan = (RecyclerView) findViewById(R.id.rc_laporan);
        rlHotspot = (RelativeLayout) findViewById(R.id.rl_tambah_admin);

        getDataPengaduan();
        ItemClickSupport.addTo(rcLaporan)
                .setOnItemClickListener(this);

        rlHotspot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MenuAdmin.this, BuatHotspot.class));
                finish();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MenuAdmin.this, MainActivity.class));
                finish();
            }
        });
    }

    private void getDataPengaduan(){
        listPengaduan = new ArrayList<ModelPengaduan>();
        DatabaseReference db = FirebaseDatabase.getInstance().getReference("pengaduan");
        db.addListenerForSingleValueEvent(valueEventListener);
    }

    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            if (dataSnapshot.exists()){
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    ModelPengaduan user = snapshot.getValue(ModelPengaduan.class);
                    listPengaduan.add(user);
                }
            }
            RecyclerPengaduan adapterAgenda = new RecyclerPengaduan(listPengaduan);
            RecyclerView.LayoutManager layoutAgenda = new LinearLayoutManager(MenuAdmin.this, LinearLayoutManager.VERTICAL, false);
            rcLaporan.setLayoutManager(layoutAgenda);
            rcLaporan.setNestedScrollingEnabled(false);
            rcLaporan.setAdapter(adapterAgenda);

        }

        @Override
        public void onCancelled(DatabaseError databaseError) {
            Toast.makeText(MenuAdmin.this, databaseError.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(MenuAdmin.this, MainActivity.class));
        finish();
    }

    @Override
    public void onItemClicked(RecyclerView recyclerView, int position, View v) {
        DetailLaporan.detailPengaduan = listPengaduan.get(position);
        startActivity(new Intent(MenuAdmin.this, DetailLaporan.class));
        finish();
    }
}
