package com.hqumath.demo.base;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

/**
 * ****************************************************************
 * 文件名称: BaseRecyclerAdapter
 * 作    者: Created by gyd
 * 创建时间: 2019/2/14 14:22
 * 文件描述: 通用RecyclerView适配器
 * 注意事项:
 * 版权声明:
 * ****************************************************************
 */
public abstract class BaseRecyclerAdapter<T> extends RecyclerView.Adapter<BaseRecyclerViewHolder> {

    private LayoutInflater mLayoutInflater;
    private OnItemClickLitener mOnItemClickListener;

    protected List<T> mData;
    private int mLayoutId;

    public BaseRecyclerAdapter(Context context, List<T> mData, int layoutId) {
        mLayoutInflater = LayoutInflater.from(context);
        this.mData = mData;
        this.mLayoutId = layoutId;
    }

    @NonNull
    @Override
    public BaseRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(mLayoutId, parent, false);
        if (mOnItemClickListener != null)
            view.setOnClickListener(v -> {
                int position = ((BaseRecyclerViewHolder) v.getTag()).getAdapterPosition();
                //视图正在重新绘制，不要点击
                if (position != RecyclerView.NO_POSITION)
                    mOnItemClickListener.onItemClick(v, position);
            });
        return new BaseRecyclerViewHolder(view);
    }

    @Override
    public final void onBindViewHolder(@NonNull BaseRecyclerViewHolder holder, int position) {
        convert(holder, position);
    }

    /**
     * 该方法需要在setAdapter之前调用
     */
    public BaseRecyclerAdapter<T> setOnItemClickListener(OnItemClickLitener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
        return this;
    }

    public abstract void convert(BaseRecyclerViewHolder holder, int position);

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void addItem(T item, boolean isNotify) {
        mData.add(item);
        if (isNotify) notifyDataSetChanged();
    }

    public void addAllItem(List<T> items, boolean isNotify) {
        mData.addAll(items);
        if (isNotify) notifyDataSetChanged();
    }

    public void clearItems() {
        mData.clear();
    }

    public interface OnItemClickLitener {
        void onItemClick(View view, int position);
    }
}
