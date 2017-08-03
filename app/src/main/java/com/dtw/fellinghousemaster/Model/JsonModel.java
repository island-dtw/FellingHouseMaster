package com.dtw.fellinghousemaster.Model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2017/7/18 0018.
 */

public class JsonModel {
    JSONObject jsonObject=null;
    public JsonModel(String json) {
        try {
            jsonObject=new JSONObject(json);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public void addData(){
        try {
            JSONArray jsonArray=jsonObject.getJSONArray("productList");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
