package com.example.yinzhiyu.butterknifedemo.diyview;

import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.yinzhiyu.butterknifedemo.LeakApplication;
import com.example.yinzhiyu.butterknifedemo.R;
import com.squareup.leakcanary.RefWatcher;

import java.lang.ref.WeakReference;

/**
 * 小米动画
 */
public class TriangleActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnStart;

    private MyVideoView myVideoView;
    private MyHandler handler = new MyHandler(this);
    /**
     * 定义的标识位,判断MyThread中的循环是否执行
     **/
    private static boolean mIsRunning = false;

    /**
     * 将标识位设置成false跳出循环
     **/
    public void setmIsRunning() {
        mIsRunning = false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_triangle);
        btnStart = (Button) findViewById(R.id.btn_start);
        btnStart.setOnClickListener(this);
        myVideoView = (MyVideoView) findViewById(R.id.myloadview);
        MyThread myThread = new MyThread(this);
        myThread.start();
    }

    @Override
    public void onClick(View v) {
        myVideoView.startTranglesAnimation();
    }

    private static class MyThread extends Thread {
        private WeakReference<TriangleActivity> weakReference;

        public MyThread(TriangleActivity mainActivity) {
            weakReference = new WeakReference<TriangleActivity>(mainActivity);
        }

        @Override
        public void run() {

            mIsRunning = true;

            for (int i = 0; i < 20; i++) {
                if (!mIsRunning) {
                    break;
                }
                SystemClock.sleep(2000);
                if (weakReference != null && weakReference.get() != null) {
                    //Message message = mHandler.obtainMessage();
                    //message.what = 0;
                    //mHandler.sendMessageDelayed(message, 5000);
                    Message message = weakReference.get().handler.obtainMessage();
                    message.what = i;
                    weakReference.get().handler.sendMessageDelayed(message, 5000);
                }
            }
        }
    }

    private static class MyHandler extends Handler {
        private WeakReference<TriangleActivity> weakReference;

        public MyHandler(TriangleActivity mainActivity) {
            weakReference = new WeakReference<TriangleActivity>(mainActivity);
        }

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    if (weakReference != null && weakReference.get() != null) {
                        Log.d("xxxxx", String.valueOf(msg.what));
                    }
                    break;
                default:
                    if (weakReference != null && weakReference.get() != null) {
                        Log.d("xxxxx", String.valueOf(msg.what));
                    }
                    break;
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 将标识位设置成false
        setmIsRunning();
        // 清空message中的消息
        handler.removeCallbacksAndMessages(null);
    }
}
