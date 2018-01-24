package com.example.davin.wecheat;

import android.app.Activity;
import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.media.Image;
import android.net.Uri;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.davin.wecheat.Utils.MySharepreferencesUtils;
import com.squareup.picasso.Picasso;

import java.io.File;

public class ChangeHostHeadAndNicknameActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "album";

    private final static int CHOOSE_FROM_ALBUM = 0;

    private EditText nicknameEditText;
    private ImageView userHeadPicImageView;
    private Button buttonConfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_host_head_and_nickname);

        initView();

        
    }

    private void initView() {
        nicknameEditText = (EditText) findViewById(R.id.edit_text_user_nickname);
        userHeadPicImageView = (ImageView) findViewById(R.id.imageView_change);
        buttonConfirm = (Button) findViewById(R.id.button_confirm);

        userHeadPicImageView.setOnClickListener(this);
        buttonConfirm.setOnClickListener(this);

        nicknameEditText.setHint(MySharepreferencesUtils.with(this).getUserName());

        /*if (MySharepreferencesUtils.with(this).getUserHeadPicUri().length() >2){
            String uriString = MySharepreferencesUtils.with(this).getUserHeadPicUri();
            Uri  uri = Uri.parse(uriString);
            Picasso.with(this)
                    .load(uri)
                    .into(userHeadPicImageView);
        }*/

        /*Picasso .with(this)
                .load("http://acloud.avori.cn:8088/project_img/tbapi/MemberImg/2018-01-17" +
                        "/c88c7fcf-8174-4852-969e-f55db4fa7d83_1516187479972_upload.jpg")
                .resize(500,500)
                .centerCrop()
                .into(userHeadPicImageView);*/
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()){
            case R.id.imageView_change:
                intent = new Intent(Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent,CHOOSE_FROM_ALBUM);
                break;
            case R.id.button_confirm:
                String newNickname = nicknameEditText.getText().toString();
                if (newNickname.length()<=0){
                    return;
                }
                MySharepreferencesUtils.with(this).setUserName(newNickname);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK){
            switch (requestCode){
                case CHOOSE_FROM_ALBUM:
                    Log.d(TAG,"requestCode = " + requestCode + " ; resultCode = " +
                    resultCode + " ; data = " + data.getData().toString());
                    Uri uri = data.getData();
                    String realPath = getImagePathOnKitkat(uri);// deprecated function
                    Log.d(TAG,"real path : " + realPath);
                    MySharepreferencesUtils.with(this).setUserHeadPicUri(realPath);

                    Picasso
                        .with(this)
                        .load(new File(realPath))
                        .resize(500,500)
                        .centerCrop()
                        .into(userHeadPicImageView);
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

    private String getImagePathOnKitkat(Uri uri){
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
        return path;
    }


    /*4.4 之后的版本由于返回url类型的原因，有可能无法转化为真实路径*/
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
    }
}
