package com.zhsoft.fretting.ui.adapter.user;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhsoft.fretting.R;
import com.zhsoft.fretting.model.user.MyFundResp;

import butterknife.BindView;
import cn.droidlover.xdroidmvp.base.SimpleRecAdapter;
import cn.droidlover.xdroidmvp.kit.KnifeKit;

/**
 * 作者：sunnyzeng on 2017/12/18 14:07
 * 描述：
 */

public class MyFundRecyleAdapter extends SimpleRecAdapter<MyFundResp, MyFundRecyleAdapter.ViewHolder> {
    public static final int ITEM_CLICK = 0;    //点击标识

    public MyFundRecyleAdapter(Context context) {
        super(context);
    }

    @Override
    public ViewHolder newViewHolder(View itemView) {
        return new ViewHolder(itemView);
    }

    @Override
    public int getLayoutId() {
        return R.layout.adapter_user_my_fund_rv_item;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.tvName.setText(data.get(position).getName());
        holder.tvMoney.setText(data.get(position).getMoney().toString());
        holder.tvYesterday.setText("+" + data.get(position).getYesterday());
        holder.tvHold.setText("-" + data.get(position).getHold());
        if (data.size() - 1 == position) {
            holder.viewLine.setVisibility(View.GONE);
        } else {
            holder.viewLine.setVisibility(View.VISIBLE);
        }
        holder.llContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getRecItemClick().onItemClick(position, data.get(position), ITEM_CLICK, holder);
            }
        });
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        /** 内容区域 */
        @BindView(R.id.ll_content) LinearLayout llContent;
        /** 基金名称 */
        @BindView(R.id.tv_name) TextView tvName;
        /** 余额 */
        @BindView(R.id.tv_money) TextView tvMoney;
        /** 昨日收益 */
        @BindView(R.id.tv_yesterday) TextView tvYesterday;
        /** 持有收益 */
        @BindView(R.id.tv_hold) TextView tvHold;
        /** 分割线 */
        @BindView(R.id.view_line) View viewLine;

        public ViewHolder(View itemView) {
            super(itemView);
            KnifeKit.bind(this, itemView);
        }
    }
}