package com.example.davin.wecheat.CustomViews;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.text.style.ImageSpan;

/**
 * Created by daniel on 18-1-31.
 */

public class MyImageSpan extends ImageSpan {

    public MyImageSpan(Context context,Bitmap b) {
        super(context,b);
    }

    public MyImageSpan(Drawable d) {
        super(d);
    }

    /*@Override
    public int getSize(Paint paint, CharSequence text, int start, int end, Paint.FontMetricsInt fm) {
        Drawable drawable = getDrawable();
        Rect rect = drawable.getBounds();
        if (fm != null){
            Paint.FontMetricsInt fmPaint = paint.getFontMetricsInt();
            int fontHeight = fmPaint.bottom - fmPaint.top;
            int drHeight = rect.bottom - rect.top;

            //对于这里我表示,我不知道为啥是这样。不应该是fontHeight/2?但是只有fontHeight/4才能对齐
            //难道是因为TextView的draw的时候top和bottom是大于实际的？具体请看下图
            //所以fontHeight/4是去除偏差?
            int top = drHeight / 2 - fontHeight / 4;
            int bottom = drHeight / 2 + fontHeight / 4;

            fm.ascent = -bottom;
            fm.top = -bottom;
            fm.bottom = top;
            fm.descent = top;
        }

        return rect.right;
    }*/

    /**
     * ImageSpan 居中显示在文本中
     * @author daniel
     * @time 18-1-31 下午6:33
     * 
     */
    @Override
    public void draw(Canvas canvas, CharSequence text, int start, int end, float x, int top, int y, int bottom, Paint paint) {

        Drawable drawable = getDrawable();
        Rect rect = drawable.getBounds();
        canvas.save();
        Paint.FontMetricsInt fontMetricsInt = paint.getFontMetricsInt();
        int transY;
        //获得将要显示的文本高度-图片高度除2等居中位置+top(换行情况)
        transY = (2*y + fontMetricsInt.ascent+fontMetricsInt.descent)/2 - rect.bottom/2;
        canvas.translate(x, transY);
        drawable.draw(canvas);
        canvas.restore();

    }
}
