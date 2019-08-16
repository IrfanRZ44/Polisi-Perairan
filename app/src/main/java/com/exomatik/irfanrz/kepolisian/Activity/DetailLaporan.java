package com.exomatik.irfanrz.kepolisian.Activity;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.exomatik.irfanrz.kepolisian.Featured.Common;
import com.exomatik.irfanrz.kepolisian.Model.ModelReply;
import com.exomatik.irfanrz.kepolisian.Model.MyResponse;
import com.exomatik.irfanrz.kepolisian.Model.Notification;
import com.exomatik.irfanrz.kepolisian.Model.Sender;
import com.exomatik.irfanrz.kepolisian.ModelClass.Data.ModelPengaduan;
import com.exomatik.irfanrz.kepolisian.ModelClass.Data.ModelPlace;
import com.exomatik.irfanrz.kepolisian.R;
import com.exomatik.irfanrz.kepolisian.Remote.APIService;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailLaporan extends AppCompatActivity {
    public static ModelPengaduan detailPengaduan;
    private ImageView back, imgPengaduan;
    private TextView tSubject, tDesc, tTts, tNama, tAlamat, tHp, tMail, tWork;
    private RelativeLayout rlLocation, rlReply;
    private EditText userTanggapan;

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
        rlReply = (RelativeLayout) findViewById(R.id.rlReply);
        userTanggapan = (EditText) findViewById(R.id.userTanggapan);

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

        rlReply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String reply = userTanggapan.getText().toString();

                if (reply.isEmpty()){
                    Toast.makeText(DetailLaporan.this, "Data tidak boleh kosong", Toast.LENGTH_SHORT).show();
                }
                else {
                    Notification notification = new Notification(reply
                            , "Tanggapan Pengaduan"
                            , "com.exomatik.irfanrz.kepolisian.fcm_TARGET_NOTIFICATION_TO_REPLY");

                    if (detailPengaduan.token == null){
                        Toast.makeText(DetailLaporan.this, "Gagal mengirimkan notifikasi ke user", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Sender sender = new Sender(detailPengaduan.token
                                , notification);
                        sendNotif(sender, reply);
                    }
                }
            }
        });
    }

    private void sendNotif(final Sender sender, final String tanggapan){
        APIService mService = Common.getFCMClient();

        mService.sendNotification(sender)
                .enqueue(new Callback<MyResponse>() {
                    @Override
                    public void onResponse(Call<MyResponse> call, Response<MyResponse> response) {
                        if (response.isSuccessful()){
                            userTanggapan.setText("");
                            Toast.makeText(DetailLaporan.this, "Tanggapan berhasil dikirim", Toast.LENGTH_SHORT).show();
                            simpanReply(new ModelReply(tanggapan, detailPengaduan.token
                                    , FirebaseInstanceId.getInstance().getToken(), detailPengaduan.idPengaduan));
                        }
                        else {
                            Toast.makeText(DetailLaporan.this, "Gagal mengirimkan notifikasi ke user", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<MyResponse> call, Throwable t) {
                        Toast.makeText(DetailLaporan.this, "Gagal mengirimkan notifikasi ke user", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void simpanReply(ModelReply dataReply) {
        FirebaseDatabase.getInstance()
                .getReference("reply")
                .child(dataReply.getTokenPenerima() + "_" + dataReply.getTokenPengirim())
                .setValue(dataReply);
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
