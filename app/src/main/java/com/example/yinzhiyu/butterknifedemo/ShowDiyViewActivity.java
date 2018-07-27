package com.example.yinzhiyu.butterknifedemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

import com.example.yinzhiyu.butterknifedemo.diyview.MyView;
import com.example.yinzhiyu.butterknifedemo.diyview.TrainDrawPath;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by yinzhiyu on 2018/1/22.
 */

public class ShowDiyViewActivity extends AppCompatActivity {
    @BindView(R.id.tv_show)
    TextView tvShow;
    @BindView(R.id.tdp)
    TrainDrawPath mTdp;
    @BindView(R.id.reset)
    Button mReset;
    @BindView(R.id.mv)
    MyView mMv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showdiyview);
        ButterKnife.bind(this);
        mMv.startAnim();

    }

//    @OnClick({R.id.tdp, R.id.reset})
//    public void onViewClicked(View view) {
//        switch (view.getId()) {
//            case R.id.tdp:
//                break;
//            case R.id.reset:
//                mTdp.reset();
//                break;
//        }
//    }
}
