package com.example.davin.wecheat.Utils;

import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.DocumentsContract;
import android.provider.MediaStore;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by daniel on 18-1-19.
 */

public class TranslationTools {

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, int dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /*
    * 由相机返回的uri获得图片的真实路径
    * */
    public static String getImageAbsolutePath(Context context,Uri uri){
        if (uri == null) return null;
        String path = null;
        if (DocumentsContract.isDocumentUri(context,uri)){
            String docId = DocumentsContract.getDocumentId(uri);
            /*
            * Android 4 之后可能会获取到的不同的uri,不能直接获取到路径
            * */
            if ("com.android.providers.media.documents".equals(uri.getAuthority())){
                String id = docId.split(":")[1];
                String selection = MediaStore.Images.Media._ID + "=" + id ;
                path = getImagePath(context,MediaStore.Images.Media.EXTERNAL_CONTENT_URI,selection);

            }else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())){
                Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"),
                        Long.valueOf(docId));
                path = getImagePath(context,contentUri,null);
            }
        }else {
            path = getImagePath(context,uri,null);
        }

        return path;
    }

    /*
    * Android 4 之前 ,可以直接由图片的uri获取到图片的绝对路径
    * */
    private static String getImagePath(Context context,Uri uri,String selection) {
        String path = null;
        if(uri == null){
            return path;
        }
        Cursor cursor = context.getContentResolver().query(uri,null,
                selection,null,null);
        if (cursor != null){
            if (cursor.moveToFirst()){
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));

            }
            cursor.close();
        }
        return path;
    }


    /*
    * 获取当前系统时间的字符串,格式为2018/01/23/12/55/55,
    * */
    public static String getCurrentTimeString(){
        Date currentTime = new Date(System.currentTimeMillis());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
                "yyyy/MM/dd/hh/mm", Locale.getDefault());
        String dateString = simpleDateFormat.format(currentTime);

        return dateString;
    }
}
