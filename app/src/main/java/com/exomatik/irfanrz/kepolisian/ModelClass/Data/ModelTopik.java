package com.exomatik.irfanrz.kepolisian.ModelClass.Data;

/**
 * Created by IrfanRZ on 18/12/2018.
 */

public class ModelTopik {
    public String namaBerita;
    public String url;
    public int foto;

    public ModelTopik() {
    }

    public ModelTopik(String namaBerita, String url, int foto) {
        this.namaBerita = namaBerita;
        this.url = url;
        this.foto = foto;
    }
}
