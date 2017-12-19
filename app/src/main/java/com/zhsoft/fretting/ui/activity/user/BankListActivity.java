package com.zhsoft.fretting.ui.activity.user;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.zhsoft.fretting.R;
import com.zhsoft.fretting.model.user.BankResp;
import com.zhsoft.fretting.ui.adapter.user.BankListAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import cn.droidlover.xdroidmvp.base.SimpleRecAdapter;
import cn.droidlover.xdroidmvp.mvp.XActivity;
import cn.droidlover.xrecyclerview.RecyclerItemCallback;
import cn.droidlover.xrecyclerview.XRecyclerView;

/**
 * 作者：sunnyzeng on 2017/12/18 19:05
 * 描述：银行列表
 */

public class BankListActivity extends XActivity {
    /** 传递银行列表数据 */
    private static final String BANK = "bank";
    private static final String CHOOSE_BANCK = "choosebank";
    private static final int RESULT_CODE = 200;
    /** 返回按钮 */
    @BindView(R.id.head_back) ImageButton headBack;
    /** 标题 */
    @BindView(R.id.head_title) TextView headTitle;
    /** 银行列表 */
    @BindView(R.id.xrv_bank_list) XRecyclerView xrvBankList;

    private ArrayList<BankResp> listResps;

    @Override
    public int getLayoutId() {
        return R.layout.activity_user_bank_list;
    }

    @Override
    public Object newP() {
        return null;
    }

    @Override
    public void initData(Bundle bundle) {
        headTitle.setText("基金开户");
        xrvBankList.verticalLayoutManager(context);//设置RecycleView类型 - 不设置RecycleView不显示
        listResps = bundle.getParcelableArrayList(BANK);
        if (listResps != null && listResps.size() > 0) {
            getBankListAdapter().addData(listResps);
        }
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
    public SimpleRecAdapter getBankListAdapter() {
        BankListAdapter adapter = new BankListAdapter(context);
        xrvBankList.setAdapter(adapter);
        adapter.setRecItemClick(new RecyclerItemCallback<BankResp, BankListAdapter.ViewHolder>() {
            @Override
            public void onItemClick(int position, BankResp model, int tag, BankListAdapter.ViewHolder holder) {
                super.onItemClick(position, model, tag, holder);
                switch (tag) {
                    //点击
                    case BankListAdapter.ITEM_CLICK:
                        Intent intent = new Intent();
                        intent.putExtra(CHOOSE_BANCK, model);
                        setResult(RESULT_CODE, intent);
                        finish();
//                        showToast(model.getBankName());
                        break;
                }
            }
        });
        return adapter;
    }


}
