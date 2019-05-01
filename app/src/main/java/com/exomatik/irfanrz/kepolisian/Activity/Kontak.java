package com.exomatik.irfanrz.kepolisian.Activity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.exomatik.irfanrz.kepolisian.ModelClass.Data.ModelPlace;
import com.exomatik.irfanrz.kepolisian.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.places.GeoDataClient;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class Kontak extends AppCompatActivity implements OnMapReadyCallback, GoogleApiClient.OnConnectionFailedListener  {
    public static ModelPlace dataKontak;
    private ImageView back;
    protected GeoDataClient mGeoDataClient;
    private TextView textNama, textPhone, textAlamat;
    private GoogleMap mMap;
    private ImageView imgPhone;
    private static final int REQUEST_PHONE_CALL = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kontak);

        back = (ImageView) findViewById(R.id.back);
        imgPhone = (ImageView) findViewById(R.id.img_phone);

        imgPhone.setVisibility(View.GONE);
        init();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Kontak.this, MainActivity.class));
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(Kontak.this, MainActivity.class));
        finish();
    }

    private void init (){
        mGeoDataClient = Places.getGeoDataClient(this, null);
        // Retrieve the AutoCompleteTextView that will display Place suggestions.
        textNama = (TextView)
                findViewById(R.id.text_hasil);
        textAlamat = (TextView) findViewById(R.id.text_alamat);
        textPhone = (TextView) findViewById(R.id.text_phone);

        textNama.setText(dataKontak.name);
        textAlamat.setText("Alamat : " + dataKontak.address);
        textPhone.setText("Kontak : " + dataKontak.phone);
        callPhone(dataKontak.phone);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(Kontak.this);
    }

    private void callPhone(final String phone){
        imgPhone.setVisibility(View.VISIBLE);

        imgPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (phone.isEmpty()){
                    Toast.makeText(Kontak.this, "Mohon maaf, nomor telepon tidak tersedia", Toast.LENGTH_SHORT).show();
                }else {
                    String uri = "tel:" + phone;
                    Intent intent = new Intent(Intent.ACTION_CALL);
                    intent.setData(Uri.parse(uri));
                    if (ActivityCompat.checkSelfPermission(Kontak.this, android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){
                        ActivityCompat.requestPermissions(Kontak.this, new String[]{android.Manifest.permission.CALL_PHONE},REQUEST_PHONE_CALL);
                        Toast.makeText(Kontak.this, "Please, Click Again After You Allow It", Toast.LENGTH_SHORT).show();
                    }else {
                        startActivity(intent);
                    }
                }
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng sydney;

        if (dataKontak.longitude.equals("-")){
            String replace = dataKontak.latitude.replace("lat/lng: (", "");
            replace = replace.replace(")","");
            String lat[] = replace.split(",");
            sydney = new LatLng(Float.parseFloat(lat[0]), Float.parseFloat(lat[1]));
        }else {
            // Add a marker in Sydney and move the camera
            float ltn = Float.parseFloat(dataKontak.latitude);
            float lng = Float.parseFloat(dataKontak.longitude);
            sydney = new LatLng(ltn, lng);
        }
        mMap.addMarker(new MarkerOptions().position(sydney).title(dataKontak.name));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(sydney, 12.0f));

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast.makeText(this, "Periksa Koneksi Anda", Toast.LENGTH_SHORT).show();
    }
}
