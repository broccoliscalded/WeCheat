package com.example.davin.wecheat.Adapter;

import android.content.Context;
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

import com.example.davin.wecheat.MyBeans.MyMoment;
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
     *重写这个方法，很重要，是加入Header和Footer的关键，我们通过判断item的类型，从而绑定不同的view
     *如果是headerview或者footerview，直接在viewholder中返回
     * */
    @Override
    public MyRecyclerAdapter.MyViewHolder onCreateViewHolder(
            ViewGroup parent, int viewType) {
        if (mheaderView != null && viewType == TYPE_HEADER){
            return new MyViewHolder(mheaderView);
        }
        if (mfooterView != null && viewType == TYPE_FOOTER){
            return new MyViewHolder(mfooterView);
        }
        MyRecyclerAdapter.MyViewHolder myViewHolder = new MyRecyclerAdapter.MyViewHolder(
                LayoutInflater.from(mContext).inflate(
                        R.layout.simple_recyclerview_item,parent,false));
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
                ((MyViewHolder) holder).textView.setText(
                        list.get(mPosition).getMomentTextContent().toString());

                ((MyViewHolder) holder).textViewUserNickname.setText(
                        list.get(mPosition).getNickName().toString());
                if (list.get(mPosition).getMomentUserPortraitPath()!=null){
                    try {
                        bitmap = BitmapFactory.decodeFile(
                                list.get(mPosition).getMomentUserPortraitPath().toString());
                        ((MyViewHolder) holder).imageViewUserPortrait.setImageBitmap(bitmap);
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
                        String durationString = null;
                        if (monthesAgo > 0){
                            durationString = monthesAgo + "个月前";
                        }else if (daysAgo > 0){
                            durationString = daysAgo + "天前";
                        }else if (hoursAgo>0){
                            durationString = hoursAgo + "小时前";
                        }else if (minutesAgo>0){
                            durationString = minutesAgo + "分钟前";
                        }else {
                            durationString = "刚刚";
                        }
                        ((MyViewHolder) holder).textViewCreateTime.setText(durationString);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }

                /*SpannableString add favorite to supporters*/
                String[] myfriends = mContext.getResources().getStringArray(R.array.my_friends);
                String mfriendsString ="";
                for (int i = 0; i < myfriends.length; i++) {
                    mfriendsString = mfriendsString + myfriends[i] + "; ";
                }
                SpannableString spString = new SpannableString("  "+mfriendsString);
                Drawable drawableFavorite = ContextCompat.getDrawable(mContext,R.drawable.ic_favorite_darkblue);
                drawableFavorite.setBounds(0,0,
                        TranslationTools.dip2px(mContext,14),
                        TranslationTools.dip2px(mContext,15));
                spString.setSpan(new ImageSpan(drawableFavorite),0,1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                ((MyViewHolder) holder).textViewFavorite.setText(spString);

                if (list.get(mPosition).getMomentPicturesPath()!= null){
                    String[] pathString = list.get(mPosition)
                                                .getMomentPicturesPath().split(";");
                    if (pathString.length > 0){
                        try{

                            for (int i = 0; i < ((MyViewHolder) holder).momentImagesList.size(); i++) {
                                if (i < pathString.length){
                                    ((MyViewHolder) holder).momentImagesList.get(i)
                                            .setVisibility(View.VISIBLE);
                                }else {
                                    ((MyViewHolder) holder).momentImagesList.get(i)
                                            .setVisibility(View.GONE);
                                }
                            }

                            for (int i = 0; i < pathString.length; i++) {
                                bitmap = BitmapFactory.decodeFile(pathString[i]);
                                ((MyViewHolder) holder).momentImagesList.get(i)
                                        .setImageBitmap(bitmap);
                                MyLog.printLog(MyLog.LEVEL_D,"path " + i + " = " + pathString[i]);
                            }

                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                }

                MyLog.printLog(MyLog.LEVEL_D,"list size = "
                        + list.get(mPosition).toString());
//                ((MyViewHolder) holder).imageViewUserPortrait;


            }
            return;
        }else if (getItemViewType(position) == TYPE_HEADER){
            return;
        }else {
            return;
        }

    }

    @Override
    public int getItemCount() {
        if (mheaderView == null && mfooterView == null){
            return list.size();
        }else if (mheaderView == null && mfooterView != null){
            return list.size() + 1 ;
        }else if (mheaderView != null && mfooterView == null){
            return list.size() + 1 ;
        }else {
            return list.size() + 2 ;
        }
//        return list.size();
    }

    public void setList(List<MyMoment> dataList) {
        this.list = dataList;
        notifyDataSetChanged();
    }

    /**
     * 加载每个view item的布局
     * */
    class MyViewHolder extends RecyclerView.ViewHolder{

        TextView textView,textViewUserNickname,textViewCreateTime,textViewFavorite;
        ImageView imageViewUserPortrait;
        List<ImageView> momentImagesList = new ArrayList<ImageView>();
        int[] imageIds = {  R.id.moments_image_1,R.id.moments_image_2,R.id.moments_image_3,
                            R.id.moments_image_4,R.id.moments_image_5,R.id.moments_image_6,
                            R.id.moments_image_7,R.id.moments_image_8,R.id.moments_image_9};

        public MyViewHolder(View itemView) {
            super(itemView);
            if (itemView == mheaderView){
                return;
            }
            if (itemView == mfooterView){
                return;
            }
            textView = (TextView) itemView.findViewById(R.id.textView_item_mark);
            textViewUserNickname = (TextView) itemView.findViewById(R.id.textView_user_nickname);
            imageViewUserPortrait = (ImageView) itemView.findViewById(R.id.imageView_user_portrait);
            textViewCreateTime = (TextView) itemView.findViewById(R.id.textView_create_time);
            textViewFavorite = (TextView) itemView.findViewById(R.id.textView_favorite);
            for (int i = 0; i < imageIds.length; i++) {
                ImageView imageView = (ImageView) itemView.findViewById(imageIds[i]);
                momentImagesList.add(imageView);
            }
        }
    }


}
