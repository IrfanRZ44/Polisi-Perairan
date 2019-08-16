package com.exomatik.irfanrz.kepolisian.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.exomatik.irfanrz.kepolisian.Adapter.RecyclerReply;
import com.exomatik.irfanrz.kepolisian.Model.ModelReply;
import com.exomatik.irfanrz.kepolisian.ModelClass.User;
import com.exomatik.irfanrz.kepolisian.Notif.SplashReplyUser;
import com.exomatik.irfanrz.kepolisian.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.ArrayList;

public class ReplyUser extends AppCompatActivity {
    private ImageView back;
    private RecyclerView rcReply;
    private ArrayList<ModelReply> listReply = new ArrayList<ModelReply>();
    private RecyclerReply adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reply_user);

        back = (ImageView) findViewById(R.id.back);
        rcReply = (RecyclerView) findViewById(R.id.rcReply);

        setAdapter();
        getData();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ReplyUser.this, MainActivity.class));
                finish();
            }
        });
    }

    private void setAdapter() {
        adapter = new RecyclerReply(listReply, ReplyUser.this, ReplyUser.this);
        LinearLayoutManager localLinearLayoutManager = new LinearLayoutManager(ReplyUser.this, 1, false);
        rcReply.setLayoutManager(localLinearLayoutManager);
        rcReply.setNestedScrollingEnabled(false);
        rcReply.setAdapter(adapter);
    }

    private void getData() {
        //Select * from user where user_uid = uid database
        Query query = FirebaseDatabase.getInstance().getReference("reply")
                .orderByChild("tokenPenerima")
                .equalTo(FirebaseInstanceId.getInstance().getToken());
        query.addListenerForSingleValueEvent(valueEventListener);
    }

    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            if (dataSnapshot.exists()) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    ModelReply data = snapshot.getValue(ModelReply.class);

                    listReply.add(data);
                    adapter.notifyDataSetChanged();
                }
            }
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {
            Toast.makeText(ReplyUser.this, databaseError.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    public void onBackPressed() {
        startActivity(new Intent(ReplyUser.this, MainActivity.class));
        finish();
    }
}
