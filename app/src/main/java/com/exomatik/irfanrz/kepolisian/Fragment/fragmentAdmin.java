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
import com.exomatik.irfanrz.kepolisian.Activity.MenuAdmin;
import com.exomatik.irfanrz.kepolisian.R;
import com.exomatik.irfanrz.kepolisian.SplashScreen;
import com.google.firebase.auth.FirebaseAuth;


/**
 * Created by IrfanRZ on 21/09/2018.
 */

public class fragmentAdmin extends Fragment {
    private View view;
    private TextView textNama, textType, textEmail;
    private RelativeLayout rlLogout, rlAdmin;

    public fragmentAdmin() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_admin, container, false);

        textNama = (TextView) view.findViewById(R.id.text_nama);
        textType = (TextView) view.findViewById(R.id.text_type);
        textEmail = (TextView) view.findViewById(R.id.text_email);
        rlLogout = (RelativeLayout) view.findViewById(R.id.rl_logout);
        rlAdmin = (RelativeLayout) view.findViewById(R.id.rl_admin);

        textNama.setText(MainActivity.currentUser.nama);
        textEmail.setText(MainActivity.currentUser.email);
        textType.setText(MainActivity.currentUser.typeUser);

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

        rlAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), MenuAdmin.class));
                getActivity().finish();
            }
        });

        return view;
    }
}
