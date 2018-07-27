package com.example.yinzhiyu.butterknifedemo;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.srl_unread_refresh)
    SwipeRefreshLayout srlUnreadRefresh;
    private List<String> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        list = new ArrayList();
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setHasFixedSize(true);
//        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));//这里用线性显示 类似于listview
//        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));//这里用线性宫格显示 类似于grid view
//        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, OrientationHelper.VERTICAL));//这里用线性宫格显示 类似于瀑布流
//        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
        mRecyclerView.addItemDecoration(new DividerListItemDecoration(this, R.drawable.shape_divide_line));
        setData();
        srlUnreadRefresh.setEnabled(true);
        srlUnreadRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                addData();
            }
        });
    }

    private void setData() {

        list.add("A");
        list.add("B");
        list.add("C");
        list.add("D");
        list.add("E");
        if (srlUnreadRefresh != null) {
            srlUnreadRefresh.setRefreshing(false);
        }
        mRecyclerView.setAdapter(new NormalRecyclerViewAdapter(this, list));
    }

    private void addData() {
        list.clear();
        setData();
        mRecyclerView.setAdapter(new NormalRecyclerViewAdapter(this, list));
    }

}
