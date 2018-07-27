package com.example.yinzhiyu.butterknifedemo.diyview;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by yinzhiyu on 2018/1/19.
 */

public class ChaosCompassView extends View {

    public ChaosCompassView(Context context) {
        super(context);
    }

    public ChaosCompassView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

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
            case MeasureSpec.EXACTLY:
                result = specSize;
                break;
            case MeasureSpec.UNSPECIFIED:
            default:
                result = size;

        }
        return result | (childMeasureState & MEASURED_STATE_MASK);
    }
}
