package cn.com.buyforyou.fund.ui.adapter.user;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.BindView;
import cn.com.buyforyou.fund.R;
import cn.com.buyforyou.fund.model.user.BankResp;
import cn.com.buyforyou.fund.ui.activity.user.AddBankCardListActivity;
import cn.droidlover.xdroidmvp.base.SimpleRecAdapter;
import cn.droidlover.xdroidmvp.kit.KnifeKit;

/**
 * Created by guolonggang on 2018/12/10.
 * Description:银行卡adapter以及添加银行卡
 */

public class AddbankcardListAdapter extends SimpleRecAdapter<BankResp, AddbankcardListAdapter.ViewHolder> {
    @Override
    public AddbankcardListAdapter.ViewHolder newViewHolder(View itemView) {
        return new ViewHolder(itemView);
    }

    public AddbankcardListAdapter(Context context) {
        super(context);
    }

    @Override
    public int getLayoutId() {
        return R.layout.adapter_user_bank_list_add_item;
    }

    @Override
    public void onBindViewHolder(AddbankcardListAdapter.ViewHolder holder, int position) {

    }

    public static class ViewHolder extends RecyclerView.ViewHolder {


        /**
         * 银行图标
         */
        @BindView(R.id.bank_image)
        ImageView bankImage;
        /**
         * 银行名称
         */
        @BindView(R.id.bank_name)
        TextView bankName;
        /**
         * 银行限额
         */
        @BindView(R.id.bank_limit)
        TextView bankLimit;
        /**
         * 内容
         */
        @BindView(R.id.rl_content)
        RelativeLayout rlContent;
        /**
         * 分割线
         */
        @BindView(R.id.view_line)
        View viewLine;

        public ViewHolder(View itemView) {
            super(itemView);
            KnifeKit.bind(this, itemView);
        }
    }

}
