package com.example.davin.wecheat.Utils;

import android.util.Log;

/**
 * Created by daniel on 18-1-24.
 */

public class MyLog {
    private static final MyLog ourInstance = new MyLog();
    public static final int LEVEL_V = 0;
    public static final int LEVEL_D = 1;
    private static final String TAG = "WeCheat";
    private boolean showLogMark = true;

    static MyLog getInstance() {
        return ourInstance;
    }

    private MyLog() {
    }

    public static void printLog(int level,String logContent){

        switch (level){
            case LEVEL_D:
                Log.d(TAG,logContent);
                break;
            case LEVEL_V:
                Log.d(TAG,logContent);
                break;
        }
    }
}
