package cn.com.buyforyou.fund.ui.activity.user;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.com.buyforyou.fund.R;
import cn.com.buyforyou.fund.constant.Constant;
import cn.com.buyforyou.fund.event.ChangeBankCardMessageEvent;
import cn.com.buyforyou.fund.model.fund.BankCard;
import cn.com.buyforyou.fund.model.fund.BuyFundResp;
import cn.com.buyforyou.fund.model.fund.InvestResp;
import cn.com.buyforyou.fund.ui.adapter.user.BuyFundAdapter;
import cn.droidlover.xdroidmvp.base.SimpleRecAdapter;

import cn.droidlover.xdroidmvp.mvp.XActivity;
import cn.droidlover.xrecyclerview.RecyclerItemCallback;
import cn.droidlover.xrecyclerview.XRecyclerView;

/**
 * Created by guolonggang on 2018/12/19.
 * Description:
 * 切换银行卡
 */

public class ChangeBankCardListActivity extends XActivity{

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


    private BuyFundResp buyFundResp;
    private BuyFundAdapter adapter;
    private InvestResp investResp;

    @Override
    public int getLayoutId() {
        return R.layout.activity_chang_bank_card_lsit_item;
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
            buyFundResp = bundle.getParcelable(Constant.BUY_FUND_OBJECT);
            investResp = bundle.getParcelable(Constant.INVEST_FUND_OBJECT);
        }
        bankListDatas(buyFundResp);

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
    public SimpleRecAdapter getBankListAdapter() {
        adapter = new BuyFundAdapter(context);
        xrvBankList.setAdapter(adapter);
        adapter.setRecItemClick(new RecyclerItemCallback<BankCard , BuyFundAdapter.ViewHolder>() {
            @Override
            public void onItemClick(int position, BankCard bankCard, int tag, BuyFundAdapter.ViewHolder holder) {
                super.onItemClick(position, bankCard, tag, holder);
                switch (tag) {
                    //点击
                    case BuyFundAdapter.ITEM_CLICK:
                        //修改银行卡信息
                        EventBus.getDefault().post(new ChangeBankCardMessageEvent(bankCard.getLogo(),bankCard.getName(),bankCard.getInfo(),bankCard.getTrade_acco()));
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
    public void bankListDatas(BuyFundResp buyFundResp) {

        List<BankCard> data = buyFundResp.getBankCard();
        if (data != null && data.size() > 0) {
            xrvBankList.setVisibility(View.VISIBLE);
            tvEmpty.setVisibility(View.GONE);
            getBankListAdapter().addData(data);

        } else {
            xrvBankList.setVisibility(View.GONE);
            tvEmpty.setVisibility(View.VISIBLE);
        }

    }
}
