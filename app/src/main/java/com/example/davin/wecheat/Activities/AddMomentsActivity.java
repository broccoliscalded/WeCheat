package com.example.davin.wecheat.Activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.davin.wecheat.MyBeans.MyMoment;
import com.example.davin.wecheat.R;
import com.example.davin.wecheat.Utils.MySharepreferencesUtils;
import com.example.davin.wecheat.Utils.ToastUtil;
import com.example.davin.wecheat.Utils.TranslationTools;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class AddMomentsActivity extends AppCompatActivity implements View.OnClickListener{

    public static final int ADD_USER_PROTRAIT_FLAG = 0;
    public static final int ADD_MOMENT_PICS_FLAG = 1;

    private ImageView friendHeadPortrait;
    private EditText friendName,friendMomentsDescription;
    private String userProtraitPath = null;
    private int[] momentIds = {R.id.imageView_moment_1,R.id.imageView_moment_2,
            R.id.imageView_moment_3,R.id.imageView_moment_4};
    private List<ImageView> momentImageList = new ArrayList<>();
    private List<String> momentImagePathList= new ArrayList<>();

    private boolean myOwnMonent = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friend_moments);

        checkExtra();

        initView();
    }

    /**
     * is this moment for myself ?
     * without extra flag MainActivity.ADD_MOMENT_FLAG,this activity will finish at once.
     * @author daniel
     * @time 18-1-31 下午3:04
     * 
     */
    private void checkExtra() {
        if (this.getIntent().hasExtra(MainActivity.ADD_MOMENT_FLAG)){
            try {
                myOwnMonent = this.getIntent().getExtras().getInt(MainActivity.ADD_MOMENT_FLAG) != 1;
            }catch (NullPointerException e){
                e.printStackTrace();
            }

        }else {

            finish();
        }
    }

    private void initView() {
        friendHeadPortrait = findViewById(R.id.imageView_friend_portrait);
        friendName = findViewById(R.id.editText_friend_nickname);

        friendMomentsDescription = findViewById(R.id.editText_text_content);
        findViewById(R.id.button_2_add_moments_detail_picture).setOnClickListener(this);
        findViewById(R.id.button_add_moment).setOnClickListener(this);
        friendHeadPortrait.setOnClickListener(this);

        if (myOwnMonent){
            friendName.setText(MySharepreferencesUtils.with(this).getUserName());
            userProtraitPath = MySharepreferencesUtils.with(this).getUserHeadPicUri();
            Bitmap bitmap = BitmapFactory.decodeFile(userProtraitPath);
            friendHeadPortrait.setImageBitmap(bitmap);
            friendName.setFocusable(false);
            friendName.setFocusableInTouchMode(false);
            friendHeadPortrait.setClickable(false);
        }
        for (int imageViewId:momentIds) {
            ImageView imageView = findViewById (imageViewId);
            momentImageList.add(imageView);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.button_add_moment:
                addOneMoment();
                break;
            case R.id.imageView_friend_portrait:
                openAlbum(ADD_USER_PROTRAIT_FLAG);
                break;
            case R.id.button_2_add_moments_detail_picture:
                openAlbum(ADD_MOMENT_PICS_FLAG);
                break;
        }
    }

    private void openAlbum(int openFlag) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent,openFlag);
    }

    /**
     * check moment information and add moment to table
     * @author daniel
     * @time 18-1-31 下午3:14
     * 
     */
    private void addOneMoment() {
        String stringFriendMomDes = friendMomentsDescription.getText().toString();
        String stringUserName = friendName.getText().toString();
        StringBuilder momentImagesPath = new StringBuilder();
        String momentCreatTimeString = TranslationTools.getCurrentTimeString();

        if (stringUserName.length() <= 0){
            ToastUtil.showShort(this
                    ,getResources().getString(R.string.string_no_name_text_entered));
            return;
        }
        if (userProtraitPath == null){
            ToastUtil.showShort(this,
                    getResources().getString(R.string.take_photos_description));
            return;
        }
        for (int i = 0; i < momentImagePathList.size(); i++) {
            momentImagesPath.append(momentImagePathList.get(i)).append(";");
        }

        MyMoment myMoment = new MyMoment();

        myMoment.setGoodsTimes(100);
        myMoment.setMomentTextContent(stringFriendMomDes);
        myMoment.setMonmentCreatedTime(momentCreatTimeString);
        myMoment.setMomentUserPortraitPath(userProtraitPath);
        myMoment.setNickName(stringUserName);
        myMoment.setMomentPicturesPath(momentImagesPath.toString());
        myMoment.save();
        finish();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK){
            Uri uri;
            switch (requestCode){
                case ADD_USER_PROTRAIT_FLAG:
                    uri = data.getData();
                    userProtraitPath = TranslationTools.getImageAbsolutePath(this,uri);
                    Picasso.with(this).load(uri).into(friendHeadPortrait);
                    break;
                case ADD_MOMENT_PICS_FLAG:
                    uri = data.getData();
                    String momentPath = TranslationTools.getImageAbsolutePath(this,uri);
                    if (momentImagePathList.size() < momentImageList.size()){
                        momentImagePathList.add(momentPath);
                        int position = momentImagePathList.size()-1;
                        Picasso.with(this).load(uri).into(momentImageList.get(position));
                    }
            }
        }
    }
}
