package com.exomatik.irfanrz.kepolisian.Activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.exomatik.irfanrz.kepolisian.ModelClass.Data.ModelHotspot;
import com.exomatik.irfanrz.kepolisian.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.places.GeoDataClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class DetailHotspot extends AppCompatActivity implements OnMapReadyCallback, GoogleApiClient.OnConnectionFailedListener {
    public static ModelHotspot dataHotspot;
    private ImageView back;
    private TextView textProv, textLocation, textAlamat, textKapal, textDanpal, textTelp;
    private GoogleMap mMap;
    protected GeoDataClient mGeoDataClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_hotspot);

        back = (ImageView) findViewById(R.id.back);
        textProv = (TextView) findViewById(R.id.text_prov);
        textLocation = (TextView) findViewById(R.id.text_location);
        textAlamat = (TextView) findViewById(R.id.text_alamat);
        textKapal = (TextView) findViewById(R.id.text_kapal);
        textDanpal = (TextView) findViewById(R.id.text_danpal);
        textTelp = (TextView) findViewById(R.id.text_telp);

        init();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DetailHotspot.this, MainActivity.class));
                finish();
            }
        });
    }

    private void init() {
        textProv.setText(dataHotspot.provinsi);
        textTelp.setText(dataHotspot.telp);
        textDanpal.setText(dataHotspot.danpal);
        textKapal.setText(dataHotspot.kapal);
        textLocation.setText(dataHotspot.location);
        textAlamat.setText(dataHotspot.alamat);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(DetailHotspot.this);
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(DetailHotspot.this, MainActivity.class));
        finish();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng sydney;

        String replace = dataHotspot.location.replace("lat/lng: (", "");
        replace = replace.replace(")", "");
        String lat[] = replace.split(",");
        sydney = new LatLng(Float.parseFloat(lat[0]), Float.parseFloat(lat[1]));

        mMap.addMarker(new MarkerOptions().position(sydney).title(dataHotspot.kapal));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(sydney, 12.0f));

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast.makeText(this, "Periksa Koneksi Anda", Toast.LENGTH_SHORT).show();
    }
}
