package com.zhsoft.fretting.ui.adapter.boot;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zhsoft.fretting.R;
import com.zhsoft.fretting.model.fund.NewestFundResp;
import com.zhsoft.fretting.utils.BigDecimalUtil;

import butterknife.BindView;
import cn.droidlover.xdroidmvp.base.SimpleRecAdapter;
import cn.droidlover.xdroidmvp.kit.KnifeKit;

/**
 * 搜索下拉选择
 * Created by ${Yis}
 * data: 2017/12/14
 */

public class SearchRecycleAdapter extends SimpleRecAdapter<NewestFundResp, SearchRecycleAdapter.ViewHolder> {

    public static final int ITEM_CLICK = 0;    //点击标识

    public SearchRecycleAdapter(Context context) {
        super(context);
    }

    @Override
    public ViewHolder newViewHolder(View itemView) {
        return new ViewHolder(itemView);
    }

    @Override
    public int getLayoutId() {
        return R.layout.adapter_search_rv_item;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        holder.tvFundName.setText(data.get(position).getFund_name());
        holder.tvFundRate.setText(BigDecimalUtil.bigdecimalToString(data.get(position).getFund_rose()));
        holder.tvFundCode.setText(data.get(position).getFund_code());
        holder.tvFundPerTime.setText("近一年");

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
        @BindView(R.id.tv_fund_name)
        TextView tvFundName;
        @BindView(R.id.tv_fund_rate)
        TextView tvFundRate;
        @BindView(R.id.tv_fund_code)
        TextView tvFundCode;
        @BindView(R.id.tv_fund_per_time)
        TextView tvFundPerTime;
        @BindView(R.id.view_line)
        View viewLine;

        public ViewHolder(View itemView) {
            super(itemView);
            KnifeKit.bind(this, itemView);
        }
    }
}