package com.exomatik.irfanrz.kepolisian.Model;

/**
 * Created by IrfanRZ on 08/08/2019.
 */

public class Notification {
    public String body;
    public String title;
    public String click_action;

    public Notification() {
    }

    public Notification(String body, String title, String click_action) {
        this.body = body;
        this.title = title;
        this.click_action = click_action;
    }
}
