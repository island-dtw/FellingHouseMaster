package com.dtw.fellinghousemaster.Model;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;

import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

/**
 * Created by Administrator on 2017/6/30 0030.
 */

public class WeiXinShareModel {
    private static WeiXinShareModel weiXinShareModel;
    private Context context;
    private IWXAPI api;
    private WeiXinShareModel(Context context){
        String weixinID =null;
        try {
            ApplicationInfo info = context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
            weixinID=info.metaData.getString("WEIXIN_ID");
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        api=WXAPIFactory.createWXAPI(context,weixinID,true);
        api.registerApp(weixinID);
    }
    public static WeiXinShareModel getInstance(Context context){
        if(weiXinShareModel==null){
            weiXinShareModel=new WeiXinShareModel(context);
        }
        return weiXinShareModel;
    }
}
