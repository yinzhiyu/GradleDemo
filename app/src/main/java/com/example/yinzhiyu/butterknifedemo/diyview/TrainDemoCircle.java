package com.example.yinzhiyu.butterknifedemo.diyview;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.example.yinzhiyu.butterknifedemo.R;

/**
 * Created by yinzhiyu on 2018/1/22.
 */

public class TrainDemoCircle extends View {
    //自定义控件大小
    private int defaultSize;
    private int diyColor;

    public TrainDemoCircle(Context context) {
        this(context, null);
    }

    public TrainDemoCircle(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TrainDemoCircle(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public TrainDemoCircle(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        //第二个参数就是我们在styles.xml文件中的<declare-styleable>标签
        //即属性集合的标签，在R文件中名称为R.styleable+name
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.TrainDemoCircle);
        //第一个参数为属性集合里面的属性，R文件名称：R.styleable+属性集合名称+下划线+属性名称
        //第二个参数为，如果没有设置这个属性，则设置的默认的值
        defaultSize = a.getDimensionPixelSize(R.styleable.TrainDemoCircle_default_size, 100);
        diyColor = a.getColor(R.styleable.TrainDemoCircle_diy_color, 0XFFFFFFFF);
        //最后记得将TypedArray对象回收
        a.recycle();
    }

    private int getMySize(int defaultSize, int measureSpec) {
        int mySize = defaultSize;

        int mode = MeasureSpec.getMode(measureSpec);
        int size = MeasureSpec.getSize(measureSpec);

        switch (mode) {
            case MeasureSpec.UNSPECIFIED: {//如果没有指定大小，就设置为默认大小
                mySize = defaultSize;
                break;
            }
            case MeasureSpec.AT_MOST: {//如果测量模式是最大取值为size
                //我们将大小取最大值,你也可以取其他值
                mySize = size;
                break;
            }
            case MeasureSpec.EXACTLY: {//如果是固定的大小，那就不要去改变它
                mySize = size;
                break;
            }
        }
        return mySize;
    }

    public static int resolveSizeAndState(int size, int measureSpec, int childMeasuredState) {
        final int specMode = MeasureSpec.getMode(measureSpec);
        final int specSize = MeasureSpec.getSize(measureSpec);
        final int result;
        switch (specMode) {
            case MeasureSpec.AT_MOST:
                if (specSize < size) {
                    result = specSize | MEASURED_STATE_TOO_SMALL;
                } else {
                    result = size;
                }
                break;
            case MeasureSpec.EXACTLY:
                result = specSize;
                break;
            case MeasureSpec.UNSPECIFIED:
            default:
                result = size;
        }
        return result | (childMeasuredState & MEASURED_STATE_MASK);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//        int width = getMySize(defaultSize, widthMeasureSpec);
//        int height = getMySize(defaultSize, heightMeasureSpec);
//
//        if (width < height) {
//            height = width;
//        } else {
//            width = height;
//        }
//
//        setMeasuredDimension(width, height);
//-------------------------------------------------------------------
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);

        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        float hScale = 1.0f;
        float vScale = 1.0f;

        if (widthMode != MeasureSpec.UNSPECIFIED && widthSize < defaultSize) {
            hScale = (float) widthSize / (float) defaultSize;
        }
        if (heightMode != MeasureSpec.UNSPECIFIED && heightSize < defaultSize) {
            vScale = (float) heightSize / (float) defaultSize;
        }
        float scale = Math.min(hScale, vScale);
        setMeasuredDimension(
                resolveSizeAndState((int) (defaultSize * scale), widthMeasureSpec, 0),
                resolveSizeAndState((int) (defaultSize * scale), heightMeasureSpec, 0)
        );

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int r = getMeasuredHeight() / 2;//也可以是getMeasuredHeight()/2,本例中我们已经将宽高设置相等了
        //圆心的横坐标为当前的View的左边起始位置+半径
        int centerX = getLeft() + r;
        //圆心的纵坐标为当前的View的顶部起始位置+半径
        int centerY = getTop() + r - 40;

        Paint paint = new Paint();
        paint.setColor(diyColor);
        paint.setAntiAlias(true);//抗锯齿功能
//        paint.setStyle(Paint.Style.STROKE);//设置画笔样式--描边
        paint.setStrokeWidth(30);//设置画笔宽度
        paint.setShadowLayer(10, 15, 15, Color.GREEN);//设置阴影
        //开始绘制
        canvas.drawCircle(centerX, centerY, r, paint);
        //画圆
        canvas.drawCircle(190, 200, 150, paint);

    }
}
