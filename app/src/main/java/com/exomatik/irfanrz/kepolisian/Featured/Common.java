package com.exomatik.irfanrz.kepolisian.Featured;


import com.exomatik.irfanrz.kepolisian.Remote.APIService;
import com.exomatik.irfanrz.kepolisian.Remote.RetrofitClient;

/**
 * Created by IrfanRZ on 08/08/2019.
 */

public class Common {
    public static String currentToken = "";

    private static String baseUrl = "http://fcm.googleapis.com/";

    public static APIService getFCMClient()
    {
        return RetrofitClient.getClient(baseUrl).create(APIService.class);
    }
}
