package com.example.yinzhiyu.butterknifedemo;

import android.view.View;
import android.widget.Button;

import com.example.yinzhiyu.butterknifedemo.diyview.CompletedView;
import com.example.yinzhiyu.butterknifedemo.easypopup.base.BaseActivity;
import com.example.yinzhiyu.butterknifedemo.easypopup.basic.BasicActivity;
import com.example.yinzhiyu.butterknifedemo.easypopup.easypop.EasyPopActivity;
import com.example.yinzhiyu.butterknifedemo.easypopup.easypop.RecyclerViewActivity;

public class EasyPopupActivity extends BaseActivity {
    private int mTotalProgress = 90;
    private int mCurrentProgress = 0;
    //进度条
    private CompletedView mTasksView;
    //使用场景 QQ+号，直播礼物弹窗，微信朋友圈评论

    private Button mBasicBtn;

    private Button mEasyBtn;

    private Button mRvBtn;

    @Override
    protected int setLayoutId() {
        return R.layout.activity_easy_popup;
    }

    @Override
    protected void initVariables() {

    }

    @Override
    protected void initViews() {
        mBasicBtn = (Button) findViewById(R.id.btn_basic);
        mEasyBtn = (Button) findViewById(R.id.btn_easy);
        mRvBtn = (Button) findViewById(R.id.btn_recycler);
        mTasksView = (CompletedView) findViewById(R.id.tasks_view);
        mTasksView.startAnim();
        new Thread(new ProgressRunable()).start();
    }

    @Override
    protected void initEvents() {
        mBasicBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goTo(BasicActivity.class);
            }
        });

        mEasyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goTo(EasyPopActivity.class);
            }
        });

        mRvBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goTo(RecyclerViewActivity.class);
            }
        });
    }

    class ProgressRunable implements Runnable {
        @Override
        public void run() {
            while (mCurrentProgress < mTotalProgress) {
                mCurrentProgress += 1;
                mTasksView.setProgress(mCurrentProgress);
                try {
                    Thread.sleep(90);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
