package com.example.yinzhiyu.butterknifedemo.diyview;

import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.os.Build;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.example.yinzhiyu.butterknifedemo.R;

/**
 * 自定义原型进度条
 * Created by yinzhiyu on 2018-2-24.
 */

public class CompletedView extends View {
    //水波纹
    private Paint mPaint;
    private Path mPath;
    private int mItemWaveLength = 1000;
    private int dx;

    // 画实心圆的画笔
    private Paint mCirclePaint;
    // 画圆环的画笔
    private Paint mRingPaint;
    // 画圆环的画笔背景色
    private Paint mRingPaintBg;
    // 画字体的画笔
    private Paint mTextPaint;
    // 圆形颜色
    private int mCircleColor;
    // 圆环颜色
    private int mRingColor;
    // 圆环背景颜色
    private int mRingBgColor;
    // 半径
    private float mRadius;
    // 圆环半径
    private float mRingRadius;
    // 圆环宽度
    private float mStrokeWidth;
    // 圆心x坐标
    private int mXCenter;
    // 圆心y坐标
    private int mYCenter;
    // 字的长度
    private float mTxtWidth;
    // 字的高度
    private float mTxtHeight;
    // 总进度
    private int mTotalProgress = 100;
    // 当前进度
    private int mProgress;

    public CompletedView(Context context) {
        this(context, null);
    }

    public CompletedView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CompletedView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public CompletedView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        //获取自定义属性
        initAttrs(context, attrs);
        //初始化畫筆
        initVariable();
    }

    /**
     * 初始化畫筆
     */
    private void initVariable() {
        //内圆
//        mCirclePaint = new Paint();
//        mCirclePaint.setAntiAlias(true);//抗锯齿功能
//        mCirclePaint.setColor(mCircleColor);//设置画笔颜色（自定义属性）
//        mCirclePaint.setStyle(Paint.Style.FILL);//设置填充样式-填充内部
        //水波纹
        mPath = new Path();
        mPaint = new Paint();
        mPaint.setColor(Color.GREEN);
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);

        //外圆弧背景
        mRingPaintBg = new Paint();
        mRingPaintBg.setAntiAlias(true);//抗锯齿功能
        mRingPaintBg.setColor(mRingBgColor);//设置画笔颜色（自定义属性）
        mRingPaintBg.setStyle(Paint.Style.STROKE);//设置填充样式-描边
        mRingPaintBg.setStrokeWidth(mStrokeWidth);//設置圆环宽度（自定义属性）

        //外圆弧
        mRingPaint = new Paint();
        mRingPaint.setAntiAlias(true);//抗锯齿功能
        mRingPaint.setColor(mRingColor);//设置画笔颜色（自定义属性）
        mRingPaint.setStyle(Paint.Style.STROKE);//设置填充样式-描边
        mRingPaint.setStrokeWidth(mStrokeWidth);//設置圆环宽度（自定义属性）
        //mRingPaint.setStrokeCap(Paint.Cap.ROUND);//设置线冒样式，有圆 有方


        //中间字
        mTextPaint = new Paint();
        mTextPaint.setAntiAlias(true);//抗锯齿功能
        mTextPaint.setStyle(Paint.Style.FILL);//设置填充样式-填充内部
        mTextPaint.setColor(mRingColor);//设置画笔颜色（自定义属性）
        mTextPaint.setTextSize(mRadius / 2);//設置字的大小（自定义属性：圆形半径/2）

        Paint.FontMetrics fm = mTextPaint.getFontMetrics();
        mTxtHeight = (int) Math.ceil(fm.descent - fm.ascent);

    }

    /**
     * 获取自定义属性
     *
     * @param context
     * @param attrs
     */
    private void initAttrs(Context context, AttributeSet attrs) {
        TypedArray typeArray = context.getTheme().obtainStyledAttributes(attrs,
                R.styleable.TasksCompletedView, 0, 0);
        mRadius = typeArray.getDimension(R.styleable.TasksCompletedView_radius, 80);
        mStrokeWidth = typeArray.getDimension(R.styleable.TasksCompletedView_strokeWidth, 10);
        mCircleColor = typeArray.getColor(R.styleable.TasksCompletedView_circleColor, 0xFFFFFFFF);
        mRingColor = typeArray.getColor(R.styleable.TasksCompletedView_ringColor, 0xFFFFFFFF);
        mRingBgColor = typeArray.getColor(R.styleable.TasksCompletedView_ringBgColor, 0xFFFFFFFF);

        mRingRadius = mRadius + mStrokeWidth / 2;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //根据给定的长度，获取圆心X的坐标，
        mXCenter = getWidth() / 2;
        mYCenter = getHeight() / 2;
        //--------水波纹--------
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
        //内圆
//        canvas.drawCircle(mXCenter, mYCenter, mRadius, mCirclePaint);

        //外圆弧背景
        RectF oval1 = new RectF();
        oval1.left = (mXCenter - mRingRadius);
        oval1.top = (mYCenter - mRingRadius);
        oval1.right = mRingRadius * 2 + (mXCenter - mRingRadius);
        oval1.bottom = mRingRadius * 2 + (mYCenter - mRingRadius);
        //圆弧所在的椭圆对象、圆弧的起始角度、圆弧的角度、是否显示半径连线
        canvas.drawArc(oval1, 0, 360, false, mRingPaintBg);
        //外圆弧
        if (mProgress > 0) {
            RectF oval = new RectF();
            oval.left = (mXCenter - mRingRadius);
            oval.top = (mYCenter - mRingRadius);
            oval.right = mRingRadius * 2 + (mXCenter - mRingRadius);
            oval.bottom = mRingRadius * 2 + (mYCenter - mRingRadius);
            canvas.drawArc(oval, -90, ((float) mProgress / mTotalProgress) * 360, false, mRingPaint); //

            //字体
            String txt = mProgress + "分";
            mTxtWidth = mTextPaint.measureText(txt, 0, txt.length());
            canvas.drawText(txt, mXCenter - mTxtWidth / 2, mYCenter + mTxtHeight / 4, mTextPaint);
        }



    }

    //设置进度
    public void setProgress(int progress) {
        mProgress = progress;
        postInvalidate();//重绘
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
