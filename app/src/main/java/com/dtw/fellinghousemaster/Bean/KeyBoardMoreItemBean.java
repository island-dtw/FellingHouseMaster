package com.dtw.fellinghousemaster.Bean;

import android.content.Intent;

/**
 * Created by Administrator on 2017/6/29 0029.
 */

public class KeyBoardMoreItemBean {
    private int imgResID;
    private String title;
    private Intent intent;

    public Intent getIntent() {
        return intent;
    }

    public void setIntent(Intent intent) {
        this.intent = intent;
    }

    public int getImgResID() {
        return imgResID;
    }

    public void setImgResID(int imgResID) {
        this.imgResID = imgResID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}
