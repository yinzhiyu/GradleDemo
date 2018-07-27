package com.example.yinzhiyu.butterknifedemo.diyview;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Build;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by yinzhiyu on 2018/1/22.
 * 画路径和文字
 */

public class TrainDrawPath extends View {
    //自定义控件大小
    private int defaultSize;
    private int diyColor;


    //手指轨迹
//    private Path mPath = new Path();
    //方法二
//    private float mPreX, mPreY;


    public TrainDrawPath(Context context) {
        this(context, null);
    }

    public TrainDrawPath(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TrainDrawPath(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public TrainDrawPath(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    //方法一：Path.lineTo（）
//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        switch (event.getAction()) {
//            case MotionEvent.ACTION_DOWN: {
//                mPath.moveTo(event.getX(), event.getY());
//                return true;
//            }
//            case MotionEvent.ACTION_MOVE:
//                mPath.lineTo(event.getX(),event.getY());
//                postInvalidate();
//                break;
//                default:
//                    break;
//
//        }
//        return super.onTouchEvent(event);
//    }
    //方法二：贝塞尔曲线
//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        switch (event.getAction()) {
//            case MotionEvent.ACTION_DOWN: {
//                mPath.moveTo(event.getX(), event.getY());
//                mPreX = event.getX();
//                mPreY = event.getY();
//                return true;
//            }
//            case MotionEvent.ACTION_MOVE: {
//                float endX = (mPreX + event.getX()) / 2;
//                float endY = (mPreY + event.getY()) / 2;
//                mPath.quadTo(mPreX, mPreY, endX, endY);
//                mPreX = event.getX();
//                mPreY = event.getY();
//                invalidate();
//            }
//            break;
//            default:
//                break;
//        }
//        return super.onTouchEvent(event);
//    }


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
        //画笔设置
//        Paint paint = new Paint();
//        paint.setColor(Color.RED);  //设置画笔颜色
//        paint.setStyle(Paint.Style.STROKE);//设置填充样式--描边
//        paint.setStrokeWidth(5);//设置画笔宽度
        //矩形
//        Path path = new Path();
//
//        path.moveTo(10, 10); //设定起始点
//        path.lineTo(10, 100);//第一条直线的终点，也是第二条直线的起点
//        path.lineTo(300, 100);//画第二条直线
//        path.lineTo(500, 100);//第三条直线
//        path.close();//闭环
//
//        canvas.drawPath(path, paint);
        //---------------圆形路径----------------------------
//        Path path = new Path();
//        path.addCircle(200, 200, 100, Path.Direction.CCW);
//        canvas.drawPath(path, paint);
        //---------------椭圆路径----------------------------
//        Path path = new Path();
//        RectF rect =  new RectF(50, 50, 240, 200);
//        path.addOval(rect, Path.Direction.CCW);
//        canvas.drawPath(path, paint);
        /**
         * 文字
         * //普通设置
         paint.setStrokeWidth (5);//设置画笔宽度
         paint.setAntiAlias(true); //指定是否使用抗锯齿功能，如果使用，会使绘图速度变慢
         paint.setStyle(Paint.Style.FILL);//绘图样式，对于设文字和几何图形都有效
         paint.setTextAlign(Align.CENTER);//设置文字对齐方式，取值：align.CENTER、align.LEFT或align.RIGHT
         paint.setTextSize(12);//设置文字大小

         //样式设置
         paint.setFakeBoldText(true);//设置是否为粗体文字
         paint.setUnderlineText(true);//设置下划线
         paint.setTextSkewX((float) -0.25);//设置字体水平倾斜度，普通斜体字是-0.25
         paint.setStrikeThruText(true);//设置带有删除线效果

         //其它设置
         paint.setTextScaleX(2);//只会将水平方向拉伸，高度不会变
         */
//        Paint paint=new Paint();
//        paint.setColor(Color.RED);  //设置画笔颜色
//
//        paint.setStrokeWidth (5);//设置画笔宽度
//        paint.setAntiAlias(true); //指定是否使用抗锯齿功能，如果使用，会使绘图速度变慢
//        paint.setTextSize(80);//设置文字大小
//
//        //绘图样式，设置为填充
//        paint.setStyle(Paint.Style.FILL);
//        canvas.drawText("欢迎光临Harvic的博客", 10,100, paint);
//
//        //绘图样式设置为描边
//        paint.setStyle(Paint.Style.STROKE);
//        canvas.drawText("欢迎光临Harvic的博客", 10,200, paint);
//
//        //绘图样式设置为填充且描边
//        paint.setStyle(Paint.Style.FILL_AND_STROKE);
//        canvas.drawText("欢迎光临Harvic的博客", 10,300, paint);
        //canvas绘图方式------------------------
//        Paint paint=new Paint();
//        paint.setColor(Color.RED);  //设置画笔颜色
//
//        paint.setStrokeWidth (5);//设置画笔宽度
//        paint.setAntiAlias(true); //指定是否使用抗锯齿功能，如果使用，会使绘图速度变慢
//        paint.setTextSize(80);//设置文字大小
//        paint.setStyle(Paint.Style.FILL);//绘图样式，设置为填充
//
//        float []pos=new float[]{80,100,
//                80,200,
//                80,300,
//                80,400};
//        canvas.drawPosText("画图示例", pos, paint);//两个构造函数
        //沿路径绘制-------------------------------------------
//        Paint paint=new Paint();
//        paint.setColor(Color.RED);  //设置画笔颜色
//
//        paint.setStrokeWidth (5);//设置画笔宽度
//        paint.setAntiAlias(true); //指定是否使用抗锯齿功能，如果使用，会使绘图速度变慢
//        paint.setTextSize(45);//设置文字大小
//        paint.setStyle(Paint.Style.STROKE);//绘图样式，设置为填充
//
//        String string="风萧萧兮易水寒，壮士一去兮不复返";
//
//        //先创建两个相同的圆形路径，并先画出两个路径原图
//        Path circlePath=new Path();
//        circlePath.addCircle(220,200, 180, Path.Direction.CCW);//逆向绘制,还记得吗,上篇讲过的
//        canvas.drawPath(circlePath, paint);//绘制出路径原形
//
//        Path circlePath2=new Path();
//        circlePath2.addCircle(750,200, 180, Path.Direction.CCW);
//        canvas.drawPath(circlePath2, paint);//绘制出路径原形
//
//        paint.setColor(Color.GREEN);
//        //hoffset、voffset参数值全部设为0，看原始状态是怎样的
//        canvas.drawTextOnPath(string, circlePath, 0, 0, paint);
//        //第二个路径，改变hoffset、voffset参数值
//        canvas.drawTextOnPath(string, circlePath2, 80, 30, paint);
        //-----------------------------------------------------------
//        int baseLineX = 0 ;
//        int baseLineY = 200;
//
//        //画基线
//        Paint paint = new Paint();
//        paint.setColor(Color.RED);
//        canvas.drawLine(baseLineX, baseLineY, 3000, baseLineY, paint);
//        //写文字
//        paint.setColor(Color.GREEN);
//        paint.setTextSize(120); //以px为单位
//        paint.setTextAlign(Paint.Align.CENTER);
//        canvas.drawText("A", baseLineX, baseLineY, paint);
        //-------------------------------带基线的文字-------------------------------
//        String text = "harvic\'s blog";
//        int top = 200;
//        int baseLineX = 0 ;
//
//        //设置paint
//        Paint paint = new Paint();
//        paint.setTextSize(120); //以px为单位
//        paint.setTextAlign(Paint.Align.LEFT);
//
//        //画top线
//        paint.setColor(Color.YELLOW);
//        canvas.drawLine(baseLineX, top, 3000, top, paint);
//
//        //计算出baseLine位置
//        Paint.FontMetricsInt fm = paint.getFontMetricsInt();
//        int baseLineY = top - fm.top;
//
//        //画基线
//        paint.setColor(Color.RED);
//        canvas.drawLine(baseLineX, baseLineY, 3000, baseLineY, paint);
//
//        //写文字
//        paint.setColor(Color.GREEN);
//        canvas.drawText(text, baseLineX, baseLineY, paint);
        //---------------- 贝塞尔曲线-1 -----------------------------------

//        Paint paint = new Paint();
//        paint.setStyle(Paint.Style.STROKE);
//        paint.setColor(Color.RED);
//
//        Path path = new Path();
//        path.moveTo(100, 300);
//        path.quadTo(200, 200, 300, 300);
//        path.quadTo(400, 400, 500, 300);
//
//        canvas.drawPath(path, paint);
        //---------------- 手指轨迹 -----------------------------------
//        Paint paint = new Paint();
//        paint.setColor(Color.GREEN);
//        paint.setStyle(Paint.Style.STROKE);
//        paint.setStrokeWidth(2);
//
//        canvas.drawPath(mPath, paint);
        //---------------- 贝塞尔曲线-rQuadTo -----------------------------------

//        Paint paint = new Paint();
//        paint.setStyle(Paint.Style.STROKE);
//        paint.setColor(Color.RED);
//        paint.setStrokeWidth(2);
//
//        Path path = new Path();
//        path.moveTo(100, 300);
//        path.rQuadTo(100, -100, 200, 0);
//        path.rQuadTo(100, 100, 200, 0);
//        canvas.drawPath(path, paint);

    }
    //手动轨迹
//    public void reset() {
//        mPath.reset();
//        invalidate();
//    }
}
