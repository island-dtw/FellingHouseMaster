package com.dtw.fellinghousemaster.Model;

import com.google.gson.jpush.Gson;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Administrator on 2017/7/17 0017.
 */

public class NetModel {
    private static NetModel netModel;
    private NetListener netListener;

    private NetModel(NetListener netListener) {
        this.netListener = netListener;
    }

    public static NetModel getInstance(NetListener netListener) {
        if (netModel == null) {
            netModel = new NetModel(netListener);
        }
        return netModel;
    }

    public <T> void getSimpleProductList(final URL url, final Class<T> tClass) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("POST");// 设置请求方法为post
                    httpURLConnection.setDoOutput(true);

                    int responseCode = httpURLConnection.getResponseCode();// 调用此方法就不必再使用conn.connect()方法
                    if (responseCode == 200) {
                        InputStream inputStream = httpURLConnection.getInputStream();
                        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                        byte[] bytes = new byte[1024];
                        int length = -1;
                        while ((length = inputStream.read(bytes)) != -1) {
                            byteArrayOutputStream.write(bytes, 0, length);
                        }
                        netListener.onData(StringToObject(new String(byteArrayOutputStream.toByteArray()), tClass));
                    } else {

                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public <T> T StringToObject(String json, Class<T> tClass) {
        Gson gson = new Gson();
        return gson.fromJson(json, tClass);
    }
}
