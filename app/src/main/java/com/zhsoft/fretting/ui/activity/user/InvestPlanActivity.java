package com.zhsoft.fretting.ui.activity.user;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.zhsoft.fretting.App;
import com.zhsoft.fretting.R;
import com.zhsoft.fretting.constant.Constant;
import com.zhsoft.fretting.model.fund.InvestResp;
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
import cn.droidlover.xrecyclerview.XRecyclerView;

/**
 * 作者：sunnyzeng on 2018/1/22 11:38
 * 描述：定投计划
 */

public class InvestPlanActivity extends XActivity<InvestPlanPresent> {
    /** 返回 */
    @BindView(R.id.head_back) ImageButton headBack;
    /** 标题 */
    @BindView(R.id.head_title) TextView headTitle;
    /** 我的定投计划 */
    @BindView(R.id.xrv_my_invest) XRecyclerView xrvMyInvest;
    /** 新增定投 */
    @BindView(R.id.btn_add_invest) Button btnAddInvest;
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
        if (bundle != null) {
            fundCode = bundle.getString(Constant.FUND_DETAIL_CODE);
            fundName = bundle.getString(Constant.FUND_DETAIL_NAME);
        } else {
            fundCode = "050003";
            fundName = "博时现金收益货币A";
        }
        xrvMyInvest.verticalLayoutManager(context);//设置RecycleView类型 - 不设置RecycleView不显示

        httpLoadingDialog.visible();
        getP().investPlanData(token, userId);

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
                //TODO 判断是否能够定投
                getP().investTime(token, userId, fundCode, fundName);
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
                        bundle.putString(Constant.INVEST_STATUS,model.getInvestStatus());
                        startActivity(InvestDeatilActivity.class,bundle);
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
}
