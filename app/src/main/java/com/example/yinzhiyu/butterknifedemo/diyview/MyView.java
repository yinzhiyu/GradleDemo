package com.example.yinzhiyu.butterknifedemo.diyview;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

/**
 * Created by yinzhiyu on 2018-2-23.
 */

public class MyView extends View {
    private Paint mPaint;
    private Path mPath;
    private int mItemWaveLength = 1000;
    private int dx;
    public MyView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPath = new Path();
        mPaint = new Paint();
        mPaint.setColor(Color.GREEN);
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPath.reset();
        int originY = 300;
        int halfWaveLen = mItemWaveLength/2;
        mPath.moveTo(-mItemWaveLength+dx,originY);
//        mPath.moveTo(-mItemWaveLength+dx,originY+dy);
        for (int i = -mItemWaveLength;i<=getWidth()+mItemWaveLength;i+=mItemWaveLength){
            mPath.rQuadTo(halfWaveLen/2,-100,halfWaveLen,0);
            mPath.rQuadTo(halfWaveLen/2,100,halfWaveLen,0);
        }
        mPath.lineTo(getWidth(),getHeight());
        mPath.lineTo(0,getHeight());
        mPath.close();

        canvas.drawPath(mPath,mPaint);
    }

    public void startAnim(){
        ValueAnimator animator = ValueAnimator.ofInt(0,mItemWaveLength);
        animator.setDuration(2000);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setInterpolator(new LinearInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                dx = (int)animation.getAnimatedValue();
                postInvalidate();
            }
        });
        animator.start();
    }
}