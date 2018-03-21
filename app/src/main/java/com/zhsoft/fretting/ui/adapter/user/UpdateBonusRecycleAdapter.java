package com.zhsoft.fretting.ui.adapter.user;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhsoft.fretting.R;
import com.zhsoft.fretting.constant.Constant;
import com.zhsoft.fretting.model.user.TransactionResp;
import com.zhsoft.fretting.model.user.UpdateBonusResp;

import butterknife.BindView;
import cn.droidlover.xdroidmvp.base.SimpleRecAdapter;
import cn.droidlover.xdroidmvp.kit.KnifeKit;

/**
 * Created by ${sunny}
 * data: 2017/12/19
 */

public class UpdateBonusRecycleAdapter extends SimpleRecAdapter<UpdateBonusResp, UpdateBonusRecycleAdapter.ViewHolder> {
    public static final int ITEM_CLICK = 0;    //点击标识

    public UpdateBonusRecycleAdapter(Context context) {
        super(context);
    }

    @Override
    public ViewHolder newViewHolder(View itemView) {
        return new ViewHolder(itemView);
    }

    @Override
    public int getLayoutId() {
        return R.layout.adapter_user_update_bonus_rv_item;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        UpdateBonusResp resp = data.get(position);
        holder.tvFundName.setText(resp.getFundname());
        holder.tvFundCode.setText(resp.getFundcode());
        holder.tvBonusType.setText(resp.getAutoBuyVal());
        holder.llContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getRecItemClick().onItemClick(position, data.get(position), ITEM_CLICK, holder);
            }
        });
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        /**
         * 点击区域
         */
        @BindView(R.id.ll_content)
        LinearLayout llContent;
        /**
         * 分红方式
         */
        @BindView(R.id.tv_bonus_type)
        TextView tvBonusType;
        /**
         * 基金名称
         */
        @BindView(R.id.tv_fund_name)
        TextView tvFundName;
        /**
         * 基金代码
         */
        @BindView(R.id.tv_fund_code)
        TextView tvFundCode;
        /**
         * 箭头
         */
        @BindView(R.id.iv_arrow)
        ImageView ivArrow;


        public ViewHolder(View itemView) {
            super(itemView);
            KnifeKit.bind(this, itemView);
        }
    }
}
