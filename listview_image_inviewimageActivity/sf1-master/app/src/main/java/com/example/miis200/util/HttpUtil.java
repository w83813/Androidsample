package com.example.miis200.util;


import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class HttpUtil {
    //HttpURLConnction
    private static String myURL=Keep.MyURL;

    public static void sendHttpRequest(final String address, final HttpCallBackListener listener){

        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection = null;
                try{
                    URL url = new URL(address);
                    connection = (HttpURLConnection)url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setConnectTimeout(4000);
                    connection.setReadTimeout(4000);
                    connection.setDoInput(true);
                    connection.setDoOutput(true);
                    InputStream in = connection.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while((line = reader.readLine()) != null){
                        response.append(line);
                    }
                    if(listener != null){
                        listener.onFinish(response.toString());
                    }
                }catch (Exception e){
                    if(listener != null){
                        listener.onError(e);
                    }
                }finally {
                    if(connection != null){
                        connection.disconnect();
                    }
                }
            }
        }).start();
    }
    //OKHttp
    public static  void sendOKHttpformdata(String address, String token, String imagepath, Callback callback){
        final OkHttpClient client = new OkHttpClient();
        String imageType = "multipart/form-data";
       try {


        File file = new File(imagepath);//imgUrl为图片位置
        RequestBody fileBody = RequestBody.create(MediaType.parse("image/jpg"), file);
           RequestBody requestBody = new MultipartBody.Builder()
                   .setType(MultipartBody.FORM)
                   .addFormDataPart("image", "head_image", fileBody)
                   .addFormDataPart("imagetype", imageType)
                   .build();
           final String myurl=address;


           Request request = new Request.Builder()
                   .url(myurl)
                   .addHeader("Cookie","JWT="+token)

                   .post(requestBody)
                   .build();
           client.newCall(request).enqueue(callback);
       }
       catch (Exception e){
           Log.i("ex",e.toString());
       }


    }
    //OKHttp
    public static  void sendOKHttp(String address, String json, Callback callback){
        final OkHttpClient client = new OkHttpClient();
        final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody mybody = RequestBody.create(JSON, json);
        final String myurl=address;
        final RequestBody myebody=mybody;

        Request request = new Request.Builder()
                .url(myurl)
                .addHeader("Content-Type","application/json")

                .post(mybody)
                .build();
        client.newCall(request).enqueue(callback);
    }
    public static  void sendOKHttp2(String address, final Callback callback){
        final OkHttpClient client = new OkHttpClient();
        final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

        final String myurl=address;

        RequestBody body = new FormBody.Builder()
                .add("grant_type", "client_credentials")
//                .add("键", "值")

                .build();
        Request request = new Request.Builder()
                .url(myURL+"auth/oauth/token")
                .addHeader("Content-Type","application/json")
                .addHeader("Authorization","Basic dmlzaW9uOnZpc2lvbg==")
                .post(body)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    String responseData = response.body().string();
                    JSONObject obj = new JSONObject(responseData);
                    String access_token = obj.getString("access_token");
                    Request request = new Request.Builder()
                            .url(myurl)
                            .addHeader("Content-Type","application/json")
                            .addHeader("x-auth-token",access_token)
                            .get()

                            .build();
                    client.newCall(request).enqueue(callback);

                }
                catch (Exception e){

                }
            }
        });
    }
    //OKHttp
    public static  void sendOKHttp1(String address, String json, final Callback callback){
     final OkHttpClient client = new OkHttpClient();
       final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody mybody = RequestBody.create(JSON, json);
        final String myurl=address;
        final RequestBody myebody=mybody;
        RequestBody body = new FormBody.Builder()
                .add("grant_type", "client_credentials")
//                .add("键", "值")

                .build();
        Request request = new Request.Builder()
                .url(myURL+"auth/oauth/token")
                .addHeader("Content-Type","application/json")
                .addHeader("Authorization","Basic dmlzaW9uOnZpc2lvbg==")
                .post(body)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    String responseData = response.body().string();
                    JSONObject obj = new JSONObject(responseData);
                    String access_token = obj.getString("access_token");
                    Request request = new Request.Builder()
                            .url(myurl)
                            .addHeader("Content-Type","application/json")
                            .addHeader("x-auth-token",access_token)
                            .post(myebody)

                            .build();
                    client.newCall(request).enqueue(callback);

                }
                catch (Exception e){

                }
            }
        });
    }
}
