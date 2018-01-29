package com.example.davin.wecheat.Activities;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
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
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.davin.wecheat.Adapter.MyRecyclerAdapter;
import com.example.davin.wecheat.MyBeans.MyMoment;
import com.example.davin.wecheat.Utils.MyRecyclerDecoration;
import com.example.davin.wecheat.R;
import com.example.davin.wecheat.Utils.AddShortCut;
import com.example.davin.wecheat.Utils.MyLog;
import com.example.davin.wecheat.Utils.MySharepreferencesUtils;
import com.example.davin.wecheat.Utils.ToastUtil;

import org.litepal.crud.DataSupport;
import org.litepal.tablemanager.Connector;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

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
        /**
         * Attempt to create a short cut at the launcher,but it doesn't work.
         * */
//        AddShortCut.createShortCut(this,R.mipmap.ic_launcher,R.string.app_name);
        try{
            momentsList = DataSupport.where("id > ?","-1")
                    .order("id desc")
                    .find(MyMoment.class);
        }catch (Exception e){
            e.printStackTrace();
        }


        initToolBar();
        initMyRecyclerView();

        /*
         *this function as its name
         * */
//        hasNavigationOrNot();
        Connector.getDatabase();


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions
            , @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode ){
            case 2:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    Intent intent = new Intent(
                            MainActivity.this,AddFriendMomentsActivity.class);
                    startActivity(intent);
                }else {
                    ToastUtil.showShort(this,getResources()
                            .getString(R.string.string_read_storage_permission_denied));
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
            Log.d("naviheight","navigation height == " + height);
        }else {
            Log.d("naviheight","has no navigation !");
        }
    }

    private void initMyRecyclerView() {
//        List<String> dataList = new ArrayList<String>();
//        for (int i = 0; i < 10; i++) {
//            String itemString = i + " ";
//            dataList.add(itemString);
//        }
        if (momentsList == null){
            return;
        }
        if (momentsList.size() == 0 ){
            return;
        }
        recyclerView = (RecyclerView) findViewById(R.id.my_and_friends_moments_recyclerView);
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
        headerBackground = (ImageView) view.findViewById(R.id.imageView_header_background);
        headerUserHeadpic = (ImageView) view.findViewById(R.id.image_change_head_and_nickname);
        headerUserNickname = (TextView) view.findViewById(R.id.textView_nickname);
        headerUserHeadpic.setOnClickListener(this);


        mAdapter.setMheaderView(view);
    }

    @Override
    protected void onResume() {
        super.onResume();
        String header_user_headpic = MySharepreferencesUtils.with(this).getUserHeadPicUri();
        if (header_user_headpic.length() > 2 && headerUserHeadpic != null){
            Bitmap bitmap = BitmapFactory.decodeFile(header_user_headpic,
                    new BitmapFactory.Options());
            headerUserHeadpic.setImageBitmap(bitmap);
        }
        String header_user_headbg = MySharepreferencesUtils.with(this).getUserHeadBgPath();
        if (header_user_headbg.length()> 2 && headerBackground!= null){
            Bitmap bitmapBg = BitmapFactory.decodeFile(header_user_headbg);
//            图片使用前需要压缩，不然会导致卡顿
            MyLog.printLog(MyLog.LEVEL_D,"BG image size = " + bitmapBg.getByteCount()/1024/1024 + " M \n" +
                    " image width = " + bitmapBg.getWidth() + " ; image height = " + bitmapBg.getHeight());

            headerBackground.setImageBitmap(bitmapBg);
        }
        if (!MySharepreferencesUtils.with(this).getUserName().isEmpty() &&
                MySharepreferencesUtils.with(this).getUserName() != null &&
                headerUserNickname != null){
            headerUserNickname.setText(MySharepreferencesUtils.with(this)
                    .getUserName().toString());
        }
        if (mAdapter != null){
           /* momentsList = DataSupport.findAll(MyMoment.class);
            List<MyMoment>*/
           momentsList = DataSupport.where("id > ?","-1")
                    .order("id desc")
                    .find(MyMoment.class);

            mAdapter.notifyDataSetChanged();

            /*for (int i = 0; i < momentsList.size(); i++) {
                MyLog.printLog(MyLog.LEVEL_D,momentsList.get(i).toString());
            }*/
        }

    }

    private void initToolBar() {
        Toolbar mToolbarTb = (Toolbar) findViewById(R.id.tb_toolbar);
        setSupportActionBar(mToolbarTb);
        mToolbarTb.setNavigationIcon(ContextCompat.getDrawable(
                this,R.drawable.ic_action_name));
        mToolbarTb.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(MainActivity.this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(MainActivity.this,new String[]{
                            Manifest.permission.WRITE_EXTERNAL_STORAGE},2);
                }else{
                    Intent intent = new Intent(
                            MainActivity.this,AddFriendMomentsActivity.class);
                    startActivity(intent);
                }

            }
        });


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
