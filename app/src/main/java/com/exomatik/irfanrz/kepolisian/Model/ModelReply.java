package com.exomatik.irfanrz.kepolisian.Model;

/**
 * Created by IrfanRZ on 16/08/2019.
 */

public class ModelReply {
    private String tanggapan;
    private String tokenPenerima;
    private String tokenPengirim;
    private int idLaporan;

    public ModelReply() {
    }

    public ModelReply(String tanggapan, String tokenPenerima, String tokenPengirim, int idLaporan) {
        this.tanggapan = tanggapan;
        this.tokenPenerima = tokenPenerima;
        this.tokenPengirim = tokenPengirim;
        this.idLaporan = idLaporan;
    }

    public String getTanggapan() {
        return tanggapan;
    }

    public void setTanggapan(String tanggapan) {
        this.tanggapan = tanggapan;
    }

    public String getTokenPenerima() {
        return tokenPenerima;
    }

    public void setTokenPenerima(String tokenPenerima) {
        this.tokenPenerima = tokenPenerima;
    }

    public String getTokenPengirim() {
        return tokenPengirim;
    }

    public void setTokenPengirim(String tokenPengirim) {
        this.tokenPengirim = tokenPengirim;
    }

    public int getIdLaporan() {
        return idLaporan;
    }

    public void setIdLaporan(int idLaporan) {
        this.idLaporan = idLaporan;
    }
}
