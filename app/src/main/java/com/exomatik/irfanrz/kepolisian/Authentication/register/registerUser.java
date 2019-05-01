package com.exomatik.irfanrz.kepolisian.Authentication.register;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.exomatik.irfanrz.kepolisian.Activity.MainActivity;
import com.exomatik.irfanrz.kepolisian.R;
import com.exomatik.irfanrz.kepolisian.SplashScreen;
import com.google.firebase.auth.FirebaseUser;

public class registerUser extends AppCompatActivity implements RegisterContract.View, AddUserContract.View {
    private EditText email, pass, name;
    private ImageView back;
    private Button register;
    private RegisterPresenter mRegisterPresenter;
    private AddUserPresenter mAddUserPresenter;
    private Button showPassword;
    private int show = 0;
    private ProgressDialog progressDialog = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);

        email = (EditText) findViewById(R.id.registerEmail);
        pass = (EditText) findViewById(R.id.registerPass);
        name = (EditText) findViewById(R.id.registerName);
        register = (Button) findViewById(R.id.registerUser);
        showPassword = (Button) findViewById(R.id.show_password);
        back = (ImageView) findViewById(R.id.registerBack);

        mRegisterPresenter = new RegisterPresenter(this);
        mAddUserPresenter = new AddUserPresenter(this);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailId = email.getText().toString();
                String password = pass.getText().toString();
                String nama = name.getText().toString();

                if ((emailId.isEmpty()) || (password.isEmpty()) || nama.isEmpty()){
                    Snackbar snackbar = Snackbar
                            .make(v, "Mohon isi data", Snackbar.LENGTH_LONG);

                    snackbar.show();
                }
                else {
                    progressDialog = new ProgressDialog(registerUser.this);
                    progressDialog.setMessage("Mohon Tunggu...");
                    progressDialog.setTitle("Proses");
                    progressDialog.setCancelable(false);
                    progressDialog.show();
                    mRegisterPresenter.register(registerUser.this, emailId, password, nama, emailId);
                }
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(registerUser.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        showPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (show == 1) {
                    show = 0;
                    showPassword.setBackgroundResource(R.drawable.ic_dont_show);
                    pass.setTransformationMethod(PasswordTransformationMethod.getInstance());
                } else if (show == 0) {
                    show = 1;
                    showPassword.setBackgroundResource(R.drawable.ic_show);
                    pass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
            }
        });
    }

    @Override
    public void onRegistrationSuccess(FirebaseUser firebaseUser, String nama, String id) {
        mAddUserPresenter.addUser(registerUser.this, firebaseUser, nama, id);
    }

    @Override
    public void onRegistrationFailure(String message) {
        progressDialog.dismiss();
        Toast.makeText(registerUser.this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onAddUserSuccess(String message) {
        Toast.makeText(registerUser.this, "Registrasi Berhasil !", Toast.LENGTH_SHORT).show();
        progressDialog.dismiss();
        Intent intent = new Intent(registerUser.this, SplashScreen.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onAddUserFailure(String message) {
        Toast.makeText(registerUser.this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(registerUser.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
