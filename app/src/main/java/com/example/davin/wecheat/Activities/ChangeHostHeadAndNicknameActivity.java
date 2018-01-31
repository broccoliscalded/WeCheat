package com.example.davin.wecheat.Activities;

import android.Manifest;
import android.app.Activity;
import android.content.ContentUris;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ProviderInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.davin.wecheat.MyBeans.MyMoment;
import com.example.davin.wecheat.R;
import com.example.davin.wecheat.Utils.MyLog;
import com.example.davin.wecheat.Utils.MySharepreferencesUtils;
import com.example.davin.wecheat.Utils.TranslationTools;
import com.squareup.picasso.Picasso;

import org.litepal.tablemanager.Connector;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ChangeHostHeadAndNicknameActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "album";

    private final static int CHOOSE_FROM_ALBUM = 0;
    private final static int CHOOSE_USER_HEAD_BACKGROUND = 1;

    private EditText nicknameEditText;
    private ImageView userHeadPicImageView,userHeaderBackgroundImageView;
    private Button buttonConfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_host_head_and_nickname);

        initView();

        MyLog.printLog(MyLog.LEVEL_D,"dp = " + 80 + "  ; px = " +
                TranslationTools.dip2px(this,80));
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
        }


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        switch (requestCode){
            case 1:
                if (grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){

                }else {
                    Toast.makeText(this,"Read Storage Permission Denied",
                            Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
        }

    }

    private void initView() {
        nicknameEditText = (EditText) findViewById(R.id.edit_text_user_nickname);
        userHeadPicImageView = (ImageView) findViewById(R.id.imageView_change);
        buttonConfirm = (Button) findViewById(R.id.button_confirm);
        userHeaderBackgroundImageView = (ImageView) findViewById(R.id.imageView_header_background);

        userHeadPicImageView.setOnClickListener(this);
        buttonConfirm.setOnClickListener(this);
        userHeaderBackgroundImageView.setOnClickListener(this);
        findViewById(R.id.button_add_moments).setOnClickListener(this);

        nicknameEditText.setHint(MySharepreferencesUtils.with(this).getUserName());


        if (MySharepreferencesUtils.with(this).getUserHeadPicUri().length() >2){
            String imagePath = MySharepreferencesUtils.with(this).getUserHeadPicUri();
            Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
            userHeadPicImageView.setImageBitmap(bitmap);
        }

        String mobilePath = Environment.getDataDirectory().getAbsolutePath();
        MyLog.printLog(MyLog.LEVEL_D,"mobile Path : " + mobilePath);


    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()){
            case R.id.imageView_change:
                openAlbum(CHOOSE_FROM_ALBUM);
                break;
            case R.id.button_confirm:
                String newNickname = nicknameEditText.getText().toString();
                if (newNickname.length()<=0){
                    return;
                }
                MySharepreferencesUtils.with(this).setUserName(newNickname);
//                addOneColumn();
                break;
            case R.id.imageView_header_background:
                openAlbum(CHOOSE_USER_HEAD_BACKGROUND);
                break;
            case R.id.button_add_moments:
//                addOneColumn();
                break;
        }
    }

    private void openAlbum(int imageType){
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent,imageType);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK){
            switch (requestCode){
                case CHOOSE_FROM_ALBUM:
                    MyLog.printLog(MyLog.LEVEL_D,"data content : "+data.toString());
                    Uri uri = data.getData();
                    String realPath = TranslationTools.getImageAbsolutePath(this,uri);// deprecated function
                    MyLog.printLog(MyLog.LEVEL_D,"realpath : " + realPath);
                    MySharepreferencesUtils.with(this).setUserHeadPicUri(realPath);

                    Picasso
                        .with(this)
                        .load(uri)
                        .into(userHeadPicImageView);
                    break;
                case CHOOSE_USER_HEAD_BACKGROUND:
                    Uri uri_bg = data.getData();
                    String realPathBg = TranslationTools.getImageAbsolutePath(this,uri_bg);
                    MySharepreferencesUtils.with(this).setUserHeadBgPath(realPathBg);
                    Picasso.with(this)
                            .load(uri_bg)
                            .into(userHeaderBackgroundImageView);
                    break;
            }
        }
    }


    private File getUriFromPath(String realPath) {
        File file = new File(realPath);
//        Uri uri = null;
        if (file.exists()){
            Log.d(TAG,"file exits");
            return file;
        }else {
            return null;
        }
//        Log.d(TAG,"uri decode : " + uri.toString());
//        return file;
    }

    /*private String getImagePathOnKitkat(Uri uri){
        if (uri == null) return null;
        String path = null;
        if (DocumentsContract.isDocumentUri(this,uri)){
            String docId = DocumentsContract.getDocumentId(uri);
            if ("com.android.providers.media.documents".equals(uri.getAuthority())){
                String id = docId.split(":")[1];
                String selection = MediaStore.Images.Media._ID + "=" + id ;
                path = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,selection);

            }else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())){
                Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"),
                        Long.valueOf(docId));
                path = getImagePath(contentUri,null);
            }
        }else{
            path = getImagePath(uri,null);
        }
        MyLog.printLog(MyLog.LEVEL_D,"getImagePath : " + path);
        return path;
    }


    *//*4.4 之后的版本由于返回url类型的原因，有可能无法转化为真实路径*//*
    private String getImagePath(Uri uri,String selection) {
        String path = null;
        if(uri == null){
            return path;
        }



        Cursor cursor = getContentResolver().query(uri,null,
                selection,null,null);
        if (cursor != null){
            if (cursor.moveToFirst()){
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));

            }
            cursor.close();
        }
        return path;
    }*/
}
