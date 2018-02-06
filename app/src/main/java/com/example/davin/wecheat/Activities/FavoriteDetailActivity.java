package com.example.davin.wecheat.Activities;

import android.database.DataSetObserver;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.example.davin.wecheat.Adapter.MySpinnerAdapter;
import com.example.davin.wecheat.MyBeans.MyFriendsInformation;
import com.example.davin.wecheat.MyBeans.MyMoment;
import com.example.davin.wecheat.R;
import com.example.davin.wecheat.Utils.MyLog;
import com.example.davin.wecheat.Utils.ToastUtil;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

public class FavoriteDetailActivity extends AppCompatActivity implements View.OnClickListener{

    public static final String MOMENT_INFO_EDIT_FLAG = "com.example.davin.wecheat.favoritedetailactivity.intentextraflag";
    private MyMoment myMoment;
    private TextInputEditText textInputEditText;
    private TextView textView2;
    private Spinner spinner;
    private MySpinnerAdapter spinnerAdapter;
    String myCustomFriends = "";
    List<MyFriendsInformation> mlist ;
    private boolean firstInFlag = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_detail);

        if (getIntent().hasExtra(MOMENT_INFO_EDIT_FLAG)){
            try{
                myMoment = (MyMoment) getIntent().getSerializableExtra(MOMENT_INFO_EDIT_FLAG);
            }catch (Exception e){
                e.printStackTrace();
                finish();
            }

        }else {
            finish();
        }

        initView();
    }

    private void initView() {
        findViewById(R.id.button_amazing_names).setOnClickListener(this);
        findViewById(R.id.button_random_names).setOnClickListener(this);
        findViewById(R.id.button_custom_name).setOnClickListener(this);

        textInputEditText = findViewById(R.id.textinput_edit_text_user_nickname);

        findViewById(R.id.button).setOnClickListener(this);
        textView2 = findViewById(R.id.textView2);
        spinner = findViewById(R.id.spinner);
//        spinner.setOnClickListener(this);

        spinnerAdapter = new MySpinnerAdapter(this);

        mlist = DataSupport
                .order("rankehot desc")
                .find(MyFriendsInformation.class);
        spinnerAdapter.setData(mlist);
        spinner.setAdapter(spinnerAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                ToastUtil.showShort(FavoriteDetailActivity.this,"position : " + position);
                if (firstInFlag){
                    firstInFlag = false;
                }else {
                    if (position >= 0 && position < mlist.size()){
                        String friendName = mlist.get(position).getFriendNickName();
                        if (myCustomFriends.isEmpty()){
                            myCustomFriends = friendName + " ,  ";
                        }else{
                            if (myCustomFriends.contains(friendName + " ,  ")){
                                return;
                            }else {
                                myCustomFriends += friendName + " ,  ";
                            }

                        }
                    }

                    textView2.setText(myCustomFriends);
                    updateDataBase(myCustomFriends);
//                    MyLog.printLog(MyLog.LEVEL_D,"friend list : " + myCustomFriends);
                }
            }


            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    /**
     * 
     * @author daniel
     * @time 18-2-3 下午5:58
     * 
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button_amazing_names:
                createAmazingName(80);
                break;
            case R.id.button_random_names:
                createRandomName(60);
                break;
            case R.id.button_custom_name:
                saveCustomFriendsName();
                break;
            case R.id.button:
                showRankeName();
                break;
//            case R.id.spinner:
//
//                break;
        }
    }

    /**
     * show choosen names
     * @author daniel
     * @time 18-2-6 下午4:34
     * 
     */
    private void showRankeName() {
        List<MyFriendsInformation> ls = new ArrayList<>();
        ls = DataSupport.findAll(MyFriendsInformation.class);

//        textView2.setText(ls.toString());
    }

    private void saveCustomFriendsName() {
        String nameToSave = textInputEditText.getText().toString();
        MyFriendsInformation myFriendsInformation = new MyFriendsInformation();
        if ( nameToSave.length() < 1 ){
            ToastUtil.showShort(this,getResources().getString(R.string.string_no_name_text_entered));
            return;
        }
//        List<MyFriendsInformation> list = new ArrayList<>();
        mlist = DataSupport
                .where("friendnickname = ?" ,nameToSave)
                .find(MyFriendsInformation.class);
        for (MyFriendsInformation singleFriend : mlist) {
            if (singleFriend.getFriendNickName().equals(nameToSave)){
                int rankeInt = singleFriend.getRankeHot();
                rankeInt++;
                myFriendsInformation.setRankeHot(rankeInt);
                myFriendsInformation.updateAll("friendnickname = ?",nameToSave);
                return;
            }
        }

        myFriendsInformation.setFriendNickName(nameToSave);
        myFriendsInformation.setRankeHot(1);
        myFriendsInformation.save();

        List<MyFriendsInformation> mlist ;
        mlist = DataSupport
                .order("rankehot desc")
                .find(MyFriendsInformation.class);
        spinnerAdapter.setData(mlist);
    }

    private void createRandomName(int favoriteTimes) {
        String[] lastNameGroup = getResources().getStringArray(R.array.lastname);
        String[] firstNameGroup = getResources().getStringArray(R.array.firstname);

        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < favoriteTimes; i++) {
            int lastNameIndex = (int) (Math.random()*lastNameGroup.length);
            String tempLastName = lastNameGroup[lastNameIndex];
            int firstNameIndex = (int) (Math.random()*firstNameGroup.length);
            String tempFirstName = firstNameGroup[firstNameIndex];

            stringBuilder.append(tempLastName).append(tempFirstName).append(" ,  ");
        }
        updateDataBase(stringBuilder);
    }

    /**
     * update mymoment.db
     * @author daniel
     * @time 18-2-1 下午5:01
     * 
     */
    private void updateDataBase(StringBuilder stringBuilder) {
        this.updateDataBase(stringBuilder.toString());

    }

    private void updateDataBase(String mstring){
        if (myMoment == null){
            return;
        }

        MyMoment moment = new MyMoment();
        moment.setFavoriteNames(mstring);
        String createTime = myMoment.getMonmentCreatedTime();
        moment.updateAll("monmentcreatedtime = ?",createTime);
    }

    private void createAmazingName(int favoriteTimes) {
        String[] nameList = getResources().getStringArray(R.array.my_friends);
        int nameListLength = nameList.length;
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < 18; i++) {
            int nameIndex = (int) (Math.random()*nameListLength);
//            MyLog.printLog(MyLog.LEVEL_D,"name Index : " + nameIndex);
            String tempName = nameList[i];
            if (nameIndex>=0 && nameIndex<nameList.length-1){
                nameList[i] = nameList[nameIndex];
                nameList[nameIndex] = tempName;
            }
        }
        if (favoriteTimes > nameList.length){
            for (String tempNameString: nameList) {
                stringBuilder.append(tempNameString).append(" ,  ");
            }
        }else {
            for (int i = 0; i < favoriteTimes; i++) {
                stringBuilder.append(nameList[i]).append(" ,  ");
            }
        }

//        MyLog.printLog(MyLog.LEVEL_D,"final name string = " + "\n" +
//                stringBuilder.toString());

        updateDataBase(stringBuilder);
    }
}
