package com.zhsoft.fretting.ui.adapter.user;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhsoft.fretting.R;
import com.zhsoft.fretting.model.user.FoundResp;
import com.zhsoft.fretting.utils.BigDecimalUtil;
import com.zhsoft.fretting.utils.RateStyleUtil;

import butterknife.BindView;
import cn.droidlover.xdroidmvp.base.SimpleRecAdapter;
import cn.droidlover.xdroidmvp.kit.KnifeKit;

/**
 * 作者：sunnyzeng on 2017/12/18 14:07
 * 描述：
 */

public class WaitSureRecyleAdapter extends SimpleRecAdapter<FoundResp, WaitSureRecyleAdapter.ViewHolder> {
    public static final int ITEM_CLICK = 0;    //点击标识

    public WaitSureRecyleAdapter(Context context) {
        super(context);
    }

    @Override
    public ViewHolder newViewHolder(View itemView) {
        return new ViewHolder(itemView);
    }

    @Override
    public int getLayoutId() {
        return R.layout.adapter_user_wait_sure_rv_item;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final FoundResp resp = data.get(position);

        holder.tvFundName.setText(resp.getFundName());
        RateStyleUtil.amountStyle(context, holder.tvAmount, resp.getHoldAmount());

        holder.llContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getRecItemClick().onItemClick(position, resp, ITEM_CLICK, holder);
            }
        });

    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        /** 内容区域 */
        @BindView(R.id.ll_content) LinearLayout llContent;
        /** 基金名称 */
        @BindView(R.id.tv_fund_name) TextView tvFundName;
        /** 金额/份额 */
        @BindView(R.id.tv_amount) TextView tvAmount;
        /** 申请时间 */
        @BindView(R.id.tv_time) TextView tvTime;
        /** 业务类型 */
        @BindView(R.id.tv_type) TextView tvType;

        public ViewHolder(View itemView) {
            super(itemView);
            KnifeKit.bind(this, itemView);
        }
    }
}
