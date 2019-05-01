package com.exomatik.irfanrz.kepolisian.CustomDialog;

import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.exomatik.irfanrz.kepolisian.Activity.MainActivity;
import com.exomatik.irfanrz.kepolisian.ModelClass.Data.ModelHukum;
import com.exomatik.irfanrz.kepolisian.R;
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

/**
 * Created by IrfanRZ on 03/11/2018.
 */

public class CustomDialogAddBerita extends DialogFragment {
    private RelativeLayout rlFotoBerita, btnTambahNews;
    private EditText etNamaBerita, etDescBerita;
    private ImageView imgBerita;
    private ProgressDialog progressDialog;
    private static int PICK_IMAGE = 100;
    private Uri imageUri;
    private StorageReference mStorageRef;

    public static CustomDialogAddBerita newInstance() {
        return new CustomDialogAddBerita();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View dialogView = inflater.inflate(R.layout.dialog_add_berita, container, false);

        btnTambahNews = (RelativeLayout) dialogView.findViewById(R.id.rl_tambah_news);
        rlFotoBerita = (RelativeLayout) dialogView.findViewById(R.id.rl_foto_news);
        etNamaBerita = (EditText) dialogView.findViewById(R.id.et_nama_news);
        etDescBerita = (EditText) dialogView.findViewById(R.id.et_desc_news);
        imgBerita = (ImageView) dialogView.findViewById(R.id.img_foto_berita);

        imgBerita.setVisibility(View.GONE);
        rlFotoBerita.setVisibility(View.VISIBLE);

        mStorageRef = FirebaseStorage.getInstance().getReference();

        rlFotoBerita.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                foto();
            }
        });

        btnTambahNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tambahBerita(v);
            }
        });

        return dialogView;
    }

    private void tambahBerita(View v) {
        String nama = etNamaBerita.getText().toString();
        String desc = etDescBerita.getText().toString();

        if ((nama.isEmpty()) || (desc.isEmpty()) || (imgBerita.getVisibility() == View.GONE)) {
            Snackbar.make(v, "Mohon, untuk melengkapi data dengan valid", Snackbar.LENGTH_LONG).show();
        } else {
            //creating and showing progress dialog
            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setMax(100);
            progressDialog.setMessage("Uploading...");
            progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            progressDialog.show();
            progressDialog.setCancelable(false);

            getId(v, nama, desc);
        }
    }

    private void foto() {
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Mohon Tunggu...");
        progressDialog.setTitle("Proses");
        progressDialog.setCancelable(false);
        progressDialog.show();
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, PICK_IMAGE);
    }

    public void uploadBerita(View view, final String nama, final String desc, final int id) {
        //accessing the firebase storage
        StorageReference storageReference = mStorageRef.child("storage/" + imageUri.getLastPathSegment());
        UploadTask uploadTask = storageReference.putFile(imageUri);

//        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//            @Override
//            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                Uri downloadUri = taskSnapshot.getDownloadUrl();
//                String uri = downloadUri.toString();
//
//                String idSize = Integer.toString(id);
//
//                DatabaseReference database = FirebaseDatabase.getInstance().getReference();
//                ModelHukum data = new ModelHukum(nama, desc, uri, idSize);
//                database.child("berita")
//                        .child("berita_" + idSize)
//                        .setValue(data)
//                        .addOnCompleteListener(new OnCompleteListener<Void>() {
//                            @Override
//                            public void onComplete(@NonNull Task<Void> task) {
//                                if (task.isSuccessful()) {
//                                    progressDialog.dismiss();
//                                    Toast.makeText(getActivity(), "Berhasil Tambah Berita", Toast.LENGTH_SHORT).show();
//                                    startActivity(new Intent(getActivity(), MainActivity.class));
//                                    getActivity().finish();
//                                } else {
//                                    progressDialog.dismiss();
//                                    Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
//                                }
//                            }
//                        });
//
//                Toast.makeText(getActivity(), "Succes", Toast.LENGTH_SHORT).show();
//            }
//        }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//                progressDialog.dismiss();
//                Toast.makeText(getActivity(), "errror " + e.getMessage().toString(), Toast.LENGTH_SHORT).show();
//            }
//        });
    }

    private void getId(final View v, final String name, final String desc) {
        DatabaseReference db = FirebaseDatabase.getInstance().getReference("berita");
        db.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<ModelHukum> list = new ArrayList<ModelHukum>();
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        ModelHukum user = snapshot.getValue(ModelHukum.class);
                        list.add(user);
                    }
                }
                int id = list.size();
                id++;
                uploadBerita(v, name, desc, id);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == getActivity().RESULT_OK && requestCode == PICK_IMAGE) {
            imageUri = data.getData();
            imgBerita.setVisibility(View.VISIBLE);
            rlFotoBerita.setVisibility(View.GONE);
            Picasso.with(getActivity()).load(imageUri).into(imgBerita);
        }
        progressDialog.dismiss();
    }
}
