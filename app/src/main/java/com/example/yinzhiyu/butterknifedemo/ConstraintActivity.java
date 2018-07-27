package com.example.yinzhiyu.butterknifedemo;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.constraint.Guideline;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yinzhiyu.butterknifedemo.custompop.CustomPopWindow;
import com.example.yinzhiyu.butterknifedemo.custompop.DiyPopupAdapter;
import com.example.yinzhiyu.butterknifedemo.custompop.PopupActivity;
import com.example.yinzhiyu.butterknifedemo.diyview.AnalogClock;
import com.example.yinzhiyu.butterknifedemo.diyview.TriangleActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ffmpeglib.utils.FFmpegKit;
import ffmpeglib.utils.ThreadPoolUtils;

/**
 * Created by yinzhiyu on 2018/1/11.
 * 测试Demo
 */
public class ConstraintActivity extends AppCompatActivity {

    @BindView(R.id.xiaomi)
    Button xiaomi;
    @BindView(R.id.other)
    Button other;
    @BindView(R.id.et_account)
    EditText etAccount;
    @BindView(R.id.et_pas)
    EditText etPas;
    @BindView(R.id.button13)
    Button button13;
    @BindView(R.id.button15)
    Button button15;
    @BindView(R.id.analogClock)
    AnalogClock analogClock;
    @BindView(R.id.textView)
    TextView textView;
    @BindView(R.id.textView2)
    TextView textView2;
    @BindView(R.id.guideline)
    Guideline guideline;
    @BindView(R.id.button)
    Button button;
    @BindView(R.id.button8)
    Button button8;

    private CustomPopWindow mListPopWindow;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_constraint);
        ButterKnife.bind(this);
        etAccount.setText(getLocalVersionName(getApplication()));
        etPas.setText(getLocalVersion(getApplication()) + "版本");
    }

    @OnClick({R.id.xiaomi, R.id.other, R.id.button, R.id.button13, R.id.button15, R.id.button8})
    public void onViewClicked(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.xiaomi:
                intent = new Intent(this, TriangleActivity.class);
                startActivity(intent);
                break;
            case R.id.other:
                showPopListView();
                Toast.makeText(this, BuildConfig.AlphaUrl, Toast.LENGTH_SHORT).show();
                break;
            case R.id.button:
                intent = new Intent(this, ShowDiyViewActivity.class);
                startActivity(intent);
                break;
            case R.id.button13://登陆点击
                intent = new Intent(this, IBMSActivity.class);
                startActivity(intent);
                break;
            case R.id.button15://登陆点击
                intent = new Intent(this, PopupActivity.class);
                startActivity(intent);
                break;
            case R.id.button8://登陆点击
                intent = new Intent(this, EasyPopupActivity.class);
                startActivity(intent);
                break;
        }
    }

    private void showPopListView() {
        View contentView = LayoutInflater.from(this).inflate(R.layout.diy_pop_list, null);
        //处理popWindow 显示内容
        handleListView(contentView);
        //创建并显示popWindow
        mListPopWindow = new CustomPopWindow.PopupWindowBuilder(this)
                .setOutsideTouchable(true)
                .setView(contentView)
                .size(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)//显示大小
                .create()
                .showAsDropDown(other, 0, 20);
    }

    private void handleListView(View contentView) {
        RecyclerView recyclerView = (RecyclerView) contentView.findViewById(R.id.recyclerView);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
        DiyPopupAdapter adapter = new DiyPopupAdapter();
        adapter.setData(ConstraintActivity.this, mockData());
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }

    private List<String> mockData() {
        List<String> data = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            data.add("Item:" + i);
        }

        return data;
    }
    /**
     * 获取本地软件版本号
     */
    public static int getLocalVersion(Context ctx) {
        int localVersion = 0;
        try {
            PackageInfo packageInfo = ctx.getApplicationContext()
                    .getPackageManager()
                    .getPackageInfo(ctx.getPackageName(), 0);
            localVersion = packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return localVersion;
    }

    /**
     * 获取本地软件版本号名称
     */
    public static String getLocalVersionName(Context ctx) {
        String localVersion = "";
        try {
            PackageInfo packageInfo = ctx.getApplicationContext()
                    .getPackageManager()
                    .getPackageInfo(ctx.getPackageName(), 0);
            localVersion = packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return localVersion;
    }

    /**
     * videoUrl是原始视频文件地址
     * imageUrl是水印图片地址
     * musicUrl是音频mp3地址
     * outputUrl是最终输出视频地址。
     */
    public void FFmpegKit(final String videoUrl, final String imageUrl, final String musicUrl, final String outputUrl) {
        Runnable compoundRun = new Runnable() {
            @Override
            public void run() {
                String[] commands = new String[11];
                commands[0] = "ffmpeg";
                commands[1] = "-i";
                commands[2] = videoUrl;
                commands[3] = "-i";
                commands[4] = imageUrl;
                commands[5] = "-filter_complex";
                commands[6] = "overlay=(main_w-overlay_w)/2:(main_h-overlay_h)/2";
                commands[7] = "-i";
                commands[8] = musicUrl;
                commands[9] = "-y";
                commands[10] = outputUrl;

                FFmpegKit.execute(commands, new FFmpegKit.KitInterface() {
                    @Override
                    public void onStart() {
                        Log.d("FFmpegLog LOGCAT", "FFmpeg 命令行开始执行了...");
                    }

                    @Override
                    public void onProgress(int progress) {
                        Log.d("FFmpegLog LOGCAT", "done com" + "FFmpeg 命令行执行进度..." + progress);
                    }

                    @Override
                    public void onEnd(int result) {
                        Log.d("FFmpegLog LOGCAT", "FFmpeg 命令行执行完成...");
//                        getWindow().setFlags(0, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
                    }
                });
            }
        };
        ThreadPoolUtils.execute(compoundRun);
    }
}
