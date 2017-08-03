package com.dtw.fellinghousemaster.Model;

import android.graphics.Bitmap;
import android.util.Log;
import android.view.View;

import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.storage.persistent.FileRecorder;
import com.qiniu.util.Auth;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * Created by Administrator on 2017/7/18 0018.
 */

public class QiNiuModel {
    private static QiNiuModel qiNiuModel;
    private String accessKey = "HvU90834iovj59vqPjS2wqQNU8_Mg4szJS-gt8Xg";
    private String secretKey = "RDwROZoaiSaVjJzMyMZamdPWpYvlolxoVDGt9psM";
    private String bucket = "fellinghouseimage";
    private QiNiuModel(){
    }
    public static  QiNiuModel getInstance(){
        if(qiNiuModel==null){
            qiNiuModel=new QiNiuModel();
        }
        return qiNiuModel;
    }

    public void upLoad(final Bitmap bitmap){
        final Configuration cfg = new Configuration(Zone.zone1());
        final UploadManager uploadManager = new UploadManager(cfg);
        final String key = null;
        new Thread(new Runnable() {
            @Override
            public void run() {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
                ByteArrayInputStream byteInputStream=new ByteArrayInputStream(baos.toByteArray());
                Auth auth = Auth.create(accessKey, secretKey);
                String upToken = auth.uploadToken(bucket);
                try {
                    Response response = uploadManager.put(byteInputStream,key,upToken,null, null);
                    //解析上传成功的结果
                    DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
                    System.out.println(putRet.key);
                    System.out.println(putRet.hash);
                } catch (QiniuException ex) {
                    Response r = ex.response;
                    System.err.println(r.toString());
                    try {
                        System.err.println(r.bodyString());
                    } catch (QiniuException ex2) {
                        //ignore
                    }
                }
            }
        }).start();
    }
    public void upLoad(String name, final String value){
        final Configuration cfg = new Configuration(Zone.zone1());
        final UploadManager uploadManager = new UploadManager(cfg);
        final String key = name;
        new Thread(new Runnable() {
            @Override
            public void run() {
                Auth auth = Auth.create(accessKey, secretKey);
                String upToken = auth.uploadToken(bucket);
                try {
                    Response response = uploadManager.put(value.getBytes(),key,upToken);
                    //解析上传成功的结果
                    DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
                    System.out.println(putRet.key);
                    System.out.println(putRet.hash);
                } catch (QiniuException ex) {
                    Response r = ex.response;
                    System.err.println(r.toString());
                    try {
                        System.err.println(r.bodyString());
                    } catch (QiniuException ex2) {
                        //ignore
                    }
                }
            }
        }).start();
    }
}
