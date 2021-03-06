package com.exomatik.irfanrz.kepolisian.Activity;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.exomatik.irfanrz.kepolisian.Adapter.ViewPagerAdapter;
import com.exomatik.irfanrz.kepolisian.Featured.Common;
import com.exomatik.irfanrz.kepolisian.Fragment.fragmentAdmin;
import com.exomatik.irfanrz.kepolisian.Fragment.fragmentHotspot;
import com.exomatik.irfanrz.kepolisian.Fragment.fragmentLogin;
import com.exomatik.irfanrz.kepolisian.Fragment.fragmentMaps;
import com.exomatik.irfanrz.kepolisian.Fragment.fragmentHukum;
import com.exomatik.irfanrz.kepolisian.Fragment.fragmentLaporan;
import com.exomatik.irfanrz.kepolisian.Fragment.fragmentUser;
import com.exomatik.irfanrz.kepolisian.Model.MyResponse;
import com.exomatik.irfanrz.kepolisian.Model.Sender;
import com.exomatik.irfanrz.kepolisian.ModelClass.User;
import com.exomatik.irfanrz.kepolisian.R;
import com.exomatik.irfanrz.kepolisian.Remote.APIService;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private TabLayout tabKategori;
    private ViewPager viewKategori;
    ViewPagerAdapter adapterKategori;
    public static User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tabKategori = (TabLayout) findViewById(R.id.tab_Kategori);
        viewKategori = (ViewPager) findViewById(R.id.view_Kategori);

        adapterKategori = new ViewPagerAdapter(getSupportFragmentManager());

        user(currentUser);
    }

    private void user(User type){
        adapterKategori.AddFragment(new fragmentHukum());
        adapterKategori.AddFragment(new fragmentHotspot());
        adapterKategori.AddFragment(new fragmentLaporan());
        adapterKategori.AddFragment(new fragmentMaps());
        if (type == null){
            adapterKategori.AddFragment(new fragmentLogin());
        }else if (type.typeUser.equals("Admin")){
            cekNotifikasi();
            adapterKategori.AddFragment(new fragmentAdmin());
        }
        else{
            adapterKategori.AddFragment(new fragmentUser());
        }

        viewKategori.setAdapter(adapterKategori);
        tabKategori.setupWithViewPager(viewKategori);

        tabKategori.getTabAt(0).setIcon(R.drawable.ic_law_white);
        tabKategori.getTabAt(1).setIcon(R.drawable.ic_hotspot_gray);
        tabKategori.getTabAt(2).setIcon(R.drawable.speaker_gray);
        tabKategori.getTabAt(3).setIcon(R.drawable.ic_location_gray);
        tabKategori.getTabAt(4).setIcon(R.drawable.ic_setting_gray);

        tabKategori.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == 0){
                    tabKategori.getTabAt(0).setIcon(R.drawable.ic_law_white);
                    tabKategori.getTabAt(1).setIcon(R.drawable.ic_hotspot_gray);
                    tabKategori.getTabAt(2).setIcon(R.drawable.speaker_gray);
                    tabKategori.getTabAt(3).setIcon(R.drawable.ic_location_gray);
                    tabKategori.getTabAt(4).setIcon(R.drawable.ic_setting_gray);
                }
                else if (tab.getPosition() == 1){
                    tabKategori.getTabAt(0).setIcon(R.drawable.ic_law_gray);
                    tabKategori.getTabAt(1).setIcon(R.drawable.ic_hotspot_white);
                    tabKategori.getTabAt(2).setIcon(R.drawable.speaker_gray);
                    tabKategori.getTabAt(3).setIcon(R.drawable.ic_location_gray);
                    tabKategori.getTabAt(4).setIcon(R.drawable.ic_setting_gray);
                }
                else if (tab.getPosition() == 2){
                    tabKategori.getTabAt(0).setIcon(R.drawable.ic_law_gray);
                    tabKategori.getTabAt(1).setIcon(R.drawable.ic_hotspot_gray);
                    tabKategori.getTabAt(2).setIcon(R.drawable.speaker_white);
                    tabKategori.getTabAt(3).setIcon(R.drawable.ic_location_gray);
                    tabKategori.getTabAt(4).setIcon(R.drawable.ic_setting_gray);
                }
                else if (tab.getPosition() == 3){
                    tabKategori.getTabAt(0).setIcon(R.drawable.ic_law_gray);
                    tabKategori.getTabAt(1).setIcon(R.drawable.ic_hotspot_gray);
                    tabKategori.getTabAt(2).setIcon(R.drawable.speaker_gray);
                    tabKategori.getTabAt(3).setIcon(R.drawable.ic_location_white);
                    tabKategori.getTabAt(4).setIcon(R.drawable.ic_setting_gray);
                }
                else if (tab.getPosition() == 4){
                    tabKategori.getTabAt(0).setIcon(R.drawable.ic_law_gray);
                    tabKategori.getTabAt(1).setIcon(R.drawable.ic_hotspot_gray);
                    tabKategori.getTabAt(2).setIcon(R.drawable.speaker_gray);
                    tabKategori.getTabAt(3).setIcon(R.drawable.ic_location_gray);
                    tabKategori.getTabAt(4).setIcon(R.drawable.ic_setting_white);
                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void cekNotifikasi() {
        Query query = FirebaseDatabase.getInstance()
                .getReference("notif")
                .orderByChild("to")
                .equalTo(currentUser.uid);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                        final Sender data = snapshot.getValue(Sender.class);

                        APIService mService = Common.getFCMClient();

                        data.setTo(FirebaseInstanceId.getInstance().getToken());

                        mService.sendNotification(data)
                                .enqueue(new Callback<MyResponse>() {
                                    @Override
                                    public void onResponse(Call<MyResponse> call, Response<MyResponse> response) {
                                        if (response.isSuccessful()){
                                            hapusNotif(currentUser.uid + "_" + data.getNotification().title);
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<MyResponse> call, Throwable t) {
                                        Log.e("Error", t.getMessage().toString());
                                    }
                                });
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    private void hapusNotif(String idRemove) {
        FirebaseDatabase.getInstance()
                .getReference("notif")
                .child(idRemove)
                .removeValue();
    }
}
