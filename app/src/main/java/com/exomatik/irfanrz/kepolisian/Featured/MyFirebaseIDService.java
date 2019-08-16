package com.exomatik.irfanrz.kepolisian.Featured;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;


/**
 * Created by IrfanRZ on 07/08/2019.
 */

public class MyFirebaseIDService extends FirebaseInstanceIdService {

    @Override
    public void onTokenRefresh() {
        String token = FirebaseInstanceId.getInstance().getToken();

        Common.currentToken = token;
    }
}
