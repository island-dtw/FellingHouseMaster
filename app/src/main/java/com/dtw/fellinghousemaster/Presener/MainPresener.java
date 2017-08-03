package com.dtw.fellinghousemaster.Presener;

import android.os.Handler;

import com.dtw.fellinghousemaster.Config;
import com.dtw.fellinghousemaster.Model.NetListener;
import com.dtw.fellinghousemaster.Model.NetModel;
import com.dtw.fellinghousemaster.View.Main.MainView;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Administrator on 2017/7/15 0015.
 */

public class MainPresener implements NetListener{
    private Handler handler=new Handler();
    private MainView mainView;
    private NetModel netModel;
    public MainPresener(MainView mainView){
        netModel = NetModel.getInstance(this);
        this.mainView=mainView;
    }
    public <T>void getSimpleProductList(Class<T> tClass){
        try {
            netModel.getSimpleProductList(new URL(Config.Url_MainSimpleProductList),tClass);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public <T> void onData(final T data) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                mainView.onData(data);
            }
        });
    }
}
