package com.exomatik.irfanrz.kepolisian.CRUD_Firebase.TambahAnggota;

import android.content.Context;

import com.exomatik.irfanrz.kepolisian.ModelClass.Data.ModelTopik;

/**
 * Created by IrfanRZ on 06/09/2018.
 */

public interface AddAnggotaContract {
    interface View {
        void onAddAnggotaSuccess(String message);

        void onAddAnggotaFailure(String message);
    }

    interface Presenter {
        void addAnggota(Context context, ModelTopik modelDataAngkatan);
    }

    interface Interactor {
        void addAnggotaToDatabase(Context context, ModelTopik modelDataAngkatan);
    }

    interface OnAnggotaDatabaseListener {
        void onSuccess(String message);

        void onFailure(String message);
    }
}
