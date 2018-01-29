package com.example.davin.wecheat.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.davin.wecheat.MyBeans.MyMoment;
import com.example.davin.wecheat.R;
import com.example.davin.wecheat.Utils.MyLog;
import com.squareup.picasso.Picasso;

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
        if (getItemViewType(position) == TYPE_NORMAL){

            if (holder instanceof MyViewHolder){
                int mPosition = position;
                if(mheaderView != null){
//                    MyLog.printLog(MyLog.LEVEL_D,"list size = " + list.size());
                    if (position > -1 && position < list.size() + 1){
//                        ((MyViewHolder)holder).textView.setText(
//                                list.get(position - 1).getMomentTextContent());
//                        MyLog.printLog(MyLog.LEVEL_D,"position = " + ( position - 1 ) +"\n"+
//                        "  ; data =  " +list.get(position -1 ));
                        mPosition = position -1;
                    }

                }else {
//                    ((MyViewHolder)holder).textView.setText(
//                            list.get(position).getMomentTextContent());
                    mPosition = position;
                }
                ((MyViewHolder) holder).textView.setText(
                        list.get(mPosition).getMomentTextContent().toString());

                ((MyViewHolder) holder).textViewUserNickname.setText(
                        list.get(mPosition).getNickName().toString());
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

        TextView textView,textViewUserNickname;
        ImageView imageViewUserPortrait;

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
        }
    }


}
