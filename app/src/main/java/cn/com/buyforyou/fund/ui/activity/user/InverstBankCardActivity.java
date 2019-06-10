package cn.com.buyforyou.fund.ui.activity.user;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.BindView;
import cn.com.buyforyou.fund.R;
import cn.com.buyforyou.fund.constant.Constant;
import cn.com.buyforyou.fund.event.ChangeBankCardMessageEvent;
import cn.com.buyforyou.fund.event.InverstBankCardEvent;
import cn.com.buyforyou.fund.model.fund.BankCard;
import cn.com.buyforyou.fund.model.fund.BuyFundResp;
import cn.com.buyforyou.fund.model.fund.InvestResp;
import cn.com.buyforyou.fund.model.user.BankCardInfoResp;
import cn.com.buyforyou.fund.ui.adapter.user.BuyFundAdapter;
import cn.com.buyforyou.fund.ui.adapter.user.InverstBankCardAdapter;
import cn.droidlover.xdroidmvp.base.SimpleRecAdapter;
import cn.droidlover.xdroidmvp.mvp.XActivity;
import cn.droidlover.xrecyclerview.RecyclerItemCallback;
import cn.droidlover.xrecyclerview.XRecyclerView;

/**
 * Created by guolonggang on 2018/12/21.
 * Description:
 */

public class InverstBankCardActivity extends XActivity {

    /** 返回按钮 */
    @BindView(R.id.head_back)
    ImageButton headBack;
    /** 标题 */
    @BindView(R.id.head_title)
    TextView headTitle;

    /** 银行列表 */
    @BindView(R.id.xrv_bank_list)
    XRecyclerView xrvBankList;
    @BindView(R.id.tv_empty) TextView tvEmpty;
    private InvestResp investResp;

    @Override
    public int getLayoutId() {
        return R.layout.inverst_bank_card_activity;
    }

    @Override
    public Object newP() {
        return null;
    }

    @Override
    public void initData(Bundle bundle) {
        headTitle.setText("切换银行卡");
        xrvBankList.verticalLayoutManager(context);//设置RecycleView类型 - 不设置RecycleView不显示

        if(bundle!=null)
        {
            investResp = bundle.getParcelable(Constant.INVEST_FUND_OBJECT);
        }
        inverstBankListData(investResp);

    }

    @Override
    public void initEvents() {
        headBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    /**
     * 初始化银行列表adapter
     *
     * @return
     */
    public SimpleRecAdapter getInverstBankListAdapter() {
        InverstBankCardAdapter adapter = new InverstBankCardAdapter(context);
        xrvBankList.setAdapter(adapter);
        adapter.setRecItemClick(new RecyclerItemCallback<BankCardInfoResp, InverstBankCardAdapter.ViewHolder>() {
            @Override
            public void onItemClick(int position, BankCardInfoResp bankCard, int tag, InverstBankCardAdapter.ViewHolder holder) {
                super.onItemClick(position, bankCard, tag, holder);
                switch (tag) {
                    //点击
                    case BuyFundAdapter.ITEM_CLICK:

                        //修改银行卡信息
                        EventBus.getDefault().post(new InverstBankCardEvent(bankCard.getBankLogo(),bankCard.getBankName(),bankCard.getLimit_per_payment(),bankCard.getLimit_per_day(),bankCard.getBankNoTail(),bankCard.getTrade_acco()));
                        finish();
                        break;
                }
            }
        });
        return adapter;
    }
    /**
     * 访问银行卡列表
     *
     * @param
     */
    public void inverstBankListData(InvestResp investResp) {

        List<BankCardInfoResp> data = investResp.getBankCard();

        if (data != null && data.size() > 0) {
            xrvBankList.setVisibility(View.VISIBLE);
            tvEmpty.setVisibility(View.GONE);
            getInverstBankListAdapter().addData(data);

        } else {
            xrvBankList.setVisibility(View.GONE);
            tvEmpty.setVisibility(View.VISIBLE);
        }

    }
}
