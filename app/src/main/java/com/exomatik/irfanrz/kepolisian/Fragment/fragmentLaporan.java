package com.exomatik.irfanrz.kepolisian.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.exomatik.irfanrz.kepolisian.Activity.PengaduanMasyarakat;
import com.exomatik.irfanrz.kepolisian.ModelClass.Data.ModelPengaduan;
import com.exomatik.irfanrz.kepolisian.R;

import java.io.IOException;
import java.io.InputStream;


/**
 * Created by IrfanRZ on 21/09/2018.
 */

public class fragmentLaporan extends Fragment {
    private View view;
    private TextView textTerms, textIsi;
    private CheckBox checkTerms;
    private RelativeLayout rlTerms, rlUser, rlLanjut;
    private String text;
    private EditText etName, etBirth, etAddress, etWork, etPhone, etMail;

    public fragmentLaporan() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_laporan, container, false);

        rlTerms = (RelativeLayout) view.findViewById(R.id.rl_terms);
        rlLanjut = (RelativeLayout) view.findViewById(R.id.rl_lanjut);
        rlUser = (RelativeLayout) view.findViewById(R.id.rl_data);
        checkTerms = (CheckBox) view.findViewById(R.id.check_terms);
        textTerms = (TextView) view.findViewById(R.id.text_isi_terms);
        textIsi = (TextView) view.findViewById(R.id.text_isi_syarat);
        etName = (EditText) view.findViewById(R.id.user_name);
        etBirth = (EditText) view.findViewById(R.id.user_birth);
        etAddress = (EditText) view.findViewById(R.id.user_address);
        etWork = (EditText) view.findViewById(R.id.user_work);
        etPhone = (EditText) view.findViewById(R.id.user_phone);
        etMail = (EditText) view.findViewById(R.id.user_email);

        rlLanjut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nama = etName.getText().toString();
                String birth = etBirth.getText().toString();
                String address = etAddress.getText().toString();
                String work = etWork.getText().toString();
                String phone = etPhone.getText().toString();
                String mail = etMail.getText().toString();

                if ((nama.isEmpty()) || (birth.isEmpty()) || (address.isEmpty()) || (work.isEmpty())
                        || (phone.isEmpty()) || (mail.isEmpty()) || (!mail.contains("@"))){
                    Snackbar snackbar = Snackbar
                            .make(view, "Mohon isi Data dengan benar", Snackbar.LENGTH_LONG);
                    snackbar.show();
                }
                else{
                    PengaduanMasyarakat.dataUser = new ModelPengaduan(nama, birth, address, work, phone, mail
                            , "", "", "", "", 0, "");
                    startActivity(new Intent(getActivity(), PengaduanMasyarakat.class));
                    getActivity().finish();
                }

            }
        });

        try {
            InputStream is = getActivity().getAssets().open("text_terms.txt");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            text = new String(buffer);
            textTerms.setText("      " + text);
        } catch (IOException ex){
            ex.printStackTrace();
        }
        try {
            InputStream is = getActivity().getAssets().open("text_syarat.txt");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            text = new String(buffer);
            textIsi.setText("      " + text);
        } catch (IOException ex){
            ex.printStackTrace();
        }

        checkTerms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rlTerms.setVisibility(View.GONE);
                rlUser.setVisibility(View.VISIBLE);
            }
        });

        return view;
    }

}
