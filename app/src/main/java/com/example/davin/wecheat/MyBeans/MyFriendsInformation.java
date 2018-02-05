package com.example.davin.wecheat.MyBeans;

import org.litepal.crud.DataSupport;

/**
 * Created by daniel on 18-2-1.
 */

public class MyFriendsInformation extends DataSupport {

    String friendNickName;
    int rankeHot;

    public String getFriendNickName() {
        return friendNickName;
    }

    public void setFriendNickName(String friendNickName) {
        this.friendNickName = friendNickName;
    }

    public int getRankeHot() {
        return rankeHot;
    }

    public void setRankeHot(int rankeHot) {
        this.rankeHot = rankeHot;
    }
}
