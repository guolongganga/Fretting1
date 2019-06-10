package cn.com.buyforyou.fund.ui.activity.user;



import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;



import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import butterknife.BindView;
import cn.com.buyforyou.fund.App;
import cn.com.buyforyou.fund.R;
import cn.com.buyforyou.fund.constant.Constant;

import cn.com.buyforyou.fund.event.RefreshUserDataEvent;
import cn.com.buyforyou.fund.model.user.BankAddResp;

import cn.com.buyforyou.fund.model.user.UserMessageResp;
import cn.com.buyforyou.fund.present.user.AddbankCardPresent;

import cn.com.buyforyou.fund.ui.adapter.user.AddbankcardListAdapter;
import cn.droidlover.xdroidmvp.dialog.httploadingdialog.HttpLoadingDialog;
import cn.droidlover.xdroidmvp.mvp.XActivity;
import cn.droidlover.xrecyclerview.RecyclerItemCallback;
import cn.droidlover.xrecyclerview.XRecyclerView;

/**
 * Created by guolonggang on 2018/12/10.
 * Description: 展示银行卡列表以及添加银行卡
 */

public class AddBankCardListActivity extends XActivity<AddbankCardPresent> {

    /**
     * 返回按钮
     */
    @BindView(R.id.head_back)
    ImageButton headBack;
    /**
     * 标题
     */
    @BindView(R.id.head_title)
    TextView headTitle;

    /**
     * 银行列表
     */
    @BindView(R.id.xrv_bank_list)
    XRecyclerView xrvBankList;
    @BindView(R.id.tv_empty)
    TextView tvEmpty;

    private HttpLoadingDialog httpLoadingDialog;

    /**
     * 添加银行卡按钮
     */
    @BindView(R.id.btn_add)
    Button buttonAdd;
    private String userId;
    private String token;
    private String struserName;
    private String certNo;


    @Override
    public int getLayoutId() {
        return R.layout.activity_bank_card_add;
    }

    @Override
    public AddbankCardPresent newP() {
        return new AddbankCardPresent();
    }

    @Override
    public void initData(final Bundle bundle) {

        headTitle.setText("我的银行卡");
        xrvBankList.verticalLayoutManager(context);//设置RecycleView类型 - 不设置RecycleView不显示
        httpLoadingDialog = new HttpLoadingDialog(context);
        EventBus.getDefault().register(this);
        //获取用户缓存的userid 和 token
        userId = App.getSharedPref().getString(Constant.USERID, "");
        token = App.getSharedPref().getString(Constant.TOKEN, "");
        //请求银行卡列表
        httpLoadingDialog.visible("加载中...");
        getP().addBankcardList(token, userId);
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //请求接口得到用户信息
                httpLoadingDialog.visible("加载中...");
                getP().getUserMessage(token, userId);

            }
        });

    }
    //更新银行卡列表信息
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onChangeTabBankList(RefreshUserDataEvent event) {
        httpLoadingDialog.visible("加载中");
        getP().addBankcardList(token, userId);

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
    public AddbankcardListAdapter getBankListAdapter() {
        AddbankcardListAdapter adapter = new AddbankcardListAdapter(context);
        xrvBankList.setAdapter(adapter);
        adapter.setRecItemClick(new RecyclerItemCallback<BankAddResp, AddbankcardListAdapter.ViewHolder>() {
            @Override
            public void onItemClick(int position, BankAddResp model, int tag, AddbankcardListAdapter.ViewHolder holder) {
                super.onItemClick(position, model, tag, holder);
                switch (tag) {
                    //点击
                    case AddbankcardListAdapter.ITEM_CLICK:
                        //跳转到切换银行卡
//                        Intent intent = new Intent();
//                        intent.putExtra(Constant.CHOOSE_BANCK, model);
//                        setResult(Constant.BANKLIST_RESULT_CODE, intent);
                        //finish();
                        //交易账号
                        //String trade_acco = model.getTrade_acco();
                        Bundle bundle=new Bundle();
                        bundle.putString(Constant.TRADEACCO,model.getTrade_acco());
                        startActivity(BankCardActivity.class,bundle);
                        break;
                }
            }
        });
        return adapter;
    }

    /**
     * 绑定银行卡失败
     */
    public void bankCardAddFail() {
        httpLoadingDialog.dismiss();
    }

    /**
     * 访问银行卡列表以及添加银行卡
     *
     * @param data
     */
    public void addBankListDatas(ArrayList<BankAddResp> data) {
        httpLoadingDialog.dismiss();
        if (data != null && data.size() > 0) {
            xrvBankList.setVisibility(View.VISIBLE);
            tvEmpty.setVisibility(View.GONE);
            getBankListAdapter().addData(data);
        } else {
            xrvBankList.setVisibility(View.GONE);
            tvEmpty.setVisibility(View.VISIBLE);
        }

    }

    /**
     * 获取用户信息成功
     */
    public void getUserMessageCode(UserMessageResp resp) {
        httpLoadingDialog.dismiss();
        String client_name = resp.getClient_name();
        String id_no = resp.getId_no();
        Bundle bundle = new Bundle();
        bundle.putString(Constant.NAME, client_name);
        bundle.putString(Constant.CERT_NO, id_no);
        //跳转到开户添加银行卡信息
        startActivity(BankCardChooseActivity.class, bundle);

    }

    /**
     * 获取用户信息失败
     */
    public void getUserMessageCodeFail() {
        httpLoadingDialog.dismiss();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
