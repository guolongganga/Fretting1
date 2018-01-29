package com.zhsoft.fretting.ui.fragment.index;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.zhsoft.fretting.R;
import com.zhsoft.fretting.constant.Constant;
import com.zhsoft.fretting.model.ApplyBaseInfo;
import com.zhsoft.fretting.model.fund.FundResp;
import com.zhsoft.fretting.model.fund.NewestFundResp;
import com.zhsoft.fretting.net.Api;
import com.zhsoft.fretting.net.HttpContent;
import com.zhsoft.fretting.present.index.TimingPresent;
import com.zhsoft.fretting.ui.activity.boot.FundDetailWebActivity;
import com.zhsoft.fretting.ui.adapter.index.TimingRecycleAdapter;
import com.zhsoft.fretting.ui.widget.PopShow;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.droidlover.xdroidmvp.mvp.XFragment;
import cn.droidlover.xrecyclerview.RecyclerAdapter;
import cn.droidlover.xrecyclerview.RecyclerItemCallback;
import cn.droidlover.xrecyclerview.XRecyclerContentLayout;
import cn.droidlover.xrecyclerview.XRecyclerView;

/**
 * 作者：sunnyzeng on 2017/12/21 17:49
 * 描述：
 */

public class TimingFragment extends XFragment<TimingPresent> {
    @BindView(R.id.tv_range)
    TextView tvRange;
    @BindView(R.id.view_line)
    View viewLine;
    @BindView(R.id.content_layout)
    XRecyclerContentLayout contentLayout;
    private int isSelector = 0;
    private TimingRecycleAdapter adapter;
    private List<ApplyBaseInfo> list;
    private int pageno = 1;
    private final int pageSize = 10;
    /** 基金类型 */
    private String tabType;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_index_timing;
    }

    @Override
    public TimingPresent newP() {
        return new TimingPresent();
    }

    @Override
    public void initData(Bundle bundle) {
        //tab类型 请求接口的时候需要
        String fundTabName = bundle.getString(Constant.FUND_TAB_NAME, "");
        tabType = fundTabType(fundTabName);

        //初始化排序条件的集合
        initOrderList();
        tvRange.setText(list.get(isSelector).getContent());
        contentLayout.getSwipeRefreshLayout().setColorSchemeResources(
                R.color.color_main,
                cn.droidlover.xrecyclerview.R.color.x_blue,
                cn.droidlover.xrecyclerview.R.color.x_yellow,
                cn.droidlover.xrecyclerview.R.color.x_green
        );

        contentLayout.getRecyclerView().verticalLayoutManager(context);
        contentLayout.getRecyclerView()
                .setAdapter(getAdapter());
        contentLayout.getRecyclerView().horizontalDivider(R.color.color_e7e7e7, R.dimen.dimen_1);  //设置divider

        getP().loadData(pageno, pageSize, tabType, list.get(isSelector).getCode());

        contentLayout.getRecyclerView()
                .setOnRefreshAndLoadMoreListener(new XRecyclerView.OnRefreshAndLoadMoreListener() {
                    @Override
                    public void onRefresh() {
                        getP().loadData(1, pageSize, tabType, list.get(isSelector).getCode());
                    }

                    @Override
                    public void onLoadMore(int page) {
                        getP().loadData(page, pageSize, tabType, list.get(isSelector).getCode());
                    }
                });

        contentLayout.loadingView(View.inflate(getContext(), R.layout.view_loading, null));
//        contentLayout.showLoading();
        contentLayout.getRecyclerView().useDefLoadMoreView();

    }

    /**
     * 根据tab类型请求相对应类型的基金
     *
     * @param fundTabName
     * @return
     */
    private String fundTabType(String fundTabName) {
        if (Constant.FUND_TAB_SHARES.equals(fundTabName)) {
            //股票型
            return Constant.FUND_TAB_SHARES_TYPE;
        } else if (Constant.FUND_TAB_BLEND.equals(fundTabName)) {
            //混合型
            return Constant.FUND_TAB_BLEND_TYPE;
        } else if (Constant.FUND_TAB_BOND.equals(fundTabName)) {
            //债券型
            return Constant.FUND_TAB_BOND_TYPE;
        } else if (Constant.FUND_TAB_FINGER.equals(fundTabName)) {
            //指数型
            return Constant.FUND_TAB_FINGER_TYPE;
        }
        return "";
    }

    /**
     * 初始化排序条件的集合
     */
    private void initOrderList() {
        list = new ArrayList<>();
        ApplyBaseInfo baseInfo1 = new ApplyBaseInfo("7", "近一年定投收益率");
        ApplyBaseInfo baseInfo2 = new ApplyBaseInfo("8", "近两年定投收益率");
        ApplyBaseInfo baseInfo3 = new ApplyBaseInfo("9", "近三年定投收益率");
        ApplyBaseInfo baseInfo4 = new ApplyBaseInfo("10", "近五年定投收益率");
        list.add(baseInfo1);
        list.add(baseInfo2);
        list.add(baseInfo3);
        list.add(baseInfo4);
    }

    @Override
    public void initEvents() {
        //涨幅
        tvRange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                PopShow popShow = new PopShow(context, viewLine);
                popShow.showRangeSelector(list, isSelector);
                popShow.setOnClickPop(new PopShow.OnClickPop() {
                    @Override
                    public void setRange(int position) {
                        isSelector = position;
                        tvRange.setText(list.get(position).getContent());
                        //回到顶部
                        contentLayout.getRecyclerView().scrollToPosition(0);
                        getP().loadData(1, pageSize, tabType, list.get(isSelector).getCode());
                    }
                });
            }
        });
        adapter.setRecItemClick(new RecyclerItemCallback<NewestFundResp, TimingRecycleAdapter.ViewHolder>() {
            @Override
            public void onItemClick(int position, NewestFundResp model, int tag, TimingRecycleAdapter.ViewHolder holder) {
                super.onItemClick(position, model, tag, holder);
                //跳转基金详情页
                Bundle bundle = new Bundle();
                bundle.putInt(Constant.WEB_TITLE, R.string.fund_detail);
                bundle.putString(Constant.WEB_LINK, Api.API_BASE_URL + HttpContent.fund_detail);
                bundle.putString(Constant.FUND_DETAIL_CODE, model.getFund_code());
                bundle.putString(Constant.FUND_DETAIL_NAME, model.getFund_name());
                startActivity(FundDetailWebActivity.class, bundle);
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
            adapter = new TimingRecycleAdapter(context);
        }
        return adapter;
    }

    /**
     * 数据展示
     *
     * @param page
     * @param item
     */
    public void showData(int page, List<NewestFundResp> item) {

        if (item != null && item.size() > 0) {
            if (page > 1) {
                getAdapter().addData(item);
            } else {
                getAdapter().setData(item);
            }
            contentLayout.getRecyclerView().setPage(page, page + 1);
        } else {
            //没有更多数据了
            contentLayout.getRecyclerView().setPage(page, page - 1);
        }
    }

    /**
     * 数据请求失败
     */
    public void showError() {
        contentLayout.showError();
    }
}
