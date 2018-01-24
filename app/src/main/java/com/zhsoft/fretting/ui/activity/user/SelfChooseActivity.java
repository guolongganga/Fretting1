package com.zhsoft.fretting.ui.activity.user;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.zhsoft.fretting.App;
import com.zhsoft.fretting.R;
import com.zhsoft.fretting.constant.Constant;
import com.zhsoft.fretting.model.fund.NewestFundResp;
import com.zhsoft.fretting.net.Api;
import com.zhsoft.fretting.net.HttpContent;
import com.zhsoft.fretting.present.user.SelfChoosePresent;
import com.zhsoft.fretting.ui.activity.boot.FundDetailWebActivity;
import com.zhsoft.fretting.ui.adapter.user.SelfChooseRecycleAdapter;

import java.util.List;

import butterknife.BindView;
import cn.droidlover.xdroidmvp.mvp.XActivity;
import cn.droidlover.xdroidmvp.net.NetError;
import cn.droidlover.xrecyclerview.RecyclerAdapter;
import cn.droidlover.xrecyclerview.RecyclerItemCallback;
import cn.droidlover.xrecyclerview.XRecyclerContentLayout;
import cn.droidlover.xrecyclerview.XRecyclerView;

/**
 * Created by ${sunny}
 * data: 2017/12/19
 * 自选基金
 */

public class SelfChooseActivity extends XActivity<SelfChoosePresent> {

    @BindView(R.id.btn_networth)
    Button btnNetWorth;
    @BindView(R.id.btn_range)
    Button btnRange;
    @BindView(R.id.content_layout)
    XRecyclerContentLayout contentLayout;
    @BindView(R.id.head_back) ImageButton headBack;
    @BindView(R.id.head_title) TextView headTitle;

    private SelfChooseRecycleAdapter adapter;

    private int pageno = 1;
    private final int pageSize = 10;
    private String token;
    private String userId;

    @Override
    public int getLayoutId() {
        return R.layout.activity_user_self_choose;
    }

    @Override
    public SelfChoosePresent newP() {
        return new SelfChoosePresent();
    }

    @Override
    public void initData(Bundle bundle) {
        headTitle.setText("自选基金");

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

        //访问数据
        getP().selfChooseFund(pageno, pageSize, token, userId);
        contentLayout.getRecyclerView()
                .setOnRefreshAndLoadMoreListener(new XRecyclerView.OnRefreshAndLoadMoreListener() {
                    @Override
                    public void onRefresh() {
                        getP().selfChooseFund(1, pageSize, token, userId);
                    }

                    @Override
                    public void onLoadMore(int page) {
                        getP().selfChooseFund(page, pageSize, token, userId);
                    }
                });

        contentLayout.loadingView(View.inflate(context, R.layout.view_loading, null));
        contentLayout.showLoading();
        contentLayout.getRecyclerView().useDefLoadMoreView();

    }

    @Override
    public void initEvents() {
        //返回
        headBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        //涨幅
        btnRange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO 排序
            }
        });
        //净值
        btnNetWorth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO 排序
            }
        });
        adapter.setRecItemClick(new RecyclerItemCallback<NewestFundResp, SelfChooseRecycleAdapter.ViewHolder>() {
            @Override
            public void onItemClick(int position, NewestFundResp model, int tag, SelfChooseRecycleAdapter.ViewHolder holder) {
                super.onItemClick(position, model, tag, holder);
                switch (tag) {
                    //点击
                    case SelfChooseRecycleAdapter.ITEM_CLICK:
                        //跳转基金详情页
                        Bundle bundle = new Bundle();
                        bundle.putInt(Constant.WEB_TITLE, R.string.fund_detail);
                        bundle.putString(Constant.WEB_LINK, Api.API_BASE_URL + HttpContent.fund_detail);
                        bundle.putString(Constant.FUND_DETAIL_CODE, model.getFund_code());
                        bundle.putString(Constant.FUND_DETAIL_NAME, model.getFund_name());
                        startActivity(FundDetailWebActivity.class, bundle);
                        break;
                }
            }
        });

    }

    /**
     * 初始化adapter
     *
     * @return
     */
    public RecyclerAdapter getAdapter() {
        if (adapter == null) {
            adapter = new SelfChooseRecycleAdapter(context);
        }
        return adapter;
    }

    /**
     * 数据展示
     *
     * @param pageno
     * @param item
     */
    public void showData(int pageno, List<NewestFundResp> item) {

        if (item != null && item.size() > 1) {
            if (pageno > 1) {
                getAdapter().addData(item);
            } else {
                getAdapter().setData(item);
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

    public void showError(NetError error) {
        contentLayout.showError();
    }


}
