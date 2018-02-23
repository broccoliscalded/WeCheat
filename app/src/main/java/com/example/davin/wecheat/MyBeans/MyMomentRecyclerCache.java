package com.example.davin.wecheat.MyBeans;

import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by daniel on 18-2-7.
 */

public class MyMomentRecyclerCache {
    private String nickName;
    private String momentTextContent;
    private List<Bitmap> momentPicturesList = new ArrayList<>();
    private String monmentCreatedTime;
    private Bitmap momentUserPortrait;
    private int goodsTimes;
    private boolean isMyOwnMoment = false;
    private String favoriteNames;

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getMomentTextContent() {
        return momentTextContent;
    }

    public void setMomentTextContent(String momentTextContent) {
        this.momentTextContent = momentTextContent;
    }

    public List<Bitmap> getMomentPicturesList() {
        return momentPicturesList;
    }

    public void setMomentPicturesList(List<Bitmap> momentPicturesList) {
        this.momentPicturesList = momentPicturesList;
    }

    public String getMonmentCreatedTime() {
        return monmentCreatedTime;
    }

    public void setMonmentCreatedTime(String monmentCreatedTime) {
        this.monmentCreatedTime = monmentCreatedTime;
    }

    public Bitmap getMomentUserPortrait() {
        return momentUserPortrait;
    }

    public void setMomentUserPortrait(Bitmap momentUserPortrait) {
        this.momentUserPortrait = momentUserPortrait;
    }

    public int getGoodsTimes() {
        return goodsTimes;
    }

    public void setGoodsTimes(int goodsTimes) {
        this.goodsTimes = goodsTimes;
    }

    public boolean isMyOwnMoment() {
        return isMyOwnMoment;
    }

    public void setMyOwnMoment(boolean myOwnMoment) {
        isMyOwnMoment = myOwnMoment;
    }

    public String getFavoriteNames() {
        return favoriteNames;
    }

    public void setFavoriteNames(String favoriteNames) {
        this.favoriteNames = favoriteNames;
    }
}
