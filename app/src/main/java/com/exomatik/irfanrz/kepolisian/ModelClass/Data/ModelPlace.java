package com.exomatik.irfanrz.kepolisian.ModelClass.Data;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by IrfanRZ on 22/12/2018.
 */

public class ModelPlace {
    public String name;
    public String address;
    public String phone;
    public String web;
    public String latitude;
    public String longitude;
    public float rating;

    public ModelPlace() {
    }

    public ModelPlace(String name, String address, String phone, String web, String latitude, String longitude, float rating) {
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.web = web;
        this.latitude = latitude;
        this.longitude = longitude;
        this.rating = rating;
    }
}
