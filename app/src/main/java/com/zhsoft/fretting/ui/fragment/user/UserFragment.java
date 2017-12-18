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
import com.zhsoft.fretting.ui.adapter.user.SwitchAccountRecycleAdapter;

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

    @BindView(R.id.head_back) ImageButton headBack;
    @BindView(R.id.head_title) TextView headTitle;
    @BindView(R.id.head_set) ImageButton headSet;
    @BindView(R.id.login) Button login;
    @BindView(R.id.register) Button register;
    @BindView(R.id.self_choose) TextView selfChoose;
    @BindView(R.id.timing) TextView timing;
    @BindView(R.id.transaction) TextView transaction;
    @BindView(R.id.profit) TextView profit;
    @BindView(R.id.remove) TextView remove;
    @BindView(R.id.ll_logout) LinearLayout llLogout;
    @BindView(R.id.xrv_my_fund) XRecyclerView xrvMyFund;

    private boolean isLogin = false;//未登录

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
                    case SwitchAccountRecycleAdapter.ITEM_CLICK:
                        showToast(model.getName());
                        break;
                }
            }
        });
        return adapter;
    }

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
