package com.exomatik.irfanrz.kepolisian.Activity;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.exomatik.irfanrz.kepolisian.ModelClass.Data.ModelPengaduan;
import com.exomatik.irfanrz.kepolisian.ModelClass.Data.ModelPlace;
import com.exomatik.irfanrz.kepolisian.R;
import com.squareup.picasso.Picasso;

public class DetailLaporan extends AppCompatActivity {
    public static ModelPengaduan detailPengaduan;
    private ImageView back, imgPengaduan;
    private TextView tSubject, tDesc, tTts, tNama, tAlamat, tHp, tMail, tWork;
    private RelativeLayout rlLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_laporan);

        back = (ImageView) findViewById(R.id.back);
        imgPengaduan = (ImageView) findViewById(R.id.img_pengaduan);
        tSubject = (TextView) findViewById(R.id.t_subject);
        tDesc = (TextView) findViewById(R.id.t_desc);
        tTts = (TextView) findViewById(R.id.t_tts);
        tNama = (TextView) findViewById(R.id.t_nama);
        tAlamat = (TextView) findViewById(R.id.t_alamat);
        tHp = (TextView) findViewById(R.id.t_hp);
        tMail = (TextView) findViewById(R.id.t_mail);
        tWork = (TextView) findViewById(R.id.t_work);
        rlLocation = (RelativeLayout) findViewById(R.id.rl_location);

        setData();

        rlLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Kontak.dataKontak = new ModelPlace(detailPengaduan.subjectPengaduan, detailPengaduan.location, detailPengaduan.phoneUser,
                        "-", detailPengaduan.location, "-", 0
                        );
                startActivity(new Intent(DetailLaporan.this, Kontak.class));
                finish();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DetailLaporan.this, MenuAdmin.class));
                finish();
            }
        });
    }

    private void setData() {
        Uri uri = Uri.parse(detailPengaduan.fotoKejadian);
        Picasso.with(DetailLaporan.this).load(uri).into(imgPengaduan);
        tSubject.setText(detailPengaduan.subjectPengaduan);
        tDesc.setText(detailPengaduan.descKejadian);
        tTts.setText(detailPengaduan.infoKejadian);
        tNama.setText(detailPengaduan.namaUser);
        tAlamat.setText(detailPengaduan.addressUser);
        tHp.setText(detailPengaduan.phoneUser);
        tMail.setText(detailPengaduan.mailUser);
        tWork.setText(detailPengaduan.workUser);
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(DetailLaporan.this, MenuAdmin.class));
        finish();
    }
}
