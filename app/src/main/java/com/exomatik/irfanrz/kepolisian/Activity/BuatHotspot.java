package com.exomatik.irfanrz.kepolisian.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.exomatik.irfanrz.kepolisian.ModelClass.Data.ModelHotspot;
import com.exomatik.irfanrz.kepolisian.ModelClass.Data.ModelPengaduan;
import com.exomatik.irfanrz.kepolisian.R;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;

public class BuatHotspot extends AppCompatActivity {
    private ImageView back;
    private RelativeLayout rlBuatHotspot, rlLocation;
    private EditText etProvinsi, etAlamat, etKapal, etDanpal, etPhone;
    private ProgressDialog progressDialog;
    private TextView textLocation;
    int PLACE_PICKER_REQUEST = 1;
    private String latitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buat_hotspot);

        back = (ImageView) findViewById(R.id.back);
        etProvinsi = (EditText) findViewById(R.id.et_provinsi);
        etAlamat = (EditText) findViewById(R.id.et_alamat);
        etKapal = (EditText) findViewById(R.id.et_kapal);
        etDanpal = (EditText) findViewById(R.id.et_danpal);
        etPhone = (EditText) findViewById(R.id.et_telp);
        rlBuatHotspot = (RelativeLayout) findViewById(R.id.rl_hotspot);
        rlLocation = (RelativeLayout) findViewById(R.id.rl_location);
        textLocation =(TextView) findViewById(R.id.text_location);

        etKapal.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                textWatcher(etKapal);
            }
        });

        etDanpal.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                textWatcher(etDanpal);
            }
        });

        rlBuatHotspot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String prov = etProvinsi.getText().toString();
                String alamat = etAlamat.getText().toString();
                String kapal = etKapal.getText().toString();
                String danpal = etDanpal.getText().toString();
                String phone = etPhone.getText().toString();

                if ((prov.isEmpty()) || (alamat.isEmpty()) || (kapal.isEmpty()) || (danpal.isEmpty()) ||
                        (phone.isEmpty()) || (textLocation.getText().toString().contains("Pilih Lokasi"))
                        ){
                    Snackbar snackbar = Snackbar
                            .make(v, "Mohon isi Data dengan Benar", Snackbar.LENGTH_LONG);
                    snackbar.show();
                }else {
                    progressDialog = new ProgressDialog(BuatHotspot.this);
                    progressDialog.setMessage("Mohon Tunggu...");
                    progressDialog.setTitle("Proses");
                    progressDialog.setCancelable(false);
                    progressDialog.show();
                    getId(v, new ModelHotspot(prov, latitude, alamat, kapal, danpal, phone, 0));
                }
            }
        });

        rlLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
                Intent intent;
                try {
                    progressDialog = new ProgressDialog(BuatHotspot.this);
                    progressDialog.setMessage("Mohon Tunggu...");
                    progressDialog.setTitle("Proses");
                    progressDialog.setCancelable(false);
                    progressDialog.show();
                    intent = builder.build(BuatHotspot.this);
                    startActivityForResult(intent, PLACE_PICKER_REQUEST);
                } catch (GooglePlayServicesRepairableException e) {
                    e.printStackTrace();
                } catch (GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(BuatHotspot.this, MenuAdmin.class));
                finish();
            }
        });
    }

    private void textWatcher(final EditText edit){
        edit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (edit.getText().toString().contains(".")){
                    String kapal = edit.getText().toString();
                    kapal = kapal.replace(".", "");
                    edit.setText(kapal);
                    Toast.makeText(BuatHotspot.this, "Maaf, tidak boleh menggunakan .", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void getId(final View v, final ModelHotspot dataHotspot) {
        DatabaseReference db = FirebaseDatabase.getInstance().getReference("hotspot");
        db.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<ModelHotspot> list = new ArrayList<ModelHotspot>();
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        ModelHotspot user = snapshot.getValue(ModelHotspot.class);
                        list.add(user);
                    }
                }
                int id = list.size();
                id++;
                uploadBerita(v, new ModelHotspot(dataHotspot.provinsi, dataHotspot.location, dataHotspot.alamat,
                        dataHotspot.kapal, dataHotspot.danpal, dataHotspot.telp, id
                        ));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void uploadBerita(View view, final ModelHotspot savePengaduan) {
                DatabaseReference database = FirebaseDatabase.getInstance().getReference();
                ModelHotspot data = new ModelHotspot(savePengaduan.provinsi, savePengaduan.location, savePengaduan.alamat,
                        savePengaduan.kapal, savePengaduan.danpal, savePengaduan.telp, savePengaduan.idHotspot
                );
                database.child("hotspot")
                        .child(data.idHotspot + "_" + data.kapal)
                        .setValue(data)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    progressDialog.dismiss();
                                    Toast.makeText(BuatHotspot.this, "Hotspot Sudah Dikirim", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(BuatHotspot.this, MainActivity.class));
                                    finish();
                                } else {
                                    progressDialog.dismiss();
                                    Toast.makeText(BuatHotspot.this, "Error", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PLACE_PICKER_REQUEST){
            if (resultCode==RESULT_OK){
                Place place = PlacePicker.getPlace(data, this);
                textLocation.setText(place.getName());
                latitude = place.getLatLng().toString();
            }
        }
        progressDialog.dismiss();
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(BuatHotspot.this, MenuAdmin.class));
        finish();
    }
}
