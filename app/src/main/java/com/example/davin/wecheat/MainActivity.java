package com.example.davin.wecheat;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.davin.wecheat.Utils.AddShortCut;
import com.example.davin.wecheat.Utils.MySharepreferencesUtils;
import com.example.davin.wecheat.Utils.TranslationTools;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private RecyclerView recyclerView;
    private MyRecyclerAdapter mAdapter;
    private View view;
    private ImageView headerBackground,headerUserHeadpic;
    private TextView headerUserNickname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AddShortCut.createShortCut(this,R.mipmap.ic_launcher,R.string.app_name);

        initToolBar();
        initMyRecyclerView();
        hasNavigationOrNot();
    }

    private void hasNavigationOrNot() {
        boolean hasMenuKey = ViewConfiguration.get(this).hasPermanentMenuKey();
        boolean hasBackKey = KeyCharacterMap.deviceHasKey(KeyEvent.KEYCODE_BACK);
        if (!hasBackKey && !hasMenuKey){
            Resources resources = getResources();
            int resourceId = resources.getIdentifier(
                    "navigation_bar_height","dimen","android");
            int height = resources.getDimensionPixelSize(resourceId);
            Log.d("naviheight","navigation height == " + height);
        }else {
            Log.d("naviheight","has no navigation !");
        }
    }

    private void initMyRecyclerView() {
        List<String> dataList = new ArrayList<String>();
        for (int i = 0; i < 10; i++) {
            String itemString = i + " ";
            dataList.add(itemString);
        }
        recyclerView = (RecyclerView) findViewById(R.id.my_and_friends_moments_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new MyRecyclerAdapter(this);
        mAdapter.setList(dataList);
        recyclerView.setAdapter(mAdapter);
        recyclerView.addItemDecoration(new MyRecyclerDecoration());

        setHeaderView(recyclerView);
    }

    private void setHeaderView(RecyclerView recyclerView) {
        view = LayoutInflater.from(this).inflate(R.layout.moments_list_layout_header
                ,recyclerView,false);
        headerBackground = (ImageView) view.findViewById(R.id.imageView_header_background);
        headerUserHeadpic = (ImageView) view.findViewById(R.id.image_change_head_and_nickname);
        headerUserNickname = (TextView) view.findViewById(R.id.textView_nickname);
        headerUserNickname.setText(MySharepreferencesUtils.with(this).getUserName().toString());
        headerUserHeadpic.setOnClickListener(this);


        mAdapter.setMheaderView(view);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Bitmap bitmap = BitmapFactory.decodeFile(MySharepreferencesUtils.with(this).getUserHeadPicUri()
        ,new BitmapFactory.Options());
        /*Picasso.with(this).load("http://img0.imgtn.bdimg.com/it/u=4136048700,1214199505&fm=214&gp=0.jpg")
//                .resize(TranslationTools.dip2px(this,80),
//                        TranslationTools.dip2px(this,80))
//                .centerCrop()
                .into(headerUserHeadpic);*/
        headerUserHeadpic.setImageBitmap(bitmap);
    }

    private void initToolBar() {
        Toolbar mToolbarTb = (Toolbar) findViewById(R.id.tb_toolbar);
        setSupportActionBar(mToolbarTb);
        mToolbarTb.setNavigationIcon(ContextCompat.getDrawable(
                this,R.drawable.ic_action_name));
//        mToolbarTb.setNavigationIcon(R.drawable.short_line_vertical);
        mToolbarTb.setOnMenuItemClickListener(menuItemListener);
//        Intent intent = new Intent(this,MomentsActivity.class);
//        startActivity(intent);
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
        Dialog dialog = new Dialog(this);
        View view = getLayoutInflater().inflate(R.layout.take_photo_or_cp_dialog_layout,null);
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()){
                    case R.id.textView_select_from_sdcard:

                        break;
                    case R.id.take_photo_choose_container:
                        
                        break;
                    default:
                        break;
                }
            }
        };
        view.findViewById(R.id.textView_select_from_sdcard).setOnClickListener(listener);

        dialog.setContentView(view);
        dialog.show();
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()){
            case R.id.image_change_head_and_nickname:
                intent = new Intent(this,ChangeHostHeadAndNicknameActivity.class);
                startActivity(intent);
                break;

        }
    }

    /*class MyRecycleAdapter extends RecyclerView.Adapter<MyRecycleAdapter.MyViewHolder>{

        private Context mContext;
        private List<String> list;

        public void setList(List<String> list){
            this.list = list;
        }

        public MyRecycleAdapter(Context context) {
            this.mContext = context;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            MyViewHolder viewHolder = new MyViewHolder(
                    LayoutInflater.from(mContext).inflate(
                            R.layout.simple_recyclerview_item,parent,false));
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            holder.textView.setText(mContext.getString(R.string.string_camera )+"\n"
                    + list.get(position));

        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        public void setHeaderView(View view) {
        }

        @Override
        public int getItemViewType(int position) {
            return super.getItemViewType(position);
        }

        class MyViewHolder extends RecyclerView.ViewHolder{

            TextView textView;

            public MyViewHolder(View itemView) {
                super(itemView);
                textView = (TextView) itemView.findViewById(R.id.textView_item_mark);
            }
        }
    }*/

}
