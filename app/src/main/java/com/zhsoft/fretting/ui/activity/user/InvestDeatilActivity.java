package com.zhsoft.fretting.ui.activity.user;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.zhsoft.fretting.App;
import com.zhsoft.fretting.R;
import com.zhsoft.fretting.constant.Constant;
import com.zhsoft.fretting.model.user.InvestPlanResp;
import com.zhsoft.fretting.model.user.InvestRecordResp;
import com.zhsoft.fretting.present.user.InvestDetailPresent;
import com.zhsoft.fretting.ui.activity.fund.InvestActivity;
import com.zhsoft.fretting.ui.adapter.user.InvestPlanRecyleAdapter;
import com.zhsoft.fretting.ui.adapter.user.InvestRecordRecyleAdapter;
import com.zhsoft.fretting.ui.adapter.user.MyFundRecyleAdapter;
import com.zhsoft.fretting.ui.widget.CustomDialog;
import com.zhsoft.fretting.ui.widget.FundBuyDialog;

import java.util.ArrayList;

import butterknife.BindView;
import cn.droidlover.xdroidmvp.base.SimpleRecAdapter;
import cn.droidlover.xdroidmvp.dialog.httploadingdialog.HttpLoadingDialog;
import cn.droidlover.xdroidmvp.mvp.XActivity;
import cn.droidlover.xrecyclerview.RecyclerItemCallback;
import cn.droidlover.xrecyclerview.XRecyclerView;

/**
 * 作者：sunnyzeng on 2018/1/22 17:03
 * 描述： 定投详情页
 */

public class InvestDeatilActivity extends XActivity<InvestDetailPresent> {
    @BindView(R.id.head_back) ImageButton headBack;
    @BindView(R.id.head_title) TextView headTitle;
    @BindView(R.id.tv_fund_name) TextView tvFundName;
    @BindView(R.id.tv_fund_code) TextView tvFundCode;
    @BindView(R.id.tv_invest_status) TextView tvInvestStatus;
    @BindView(R.id.tv_invest_day) TextView tvInvestDay;
    @BindView(R.id.tv_sum) TextView tvSum;
    @BindView(R.id.tv_total) TextView tvTotal;
    @BindView(R.id.tv_stage) TextView tvStage;
    @BindView(R.id.tv_bank_name) TextView tvBankName;
    @BindView(R.id.tv_bank_tail) TextView tvBankTail;
    @BindView(R.id.tv_protocol_number) TextView tvProtocolNumber;
    @BindView(R.id.tv_day_week) TextView tvDayWeek;
    @BindView(R.id.tv_next_time) TextView tvNextTime;
    @BindView(R.id.tv_next_time_hint) TextView tvNextTimeHint;
    @BindView(R.id.xrv_invest_record) XRecyclerView xrvInvestRecord;
    @BindView(R.id.btn_end) Button btnEnd;
    @BindView(R.id.btn_stop) Button btnStop;
    @BindView(R.id.btn_update) Button btnUpdate;
    /** 登录标识 */
    private String token;
    /** 用户编号 */
    private String userId;
    /** 加载圈 */
    private HttpLoadingDialog httpLoadingDialog;
    /** 定投状态 */
    private String investStatus;
    /** 终止弹框 */
    private CustomDialog endDialog;
    /** 输入密码弹框 */
    private FundBuyDialog endPasswordDialog;

    @Override
    public int getLayoutId() {
        return R.layout.activity_user_invest_detail;
    }

    @Override
    public InvestDetailPresent newP() {
        return new InvestDetailPresent();
    }

    @Override
    public void initData(Bundle bundle) {
        headTitle.setText("定投详情");
        httpLoadingDialog = new HttpLoadingDialog(context);
        token = App.getSharedPref().getString(Constant.TOKEN, "");
        userId = App.getSharedPref().getString(Constant.USERID, "");
        if (bundle != null) {
            investStatus = bundle.getString(Constant.INVEST_STATUS);
        }

        if (Constant.INVEST_PLAN_STOP.equals(investStatus)) {
            //如果是暂停状态，显示 暂停，下次扣款时间：--，请保持账户资金充足 暂停按钮消失
            tvInvestStatus.setVisibility(View.VISIBLE);
            tvDayWeek.setVisibility(View.GONE);
            tvNextTime.setVisibility(View.VISIBLE);
            tvNextTimeHint.setText("请保持账户资金充足");
            btnStop.setVisibility(View.GONE);
        } else {
            tvInvestStatus.setVisibility(View.GONE);
            tvDayWeek.setVisibility(View.VISIBLE);
            tvNextTime.setVisibility(View.GONE);
            tvNextTimeHint.setText("将进行新一期定投扣款，请保持账户资金充足");
            btnStop.setVisibility(View.VISIBLE);
        }

        xrvInvestRecord.verticalLayoutManager(context);//设置RecycleView类型 - 不设置RecycleView不显示
        getP().investDetailData(token, userId);

    }

    @Override
    public void initEvents() {
        headBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        btnEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (endDialog == null) {
                    endDialog = new CustomDialog.Builder(context)
                            .setMessage("终止后将不再执行定期扣款协议且无法恢复，确认要终止定投吗？")
                            .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    endDialog.dismiss();
                                }
                            }).setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    endDialog.dismiss();
                                    //TODO 弹出框
                                    endPasswordDialog = new FundBuyDialog
                                            .Builder(context)
                                            .setOnTextFinishListener(new FundBuyDialog.OnTextFinishListener() {
                                                @Override
                                                public void onFinish(String str) {
                                                    endPasswordDialog.dismiss();
                                                    //TODO 请求终止接口
                                                    if (str.equals("123456")) {
                                                        showToast("密码正确");
                                                    } else {
                                                        showToast("密码错误");
                                                    }

                                                }
                                            }).create();
                                    endPasswordDialog.show();

                                }
                            }).create();
                }
                endDialog.show();
            }
        });
        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    /**
     * 初始化定投计划adapter
     *
     * @return
     */
    public SimpleRecAdapter getInvestRecordAdapter() {
        InvestRecordRecyleAdapter adapter = new InvestRecordRecyleAdapter(context);
        xrvInvestRecord.setAdapter(adapter);
        adapter.setRecItemClick(new RecyclerItemCallback<InvestRecordResp, InvestRecordRecyleAdapter.ViewHolder>() {
            @Override
            public void onItemClick(int position, InvestRecordResp model, int tag, InvestRecordRecyleAdapter.ViewHolder holder) {
                super.onItemClick(position, model, tag, holder);
                switch (tag) {
                    //点击
                    case MyFundRecyleAdapter.ITEM_CLICK:
                        showToast(model.getDay());
                        break;
                }
            }
        });
        return adapter;
    }

    /**
     * 请求定投详情接口数据成功
     *
     * @param list
     */
    public void requestInvestDetailSuccess(ArrayList<InvestRecordResp> list) {
        getInvestRecordAdapter().addData(list);
    }

    /**
     * 请求定投详情接口数据失败
     */
    public void requestInvestDetailFail() {
    }
}
