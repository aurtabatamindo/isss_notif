package com.example.isss_usr.SendNotificationPack;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface APIService {
    @Headers(
            {
                    "Content-Type:application/json",
                    "Authorization:key=AAAAYAqZC7w:APA91bFMS5XoroMcJcTbOE7Kl6HP5jYkfR__1ZvJMvSiaJLj6diHkdwuUp4BB-SrSWQiV4vf5_9PoEtecj6w4mzebVqM3BV529XLwJOuVpgCv2okTNo_Wu5Iq5XGX9WcQsumbicmzMrP" // Your server key refer to video for finding your server key
            }
    )

    @POST("fcm/send")
    Call<MyResponse> sendNotifcation(@Body NotificationSender body);
}

