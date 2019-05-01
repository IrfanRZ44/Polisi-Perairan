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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.exomatik.irfanrz.kepolisian.Activity.MainActivity;
import com.exomatik.irfanrz.kepolisian.CRUD_Firebase.TambahAnggota.AddAnggotaContract;
import com.exomatik.irfanrz.kepolisian.CRUD_Firebase.TambahAnggota.AddAnggotaPresenter;
import com.exomatik.irfanrz.kepolisian.ModelClass.Data.ModelTopik;
import com.exomatik.irfanrz.kepolisian.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by IrfanRZ on 03/11/2018.
 */

public class CustomDialogAddTopik extends DialogFragment implements AddAnggotaContract.View  {
    private AddAnggotaPresenter mAddAnggotaPresenter;
    private Spinner spinnerTopik;
    private ProgressDialog progressDialog;
    private RelativeLayout rlFotoTopik, btnTambahTopik;
    private ImageView imgTopik;
    private EditText etNamaTopik;
    private int positionBerita;
    private ArrayList<String> listBerita = new ArrayList<String>();
    private static int PICK_IMAGE = 100;
    private Uri imageUri;
    private StorageReference mStorageRef;

    public static CustomDialogAddTopik newInstance() {
        return new CustomDialogAddTopik();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View dialogView = inflater.inflate(R.layout.dialog_add_topik, container, false);

        rlFotoTopik = (RelativeLayout) dialogView.findViewById(R.id.rl_foto);
        imgTopik = (ImageView) dialogView.findViewById(R.id.img_foto);
        btnTambahTopik = (RelativeLayout) dialogView.findViewById(R.id.rl_tambah_berita);
        etNamaTopik = (EditText) dialogView.findViewById(R.id.et_nama_foto);
        spinnerTopik = (Spinner) dialogView.findViewById(R.id.spinner_image);

        mAddAnggotaPresenter = new AddAnggotaPresenter(this);
        mStorageRef = FirebaseStorage.getInstance().getReference();
        imgTopik.setVisibility(View.GONE);
        rlFotoTopik.setVisibility(View.VISIBLE);
        setSpinner();
        spinnerTopik.setSelection(positionBerita);

        rlFotoTopik.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                foto();
            }
        });

        spinnerTopik.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                positionBerita = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnTambahTopik.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tambahTopik(v);
            }
        });

        return dialogView;
    }

    private void tambahTopik(View v) {
        String nama = etNamaTopik.getText().toString();

        if ((nama.isEmpty()) || (imgTopik.getVisibility() == View.GONE)) {
            Snackbar.make(v, "Mohon, untuk melengkapi data dengan valid", Snackbar.LENGTH_LONG).show();
        } else {
            //creating and showing progress dialog
            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setMax(100);
            progressDialog.setMessage("Uploading...");
            progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            progressDialog.show();
            progressDialog.setCancelable(false);

            uploadTopik(v, nama);
        }
    }

    public void uploadTopik(View view, final String nama) {
        //accessing the firebase storage
        StorageReference storageReference = mStorageRef.child("storage/" + imageUri.getLastPathSegment());
        UploadTask uploadTask = storageReference.putFile(imageUri);

        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Uri downloadUri = taskSnapshot.getDownloadUrl();
                String uri = downloadUri.toString();
                progressDialog.dismiss();
                ModelTopik modelKegiatan = new ModelTopik(nama, uri, spinnerTopik.getSelectedItemPosition());
                mAddAnggotaPresenter.addAnggota(getActivity(), modelKegiatan);

                Toast.makeText(getActivity(), "Succes", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                Toast.makeText(getActivity(), "errror " + e.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setSpinner() {
        listBerita.add("Topik 1");
        listBerita.add("Topik 2");
        listBerita.add("Topik 3");
        listBerita.add("Topik 4");
        listBerita.add("Topik 5");

        ArrayAdapter<String> dataBerita = new ArrayAdapter<String>(getActivity(),
                R.layout.spinner_berita, listBerita);

        spinnerTopik.setAdapter(dataBerita);
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == getActivity().RESULT_OK && requestCode == PICK_IMAGE) {
            imageUri = data.getData();
            imgTopik.setVisibility(View.VISIBLE);
            rlFotoTopik.setVisibility(View.GONE);
            Picasso.with(getActivity()).load(imageUri).into(imgTopik);
        }
        progressDialog.dismiss();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onAddAnggotaSuccess(String message) {
        Toast.makeText(getContext(), "Berhasil Menyimpan Data ", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(getActivity(), MainActivity.class));
        getActivity().finish();
        progressDialog.dismiss();
        dismiss();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onAddAnggotaFailure(String message) {
        Toast.makeText(getContext(), "Gagal Menyimpan Data", Toast.LENGTH_SHORT).show();
        progressDialog.dismiss();
    }
}
