package com.example.davin.wecheat.Activities;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.davin.wecheat.MyBeans.MyMoment;
import com.example.davin.wecheat.R;
import com.example.davin.wecheat.Utils.ToastUtil;
import com.example.davin.wecheat.Utils.TranslationTools;
import com.squareup.picasso.Picasso;

public class AddFriendMomentsActivity extends AppCompatActivity implements View.OnClickListener{

    public static final int ADD_USER_PROTRAIT_FLAG = 0;

    private ImageView friendHeadPortrait;
    private EditText friendName,friendMomentsDescription;
    private String userProtraitPath = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friend_moments);

        initView();
    }

    private void initView() {
        friendHeadPortrait = (ImageView) findViewById(R.id.imageView_friend_portrait);
        friendName = (EditText) findViewById(R.id.editText_friend_nickname);
        friendMomentsDescription = (EditText) findViewById(R.id.editText_text_content);
        findViewById(R.id.button_2_add_moments_detail_picture).setOnClickListener(this);
        findViewById(R.id.button_add_moment).setOnClickListener(this);
        friendHeadPortrait.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.button_add_moment:
                addOneMoment();
                break;
            case R.id.imageView_friend_portrait:
                openAlbum();
                break;
            case R.id.button_2_add_moments_detail_picture:

                break;
        }
    }

    private void openAlbum() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent,ADD_USER_PROTRAIT_FLAG);
    }

    private void addOneMoment() {
        String stringFriendMomDes = friendMomentsDescription.getText().toString();
        String stringUserName = friendName.getText().toString();
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
        MyMoment myMoment = new MyMoment();
        myMoment.setGoodsTimes(100);
        myMoment.setMomentTextContent(stringFriendMomDes);
        myMoment.setMonmentCreatedTime(TranslationTools.getCurrentTimeString());
        myMoment.setNickName(stringUserName);
        myMoment.setMomentPicturesPath("111111111111;2222222222222;33333333333333");
        myMoment.save();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK){
            switch (requestCode){
                case ADD_USER_PROTRAIT_FLAG:
                    Uri uri = data.getData();
                    userProtraitPath = TranslationTools.getImageAbsolutePath(this,uri);
                    Picasso.with(this).load(uri).into(friendHeadPortrait);
                    break;
            }
        }else {
            return;
        }
    }
}
