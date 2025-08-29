package com.hqumath.demo.base;

import android.util.SparseArray;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * ****************************************************************
 * 文件名称: BaseRecyclerViewHolder
 * 作    者: Created by gyd
 * 创建时间: 2019/2/14 14:27
 * 文件描述: 封装了基础的ViewHolder使用方法
 * 注意事项:
 * 版权声明:
 * ****************************************************************
 */
public class BaseRecyclerViewHolder extends RecyclerView.ViewHolder {

    private View mConvertView;
    private SparseArray<View> mViews = new SparseArray<View>();

    public BaseRecyclerViewHolder(@NonNull View itemView) {
        super(itemView);
        mConvertView = itemView;
        mConvertView.setTag(this);
    }

    public <T extends View> T getView(int viewId) {
        View view = mViews.get(viewId);
        if (view == null) {
            view = mConvertView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T) view;
    }

    public BaseRecyclerViewHolder setText(int viewId, String text) {
        TextView view = getView(viewId);
        view.setText(text);
        return this;
    }

    public View getHolderView() {
        return mConvertView;
    }
}
