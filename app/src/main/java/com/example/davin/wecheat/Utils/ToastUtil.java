package com.example.davin.wecheat.Utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by daniel on 18-1-27.
 */

public class ToastUtil {
    public static Toast toast = null;

    public static void showShort(Context context,String content){
        if (toast == null){
            toast = Toast.makeText(context,content,Toast.LENGTH_SHORT);
        }else {
            toast.setDuration(Toast.LENGTH_SHORT);
            toast.setText(content);
        }
        toast.show();
    }

}
