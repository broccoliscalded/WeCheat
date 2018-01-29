package com.example.davin.wecheat.Utils;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

/**
 * Created by daniel on 18-1-15.
 */

public class MyRecyclerDecoration extends RecyclerView.ItemDecoration {

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent
            , RecyclerView.State state) {
//        Rect myDecorationRect = new Rect(0,10,0,0);

        Log.d("decoration","children size = " + parent.getChildCount());
        super.getItemOffsets(outRect, view, parent, state);
        outRect.bottom = 1;//item offset 需要在这个位置修改才会起效，
        // 如何判定哪一个child是HeaderView还需要再想办法
//        if (parent.getChildCount() != 1){
//            outRect.bottom = 1;
//        }

    }
}
