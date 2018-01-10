package com.zhsoft.fretting.ui.adapter.fund;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.zhsoft.fretting.R;
import com.zhsoft.fretting.model.fund.NewestFundResp;
import com.zhsoft.fretting.utils.BigDecimalUtil;

import butterknife.BindView;
import cn.droidlover.xdroidmvp.base.SimpleRecAdapter;
import cn.droidlover.xdroidmvp.kit.KnifeKit;

/**
 * Created by ${sunny}
 * data: 2017/12/19
 */

public class FundContentRecycleAdapter extends SimpleRecAdapter<NewestFundResp, FundContentRecycleAdapter.ViewHolder> {

    private String tabname;

    public FundContentRecycleAdapter(Context context, String tabname) {
        super(context);
        this.tabname = tabname;
    }

    @Override
    public ViewHolder newViewHolder(View itemView) {
        return new ViewHolder(itemView);
    }

    @Override
    public int getLayoutId() {
        return R.layout.adapter_fund_content_rv_item;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.tvName.setText(tabname + data.get(position).getFund_name());
        holder.tvCode.setText(data.get(position).getFund_code());
        holder.tvValue.setText(BigDecimalUtil.bigdecimalToString(data.get(position).getNet_value()));
        holder.tvRange.setText("+" + BigDecimalUtil.bigdecimalToString(data.get(position).getFund_rose()));
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_code)
        TextView tvCode;
        @BindView(R.id.tv_value)
        TextView tvValue;
        @BindView(R.id.tv_range)
        TextView tvRange;

        public ViewHolder(View itemView) {
            super(itemView);
            KnifeKit.bind(this, itemView);
        }
    }
}
