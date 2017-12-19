package com.zhsoft.fretting.ui.adapter.user;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.zhsoft.fretting.R;
import com.zhsoft.fretting.model.user.BankResp;

import butterknife.BindView;
import cn.droidlover.xdroidmvp.base.SimpleRecAdapter;
import cn.droidlover.xdroidmvp.kit.KnifeKit;

/**
 * 作者：sunnyzeng on 2017/12/18 14:07
 * 描述：银行列表adapter
 */

public class BankListAdapter extends SimpleRecAdapter<BankResp, BankListAdapter.ViewHolder> {
    public static final int ITEM_CLICK = 0;    //点击标识
    private ImageLoader imageLoader = ImageLoader.getInstance();

    public BankListAdapter(Context context) {
        super(context);
    }

    @Override
    public ViewHolder newViewHolder(View itemView) {
        return new ViewHolder(itemView);
    }

    @Override
    public int getLayoutId() {
        return R.layout.adapter_user_bank_list_rv_item;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        imageLoader.displayImage(data.get(position).getBankImage(), holder.bankImage);
        holder.bankName.setText(data.get(position).getBankName());
        holder.bankLimit.setText(data.get(position).getBankLimit());
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
        /** 银行图标 */
        @BindView(R.id.bank_image) ImageView bankImage;
        /** 银行名称 */
        @BindView(R.id.bank_name) TextView bankName;
        /** 银行限额 */
        @BindView(R.id.bank_limit) TextView bankLimit;
        /** 内容 */
        @BindView(R.id.ll_content) LinearLayout llContent;
        /** 分割线 */
        @BindView(R.id.view_line) View viewLine;

        public ViewHolder(View itemView) {
            super(itemView);
            KnifeKit.bind(this, itemView);
        }
    }
}
