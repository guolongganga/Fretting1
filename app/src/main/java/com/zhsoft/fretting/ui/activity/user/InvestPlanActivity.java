package com.zhsoft.fretting.ui.activity.user;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zhsoft.fretting.App;
import com.zhsoft.fretting.R;
import com.zhsoft.fretting.constant.Constant;
import com.zhsoft.fretting.model.ApplyBaseInfo;
import com.zhsoft.fretting.model.fund.InvestResp;
import com.zhsoft.fretting.model.user.InvestPlanResp;
import com.zhsoft.fretting.present.user.InvestPlanPresent;
import com.zhsoft.fretting.ui.activity.fund.InvestActivity;
import com.zhsoft.fretting.ui.activity.index.TimingActivity;
import com.zhsoft.fretting.ui.adapter.user.InvestPlanRecyleAdapter;
import com.zhsoft.fretting.ui.adapter.user.MyFundRecyleAdapter;
import com.zhsoft.fretting.ui.widget.PopShow;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.droidlover.xdroidmvp.base.SimpleRecAdapter;
import cn.droidlover.xdroidmvp.dialog.httploadingdialog.HttpLoadingDialog;
import cn.droidlover.xdroidmvp.mvp.XActivity;
import cn.droidlover.xrecyclerview.RecyclerItemCallback;
import cn.droidlover.xrecyclerview.XRecyclerView;

/**
 * 作者：sunnyzeng on 2018/1/22 11:38
 * 描述：定投计划/我的定投
 */

public class InvestPlanActivity extends XActivity<InvestPlanPresent> {
    /** 返回 */
    @BindView(R.id.head_back) ImageButton headBack;
    /** 标题 */
    @BindView(R.id.head_title) TextView headTitle;
    /** 条件选择 */
    @BindView(R.id.rl_selector) RelativeLayout rlSelector;
    /** 基金名称选择 */
    @BindView(R.id.tv_fund) TextView tvFund;
    /** 定投状态 */
    @BindView(R.id.tv_range) TextView tvRange;
    /** 我的定投计划 */
    @BindView(R.id.xrv_my_invest) XRecyclerView xrvMyInvest;
    /** 新增定投 */
    @BindView(R.id.btn_add_invest) Button btnAddInvest;
    /** 线 */
    @BindView(R.id.view_line) View viewLine;
    /** 登录标识 */
    private String token;
    /** 用户编号 */
    private String userId;
    /** 基金代码 */
    private String fundCode;
    /** 基金名称 */
    private String fundName;
    /** 加载圈 */
    private HttpLoadingDialog httpLoadingDialog;
    /** 页面标题 */
    private String activityName;
    private int fundSelector = 0;
    private int statusSelector = 0;
    private List<ApplyBaseInfo> fundList;
    private List<ApplyBaseInfo> statusList;

    @Override
    public int getLayoutId() {
        return R.layout.activity_user_invest_plan;
    }

    @Override
    public InvestPlanPresent newP() {
        return new InvestPlanPresent();
    }

    @Override
    public void initData(Bundle bundle) {
        httpLoadingDialog = new HttpLoadingDialog(context);
        token = App.getSharedPref().getString(Constant.TOKEN, "");
        userId = App.getSharedPref().getString(Constant.USERID, "");
        xrvMyInvest.verticalLayoutManager(context);//设置RecycleView类型 - 不设置RecycleView不显示

        if (bundle != null) {
            activityName = bundle.getString(Constant.ACTIVITY_NAME);
            //定投计划
            if (Constant.INVEST_PLAN.equals(activityName)) {
                fundCode = bundle.getString(Constant.FUND_DETAIL_CODE);
                fundName = bundle.getString(Constant.FUND_DETAIL_NAME);
                initInvestPlan();
            } else if (Constant.MY_INVEST.equals(activityName)) {
                initMyInvest();
            }

        }
    }


    /**
     * 初始化 定投计划
     */
    private void initInvestPlan() {
        headTitle.setText("定投计划");
        rlSelector.setVisibility(View.GONE);

        httpLoadingDialog.visible();
        getP().investPlanData(token, userId);

    }

    /**
     * 初始化 我的定投
     */
    private void initMyInvest() {
        headTitle.setText("我的定投");
        rlSelector.setVisibility(View.VISIBLE);

        httpLoadingDialog.visible();
        getP().investPlanData(token, userId);
        tvFund.setText(fundList.get(fundSelector).getContent());
        tvRange.setText(statusList.get(statusSelector).getContent());

    }

    @Override
    public void initEvents() {
        headBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        tvFund.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopShow popShow = new PopShow(context, viewLine);
                popShow.showRangeSelector(fundList, fundSelector);
                popShow.setOnClickPop(new PopShow.OnClickPop() {
                    @Override
                    public void setRange(int position) {
                        fundSelector = position;
                        tvFund.setText(fundList.get(position).getContent());
                        //回到顶部
                        xrvMyInvest.scrollToPosition(0);
                        //TODO 需要传 fundList.get(fundSelector).getCode()，statusList.get(position).getCode()
                        getP().investPlanData(token, userId);
                    }
                });
            }
        });
        tvRange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopShow popShow = new PopShow(context, viewLine);
                popShow.showRangeSelector(statusList, statusSelector);
                popShow.setOnClickPop(new PopShow.OnClickPop() {
                    @Override
                    public void setRange(int position) {
                        statusSelector = position;
                        tvRange.setText(statusList.get(position).getContent());
                        //回到顶部
                        xrvMyInvest.scrollToPosition(0);
                        //TODO 需要传 fundList.get(fundSelector).getCode()，statusList.get(position).getCode()
                        getP().investPlanData(token, userId);
                    }
                });
            }
        });
        btnAddInvest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (Constant.INVEST_PLAN.equals(activityName)) {
                    //TODO 判断是否能够定投
                    getP().investTime(token, userId, fundCode, fundName);
                } else if (Constant.MY_INVEST.equals(activityName)) {
                    //跳转 优选定投排行
                    startActivity(TimingActivity.class);
                }

            }
        });
    }

    /**
     * 初始化定投计划adapter
     *
     * @return
     */
    public SimpleRecAdapter getMyInvestAdapter() {
        InvestPlanRecyleAdapter adapter = new InvestPlanRecyleAdapter(context);
        xrvMyInvest.setAdapter(adapter);
        adapter.setRecItemClick(new RecyclerItemCallback<InvestPlanResp, InvestPlanRecyleAdapter.ViewHolder>() {
            @Override
            public void onItemClick(int position, InvestPlanResp model, int tag, InvestPlanRecyleAdapter.ViewHolder holder) {
                super.onItemClick(position, model, tag, holder);
                switch (tag) {
                    //点击
                    case MyFundRecyleAdapter.ITEM_CLICK:
                        Bundle bundle = new Bundle();
                        bundle.putString(Constant.INVEST_STATUS, model.getInvestStatus());
                        bundle.putString(Constant.FUND_DETAIL_CODE, fundCode);
                        bundle.putString(Constant.FUND_DETAIL_NAME, fundName);
                        startActivity(InvestDeatilActivity.class, bundle, Constant.INVEST_PLAN_ACTIVITY);
                        break;
                }
            }
        });
        return adapter;
    }

    /**
     * 请求我的定投计划数据失败
     */
    public void requestDataFail() {
        httpLoadingDialog.dismiss();
    }

    /**
     * 请求我的定投计划数据成功
     */
    public void requestDataSuccess(ArrayList<InvestPlanResp> list) {
        httpLoadingDialog.dismiss();
        //初始化基金选择框
        fundList = new ArrayList<>();
        ApplyBaseInfo applyBaseInfo0 = new ApplyBaseInfo("000000", "全部基金");
        ApplyBaseInfo applyBaseInfo1 = new ApplyBaseInfo("050001", "博时精选基金1");
        ApplyBaseInfo applyBaseInfo2 = new ApplyBaseInfo("050002", "博时精选基金2");
        ApplyBaseInfo applyBaseInfo3 = new ApplyBaseInfo("050003", "博时精选基金3");
        fundList.add(applyBaseInfo0);
        fundList.add(applyBaseInfo1);
        fundList.add(applyBaseInfo2);
        fundList.add(applyBaseInfo3);

        //初始化状态选择框
        statusList = new ArrayList<>();
        ApplyBaseInfo all = new ApplyBaseInfo("0", "全部定投状态");
        ApplyBaseInfo normal = new ApplyBaseInfo("1", "正常");
        ApplyBaseInfo stop = new ApplyBaseInfo("1", "暂停");
        ApplyBaseInfo end = new ApplyBaseInfo("2", "终止");
        statusList.add(all);
        statusList.add(normal);
        statusList.add(stop);
        statusList.add(end);

        getMyInvestAdapter().addData(list);
    }

    /**
     * 请求定投验证接口失败
     */
    public void requestInvestFail() {

    }

    /**
     * 请求定投验证接口成功
     *
     * @param resp
     */
    public void requestInvestSuccess(final InvestResp resp) {
        //去定投
        Bundle bundle = new Bundle();
        bundle.putString(Constant.INVEST_ACTIVITY_TYPE, Constant.INVEST_ACTIVITY);
        bundle.putString(Constant.FUND_DETAIL_CODE, fundCode);
        bundle.putString(Constant.FUND_DETAIL_NAME, fundName);
        bundle.putParcelable(Constant.INVEST_FUND_OBJECT, resp);
        startActivity(InvestActivity.class, bundle);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constant.INVEST_PLAN_ACTIVITY && resultCode == Constant.INVEST_DETAIL_BACK) {
            showToast("刷新页面数据");
            httpLoadingDialog.visible();
            getP().investPlanData(token, userId);
        }
    }
}
