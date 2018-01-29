package com.example.davin.wecheat.MyBeans;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by daniel on 18-1-25.
 */

public class MyMoment extends DataSupport{
    private String nickName;
    private String momentTextContent;
    private String momentPicturesPath ;
    private String monmentCreatedTime;
    private String momentUserPortraitPath;
    private int goodsTimes;


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

    /*public List<String> getMomentPicturesPath() {
        return momentPicturesPath;
    }

    public void setMomentPicturesPath(List<String> momentPicturesPath) {
        this.momentPicturesPath = momentPicturesPath;
    }*/

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
                '}';
    }
}
