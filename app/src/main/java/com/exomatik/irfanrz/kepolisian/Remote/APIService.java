package com.exomatik.irfanrz.kepolisian.Remote;


import com.exomatik.irfanrz.kepolisian.Model.MyResponse;
import com.exomatik.irfanrz.kepolisian.Model.Sender;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by IrfanRZ on 08/08/2019.
 */

public interface APIService {

    @Headers({
            "Content-Type:application/json",
            "Authorization:key=AAAAt4LpFOI:APA91bEL7l_TcFIZklJfR7KfYF3NF53SW1MXhHXuoiL6Ifj2NMOBAe98oBWuHkln8crv1QOIsAKUO4iXvG2S4LO6RbNOvymf_27KY-IGLYUOVg_b5obkDxG-8yWTbjFX9DkYGcRE5yv-"
    })

    @POST("fcm/send")
    Call<MyResponse> sendNotification(@Body Sender body);
}
