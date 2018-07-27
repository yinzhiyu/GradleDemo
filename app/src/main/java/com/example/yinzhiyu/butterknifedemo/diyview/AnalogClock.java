package com.example.yinzhiyu.butterknifedemo.diyview;

import android.annotation.TargetApi;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.Nullable;
import android.text.format.Time;
import android.util.AttributeSet;
import android.view.View;

import com.example.yinzhiyu.butterknifedemo.R;

import java.util.TimeZone;

/**
 * Created by yinzhiyu on 2018/1/19.
 */

public class AnalogClock extends View {

    private Time mCalendar;//用来记录当前时间
    //用来存放三张图片资源
    private Drawable mHourHand;
    private Drawable mMinuteHand;
    private Drawable mDial;

    //用来记录表盘图片的宽和高，
    //以便帮助我们在onMeasure中确定View的大小
    //毕竟，我们的View中最大的一个Drawable就是它了。
    private int mDialWidth;
    private int mDialHeight;

    //用来记录View是否被加入到了Window中，我们在View attached到
    //Window时注册监听器，监听时间的变更，并根据时间的变更，改变自己
    //的绘制，在View从Window中剥离时，解除注册，因为我们不需要再监听
    //时间变更了，没人能看得到我们的View了。
    private boolean mAttached;

    private float mMinutes;
    private float mHour;

    //用来跟踪我们的View 的尺寸的变化，
    //当发生尺寸变化时，我们在绘制自己时要进行适当的缩放。
    private boolean mChanged;

    public AnalogClock(Context context) {
        this(context, null);
    }

    public AnalogClock(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AnalogClock(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public AnalogClock(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

        final Resources r = context.getResources();
        if (mDial == null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                mDial = context.getDrawable(R.drawable.click_dial);
            }
        }
        if (mHourHand == null) {
            mHourHand = context.getDrawable(R.drawable.clock_hand_hour);
        }
        if (mMinuteHand == null) {
            mMinuteHand = context.getDrawable(R.drawable.clock_hand_minute);
        }
        mCalendar = new Time();

        mDialWidth = mDial.getIntrinsicWidth();
        mDialHeight = mDial.getIntrinsicHeight();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);

        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        float hScale = 1.0f;
        float vScale = 1.0f;

        if (widthMode != MeasureSpec.UNSPECIFIED && widthSize < mDialWidth) {
            hScale = (float) widthSize / (float) mDialWidth;
        }
        if (heightMode != MeasureSpec.UNSPECIFIED && heightSize < mDialHeight) {
            vScale = (float) heightSize / (float) mDialHeight;
        }
        float scale = Math.min(hScale, vScale);
        setMeasuredDimension(
                resolveSizeAndState((int) (mDialWidth * scale), widthMeasureSpec, 0),
                resolveSizeAndState((int) (mDialHeight * scale), heightMeasureSpec, 0)
        );

    }

    /**
     * View可以把自己想要的宽和高进行一个resolveSizeAndState处理，就可以达到上述目的。
     * 即如果想要的大小没超过要求，一切都Ok，
     * 如果超过了，在该方法内部，就会把尺寸调整成符合ViewGroup要求的，
     * 但是也会在尺寸中设置一个标记，告诉ViewGroup，这个大小是子View委屈求全的结果。
     * 至于ViewGroup会不会理会这一标记，要看不同的ViewGroup了。
     * 如果你实现自己的ViewGroup，最好还是关注下这个标记，毕竟作为大哥的你，
     * 最主要的职责就是把自己的小弟（子View）安排好，让它们都满意嘛。
     * （这一点，我没有看到任何一篇讲解自定义View的文章提到过！）
     * 什么？好奇的你想看看究竟是怎样设置标记的？来来来，满足你：
     *
     * @param size
     * @param measureSpec
     * @param childMeasureState
     * @return
     */
    public static int resolveSizeAndState(int size, int measureSpec, int childMeasureState) {
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
            case MeasureSpec.EXACTLY://match
                result = specSize;
                break;
            case MeasureSpec.UNSPECIFIED:
            default:
                result = size;

        }
        return result | (childMeasureState & MEASURED_STATE_MASK);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mChanged = true;
    }

    private void onTimeChanged() {
        mCalendar.setToNow();

        int hour = mCalendar.hour;
        int minute = mCalendar.minute;
        int second = mCalendar.second;
        /*这里我们为什么不直接把minute设置给mMinutes，而是要加上
            second /60.0f呢，这个值不是应该一直为0吗？
            这里又涉及到Calendar的 一个知识点，
            也就是它可以是Linient模式，
            此模式下，second和minute是可能超过60和24的，具体这里就不展开了，
            如果不是很清楚，建议看看Google的官方文档中讲Calendar的部分*/
        mMinutes = minute + second / 60.0f;
        mHour = hour + mMinutes / 60.0f;
        mChanged = true;
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (!mAttached) {
            IntentFilter filter = new IntentFilter();
            //这里确定我们要监听的三种系统广播
            filter.addAction(Intent.ACTION_TIME_TICK);
            filter.addAction(Intent.ACTION_TIME_CHANGED);
            filter.addAction(Intent.ACTION_TIMEZONE_CHANGED);
            getContext().registerReceiver(mIntentReceiver, filter);
        }
        mCalendar = new Time();
        onTimeChanged();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (mAttached) {
            getContext().unregisterReceiver(mIntentReceiver);
            mAttached = false;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //View尺寸变化后，我们用changed变量记录下来，
        //同时，恢复mChanged为false，以便继续监听View的尺寸变化。
        boolean changed = mChanged;
        if (changed) {
            mChanged = false;
        }
        /* 请注意，这里的availableWidth和availableHeight，
           每次绘制时是可能变化的，
           我们可以从mChanged变量的值判断它是否发生了变化，
           如果变化了，说明View的尺寸发生了变化，
           那么就需要重新为时针、分针设置Bounds，
           因为我们需要时针，分针始终在View的中心。*/
        int availableWidth = super.getRight() - super.getLeft();
        int availableHeight = super.getBottom() - super.getTop();
        /* 这里的x和y就是View的中心点的坐标，
          注意这个坐标是以View的左上角为0点，向右x，向下y的坐标系来计算的。
          这个坐标系主要是用来为View中的每一个Drawable确定位置。
          就像View的坐标是用parent的左上角为0点的坐标系计算得来的一样。
          简单来讲，就是ViewGroup用自己左上角为0点的坐标系为
          各个子View安排位置，
          View同样用自己左上角为0点的坐标系
          为它里面的Drawable安排位置。
          注意不要搞混了。*/
        int x = availableWidth / 2;
        int y = availableHeight / 2;

        final Drawable dial = mDial;
        int w = dial.getIntrinsicWidth();
        int h = dial.getIntrinsicHeight();
        boolean scaled = false;
        /*如果可用的宽高小于表盘图片的宽高，
           就要进行缩放，不过这里，我们是通过坐标系的缩放来实现的。
          而且，这个缩放效果影响是全局的，
          也就是下面绘制的表盘、时针、分针都会受到缩放的影响。*/
        if (availableWidth < w || availableHeight < h) {
            scaled = true;
            float scale = Math.min((float) availableWidth / (float) w,
                    (float) availableHeight / (float) h);
            canvas.save();
            canvas.scale(scale, scale, x, y);
        }
        /*如果尺寸发生变化，我们要重新为表盘设置Bounds。
           这里的Bounds就相当于是为Drawable在View中确定位置，
           只是确定的方式更直接，直接在View中框出一个与Drawable大小
           相同的矩形，
           Drawable就在这个矩形里绘制自己。
           这里框出的矩形，是以(x,y)为中心的，宽高等于表盘图片的宽高的一个矩形，
           不用担心表盘图片太大绘制不完整，
            因为我们已经提前进行了缩放了。*/
        if (changed) {
            dial.setBounds(x - (w / 2), y - (h / 2), x + (w / 2), y + (h / 2));
        }
        dial.draw(canvas);

        canvas.save();
        /*根据小时数，以点(x,y)为中心旋转坐标系。
            如果你对来回旋转的坐标系感到头晕，摸不着头脑，
            建议你看一下**徐宜生**《安卓群英传》中讲解2D绘图部分中的Canvas一节。*/
        canvas.rotate(mHour / 12.0f * 360.0f, x, y);
        final Drawable hourHand = mHourHand;

        //同样，根据变化重新设置时针的Bounds
        if (changed) {
            w = hourHand.getIntrinsicWidth();
            h = hourHand.getIntrinsicHeight();
        /* 仔细体会这里设置的Bounds，我们所画出的矩形，
                同样是以(x,y)为中心的
                矩形，时针图片放入该矩形后，时针的根部刚好在点(x,y)处，
                因为我们之前做时针图片时，
                已经让图片中的时针根部在图片的中心位置了，
                虽然，看起来浪费了一部分图片空间（就是时针下半部分是空白的），
                但却换来了建模的简单性，还是很值的。*/
            hourHand.setBounds(x - (w / 2), y - (h / 2), x + (w / 2), y + (h / 2));
        }
        hourHand.draw(canvas);
        canvas.restore();

        canvas.save();
        //根据分针旋转坐标系
        canvas.rotate(mMinutes / 60.0f * 360.0f, x, y);
        final Drawable minuteHand = mMinuteHand;

        if (changed) {
            w = minuteHand.getIntrinsicWidth();
            h = minuteHand.getIntrinsicHeight();
            minuteHand.setBounds(x - (w / 2), y - (h / 2), x + (w / 2), y + (h / 2));
        }
        minuteHand.draw(canvas);
        canvas.restore();
        //最后，我们把缩放的坐标系复原。
        if (scaled) {
            canvas.restore();
        }

    }

    private final BroadcastReceiver mIntentReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            //这个if判断主要是用来在时区发生变化时，更新mCalendar的时区的，这
            //样，我们的自定义View在全球都可以使用了。
            if (intent.getAction().equals(Intent.ACTION_TIMEZONE_CHANGED)) {
                String tz = intent.getStringExtra("time-zone");
                mCalendar = new Time(TimeZone.getTimeZone(tz).getID());
            }
            //进行时间的更新
            onTimeChanged();
            //invalidate当然是用来引发重绘了。
            invalidate();
        }
    };
}
