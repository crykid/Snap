package com.margin.snap.view;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.Nullable;

public class XTextView extends androidx.appcompat.widget.AppCompatTextView {
    private static final String TAG = "XTextView";

    public XTextView(Context context) {
        super(context);
    }

    public XTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public XTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widht = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(widthMeasureSpec);
        Log.d(TAG, "onMeasure: width = " + widht + " , height = " + height);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        Log.d(TAG, "onSizeChanged: w = " + w + " ,h = " + h + " , oldw = " + oldw + " , oldh = " + oldh);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        int widht = getMeasuredWidth();
//        int height = getMeasuredHeight();
        int widht = getWidth();
        int height = getHeight();
        Log.d(TAG, "onDraw: width = " + widht + " , height = " + height);
    }
}
