package com.example.davin.wecheat.MyBeans;

import org.litepal.crud.DataSupport;

/**
 * Created by daniel on 18-1-31.
 */

public class MyOwnMoment extends DataSupport {

    private String monmentCreatedTime;
    private String momentTextContent;
    private String momentPicturesPath ;
    private String friendPortraitGroupString;

    public String getFriendPortraitGroupString() {
        return friendPortraitGroupString;
    }

    public void setFriendPortraitGroupString(String friendPortraitGroupString) {
        this.friendPortraitGroupString = friendPortraitGroupString;
    }

    public String getMonmentCreatedTime() {
        return monmentCreatedTime;
    }

    public void setMonmentCreatedTime(String monmentCreatedTime) {
        this.monmentCreatedTime = monmentCreatedTime;
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
}
