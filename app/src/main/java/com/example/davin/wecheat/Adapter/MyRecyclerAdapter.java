package com.example.davin.wecheat.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ImageSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.davin.wecheat.Activities.FavoriteDetailActivity;
import com.example.davin.wecheat.CustomViews.MyImageSpan;
import com.example.davin.wecheat.MyBeans.MyMoment;
import com.example.davin.wecheat.MyBeans.MyMomentRecyclerCache;
import com.example.davin.wecheat.R;
import com.example.davin.wecheat.Utils.MyLog;
import com.example.davin.wecheat.Utils.TranslationTools;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by daniel on 18-1-17.
 */

public class MyRecyclerAdapter extends RecyclerView.Adapter<MyRecyclerAdapter.MyViewHolder> {

    private static final int TYPE_HEADER = 0 ;//Flag for RecyclerView with Header View
    private static final int TYPE_FOOTER = 1 ;//Flag for RecyclerView with Footer View
    private static final int TYPE_NORMAL = 2 ;//Flag for normal RecyclerView

    private View mheaderView;//emmmm... my headerview
    private View mfooterView;//emmmm... my footerview
    private Context mContext;
    private List<MyMoment> list ;
    private List<MyMomentRecyclerCache> cacheList = new ArrayList<>();

    public MyRecyclerAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public View getMheaderView() {
        return mheaderView;
    }

    public void setMheaderView(View mheaderView) {
        this.mheaderView = mheaderView;
        notifyItemInserted(0);
    }

    public View getMfooterView() {
        return mfooterView;
    }

    public void setMfooterView(View mfooterView) {
        this.mfooterView = mfooterView;
        notifyItemInserted(getItemCount() - 1);
    }

    @Override
    public int getItemViewType(int position) {
        if (mheaderView == null && mfooterView == null){
            return TYPE_NORMAL;
        }

        if (position == 0){
            return TYPE_HEADER;
        }
        if (position == getItemCount()-1 && mfooterView != null){
            return TYPE_FOOTER;
        }
        return TYPE_NORMAL;
    }

    /**
     * 重写这个方法，很重要，是加入Header和Footer的关键，我们通过判断item的类型，从而绑定不同的view
     * 如果是headerview或者footerview，直接在viewholder中返回
     * @author daniel
     * @time 18-2-1 上午10:31
     *
     */
    @Override
    public MyRecyclerAdapter.MyViewHolder onCreateViewHolder(
            ViewGroup parent, int viewType) {
        if (mheaderView != null && viewType == TYPE_HEADER){
            return new MyViewHolder(mheaderView);
        }
        if (mfooterView != null && viewType == TYPE_FOOTER){
            return new MyViewHolder(mfooterView);
        }
        MyRecyclerAdapter.MyViewHolder myViewHolder = new MyRecyclerAdapter.MyViewHolder
                (LayoutInflater.from(mContext).inflate
                    (R.layout.simple_recyclerview_item,
                    parent,false
                    )
                );
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyRecyclerAdapter.MyViewHolder holder, int position) {
//        MyLog.printLog(MyLog.LEVEL_D," position Abs  : " + position);
        Bitmap bitmap;

        if (getItemViewType(position) == TYPE_NORMAL){

            if (holder instanceof MyViewHolder){
                int mPosition = position;
                if(mheaderView != null){
                    if (position > -1 && position < list.size() + 1){
                        mPosition = position -1;
                    }

                }else {
                    mPosition = position;
                }

                final int finalMPosition = mPosition;
                holder.favoriteDetail.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(mContext, FavoriteDetailActivity.class);
                        intent.putExtra(FavoriteDetailActivity.MOMENT_INFO_EDIT_FLAG,list.get(finalMPosition));
                        mContext.startActivity(intent);
                    }
                });

                holder.textView.setText(
                        list.get(mPosition).getMomentTextContent());

                holder.textViewUserNickname.setText(
                        list.get(mPosition).getNickName());
                if (list.get(mPosition).getMomentUserPortraitPath()!=null){
                    try {
                        /*bitmap = BitmapFactory.decodeFile(
                                list.get(mPosition).getMomentUserPortraitPath());*/
//                        bitmap = TranslationTools.SimplerCompressionPackge(
//                                list.get(mPosition).getMomentUserPortraitPath(),
//                                TranslationTools.dip2px(mContext,40),
//                                TranslationTools.dip2px(mContext,40)
//                        );
                        bitmap = cacheList.get(mPosition).getMomentUserPortrait();
                        holder.imageViewUserPortrait.setImageBitmap(bitmap);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
                String createTimeString = list.get(mPosition).getMonmentCreatedTime();
                if (createTimeString!= null && createTimeString.length()>0){
                    String currentTime = TranslationTools.getCurrentTimeString();
                    try {
                        String[] currentTimeGroup = currentTime.split("/");
                        String[] createTimeGroup = list.get(mPosition).getMonmentCreatedTime().split("/");

                        int monthesAgo = Integer.valueOf(currentTimeGroup[1])
                                - Integer.valueOf(createTimeGroup[1]);
                        int daysAgo = Integer.valueOf(currentTimeGroup[2])
                                - Integer.valueOf(createTimeGroup[2]);
                        int hoursAgo = Integer.valueOf(currentTimeGroup[3])
                                - Integer.valueOf(createTimeGroup[3]);
                        int minutesAgo = Integer.valueOf(currentTimeGroup[4])
                                - Integer.valueOf(createTimeGroup[4]);
                        String durationString;
                        if (monthesAgo > 0){
                            durationString = monthesAgo + "个月前";
                        }else if (daysAgo > 0){
                            if(daysAgo == 1){
                                durationString = "昨天";
                            }else {
                                durationString = daysAgo + "天前";
                            }

                        }else if (hoursAgo>0){
                            durationString = hoursAgo + "小时前";
                        }else if (minutesAgo>0){
                            durationString = minutesAgo + "分钟前";
                        }else {
                            durationString = "刚刚";
                        }
                        holder.textViewCreateTime.setText(durationString);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }

                /*SpannableString add favorite to supporters*/
//                String[] myfriends = mContext.getResources().getStringArray(R.array.my_friends);
                String mfriendsString ="" + list.get(mPosition).getFavoriteNames();
//                for (int i = 0; i < myfriends.length; i++) {
//                    if (i == myfriends.length - 1){
//                        mfriendsString = mfriendsString + myfriends[i];
//                    }else {
//                        mfriendsString += myfriends[i] + " ,  ";
//                    }
//
//                }
                SpannableString spString = new SpannableString("  "+mfriendsString);
                Drawable drawableFavorite = ContextCompat.getDrawable(mContext,R.drawable.ic_favorite_darkblue);
                drawableFavorite.setBounds(0,0,
                        TranslationTools.dip2px(mContext,14),
                        TranslationTools.dip2px(mContext,15));
                spString.setSpan(new MyImageSpan(drawableFavorite),0,1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                holder.textViewFavorite.setText(spString);

                if (list.get(mPosition).getMomentPicturesPath()!= null){
                    String[] pathString = list.get(mPosition)
                                                .getMomentPicturesPath().split(";");
                    if (pathString.length > 0){
                        try{

                            for (int i = 0; i < holder.momentImagesList.size(); i++) {
                                if (i < pathString.length){
                                    holder.momentImagesList.get(i)
                                            .setVisibility(View.VISIBLE);
                                }else {
                                    holder.momentImagesList.get(i)
                                            .setVisibility(View.GONE);
                                }
                            }

                            for (int i = 0; i < pathString.length; i++) {
//                                bitmap = BitmapFactory.decodeFile(pathString[i]);
//                                bitmap = TranslationTools.SimplerCompressionPackge(
//                                        pathString[i],
//                                        TranslationTools.dip2px(mContext,80),
//                                        TranslationTools.dip2px(mContext,80));
                                bitmap = cacheList.get(mPosition).getMomentPicturesList().get(i);
                                holder.momentImagesList.get(i)
                                        .setImageBitmap(bitmap);
//                                Picasso.with(mContext).load("file:"+pathString[i]).into(holder.momentImagesList.get(i));
//                                MyLog.printLog(MyLog.LEVEL_D,"path " + i + " = " + pathString[i]);
                            }

                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                }

//                MyLog.printLog(MyLog.LEVEL_D,"list size = "
//                        + list.get(mPosition).toString());

            }
//            return;
        }/*
//        return is not necessary as return type is void
        else if (getItemViewType(position) == TYPE_HEADER){
            return;
        }else {
            return;
        }*/

    }

    @Override
    public int getItemCount() {
        if (mheaderView == null && mfooterView == null){
            return list.size();
        }else if (mheaderView == null){/*mfooterView != null always be true*/
            return list.size() + 1 ;
        }else if ( mfooterView == null){/*mheaderView != null  always be true*/
            return list.size() + 1 ;
        }else {
            return list.size() + 2 ;
        }
//        return list.size();
    }

    public void setList(List<MyMoment> dataList) {
        for (MyMoment tempMoment:dataList) {
            MyMomentRecyclerCache momentCache = tempMoment.toMymomentRecyclerCache(mContext);
            cacheList.add(momentCache);
        }
        this.list = dataList;
        notifyDataSetChanged();
    }

    /**
     * 加载每个view item的布局
     * @author daniel
     * @time 18-2-1 上午10:24
     *
     */
    class MyViewHolder extends RecyclerView.ViewHolder{

        TextView textView,textViewUserNickname,textViewCreateTime,textViewFavorite;
        ImageView imageViewUserPortrait;
        ImageView favoriteDetail;
        List<ImageView> momentImagesList = new ArrayList<>();
        int[] imageIds = {  R.id.moments_image_1,R.id.moments_image_2,R.id.moments_image_3,
                            R.id.moments_image_4,R.id.moments_image_5,R.id.moments_image_6,
                            R.id.moments_image_7,R.id.moments_image_8,R.id.moments_image_9};

        private MyViewHolder(View itemView) {
            super(itemView);
            if (itemView == mheaderView){
                return;
            }
            if (itemView == mfooterView){
                return;
            }
            textView = itemView.findViewById(R.id.textView_item_mark);
            textViewUserNickname =  itemView.findViewById(R.id.textView_user_nickname);
            imageViewUserPortrait =  itemView.findViewById(R.id.imageView_user_portrait);
            textViewCreateTime = itemView.findViewById(R.id.textView_create_time);
            textViewFavorite =  itemView.findViewById(R.id.textView_favorite);
            favoriteDetail = itemView.findViewById(R.id.imageView_add_favorite);

            for (int aid: imageIds) {
                ImageView imageView = itemView.findViewById(aid);
                momentImagesList.add(imageView);
            }
        }
    }


}
