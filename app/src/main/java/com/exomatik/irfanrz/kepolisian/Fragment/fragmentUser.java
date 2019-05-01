package com.exomatik.irfanrz.kepolisian.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.exomatik.irfanrz.kepolisian.Activity.MainActivity;
import com.exomatik.irfanrz.kepolisian.R;
import com.exomatik.irfanrz.kepolisian.SplashScreen;
import com.google.firebase.auth.FirebaseAuth;


/**
 * Created by IrfanRZ on 21/09/2018.
 */

public class fragmentUser extends Fragment  {
    private View view;
    private TextView textNama, textEmail;
    private RelativeLayout rlLogout;

    public fragmentUser() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_user, container, false);

        textNama = (TextView) view.findViewById(R.id.text_nama);
        textEmail = (TextView) view.findViewById(R.id.text_email);
        rlLogout = (RelativeLayout) view.findViewById(R.id.rl_logout);

        textNama.setText(MainActivity.currentUser.nama);
        textEmail.setText(MainActivity.currentUser.email);

        rlLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Berhasil Keluar", Toast.LENGTH_SHORT).show();
                MainActivity.currentUser = null;
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getActivity(), SplashScreen.class));
                getActivity().finish();
            }
        });

        return view;
    }
}
