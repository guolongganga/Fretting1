package com.zhsoft.fretting.ui.adapter.index;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zhsoft.fretting.R;
import com.zhsoft.fretting.model.index.HotNewsResp;
import com.zhsoft.fretting.model.index.PopularityResp;

import butterknife.BindView;
import cn.droidlover.xdroidmvp.base.SimpleRecAdapter;
import cn.droidlover.xdroidmvp.imageloader.ILFactory;
import cn.droidlover.xdroidmvp.kit.KnifeKit;

/**
 * 人气产品
 * Created by ${Yis}
 * data: 2017/12/14
 */

public class HotNewsRecycleAdapter extends SimpleRecAdapter<HotNewsResp, HotNewsRecycleAdapter.ViewHolder> {

    public static final int ITEM_CLICK = 0;    //点击标识

    public HotNewsRecycleAdapter(Context context) {
        super(context);
    }

    @Override
    public ViewHolder newViewHolder(View itemView) {
        return new ViewHolder(itemView);
    }

    @Override
    public int getLayoutId() {
        return R.layout.adapter_index_hot_news_rv_item;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        holder.tvTitle.setText(data.get(position).getTitle());
        holder.tvTime.setText(data.get(position).getTime());

        ILFactory.getLoader().loadNet(holder.ivNews, data.get(position).getImg(), null);

        if (data.size() - 1 == position) {
            holder.viewLine.setVisibility(View.GONE);
        } else {
            holder.viewLine.setVisibility(View.VISIBLE);
        }

        holder.rlContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getRecItemClick().onItemClick(position, data.get(position), ITEM_CLICK, holder);
            }
        });
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.rl_content)
        RelativeLayout rlContent;
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.tv_time)
        TextView tvTime;
        @BindView(R.id.iv_news)
        ImageView ivNews;
        @BindView(R.id.view_line)
        View viewLine;

        public ViewHolder(View itemView) {
            super(itemView);
            KnifeKit.bind(this, itemView);
        }
    }
}
