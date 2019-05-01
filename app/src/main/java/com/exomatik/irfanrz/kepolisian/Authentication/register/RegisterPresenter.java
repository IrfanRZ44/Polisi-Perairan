package com.exomatik.irfanrz.kepolisian.Authentication.register;

import android.app.Activity;

import com.google.firebase.auth.FirebaseUser;

/**
 * Created by IrfanRZ on 06/09/2018.
 */

public class RegisterPresenter implements RegisterContract.Presenter, RegisterContract.OnRegistrationListener {
    private RegisterContract.View mRegisterView;
    private RegisterInteractor mRegisterInteractor;

    public RegisterPresenter(RegisterContract.View registerView) {
        this.mRegisterView = registerView;
        mRegisterInteractor = new RegisterInteractor(this);
    }

    @Override
    public void register(Activity activity, String email, String password, String nama, String idMhs) {
        mRegisterInteractor.performFirebaseRegistration(activity, email, password, nama, idMhs);
    }

    @Override
    public void onSuccess(FirebaseUser firebaseUser, String nama, String id) {
        mRegisterView.onRegistrationSuccess(firebaseUser, nama, id);
    }

    @Override
    public void onFailure(String message) {
        mRegisterView.onRegistrationFailure(message);
    }

}
