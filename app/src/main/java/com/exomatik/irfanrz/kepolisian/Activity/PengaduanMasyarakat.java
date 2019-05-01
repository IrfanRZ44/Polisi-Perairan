package com.exomatik.irfanrz.kepolisian.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

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
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class PengaduanMasyarakat extends AppCompatActivity {
    private ImageView back;
    private RelativeLayout rlImage, rlSend, rlLocation;
    private ImageView image;
    private ProgressDialog progressDialog;
    private static int PICK_IMAGE = 100;
    private Uri imageUri;
    private StorageReference mStorageRef;
    private EditText etSubject, etPlace, etDesc;
    public static ModelPengaduan dataUser;
    private TextView textLocation;
    int PLACE_PICKER_REQUEST = 1;
    private String latitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pengaduan_masyarakat);

        back = (ImageView) findViewById(R.id.back);
        rlImage = (RelativeLayout) findViewById(R.id.rl_foto_news);
        rlSend = (RelativeLayout) findViewById(R.id.rl_kirim);
        image = (ImageView) findViewById(R.id.img_foto_berita);
        etSubject = (EditText) findViewById(R.id.user_subject);
        etPlace = (EditText) findViewById(R.id.user_place);
        etDesc = (EditText) findViewById(R.id.user_desc);
        rlLocation = (RelativeLayout) findViewById(R.id.rl_location);
        textLocation = (TextView) findViewById(R.id.text_location);

        mStorageRef = FirebaseStorage.getInstance().getReference();

        rlImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                foto();
            }
        });

        rlLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
                Intent intent;
                try {
                    progressDialog = new ProgressDialog(PengaduanMasyarakat.this);
                    progressDialog.setMessage("Mohon Tunggu...");
                    progressDialog.setTitle("Proses");
                    progressDialog.setCancelable(false);
                    progressDialog.show();
                    intent = builder.build(PengaduanMasyarakat.this);
                    startActivityForResult(intent, PLACE_PICKER_REQUEST);
                } catch (GooglePlayServicesRepairableException e) {
                    e.printStackTrace();
                } catch (GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }
            }
        });

        rlSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String subject = etSubject.getText().toString();
                String place = etPlace.getText().toString();
                String desc = etDesc.getText().toString();

                if ((subject.isEmpty()) || (place.isEmpty()) || (desc.isEmpty() || (image.getVisibility() == View.GONE)) ||
                        textLocation.getText().toString().contains("Lokasi Kejadian")){
                    Snackbar snackbar = Snackbar
                            .make(v, "Mohon isi Data dengan Benar", Snackbar.LENGTH_LONG);
                    snackbar.show();
                }
                else {
                    progressDialog = new ProgressDialog(PengaduanMasyarakat.this);
                    progressDialog.setMessage("Mohon Tunggu...");
                    progressDialog.setTitle("Proses");
                    progressDialog.setCancelable(false);
                    progressDialog.show();
                    getId(v, new ModelPengaduan(dataUser.namaUser, dataUser.birthUser, dataUser.addressUser, dataUser.workUser,
                            dataUser.phoneUser, dataUser.mailUser, subject, place, desc, "", 0, latitude
                            ));
                }
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PengaduanMasyarakat.this, MainActivity.class));
                finish();
            }
        });
    }

    private void getId(final View v, final ModelPengaduan dataPengaduan) {
        DatabaseReference db = FirebaseDatabase.getInstance().getReference("pengaduan");
        db.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<ModelPengaduan> list = new ArrayList<ModelPengaduan>();
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        ModelPengaduan user = snapshot.getValue(ModelPengaduan.class);
                        list.add(user);
                    }
                }
                int id = list.size();
                id++;
                uploadBerita(v, new ModelPengaduan(dataPengaduan.namaUser, dataPengaduan.birthUser, dataPengaduan.addressUser,
                        dataPengaduan.workUser, dataPengaduan.phoneUser, dataPengaduan.mailUser, dataPengaduan.subjectPengaduan,
                        dataPengaduan.infoKejadian, dataPengaduan.descKejadian, dataPengaduan.fotoKejadian, id, dataPengaduan.location
                ));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void foto() {
        progressDialog = new ProgressDialog(PengaduanMasyarakat.this);
        progressDialog.setMessage("Mohon Tunggu...");
        progressDialog.setTitle("Proses");
        progressDialog.setCancelable(false);
        progressDialog.show();
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, PICK_IMAGE);
    }

    public void uploadBerita(View view, final ModelPengaduan savePengaduan) {
        //accessing the firebase storage
        StorageReference storageReference = mStorageRef.child("storage/" + imageUri.getLastPathSegment());
        UploadTask uploadTask = storageReference.putFile(imageUri);

        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Uri downloadUri = taskSnapshot.getDownloadUrl();
                String uri = downloadUri.toString();
                Toast.makeText(PengaduanMasyarakat.this, "Succes Upload Foto", Toast.LENGTH_SHORT).show();

                DatabaseReference database = FirebaseDatabase.getInstance().getReference();
                ModelPengaduan data = new ModelPengaduan(savePengaduan.namaUser, savePengaduan.birthUser, savePengaduan.addressUser,
                        savePengaduan.workUser, savePengaduan.phoneUser, savePengaduan.mailUser, savePengaduan.subjectPengaduan,
                        savePengaduan.infoKejadian, savePengaduan.descKejadian, uri, savePengaduan.idPengaduan, savePengaduan.location
                        );
                database.child("pengaduan")
                        .child(data.idPengaduan + "_" + data.subjectPengaduan)
                        .setValue(data)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    progressDialog.dismiss();
                                    Toast.makeText(PengaduanMasyarakat.this, "Subject Sudah Dikirim", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(PengaduanMasyarakat.this, MainActivity.class));
                                    finish();
                                } else {
                                    progressDialog.dismiss();
                                    Toast.makeText(PengaduanMasyarakat.this, "Error", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                Toast.makeText(PengaduanMasyarakat.this, "errror " + e.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE) {
            imageUri = data.getData();
            image.setVisibility(View.VISIBLE);
            rlImage.setVisibility(View.GONE);
            Picasso.with(this).load(imageUri).into(image);
        }
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
        startActivity(new Intent(PengaduanMasyarakat.this, MainActivity.class));
        finish();
    }
}
