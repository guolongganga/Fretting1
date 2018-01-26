package com.zhsoft.fretting.ui.activity.user;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.zhsoft.fretting.App;
import com.zhsoft.fretting.R;
import com.zhsoft.fretting.constant.Constant;
import com.zhsoft.fretting.model.fund.InvestResp;
import com.zhsoft.fretting.model.user.InvestInfoResp;
import com.zhsoft.fretting.model.user.InvestPlanResp;
import com.zhsoft.fretting.present.user.InvestPlanPresent;
import com.zhsoft.fretting.ui.activity.fund.InvestActivity;
import com.zhsoft.fretting.ui.adapter.user.InvestPlanRecyleAdapter;
import com.zhsoft.fretting.ui.adapter.user.MyFundRecyleAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import cn.droidlover.xdroidmvp.base.SimpleRecAdapter;
import cn.droidlover.xdroidmvp.dialog.httploadingdialog.HttpLoadingDialog;
import cn.droidlover.xdroidmvp.mvp.XActivity;
import cn.droidlover.xrecyclerview.RecyclerItemCallback;
import cn.droidlover.xrecyclerview.XRecyclerContentLayout;
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
    /** 我的定投计划 */
//    @BindView(R.id.xrv_my_invest) XRecyclerView xrvMyInvest;
    @BindView(R.id.content_layout) XRecyclerContentLayout contentLayout;
    /** 新增定投 */
    @BindView(R.id.btn_add_invest) Button btnAddInvest;
    /** 线 */
    @BindView(R.id.view_line) View viewLine;

    private InvestPlanRecyleAdapter adapter;
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
    /** 每页显示数量 */
    private int pageSize = 10;

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
        headTitle.setText("定投计划");

        httpLoadingDialog = new HttpLoadingDialog(context);
        token = App.getSharedPref().getString(Constant.TOKEN, "");
        userId = App.getSharedPref().getString(Constant.USERID, "");

        contentLayout.getSwipeRefreshLayout().setColorSchemeResources(
                R.color.color_main,
                cn.droidlover.xrecyclerview.R.color.x_blue,
                cn.droidlover.xrecyclerview.R.color.x_yellow,
                cn.droidlover.xrecyclerview.R.color.x_green
        );

        contentLayout.getRecyclerView().verticalLayoutManager(context);
        contentLayout.getRecyclerView().setAdapter(getAdapter());
        contentLayout.getRecyclerView().horizontalDivider(R.color.color_e7e7e7, R.dimen.dimen_1);  //设置divider

        if (bundle != null) {
            fundCode = bundle.getString(Constant.FUND_DETAIL_CODE);
            fundName = bundle.getString(Constant.FUND_DETAIL_NAME);
            httpLoadingDialog.visible();
            getP().investPlanData(1, pageSize, token, userId);

        }

        contentLayout.getRecyclerView()
                .setOnRefreshAndLoadMoreListener(new XRecyclerView.OnRefreshAndLoadMoreListener() {
                    @Override
                    public void onRefresh() {
                        getP().investPlanData(1, pageSize, token, userId);
                    }

                    @Override
                    public void onLoadMore(int page) {
                        getP().investPlanData(page, pageSize, token, userId);
                    }
                });

        contentLayout.loadingView(View.inflate(context, R.layout.view_loading, null));
        contentLayout.getRecyclerView().useDefLoadMoreView();
//        xrvMyInvest.verticalLayoutManager(context);//设置RecycleView类型 - 不设置RecycleView不显示


    }


    @Override
    public void initEvents() {
        headBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnAddInvest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                getP().investTime(token, userId, fundCode, fundName);
            }
        });

        adapter.setRecItemClick(new RecyclerItemCallback<InvestInfoResp, InvestPlanRecyleAdapter.ViewHolder>() {
            @Override
            public void onItemClick(int position, InvestInfoResp model, int tag, InvestPlanRecyleAdapter.ViewHolder holder) {
                super.onItemClick(position, model, tag, holder);
                switch (tag) {
                    //点击
                    case MyFundRecyleAdapter.ITEM_CLICK:
                        Bundle bundle = new Bundle();
                        bundle.putString(Constant.INVEST_PROTOCOL_ID, model.getScheduled_protocol_state());
                        bundle.putString(Constant.FUND_DETAIL_CODE, model.getFund_code());
                        bundle.putString(Constant.FUND_DETAIL_NAME, model.getFund_name());
                        startActivity(InvestDeatilActivity.class, bundle, Constant.INVEST_PLAN_ACTIVITY);
                        break;
                }
            }
        });
    }

    /**
     * 初始化定投计划adapter
     *
     * @return
     */
    public SimpleRecAdapter getAdapter() {
        if (adapter == null) {
            adapter = new InvestPlanRecyleAdapter(context);
        }
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
    public void requestDataSuccess(int pageno, ArrayList<InvestPlanResp> list) {
        httpLoadingDialog.dismiss();

        if (list != null && list.size() > 1) {
            if (pageno > 1) {
                getAdapter().addData(list);
            } else {
                getAdapter().setData(list);
            }
            contentLayout.getRecyclerView().setPage(pageno, pageno + 1);
        } else {
            if (pageno == 1) {
                contentLayout.showEmpty();
            } else {
                //没有更多数据了
                contentLayout.getRecyclerView().setPage(pageno, pageno - 1);
            }

        }
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
            getP().investPlanData(1, pageSize, token, userId);
        }
    }
}
