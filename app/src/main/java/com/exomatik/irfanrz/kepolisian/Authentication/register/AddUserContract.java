package com.exomatik.irfanrz.kepolisian.Authentication.register;

import android.content.Context;

import com.google.firebase.auth.FirebaseUser;

/**
 * Created by IrfanRZ on 06/09/2018.
 */

public interface AddUserContract {
    interface View {
        void onAddUserSuccess(String message);

        void onAddUserFailure(String message);
    }

    interface Presenter {
        void addUser(Context context, FirebaseUser firebaseUser, String nama, String idMhs);
    }

    interface Interactor {
        void addUserToDatabase(Context context, FirebaseUser firebaseUser, String nama, String idMhs);
    }

    interface OnUserDatabaseListener {
        void onSuccess(String message);

        void onFailure(String message);
    }
}
