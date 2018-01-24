package com.zhsoft.fretting.ui.fragment.user;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhsoft.fretting.App;
import com.zhsoft.fretting.R;
import com.zhsoft.fretting.constant.Constant;
import com.zhsoft.fretting.event.OpenAccountEvent;
import com.zhsoft.fretting.model.Happ;
import com.zhsoft.fretting.model.TaetResp;
import com.zhsoft.fretting.model.fund.FundResp;
import com.zhsoft.fretting.model.user.FoundResp;
import com.zhsoft.fretting.model.user.MyFundResp;
import com.zhsoft.fretting.model.user.UserAccountResp;
import com.zhsoft.fretting.present.user.UserPresent;
import com.zhsoft.fretting.ui.activity.user.InvestPlanActivity;
import com.zhsoft.fretting.ui.activity.user.LoginActivity;
import com.zhsoft.fretting.ui.activity.user.RegisterFirstActivity;
import com.zhsoft.fretting.ui.activity.user.RegisterSecondActivity;
import com.zhsoft.fretting.ui.activity.user.SelfChooseActivity;
import com.zhsoft.fretting.ui.activity.user.SettingActivity;
import com.zhsoft.fretting.ui.adapter.user.MyFundRecyleAdapter;
import com.zhsoft.fretting.utils.RuntimeHelper;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.math.BigDecimal;
import java.util.List;

import butterknife.BindView;
import cn.droidlover.xdroidmvp.base.SimpleRecAdapter;
import cn.droidlover.xdroidmvp.dialog.httploadingdialog.HttpLoadingDialog;
import cn.droidlover.xdroidmvp.event.BusProvider;
import cn.droidlover.xdroidmvp.mvp.XFragment;
import cn.droidlover.xrecyclerview.RecyclerItemCallback;
import cn.droidlover.xrecyclerview.XRecyclerView;
import io.reactivex.functions.Consumer;

/**
 * 作者：sunnyzeng on 2017/12/5
 * 描述：我的
 */

public class UserFragment extends XFragment<UserPresent> {
    private static final String PHONE = "phone";
    /** 返回按钮 */
    @BindView(R.id.head_back) ImageButton headBack;
    /** 标题 */
    @BindView(R.id.head_title) TextView headTitle;
    /** 设置 */
    @BindView(R.id.head_right_imgbtn) ImageButton headRightImgbtn;
    /** 总资产 */
    @BindView(R.id.tv_total_assets) TextView tvTotalAssets;
    /** 昨日收益 */
    @BindView(R.id.tv_yesterday_income) TextView tvYesterdayIncome;
    /** 累计收益时间 */
    @BindView(R.id.tv_time_accumlate) TextView tvTimeAccumlate;
    /** 累计收益 */
    @BindView(R.id.tv_accumulate_earn) TextView tvAccumulateEarn;
    /** 登录 */
    @BindView(R.id.login) Button login;
    /** 注册 */
    @BindView(R.id.register) Button register;
    /** 自选 */
    @BindView(R.id.self_choose) TextView selfChoose;
    /** 定投 */
    @BindView(R.id.timing) TextView timing;
    /** 交易查询 */
    @BindView(R.id.transaction) TextView transaction;
    /** 分红 */
    @BindView(R.id.profit) TextView profit;
    /** 撤单 */
    @BindView(R.id.remove) TextView remove;
    /** 未登录界面 */
    @BindView(R.id.ll_logout) LinearLayout llLogout;
    /** 我的基金列表 */
    @BindView(R.id.xrv_my_fund) XRecyclerView xrvMyFund;
    /** 您还没有持仓 */
    @BindView(R.id.tv_empty) TextView tvEmpty;
    /** 去开户 */
    @BindView(R.id.to_finish_register) Button toFinishRegister;

    /** 是否登录标识 */
//    private boolean isLogin = false;

    private String isOpenAccount;
    private String userId;
    private String token;
    private HttpLoadingDialog httpLoadingDialog;

    @Override
    public void initData(Bundle savedInstanceState) {
        //设置标题
        headBack.setVisibility(View.GONE);
        headTitle.setText("我的");
        headRightImgbtn.setVisibility(View.VISIBLE);
        headRightImgbtn.setImageResource(R.mipmap.icon_user_set);

        //加载框
        httpLoadingDialog = new HttpLoadingDialog(context);

        isOpenAccountView();
        //注册事件
        EventBus.getDefault().register(this);

        xrvMyFund.verticalLayoutManager(context);//设置RecycleView类型 - 不设置RecycleView不显示

        if (RuntimeHelper.getInstance().isLogin()) {
            requestFund();
        }

    }

    /**
     * 我的资产请求
     */
    private void requestFund() {
        //获得本地缓存的token和userID
        userId = App.getSharedPref().getString(Constant.USERID, "");
        token = App.getSharedPref().getString(Constant.TOKEN, "");
        httpLoadingDialog.visible();
        httpLoadingDialog.setCanceledOnKeyBack();
        getP().getFundHome(token, userId);
    }

    /**
     * 是否开户
     */
    public void isOpenAccountView() {
        //获得本地缓存的开户标识
        isOpenAccount = App.getSharedPref().getString(Constant.IS_OPEN_ACCOUNT, "");
        //1位未开户  0 位开户
        if (Constant.ALREADY_OPEN_ACCOUNT.equals(isOpenAccount)) {
            toFinishRegister.setVisibility(View.GONE);
            xrvMyFund.setVisibility(View.VISIBLE);
        } else {
            toFinishRegister.setVisibility(View.VISIBLE);
            xrvMyFund.setVisibility(View.GONE);
        }
    }


    @Override
    public void initEvents() {

        headRightImgbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (RuntimeHelper.getInstance().isLogin()) {
                    startActivity(SettingActivity.class);
                } else {
                    showToast("您尚未登录，请先登录");
                }

            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(LoginActivity.class);
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(RegisterFirstActivity.class);
            }
        });

        selfChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(SelfChooseActivity.class);
            }
        });

        timing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString(Constant.ACTIVITY_NAME, Constant.MY_INVEST);
                startActivity(InvestPlanActivity.class, bundle);
            }
        });

        transaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showToast("交易查询");
            }
        });

        profit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showToast("分红");
            }
        });

        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showToast("撤销");
            }
        });

        toFinishRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String strPhone = App.getSharedPref().getString(Constant.USER_PHONE, "");
                Bundle bundle = new Bundle();
                bundle.putString(PHONE, strPhone);
                startActivity(RegisterSecondActivity.class, bundle);
            }
        });


    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_user;
    }

    @Override
    public UserPresent newP() {
        return new UserPresent();
    }

    public void showData(TaetResp data) {
        List<Happ> list = data.getDictData();
        list.get(0);
    }

    /**
     * 初始化我的基金adapter
     *
     * @return
     */
    public SimpleRecAdapter getMyFundAdapter() {
        MyFundRecyleAdapter adapter = new MyFundRecyleAdapter(context);
        xrvMyFund.setAdapter(adapter);
        adapter.setRecItemClick(new RecyclerItemCallback<FoundResp, MyFundRecyleAdapter.ViewHolder>() {
            @Override
            public void onItemClick(int position, FoundResp model, int tag, MyFundRecyleAdapter.ViewHolder holder) {
                super.onItemClick(position, model, tag, holder);
                switch (tag) {
                    //点击
                    case MyFundRecyleAdapter.ITEM_CLICK:
                        showToast(model.getFundName());
                        break;
                }
            }
        });
        return adapter;
    }

    /**
     * 获得我的基金数据
     *
     * @param resps
     */
    public void showMyFund(UserAccountResp resps) {
        httpLoadingDialog.dismiss();
        if (resps != null) {
            //总资产
            tvTotalAssets.setText(resps.getTotalAssets().setScale(2, BigDecimal.ROUND_HALF_UP).toString());
            //昨日收益
            tvYesterdayIncome.setText(resps.getEarningsLastDay().setScale(2, BigDecimal.ROUND_HALF_UP).toString());
            //累计收益时间
            tvTimeAccumlate.setText("(" + resps.getYesterday() + ")累计收益(元)");
            //累计收益
            tvAccumulateEarn.setText(resps.getCumulativeIncome().setScale(2, BigDecimal.ROUND_HALF_UP).toString());

            //持仓基金
            if (resps.getFundList() != null && resps.getFundList().size() > 0) {
                getMyFundAdapter().addData(resps.getFundList());
            } else {
                xrvMyFund.setVisibility(View.GONE);
                tvEmpty.setVisibility(View.VISIBLE);
            }

        }
    }

    @Override
    public void onResume() {
        super.onResume();
        //判断是否登录
        if (RuntimeHelper.getInstance().isLogin()) {
            llLogout.setVisibility(View.GONE);
        } else {
            llLogout.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onDestroyView() {
        EventBus.getDefault().unregister(this);
        super.onDestroyView();
    }

    /**
     * 是否开户事件
     *
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.POSTING)
    public void onOpenAccountEvent(OpenAccountEvent event) {
        isOpenAccountView();
        requestFund();
    }

    /**
     * 请求账户数据失败
     */
    public void requestFundFail() {
        httpLoadingDialog.dismiss();
    }
}
