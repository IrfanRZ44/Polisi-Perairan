package com.exomatik.irfanrz.kepolisian.ModelClass.Data;

/**
 * Created by IrfanRZ on 18/12/2018.
 */

public class ModelHukum {
    private String title;
    private String deskripsi;
    private boolean isExpandable;

    public ModelHukum() {
    }

    public ModelHukum(String title, String deskripsi, boolean isExpandable) {
        this.title = title;
        this.deskripsi = deskripsi;
        this.isExpandable = isExpandable;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public boolean isExpandable() {
        return isExpandable;
    }

    public void setExpandable(boolean expandable) {
        isExpandable = expandable;
    }
}
