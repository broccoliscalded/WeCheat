package com.example.davin.wecheat.Activities;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.davin.wecheat.Adapter.MyRecyclerAdapter;
import com.example.davin.wecheat.MyBeans.MyMoment;
import com.example.davin.wecheat.Utils.MyRecyclerDecoration;
import com.example.davin.wecheat.R;
import com.example.davin.wecheat.Utils.MyLog;
import com.example.davin.wecheat.Utils.MySharepreferencesUtils;
import com.example.davin.wecheat.Utils.ToastUtil;
import com.example.davin.wecheat.Utils.TranslationTools;
import com.squareup.picasso.Picasso;

import org.litepal.crud.DataSupport;
import org.litepal.tablemanager.Connector;

import java.io.IOException;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    /*flag value 0 means add my own moment; flag value 1 means add friend flag*/
    public static final String ADD_MOMENT_FLAG = "com.example.davin.wecheat.MainActivity.AMomentFlag";
//    public static final String ADD_MY_OWN_MOMENT_FLAG = "com.example.davin.wecheat.MainActivity.aMyOwnMomentFlag";

    private RecyclerView recyclerView;
    private MyRecyclerAdapter mAdapter;
    private View view;
    private ImageView headerBackground,headerUserHeadpic;
    private TextView headerUserNickname;
    List<MyMoment> momentsList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //        AddShortCut.createShortCut(this,R.mipmap.ic_launcher,R.string.app_name);
        try{
            momentsList = DataSupport.where("id > ?","-1")
                    .order("id desc")
                    .find(MyMoment.class);
        }catch (Exception e){
            e.printStackTrace();
        }

        getPermissions();
        initToolBar();
        initMyRecyclerView();

        /*try {
            TranslationTools.SaveImages(null,"balabala");
        } catch (IOException e) {
            e.printStackTrace();
        }*/
//        TranslationTools.SaveImages(null,"balabala");
        Connector.getDatabase();
        getExternalCacheDir();

    }

    private void getPermissions() {
        if (ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(MainActivity.this,new String[]{
                    Manifest.permission.WRITE_EXTERNAL_STORAGE},2);
        }

        /*if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){

        }*/
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions
            , @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode ){
            case 2:
                if ((grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)){
//u must got the {Manifest.permission.WRITE_EXTERNAL_STORAGE},otherwise any user action may cause crash
                    ActivityCompat.requestPermissions(MainActivity.this,new String[]{
                            Manifest.permission.WRITE_EXTERNAL_STORAGE},2);
                }
                break;
        }
    }

    private void hasNavigationOrNot() {
        boolean hasMenuKey = ViewConfiguration.get(this).hasPermanentMenuKey();
        boolean hasBackKey = KeyCharacterMap.deviceHasKey(KeyEvent.KEYCODE_BACK);
        if (!hasBackKey && !hasMenuKey){
            Resources resources = getResources();
            int resourceId = resources.getIdentifier(
                    "navigation_bar_height","dimen","android");
            int height = resources.getDimensionPixelSize(resourceId);
//            Log.d("naviheight","navigation height == " + height);
        }
    }
    
    private void initMyRecyclerView() {

        if (momentsList == null){
            return;
        }
        if (momentsList.size() == 0 ){
            return;
        }
        recyclerView = findViewById(R.id.my_and_friends_moments_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new MyRecyclerAdapter(this);
        mAdapter.setList(momentsList);
        recyclerView.setAdapter(mAdapter);
        recyclerView.addItemDecoration(new MyRecyclerDecoration());

        setHeaderView(recyclerView);
    }

    private void setHeaderView(RecyclerView recyclerView) {
        view = LayoutInflater.from(this).inflate(R.layout.moments_list_layout_header
                ,recyclerView,false);
        headerBackground =  view.findViewById(R.id.imageView_header_background);
        headerUserHeadpic =  view.findViewById(R.id.image_change_head_and_nickname);
        headerUserNickname =  view.findViewById(R.id.textView_nickname);
        headerUserHeadpic.setOnClickListener(this);


        mAdapter.setMheaderView(view);
    }

    @Override
    protected void onResume() {
        super.onResume();
        String header_user_headpic = MySharepreferencesUtils.with(this).getUserHeadPicUri();
        if (header_user_headpic.length() > 2 && headerUserHeadpic != null){
            Picasso.with(this)
                    .load("file:"+header_user_headpic)
//                    .centerCrop()
                    .into(headerUserHeadpic);

//            headerUserHeadpic.setImageBitmap(bitmap);
        }


        String header_user_headbg = MySharepreferencesUtils.with(this).getUserHeadBgPath();
        if (header_user_headbg.length()> 2 && headerBackground!= null){

            Bitmap bitmapBg = BitmapFactory.decodeFile(header_user_headbg);

            headerBackground.setImageBitmap(bitmapBg);

        }
        if (!MySharepreferencesUtils.with(this).getUserName().isEmpty() &&
                MySharepreferencesUtils.with(this).getUserName() != null &&
                headerUserNickname != null){
            headerUserNickname.setText(MySharepreferencesUtils.with(this)
                    .getUserName());
        }
        if (mAdapter != null){

            momentsList = DataSupport//.where("id > ?","-1")
                .order("id desc")
                .find(MyMoment.class);
            mAdapter.setList(momentsList);
//            mAdapter.notifyDataSetChanged();


        }

    }

    private void initToolBar() {
        Toolbar mToolbarTb = findViewById(R.id.tb_toolbar);
        setSupportActionBar(mToolbarTb);
        mToolbarTb.setNavigationIcon(ContextCompat.getDrawable(
                this,R.drawable.ic_action_name));
        mToolbarTb.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(
                        MainActivity.this,AddMomentsActivity.class);
                intent.putExtra(ADD_MOMENT_FLAG,1);
                startActivity(intent);

            }
        });


        mToolbarTb.setOnMenuItemClickListener(menuItemListener);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return true;
    }

    private Toolbar.OnMenuItemClickListener menuItemListener = new Toolbar.OnMenuItemClickListener() {
        @Override
        public boolean onMenuItemClick(MenuItem item) {
            switch (item.getItemId()){
                case R.id.item_take_photo:
                    showAddMonmentsDialog();
                    break;
            }
            return true;
        }
    };

    private void showAddMonmentsDialog() {
        final Dialog dialog = new Dialog(this);
        View view = getLayoutInflater().inflate(R.layout.take_photo_or_cp_dialog_layout,null);
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()){
                    case R.id.textView_select_from_sdcard:
                        dialog.dismiss();
                        Intent intent = new Intent(MainActivity.this,ChangeHostHeadAndNicknameActivity.class);
                        startActivity(intent);

                        break;
                    case R.id.take_photo_choose_container:
                        dialog.dismiss();
                        Intent intent2 = new Intent(
                                MainActivity.this,AddMomentsActivity.class);
                        intent2.putExtra(ADD_MOMENT_FLAG,0);
                        startActivity(intent2);
                        /*
                        通往修改头像,昵称,背景图片的通路
                        Intent intent = new Intent(MainActivity.this,ChangeHostHeadAndNicknameActivity.class);
                        startActivity(intent);*/
                        break;
                    default:
                        break;
                }
            }
        };
        view.findViewById(R.id.textView_select_from_sdcard).setOnClickListener(listener);
        view.findViewById(R.id.take_photo_choose_container).setOnClickListener(listener);

        dialog.setContentView(view);
        dialog.show();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.image_change_head_and_nickname:

                break;

        }
    }

}
