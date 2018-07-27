package com.example.yinzhiyu.butterknifedemo.diyview;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Build;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.example.yinzhiyu.butterknifedemo.R;

/**
 * Created by yinzhiyu on 2018/1/22.
 * 画几何图型
 */

public class TrainDrawLine extends View {
    //自定义控件大小
    private int defaultSize;
    private int diyColor;

    public TrainDrawLine(Context context) {
        this(context, null);
    }

    public TrainDrawLine(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TrainDrawLine(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public TrainDrawLine(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
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

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = new Paint();
        paint.setColor(Color.RED);  //设置画笔颜色
        paint.setStyle(Paint.Style.FILL);//设置填充样式
        paint.setStrokeWidth(5);//设置画笔宽度
//        //画两条直线
//        float[] pts = {10, 10, 100, 100, 200, 200, 400, 400};
//        canvas.drawLines(pts, paint);
//        //画个点
//        canvas.drawPoint(110, 110, paint);
//        //画多个点
//        float []pts1={5,5,120,120,190,190,410,410};
//        canvas.drawPoints(pts1, 2, 4, paint);

        //矩形工具类RectF与Rect
        canvas.drawRect(10, 10, 100, 100, paint);//直接构造

        RectF rect = new RectF(120, 10, 210, 100);
//        canvas.drawRect(rect, paint);//使用RectF构造
        canvas.drawRoundRect(rect, 20, 10, paint);//圆角矩形

        Rect rect2 = new Rect(230, 10, 320, 100);
        canvas.drawRect(rect2, paint);//使用Rect构造

        canvas.drawCircle(400, 60, 50, paint);
        //  将画笔设为填充
        RectF rect1 = new RectF(100, 10, 300, 100);
        canvas.drawArc(rect1, 0, 90, true, paint);
    }
}
