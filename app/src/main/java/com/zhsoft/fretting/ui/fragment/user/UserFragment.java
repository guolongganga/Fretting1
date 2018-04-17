package com.zhsoft.fretting.ui.fragment.user;


import android.app.ActivityManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.system.Os;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhsoft.fretting.App;
import com.zhsoft.fretting.R;
import com.zhsoft.fretting.constant.Constant;
import com.zhsoft.fretting.event.InvalidTokenEvent;
import com.zhsoft.fretting.event.RefreshUserDataEvent;
import com.zhsoft.fretting.model.user.HoldFundResp;
import com.zhsoft.fretting.model.user.MyHoldFundResp;
import com.zhsoft.fretting.model.user.UserAccountResp;
import com.zhsoft.fretting.net.Api;
import com.zhsoft.fretting.present.user.UserPresent;
import com.zhsoft.fretting.ui.activity.user.BonusActivity;
import com.zhsoft.fretting.ui.activity.user.CancleOrderActivity;
import com.zhsoft.fretting.ui.activity.user.LoginActivity;
import com.zhsoft.fretting.ui.activity.user.MyInvestActivity;
import com.zhsoft.fretting.ui.activity.user.RegisterFirstActivity;
import com.zhsoft.fretting.ui.activity.user.RegisterSecondActivity;
import com.zhsoft.fretting.ui.activity.user.SelfChooseActivity;
import com.zhsoft.fretting.ui.activity.user.SettingActivity;
import com.zhsoft.fretting.ui.activity.user.TransactionQueryActivity;
import com.zhsoft.fretting.ui.adapter.fund.FundTabViewPagerAdapter;
import com.zhsoft.fretting.utils.BigDecimalUtil;
import com.zhsoft.fretting.utils.RuntimeHelper;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.droidlover.xdroidmvp.dialog.httploadingdialog.HttpLoadingDialog;
import cn.droidlover.xdroidmvp.log.XLog;
import cn.droidlover.xdroidmvp.mvp.XFragment;
import cn.droidlover.xdroidmvp.net.NetError;

/**
 * 作者：sunnyzeng on 2017/12/5
 * 描述：我的
 */

public class UserFragment extends XFragment<UserPresent> {
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
     * 设置
     */
    @BindView(R.id.head_right)
    Button headRight;
    /**
     * 总资产
     */
    @BindView(R.id.tv_total_assets)
    TextView tvTotalAssets;
    /**
     * 昨日收益
     */
    @BindView(R.id.tv_yesterday_income)
    TextView tvYesterdayIncome;
    /**
     * 累计收益时间
     */
    @BindView(R.id.tv_time_accumlate)
    TextView tvTimeAccumlate;
    /**
     * 累计收益
     */
    @BindView(R.id.tv_accumulate_earn)
    TextView tvAccumulateEarn;
    /**
     * 在途资产
     */
    @BindView(R.id.tv_passage)
    TextView tvPassage;
    /**
     * 收益时间
     */
    @BindView(R.id.tv_time)
    TextView tvTime;
    /**
     * 登录
     */
    @BindView(R.id.login)
    Button login;
    /**
     * 注册
     */
    @BindView(R.id.register)
    Button register;
    /**
     * 自选
     */
    @BindView(R.id.self_choose)
    TextView selfChoose;
    /**
     * 定投
     */
    @BindView(R.id.timing)
    TextView timing;
    /**
     * 交易查询
     */
    @BindView(R.id.transaction)
    TextView transaction;
    /**
     * 分红
     */
    @BindView(R.id.profit)
    TextView profit;
    /**
     * 撤单
     */
    @BindView(R.id.remove)
    TextView remove;
    /**
     * 未登录界面
     */
    @BindView(R.id.ll_logout)
    LinearLayout llLogout;
    /**
     * 已开户页卡界面
     */
    @BindView(R.id.ll_fund_content)
    LinearLayout llFundContent;
    /**
     * 去开户
     */
    @BindView(R.id.to_finish_register)
    Button toFinishRegister;
    /**
     * 页卡标签
     */
    @BindView(R.id.tab_layout)
    TabLayout mTabLayout;
    /**
     * 页卡
     */
    @BindView(R.id.view_pager)
    ViewPager mViewPager;

//    @BindView(R.id.user_frame_top)
//    LinearLayout userFrameTop;
//    @BindView(R.id.ll_fund_middle)
//    LinearLayout llFundMiddlw;

    /**
     * 是否开户
     */
    private String isOpenAccount;
    /**
     * 用户编号
     */
    private String userId;
    /**
     * 登录标识
     */
    private String token;
    /**
     * 加载圈
     */
    private HttpLoadingDialog httpLoadingDialog;
    /**
     * 我的持仓基金
     */
    private ArrayList<MyHoldFundResp> fundList = new ArrayList<>();

    /**
     * 在途基金
     */
    private ArrayList<HoldFundResp> passageList = new ArrayList<>();


    @Override
    public int getLayoutId() {
        return R.layout.fragment_user;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        //设置标题
        headBack.setVisibility(View.GONE);
        headTitle.setText("我的");
        headRight.setVisibility(View.VISIBLE);
        headRight.setText("设置");
        mTabLayout.setTabMode(TabLayout.MODE_FIXED);
        //加载框
        httpLoadingDialog = new HttpLoadingDialog(context);
        //是否开户
        isOpenAccountView();
        //注册事件
        EventBus.getDefault().register(this);

//        xrvMyFund.verticalLayoutManager(context);//设置RecycleView类型 - 不设置RecycleView不显示
        //如果已登录，请求我的持仓基金数据
        if (RuntimeHelper.getInstance().isLogin() && Constant.ALREADY_OPEN_ACCOUNT.equals(isOpenAccount)) {
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
            //已开户
            toFinishRegister.setVisibility(View.GONE);
//            llFundContent.setVisibility(View.VISIBLE);
            mViewPager.setVisibility(View.VISIBLE);
//            userFrameTop.setVisibility(View.VISIBLE);
//            llFundMiddlw.setVisibility(View.VISIBLE);
        } else {
            //未开户
            toFinishRegister.setVisibility(View.VISIBLE);
//            llFundContent.setVisibility(View.GONE);
//            userFrameTop.setVisibility(View.INVISIBLE);
            mViewPager.setVisibility(View.GONE);
//            llFundMiddlw.setVisibility(View.GONE);
        }
    }


    @Override
    public void initEvents() {
        //TabLayout监听滑动或点击
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mViewPager.setCurrentItem(tab.getPosition(), false);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        //设置按钮
        headRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!RuntimeHelper.getInstance().isLogin()) {
                    showToast("您尚未登录，请先登录");
                    return;
                }
                startActivity(SettingActivity.class);

            }
        });
        //登录
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(LoginActivity.class);
            }
        });
        if (App.isDebug)
            login.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setTitle("请选择您的测试服务器地址");
                    builder.setSingleChoiceItems(Api.urls, App.getSharedPref().getInt("url", 0), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            Api.resetUrl(which);
                            dialog.dismiss();
                            showToast("设置成功 " + Api.urls[which]);
                        }
                    });
                    builder.create().show();
                    return true;
                }
            });
        //注册
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(RegisterFirstActivity.class);
            }
        });
        //自选
        selfChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!Constant.ALREADY_OPEN_ACCOUNT.equals(isOpenAccount)) {
                    showToast("您还未开户，请先去开户");
                    return;
                }
                startActivity(SelfChooseActivity.class);
            }
        });
        //定投
        timing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!Constant.ALREADY_OPEN_ACCOUNT.equals(isOpenAccount)) {
                    showToast("您还未开户，请先去开户");
                    return;
                }
                Bundle bundle = new Bundle();
                bundle.putString(Constant.ACTIVITY_NAME, Constant.MY_INVEST);
                startActivity(MyInvestActivity.class, bundle);
            }
        });
        //交易查询
        transaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!Constant.ALREADY_OPEN_ACCOUNT.equals(isOpenAccount)) {
                    showToast("您还未开户，请先去开户");
                    return;
                }
                startActivity(TransactionQueryActivity.class);
            }
        });
        //分红
        profit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!Constant.ALREADY_OPEN_ACCOUNT.equals(isOpenAccount)) {
                    showToast("您还未开户，请先去开户");
                    return;
                }
                startActivity(BonusActivity.class);
            }
        });
        //撤单
        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!Constant.ALREADY_OPEN_ACCOUNT.equals(isOpenAccount)) {
                    showToast("您还未开户，请先去开户");
                    return;
                }
                startActivity(CancleOrderActivity.class);
            }
        });
        //去开户
        toFinishRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(RegisterSecondActivity.class);
            }
        });

    }


    @Override
    public UserPresent newP() {
        return new UserPresent();
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
            tvTotalAssets.setText(BigDecimalUtil.bigdecimalToString(resps.getTotalAssets()));
            //昨日收益
            tvYesterdayIncome.setText(BigDecimalUtil.bigdecimalToString(resps.getYesterdayIncome()));
            //累计收益时间
//            tvTimeAccumlate.setText("累计收益(元)");
            //累计收益
            tvAccumulateEarn.setText(BigDecimalUtil.bigdecimalToString(resps.getTotalIncome()));
            //在途资产
            tvPassage.setText(BigDecimalUtil.bigdecimalToString(resps.getOntheRoadAssets()));
            //收益时间
            tvTime.setText("昨日收益(" + resps.getYesterday() + ")");

            //持仓基金
            if (fundList != null) {
                fundList.clear();
            }
            fundList.addAll(resps.getFundList());

            //待确认基金
            if (passageList != null) {
                passageList.clear();
            }
            passageList.addAll(resps.getHoldList());

            showChannel();

        }
    }

    /**
     * 频道数据展示
     */
    public void showChannel() {

        FragmentManager fragmentManager = getChildFragmentManager();
        List<Fragment> fragmentList = new ArrayList<>();

        List<String> tabName = new ArrayList<>();
        tabName.add(Constant.MY_HOLD);
        tabName.add(Constant.MY_WAIT);

        //我的持仓基金
        UserHoldFragment fragment = new UserHoldFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(Constant.FUND_OBJECT, fundList);
        fragment.setArguments(bundle);
        fragmentList.add(fragment);

        //待确认基金
        WaitSureFragment waitSureFragment = new WaitSureFragment();
        Bundle bundle2 = new Bundle();
        bundle2.putParcelableArrayList(Constant.ACTIVITY_OBJECT, passageList);
        waitSureFragment.setArguments(bundle2);
        fragmentList.add(waitSureFragment);

        FundTabViewPagerAdapter adapter = new FundTabViewPagerAdapter(fragmentManager, fragmentList, tabName);
        mViewPager.setAdapter(adapter);

        mTabLayout.setupWithViewPager(mViewPager);
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
    public void onRefreshUserDataEvent(RefreshUserDataEvent event) {
        isOpenAccountView();
        requestFund();
    }

    /**
     * 请求账户数据失败
     */
    public void requestFundFail() {
        httpLoadingDialog.dismiss();
    }

    /**
     * 已经登出系统，请重新登录
     */
    public void areadyLogout() {
        httpLoadingDialog.dismiss();
//        EventBus.getDefault().post(new InvalidTokenEvent());
        //清除本地缓存，设置成未登录
        RuntimeHelper.getInstance().isInvalidToken();
        //跳转登录界面
        Bundle bundle = new Bundle();
        bundle.putString(Constant.SKIP_SIGN, Constant.SKIP_INDEX_ACTIVITY);
        startActivity(LoginActivity.class, bundle);
    }

}
