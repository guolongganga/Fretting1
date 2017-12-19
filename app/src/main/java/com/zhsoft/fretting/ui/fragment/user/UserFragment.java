package com.zhsoft.fretting.ui.fragment.user;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhsoft.fretting.R;
import com.zhsoft.fretting.model.Happ;
import com.zhsoft.fretting.model.TaetModel;
import com.zhsoft.fretting.model.user.MyFundResp;
import com.zhsoft.fretting.present.user.UserPresent;
import com.zhsoft.fretting.ui.activity.user.LoginActivity;
import com.zhsoft.fretting.ui.activity.user.RegisterFirstActivity;
import com.zhsoft.fretting.ui.activity.user.SettingActivity;
import com.zhsoft.fretting.ui.adapter.user.MyFundRecyleAdapter;

import java.util.List;

import butterknife.BindView;
import cn.droidlover.xdroidmvp.base.SimpleRecAdapter;
import cn.droidlover.xdroidmvp.mvp.XFragment;
import cn.droidlover.xrecyclerview.RecyclerItemCallback;
import cn.droidlover.xrecyclerview.XRecyclerView;

/**
 * 作者：sunnyzeng on 2017/12/5
 * 描述：我的
 */

public class UserFragment extends XFragment<UserPresent> {
    /** 返回按钮 */
    @BindView(R.id.head_back) ImageButton headBack;
    /** 标题 */
    @BindView(R.id.head_title) TextView headTitle;
    /** 设置 */
    @BindView(R.id.head_set) ImageButton headSet;
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

    /** 是否登录标识 */
    private boolean isLogin = false;


    @Override
    public void initData(Bundle savedInstanceState) {
        headBack.setVisibility(View.GONE);
        headTitle.setText("我的");
        headSet.setVisibility(View.VISIBLE);
        xrvMyFund.verticalLayoutManager(context);//设置RecycleView类型 - 不设置RecycleView不显示
        getP().loadTestData();
    }


    @Override
    public void initEvents() {

        headSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(SettingActivity.class);
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
                showToast("自选");
            }
        });
        timing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showToast("定投");
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

    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_user;
    }

    @Override
    public UserPresent newP() {
        return new UserPresent();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    public void showData(TaetModel data) {
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
        adapter.setRecItemClick(new RecyclerItemCallback<MyFundResp, MyFundRecyleAdapter.ViewHolder>() {
            @Override
            public void onItemClick(int position, MyFundResp model, int tag, MyFundRecyleAdapter.ViewHolder holder) {
                super.onItemClick(position, model, tag, holder);
                switch (tag) {
                    //点击
                    case MyFundRecyleAdapter.ITEM_CLICK:
                        showToast(model.getName());
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
    public void showMyFund(List<MyFundResp> resps) {
        if (resps != null && resps.size() > 0) {
            getMyFundAdapter().addData(resps);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (isLogin) {
            llLogout.setVisibility(View.GONE);
        } else {
            llLogout.setVisibility(View.VISIBLE);
        }
    }
}
