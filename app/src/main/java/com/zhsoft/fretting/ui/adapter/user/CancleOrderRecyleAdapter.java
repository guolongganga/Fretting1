package com.zhsoft.fretting.ui.adapter.user;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zhsoft.fretting.R;
import com.zhsoft.fretting.constant.Constant;
import com.zhsoft.fretting.model.user.InvestPlanResp;
import com.zhsoft.fretting.model.user.TransactionResp;

import butterknife.BindView;
import cn.droidlover.xdroidmvp.base.SimpleRecAdapter;
import cn.droidlover.xdroidmvp.kit.KnifeKit;

/**
 * 作者：sunnyzeng on 2017/12/18 14:07
 * 描述：定投计划
 */

public class CancleOrderRecyleAdapter extends SimpleRecAdapter<TransactionResp, CancleOrderRecyleAdapter.ViewHolder> {
    public static final int ITEM_CLICK = 0;    //点击标识

    public CancleOrderRecyleAdapter(Context context) {
        super(context);
    }

    @Override
    public ViewHolder newViewHolder(View itemView) {
        return new ViewHolder(itemView);
    }

    @Override
    public int getLayoutId() {
        return R.layout.adapter_user_cancle_order_rv_item;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        TransactionResp resp = data.get(position);
        holder.tvFundName.setText(resp.getFundName());
        holder.tvFundCode.setText(resp.getFundCode());
        holder.tvTime.setText(resp.getTime());
        holder.tvType.setText(resp.getType());
        holder.tvmount.setText(resp.getAmount() + "份");

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
        @BindView(R.id.tv_fund_name) TextView tvFundName;
        /** 基金代码 */
        @BindView(R.id.tv_fund_code) TextView tvFundCode;
        /** 方式 */
        @BindView(R.id.tv_type) TextView tvType;
        /** 交易时间 */
        @BindView(R.id.tv_time) TextView tvTime;
        /** 份额 */
        @BindView(R.id.tv_amount) TextView tvmount;

        public ViewHolder(View itemView) {
            super(itemView);
            KnifeKit.bind(this, itemView);
        }
    }
}
