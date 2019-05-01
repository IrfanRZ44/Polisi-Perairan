package com.exomatik.irfanrz.kepolisian.CustomDialog;

import android.app.DialogFragment;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.exomatik.irfanrz.kepolisian.ModelClass.Data.ModelHukum;
import com.exomatik.irfanrz.kepolisian.R;
import com.squareup.picasso.Picasso;

/**
 * Created by IrfanRZ on 03/11/2018.
 */

public class CustomDialogBerita extends DialogFragment {
    TextView textNama, textDesc;
    Button customDialog_Dismiss;
    ImageView imageView;
    public static ModelHukum dataBerita;

    public static CustomDialogBerita newInstance() {
        return new CustomDialogBerita();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View dialogView = inflater.inflate(R.layout.dialog_berita, container, false);

        textNama = (TextView) dialogView.findViewById(R.id.text_nama);
        textDesc = (TextView) dialogView.findViewById(R.id.text_desc);
        customDialog_Dismiss = (Button) dialogView.findViewById(R.id.dialog_dismiss);
        imageView = (ImageView) dialogView.findViewById(R.id.img_dialog_fragment);

//        textNama.setText(dataBerita.title);
//        textDesc.setText("     " + dataBerita.deskripsi);
//
//        Uri uri = Uri.parse(dataBerita.url);
//        Picasso.with(getContext()).load(uri).into(imageView);

        customDialog_Dismiss.setOnClickListener(customDialog_DismissOnClickListener);

        return dialogView;
    }
    private Button.OnClickListener customDialog_DismissOnClickListener
            = new Button.OnClickListener() {

        @Override
        public void onClick(View arg0) {
            // TODO Auto-generated method stub
            dismiss();
        }
    };

}
