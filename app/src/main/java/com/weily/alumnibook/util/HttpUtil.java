package com.weily.alumnibook.util;


import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class HttpUtil {

    public static void sendInfo(String address,String username,String password,okhttp3.Callback callback){
        OkHttpClient client=new OkHttpClient();
        RequestBody requestBody=new FormBody.Builder().add("username",username).add("password",password).build();
        Request request=new Request.Builder().url(address).post(requestBody).build();
        client.newCall(request).enqueue(callback);
    }
}