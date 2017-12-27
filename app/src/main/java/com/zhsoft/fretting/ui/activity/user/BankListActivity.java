package com.zhsoft.fretting.ui.activity.user;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.zhsoft.fretting.R;
import com.zhsoft.fretting.constant.Constant;
import com.zhsoft.fretting.model.user.BankResp;
import com.zhsoft.fretting.present.user.BankListPresent;
import com.zhsoft.fretting.ui.adapter.user.BankListAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import cn.droidlover.xdroidmvp.base.SimpleRecAdapter;
import cn.droidlover.xdroidmvp.dialog.httploadingdialog.HttpLoadingDialog;
import cn.droidlover.xdroidmvp.mvp.XActivity;
import cn.droidlover.xrecyclerview.RecyclerItemCallback;
import cn.droidlover.xrecyclerview.XRecyclerView;

/**
 * 作者：sunnyzeng on 2017/12/18 19:05
 * 描述：银行列表
 */

public class BankListActivity extends XActivity<BankListPresent> {

    /** 返回按钮 */
    @BindView(R.id.head_back) ImageButton headBack;
    /** 标题 */
    @BindView(R.id.head_title) TextView headTitle;
    /** 银行列表 */
    @BindView(R.id.xrv_bank_list) XRecyclerView xrvBankList;

    private HttpLoadingDialog httpLoadingDialog;

    @Override
    public int getLayoutId() {
        return R.layout.activity_user_bank_list;
    }

    @Override
    public BankListPresent newP() {
        return new BankListPresent();
    }

    @Override
    public void initData(Bundle bundle) {
        headTitle.setText("选择银行");
        xrvBankList.verticalLayoutManager(context);//设置RecycleView类型 - 不设置RecycleView不显示
        httpLoadingDialog = new HttpLoadingDialog(context);
        //请求银行卡列表
        httpLoadingDialog.visible("加载中...");
        getP().getBankList();

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
                        intent.putExtra(Constant.CHOOSE_BANCK, model);
                        setResult(Constant.BANKLIST_RESULT_CODE, intent);
                        finish();
//                        showToast(model.getBankName());
                        break;
                }
            }
        });
        return adapter;
    }


    /**
     * 绑定银行卡失败
     */
    public void requestFail() {
        httpLoadingDialog.dismiss();
    }

    /**
     * 访问银行卡列表
     *
     * @param data
     */
    public void bankListData(ArrayList<BankResp> data) {
        httpLoadingDialog.dismiss();
        if (data != null && data.size() > 0) {
            getBankListAdapter().addData(data);
        }

    }

}
