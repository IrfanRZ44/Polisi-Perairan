package com.exomatik.irfanrz.kepolisian.ModelClass.Data;

/**
 * Created by IrfanRZ on 03/01/2019.
 */

public class ModelHotspot {
    public String provinsi;
    public String location;
    public String alamat;
    public String kapal;
    public String danpal;
    public String telp;
    public int idHotspot;

    public ModelHotspot() {
    }

    public ModelHotspot(String provinsi, String location, String alamat, String kapal, String danpal, String telp, int idHotspot) {
        this.provinsi = provinsi;
        this.location = location;
        this.alamat = alamat;
        this.kapal = kapal;
        this.danpal = danpal;
        this.telp = telp;
        this.idHotspot = idHotspot;
    }
}
