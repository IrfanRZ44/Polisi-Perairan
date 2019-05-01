package com.exomatik.irfanrz.kepolisian.CRUD_Firebase.TambahAnggota;

import android.content.Context;
import android.support.annotation.NonNull;

import com.exomatik.irfanrz.kepolisian.ModelClass.Data.ModelTopik;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by IrfanRZ on 06/09/2018.
 */

public class AddAnggotaInteractor implements AddAnggotaContract.Interactor  {
    private AddAnggotaContract.OnAnggotaDatabaseListener mOnAnggotaDatabaseListener;
    private Context ctx;

    public AddAnggotaInteractor(AddAnggotaContract.OnAnggotaDatabaseListener onAnggotaDatabaseListener) {
        this.mOnAnggotaDatabaseListener = onAnggotaDatabaseListener;
    }

    @Override
    public void addAnggotaToDatabase(final Context context, ModelTopik modelDataAngkatan) {
        String angkatan = "foto_"+modelDataAngkatan.foto;
        ctx = context;
        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        ModelTopik data = new ModelTopik(modelDataAngkatan.namaBerita, modelDataAngkatan.url, modelDataAngkatan.foto);
        database.child("foto")
                .child(angkatan)
                .setValue(data)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            mOnAnggotaDatabaseListener.onSuccess("Succes");

                        } else {
                            mOnAnggotaDatabaseListener.onFailure("Unable to add");
                        }
                    }
                });
    }
}
