package com.zhsoft.fretting.ui.adapter.index;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zhsoft.fretting.R;
import com.zhsoft.fretting.model.index.ProductModel;
import com.zhsoft.fretting.utils.BigDecimalUtil;

import butterknife.BindView;
import cn.droidlover.xdroidmvp.base.SimpleRecAdapter;
import cn.droidlover.xdroidmvp.kit.KnifeKit;

/**
 * 优选定投
 * Created by ${zengsuwa}
 * data: 2017/12/14
 */

public class PreferRecycleAdapter extends SimpleRecAdapter<ProductModel, PreferRecycleAdapter.ViewHolder> {

    public static final int ITEM_CLICK = 0;    //点击标识

    public PreferRecycleAdapter(Context context) {
        super(context);
    }

    @Override
    public ViewHolder newViewHolder(View itemView) {
        return new ViewHolder(itemView);
    }

    @Override
    public int getLayoutId() {
        return R.layout.adapter_index_prefer_rv_item;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        holder.preferredName.setText(data.get(position).getFund_name());
        holder.preferredRate.setText(BigDecimalUtil.bigdecimalToString(data.get(position).getFund_rose()) + "%");
        holder.tvRateDesc.setText(data.get(position).getRiseTermDesc());

        if (data.size() - 1 == position) {
            holder.viewLine.setVisibility(View.GONE);
        } else {
            holder.viewLine.setVisibility(View.VISIBLE);
        }

        holder.btnInvest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getRecItemClick().onItemClick(position, data.get(position), ITEM_CLICK, holder);
            }
        });
        holder.llContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getRecItemClick().onItemClick(position, data.get(position), ITEM_CLICK, holder);
            }
        });
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.ll_content)
        LinearLayout llContent;
        @BindView(R.id.preferred_name)
        TextView preferredName;
        @BindView(R.id.preferred_rate)
        TextView preferredRate;
        @BindView(R.id.tv_rate_desc)
        TextView tvRateDesc;
        @BindView(R.id.btn_invest)
        Button btnInvest;
        @BindView(R.id.view_line)
        View viewLine;

        public ViewHolder(View itemView) {
            super(itemView);
            KnifeKit.bind(this, itemView);
        }
    }
}
