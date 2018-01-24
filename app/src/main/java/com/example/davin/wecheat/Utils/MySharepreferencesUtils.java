package com.example.davin.wecheat.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

/**
 * Created by daniel on 18-1-19.
 */

public class MySharepreferencesUtils {

    private static final String FILL_NAME = "OneMoreInformation";
    private static final String PRIVATE_NICK_NAME = "private_user_nick_name";
    private static final String USER_HEAD_URI = "mini_user_headpic_path";

    public static MySharepreferencesUtils mySharepreferencesUtils;

    private Context mcontext;
    private static SharedPreferences msharedPreferences;
    private static SharedPreferences.Editor meditor;

    public static MySharepreferencesUtils with(Context context){
        if (mySharepreferencesUtils == null){
            mySharepreferencesUtils = new MySharepreferencesUtils(context);
        }
        return mySharepreferencesUtils;
    }

    public MySharepreferencesUtils(Context context) {
        this.mcontext = context;
        msharedPreferences = context.getSharedPreferences(FILL_NAME,Context.MODE_PRIVATE);
        meditor = context.getSharedPreferences(FILL_NAME,Context.MODE_PRIVATE).edit();
    }

    public void setUserName (String name){
        meditor.putString(PRIVATE_NICK_NAME,name);
        /*
        * 效率较高，但是不会理会是否修改成功
        * */
        meditor.apply();
        /*
        * 会返回修改是否成功的结果，但是效率较低
        * */
//        editor.commit();
    }

    public String getUserName (){
        return msharedPreferences.getString(PRIVATE_NICK_NAME,"No NickName");
    }

    public void setUserHeadPicUri(String muri){
        Log.d("album","uri tostring = " + muri);
        meditor.putString(USER_HEAD_URI,muri);
        meditor.apply();
    }
    public String getUserHeadPicUri(){
        String uri = msharedPreferences.getString(USER_HEAD_URI,"");
        Log.d("album","uri get " + uri);
        return uri;
    }
}
