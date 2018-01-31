package com.zhsoft.fretting.ui.activity.user;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.zhsoft.fretting.App;
import com.zhsoft.fretting.R;
import com.zhsoft.fretting.constant.Constant;
import com.zhsoft.fretting.model.user.CancleOrderResp;
import com.zhsoft.fretting.present.user.CancleOrderPresent;
import com.zhsoft.fretting.ui.adapter.user.CancleOrderRecyleAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import cn.droidlover.xdroidmvp.base.SimpleRecAdapter;
import cn.droidlover.xdroidmvp.dialog.httploadingdialog.HttpLoadingDialog;
import cn.droidlover.xdroidmvp.mvp.XActivity;
import cn.droidlover.xrecyclerview.RecyclerItemCallback;
import cn.droidlover.xrecyclerview.XRecyclerView;

/**
 * 作者：sunnyzeng on 2018/1/25 11:39
 * 描述：撤单
 */

public class CancleOrderActivity extends XActivity<CancleOrderPresent> {
    @BindView(R.id.head_back) ImageButton headBack;
    @BindView(R.id.head_title) TextView headTitle;
    @BindView(R.id.xrv_my_invest) XRecyclerView xrvMyInvest;
    @BindView(R.id.tv_empty) TextView tvEmpty;
    /** 登录标识 */
    private String token;
    /** 用户编号 */
    private String userId;
    /** 加载圈 */
    private HttpLoadingDialog httpLoadingDialog;

    @Override
    public int getLayoutId() {
        return R.layout.activity_user_cancle_order;
    }

    @Override
    public CancleOrderPresent newP() {
        return new CancleOrderPresent();
    }

    @Override
    public void initData(Bundle bundle) {
        headTitle.setText("撤单");
        //获取用户缓存的userid 和 token
        httpLoadingDialog = new HttpLoadingDialog(context);
        userId = App.getSharedPref().getString(Constant.USERID, "");
        token = App.getSharedPref().getString(Constant.TOKEN, "");
        xrvMyInvest.verticalLayoutManager(context);//设置RecycleView类型 - 不设置RecycleView不显示

        httpLoadingDialog.visible();
        getP().cancleOrderData(token, userId);
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
     * 初始化定投计划adapter
     *
     * @return
     */
    public SimpleRecAdapter getCancleOrderAdapter() {
        CancleOrderRecyleAdapter adapter = new CancleOrderRecyleAdapter(context);
        xrvMyInvest.setAdapter(adapter);
        adapter.setRecItemClick(new RecyclerItemCallback<CancleOrderResp, CancleOrderRecyleAdapter.ViewHolder>() {
            @Override
            public void onItemClick(int position, CancleOrderResp model, int tag, CancleOrderRecyleAdapter.ViewHolder holder) {
                super.onItemClick(position, model, tag, holder);
                switch (tag) {
                    //点击
                    case CancleOrderRecyleAdapter.ITEM_CLICK:
                        Bundle bundle = new Bundle();
                        bundle.putString(Constant.INVEST_RECORD_STATUS, "定投成功");
                        //交易流水号
                        bundle.putString(Constant.INVEST_PROTOCOL_ID, model.getAllot_no());
                        startActivity(ResultDetailOneActivity.class, bundle);
                        break;
                }
            }
        });
        return adapter;
    }

    /**
     * 请求撤单列表成功
     */
    public void requestOrderSuccess(ArrayList<CancleOrderResp> list) {
        httpLoadingDialog.dismiss();
        if (list != null && list.size() > 0) {
            getCancleOrderAdapter().addData(list);
            xrvMyInvest.setVisibility(View.VISIBLE);
            tvEmpty.setVisibility(View.GONE);
        } else {
            xrvMyInvest.setVisibility(View.GONE);
            tvEmpty.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 请求撤单列表失败
     */
    public void requestOrderFail() {
        httpLoadingDialog.dismiss();
    }
}
