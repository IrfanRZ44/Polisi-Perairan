package com.exomatik.irfanrz.kepolisian.ModelClass.Data;

/**
 * Created by IrfanRZ on 21/12/2018.
 */

public class ModelPengaduan {
    public String namaUser;
    public String birthUser;
    public String addressUser;
    public String workUser;
    public String phoneUser;
    public String mailUser;
    public String subjectPengaduan;
    public String infoKejadian;
    public String descKejadian;
    public String fotoKejadian;
    public int idPengaduan;
    public String location;

    public ModelPengaduan(String namaUser, String birthUser, String addressUser, String workUser, String phoneUser, String mailUser, String subjectPengaduan, String infoKejadian, String descKejadian, String fotoKejadian, int idPengaduan, String location) {
        this.namaUser = namaUser;
        this.birthUser = birthUser;
        this.addressUser = addressUser;
        this.workUser = workUser;
        this.phoneUser = phoneUser;
        this.mailUser = mailUser;
        this.subjectPengaduan = subjectPengaduan;
        this.infoKejadian = infoKejadian;
        this.descKejadian = descKejadian;
        this.fotoKejadian = fotoKejadian;
        this.idPengaduan = idPengaduan;
        this.location = location;
    }

    public ModelPengaduan() {
    }
}
