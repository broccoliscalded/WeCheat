package com.example.davin.wecheat.MyBeans;

import android.content.Context;
import android.graphics.Bitmap;

import com.example.davin.wecheat.Utils.TranslationTools;

import org.litepal.crud.DataSupport;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by daniel on 18-1-25.
 */

public class MyMoment extends DataSupport implements Serializable{
    private String nickName;
    private String momentTextContent;
    private String momentPicturesPath ;
    private String monmentCreatedTime;
    private String momentUserPortraitPath;
    private int goodsTimes;
    private boolean isMyOwnMoment = false;
    private String favoriteNames;

    public String getFavoriteNames() {
        return favoriteNames;
    }

    public void setFavoriteNames(String favoriteNames) {
        this.favoriteNames = favoriteNames;
    }

    public boolean isMyOwnMoment() {
        return isMyOwnMoment;
    }

    public void setMyOwnMoment(boolean myOwnMoment) {
        isMyOwnMoment = myOwnMoment;
    }

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


    public String getMomentPicturesPath() {
        return momentPicturesPath;
    }

    public void setMomentPicturesPath(String momentPicturesPath) {
        this.momentPicturesPath = momentPicturesPath;
    }

    public String getMonmentCreatedTime() {
        return monmentCreatedTime;
    }

    public void setMonmentCreatedTime(String monmentCreatedTime) {
        this.monmentCreatedTime = monmentCreatedTime;
    }

    public int getGoodsTimes() {
        return goodsTimes;
    }

    public void setGoodsTimes(int goodsTimes) {
        this.goodsTimes = goodsTimes;
    }

    public String getMomentUserPortraitPath() {
        return momentUserPortraitPath;
    }

    public void setMomentUserPortraitPath(String momentUserPortraitPath) {
        this.momentUserPortraitPath = momentUserPortraitPath;
    }

    @Override
    public String toString() {
        return "MyMoment{" +
                "nickName='" + nickName + '\'' +
                ", momentTextContent='" + momentTextContent + '\'' +
                ", momentPicturesPath='" + momentPicturesPath + '\'' +
                ", monmentCreatedTime='" + monmentCreatedTime + '\'' +
                ", momentUserPortraitPath='" + momentUserPortraitPath + '\'' +
                ", goodsTimes=" + goodsTimes +
                ", isMyOwnMoment=" + isMyOwnMoment +
                ", favoriteNames='" + favoriteNames + '\'' +
                '}';
    }

    public MyMomentRecyclerCache toMymomentRecyclerCache(Context context){
        MyMomentRecyclerCache momentCache = new MyMomentRecyclerCache();
        momentCache.setFavoriteNames(getFavoriteNames());
        momentCache.setGoodsTimes(getGoodsTimes());
        String[] tempPathArray = getMomentPicturesPath().split(";");
        List<Bitmap> tempImagesList = new ArrayList<>();
        for (String path: tempPathArray) {
            Bitmap bitmap = TranslationTools.SimplerCompressionPackge(
                    path,
                    TranslationTools.dip2px(context,80),
                    TranslationTools.dip2px(context,80));
            tempImagesList.add(bitmap);
        }
        momentCache.setMomentPicturesList(tempImagesList);
        momentCache.setMomentTextContent(getMomentTextContent());
        Bitmap bitmapPortrait = TranslationTools.SimplerCompressionPackge(
                getMomentUserPortraitPath(),
                TranslationTools.dip2px(context,40),
                TranslationTools.dip2px(context,40));
        momentCache.setMomentUserPortrait(bitmapPortrait);
        momentCache.setMonmentCreatedTime(getMonmentCreatedTime());

        momentCache.setNickName(getNickName());

        momentCache.setMyOwnMoment(isMyOwnMoment());
        return momentCache;
    }
}
