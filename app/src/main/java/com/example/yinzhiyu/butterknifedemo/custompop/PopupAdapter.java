package com.example.yinzhiyu.butterknifedemo.custompop;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.yinzhiyu.butterknifedemo.R;

import java.util.List;
/**
 * Created by yinzhiyu on 2018-2-7.
 */

public class PopupAdapter extends RecyclerView.Adapter{
    private List<String> mData;

    public void setData(List<String> data) {
        mData = data;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.popup_list_item,null));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;

        viewHolder.mTextView.setText(mData.get(position));
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0:mData.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView mTextView;
        public ViewHolder(View itemView) {
            super(itemView);
            mTextView = (TextView) itemView.findViewById(R.id.text_content);
        }
    }
}