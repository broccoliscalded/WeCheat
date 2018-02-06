package com.example.davin.wecheat.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.example.davin.wecheat.MyBeans.MyFriendsInformation;
import com.example.davin.wecheat.R;

import java.util.List;

/**
 * Created by daniel on 18-2-6.
 */

public class MySpinnerAdapter extends BaseAdapter implements SpinnerAdapter{
    List<MyFriendsInformation> data;
    Context mcontext;

    public MySpinnerAdapter(Context mcontext) {
        this.mcontext = mcontext;
    }

    public void setData(List<MyFriendsInformation> list){
        this.data = list;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return data == null ? 0 : data.size();
    }

    @Override
    public Object getItem(int position) {
        return data == null ? null:data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return data == null? 0:position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
//        View view;
        MyHolder myHolder = new MyHolder();
        if (convertView == null){
            convertView = View.inflate(mcontext, R.layout.spinner_item,null);
            myHolder.textView = convertView.findViewById(R.id.textView3);
            convertView.setTag(myHolder);
        }else {
            myHolder =(MyHolder) convertView.getTag();
        }
        myHolder.textView.setText(data.get(position).getFriendNickName());

        return convertView;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        MyHolder myHolder = new MyHolder();
        if (convertView == null){
            convertView = View.inflate(mcontext, R.layout.spinner_item_dropdown,null);
            myHolder.textView = convertView.findViewById(R.id.textView4);
            convertView.setTag(myHolder);
        }else {
            myHolder =(MyHolder) convertView.getTag();
        }
        myHolder.textView.setText(data.get(position).getFriendNickName());

        return convertView;
    }

    private static class MyHolder{
        TextView textView;
    }

}
