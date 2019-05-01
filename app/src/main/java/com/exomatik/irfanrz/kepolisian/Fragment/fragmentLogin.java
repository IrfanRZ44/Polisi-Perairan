package com.exomatik.irfanrz.kepolisian.Fragment;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.exomatik.irfanrz.kepolisian.Authentication.login.LoginContract;
import com.exomatik.irfanrz.kepolisian.Authentication.login.LoginPresenter;
import com.exomatik.irfanrz.kepolisian.Authentication.register.registerUser;
import com.exomatik.irfanrz.kepolisian.R;
import com.exomatik.irfanrz.kepolisian.SplashScreen;


/**
 * Created by IrfanRZ on 21/09/2018.
 */

public class fragmentLogin extends Fragment implements LoginContract.View  {
    private View view;
    private EditText userEmail, userPass;
    private Button userLogin, userRegister;
    private LoginPresenter mLoginPresenter;
    private Button showPassword;
    private int show = 0;
    private ProgressDialog progressDialog = null;

    public fragmentLogin() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_login_user, container, false);

        userEmail = (EditText) view.findViewById(R.id.userNim);
        userPass = (EditText) view.findViewById(R.id.userPass);
        userLogin = (Button) view.findViewById(R.id.userLogin);
        userRegister = (Button) view.findViewById(R.id.userRegister);
        showPassword = (Button) view.findViewById(R.id.show_password);

        mLoginPresenter = new LoginPresenter(this);

        showPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (show == 1) {
                    show = 0;
                    showPassword.setBackgroundResource(R.drawable.ic_dont_show);
                    userPass.setTransformationMethod(PasswordTransformationMethod.getInstance());
                } else if (show == 0) {
                    show = 1;
                    showPassword.setBackgroundResource(R.drawable.ic_show);
                    userPass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
            }
        });

        userLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog = new ProgressDialog(getActivity());
                progressDialog.setMessage("Mohon Tunggu...");
                progressDialog.setTitle("Proses");
                progressDialog.setCancelable(false);
                progressDialog.show();

                String emailId = userEmail.getText().toString();
                String password = userPass.getText().toString();

                mLoginPresenter.login(getActivity(), emailId, password);
            }
        });

        userRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), registerUser.class);
                startActivity(intent);
                getActivity().finish();
            }
        });

        return view;
    }


    @Override
    public void onLoginSuccess(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
        startActivity(new Intent(getActivity(), SplashScreen.class));
        getActivity().finish();
        progressDialog.dismiss();
    }

    @Override
    public void onLoginFailure(String message) {
        progressDialog.dismiss();
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }
}
