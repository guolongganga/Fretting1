package com.zhsoft.fretting.ui.fragment.user;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.zhsoft.fretting.R;
import com.zhsoft.fretting.constant.Constant;
import com.zhsoft.fretting.model.user.FoundResp;
import com.zhsoft.fretting.net.Api;
import com.zhsoft.fretting.net.HttpContent;
import com.zhsoft.fretting.ui.activity.boot.FundDetailWebActivity;
import com.zhsoft.fretting.ui.adapter.user.MyFundRecyleAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import cn.droidlover.xdroidmvp.base.SimpleRecAdapter;
import cn.droidlover.xdroidmvp.mvp.XFragment;
import cn.droidlover.xrecyclerview.RecyclerItemCallback;
import cn.droidlover.xrecyclerview.XRecyclerView;

/**
 * 作者：sunnyzeng on 2018/3/19 19:40
 * 描述：
 */

public class UserHoldFragment extends XFragment {
    @BindView(R.id.xrv_user_hold) XRecyclerView xrvUserHold;
    @BindView(R.id.tv_empty) TextView tvEmpty;
    /**
     * 我的持仓基金
     */
    private ArrayList<FoundResp> fundList;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_user_hold;
    }

    @Override
    public Object newP() {
        return null;
    }

    @Override
    public void initData(Bundle bundle) {
        xrvUserHold.setFocusable(false);
        xrvUserHold.verticalLayoutManager(context);//设置RecycleView类型 - 不设置RecycleView不显示
        if (bundle != null) {
            fundList = bundle.getParcelableArrayList(Constant.ACTIVITY_OBJECT);
            if (fundList != null && fundList.size() > 0) {
                getMyFundAdapter().addData(fundList);
            } else {
                xrvUserHold.setVisibility(View.GONE);
                tvEmpty.setVisibility(View.VISIBLE);
            }
        }


    }

    /**
     * 初始化我的基金adapter
     *
     * @return
     */
    public SimpleRecAdapter getMyFundAdapter() {
        MyFundRecyleAdapter adapter = new MyFundRecyleAdapter(context);
        xrvUserHold.setAdapter(adapter);
        adapter.setRecItemClick(new RecyclerItemCallback<FoundResp, MyFundRecyleAdapter.ViewHolder>() {
            @Override
            public void onItemClick(int position, FoundResp model, int tag, MyFundRecyleAdapter.ViewHolder holder) {
                super.onItemClick(position, model, tag, holder);
                switch (tag) {
                    //点击
                    case MyFundRecyleAdapter.ITEM_CLICK:
                        //跳转基金详情页
                        Bundle bundle = new Bundle();
                        bundle.putInt(Constant.WEB_TITLE, R.string.fund_detail);
                        bundle.putString(Constant.WEB_LINK, Api.API_BASE_URL + HttpContent.hold_fund_detail);
                        bundle.putString(Constant.FUND_DETAIL_CODE, model.getFundCode());
                        bundle.putString(Constant.FUND_DETAIL_NAME, model.getFundName());
                        startActivity(FundDetailWebActivity.class, bundle);
                        break;
                }
            }
        });
        return adapter;
    }

    @Override
    public void initEvents() {

    }
}
