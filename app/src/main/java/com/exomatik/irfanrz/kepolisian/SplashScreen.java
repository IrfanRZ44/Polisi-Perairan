package com.exomatik.irfanrz.kepolisian;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.exomatik.irfanrz.kepolisian.Activity.MainActivity;
import com.exomatik.irfanrz.kepolisian.ModelClass.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.victor.loading.newton.NewtonCradleLoading;

public class SplashScreen extends AppCompatActivity {
    private NewtonCradleLoading newtonCradleLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_splash);

        newtonCradleLoading = (NewtonCradleLoading) findViewById(R.id.newton_cradle_loading);

        newtonCradleLoading.start();

        if (FirebaseAuth.getInstance().getCurrentUser() != null){
            //Select * from user where user_uid = uid database
            Query query = FirebaseDatabase.getInstance().getReference("users")
                    .orderByChild("uid")
                    .equalTo(FirebaseAuth.getInstance().getCurrentUser().getUid());
            query.addListenerForSingleValueEvent(valueEventListener);
        }
        else {
            new Handler().postDelayed(new Runnable(){
                @Override
                public void run(){
                    Intent homeIntent = new Intent(SplashScreen.this, MainActivity.class);
                    startActivity(homeIntent);
                    finish();
                }
            },2000);
        }
    }

    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            if (dataSnapshot.exists()){
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    User user = snapshot.getValue(User.class);
                    MainActivity.currentUser = user;
                    new Handler().postDelayed(new Runnable(){
                        @Override
                        public void run(){
                            Intent homeIntent = new Intent(SplashScreen.this, MainActivity.class);
                            startActivity(homeIntent);
                            finish();
                        }
                    },2000);
                }
            }
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {
            Toast.makeText(SplashScreen.this, databaseError.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    public void onBackPressed() {

    }
}
