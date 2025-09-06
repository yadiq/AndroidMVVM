package com.hqumath.demo.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.hqumath.github.R;
import com.hqumath.demo.base.BaseRecyclerAdapter;
import com.hqumath.demo.base.BaseRecyclerViewHolder;
import com.hqumath.demo.bean.CommitEntity;
import com.hqumath.demo.bean.ReposEntity;
import com.hqumath.demo.bean.UserInfoEntity;
import com.hqumath.demo.utils.CommonUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class MyRecyclerAdapters {

    //我的跟随
    public static class FollowRecyclerAdapter extends BaseRecyclerAdapter<UserInfoEntity> {
        public FollowRecyclerAdapter(Context context, List<UserInfoEntity> mData) {
            super(context, mData, R.layout.recycler_item_followers);
        }

        public void setData(List<UserInfoEntity> list) {
            this.mData = list;
        }

        @Override
        public void convert(BaseRecyclerViewHolder holder, int position) {
            UserInfoEntity data = mData.get(position);
            holder.setText(R.id.tv_name, data.getLogin());
            ImageView ivHead = holder.getView(R.id.iv_head);
            if (!TextUtils.isEmpty(data.getAvatar_url())) {
                Glide.with(CommonUtil.getContext())
                        .load(data.getAvatar_url())
                        .circleCrop()
                        .into(ivHead);
            }
        }
    }

    //我的仓库
    public static class ReposRecyclerAdapter extends BaseRecyclerAdapter<ReposEntity> {
        public ReposRecyclerAdapter(Context context, List<ReposEntity> mData) {
            super(context, mData, R.layout.recycler_item_repos);
        }

        @Override
        public void convert(BaseRecyclerViewHolder holder, int position) {
            ReposEntity data = mData.get(position);
            holder.setText(R.id.tv_name, data.getName());
            holder.setText(R.id.tv_description, data.getDescription());
            holder.setText(R.id.tv_author, data.getOwner().getLogin());
        }
    }

    //提交记录
    public static class CommitsRecyclerAdapter extends BaseRecyclerAdapter<CommitEntity> {
        public CommitsRecyclerAdapter(Context context, List<CommitEntity> mData) {
            super(context, mData, R.layout.recycler_item_commits);
        }

        @Override
        public void convert(BaseRecyclerViewHolder holder, int position) {
            CommitEntity data = mData.get(position);
            holder.setText(R.id.tv_name, data.getCommit().getCommitter().getName());
            holder.setText(R.id.tv_message, data.getCommit().getMessage());
            holder.setText(R.id.tv_sha, data.getSha());
            //时间格式化
            String date = data.getCommit().getCommitter().getDate();//2011-12-29T04:45:11Z
            date = date.replace("Z", " UTC");//UTC是世界标准时间
            SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss Z");
            SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try {
                Date date1 = format1.parse(date);
                String date2 = format2.format(date1);
                holder.setText(R.id.tv_time, date2);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }
}


