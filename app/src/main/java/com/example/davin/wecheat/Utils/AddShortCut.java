package com.example.davin.wecheat.Utils;

import android.app.Activity;
import android.content.Intent;
import android.os.Parcelable;

/**
 * Created by daniel on 18-1-18.
 */

public class AddShortCut {
    public static void createShortCut(Activity activity, int iconResId ,int appnameResId){
        Intent intent = new Intent("com.android.launcher.action.INSTALL_SHORTCUT");
        //不允许重复创建
        intent.putExtra("duplicate",false);
        //short cut 的名称
        intent.putExtra(Intent.EXTRA_SHORTCUT_NAME,activity.getResources().getString(appnameResId));
        //快捷的图片
        Parcelable icon = Intent.ShortcutIconResource.fromContext(activity.getApplicationContext(),
                iconResId);
        intent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE,icon);
        //点击快捷图标，启动应用程序
        intent.putExtra(Intent.EXTRA_SHORTCUT_INTENT,new Intent(activity.getApplicationContext(),
                activity.getClass()));
        activity.sendBroadcast(intent);
    }
}
