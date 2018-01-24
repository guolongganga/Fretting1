package com.zhsoft.fretting.ui.fragment.user;

import android.os.Bundle;
import android.view.View;

import com.zhsoft.fretting.R;
import com.zhsoft.fretting.constant.Constant;
import com.zhsoft.fretting.model.fund.NewestFundResp;
import com.zhsoft.fretting.model.user.TransactionResp;
import com.zhsoft.fretting.present.user.TransactionContentPresent;
import com.zhsoft.fretting.ui.adapter.user.TransactionContentRecycleAdapter;

import java.util.List;

import butterknife.BindView;
import cn.droidlover.xdroidmvp.mvp.XFragment;
import cn.droidlover.xdroidmvp.net.NetError;
import cn.droidlover.xrecyclerview.RecyclerAdapter;
import cn.droidlover.xrecyclerview.XRecyclerContentLayout;
import cn.droidlover.xrecyclerview.XRecyclerView;

/**
 * 作者：sunnyzeng on 2018/1/24 15:46
 * 描述：交易查询
 */

public class TransactionContentFragment extends XFragment<TransactionContentPresent> {

    @BindView(R.id.content_layout) XRecyclerContentLayout contentLayout;
    private TransactionContentRecycleAdapter adapter;
    private final int pageSize = 10;
    /** 基金类型 */
    private String tabType;
    private String fundTabName;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_user_transaction_content;
    }

    @Override
    public TransactionContentPresent newP() {
        return new TransactionContentPresent();
    }

    @Override
    public void initData(Bundle bundle) {
        //tab类型 请求接口的时候需要
        fundTabName = bundle.getString(Constant.FUND_TAB_NAME, "");
        tabType = fundTabType(fundTabName);

        contentLayout.getSwipeRefreshLayout().setColorSchemeResources(
                R.color.color_main,
                cn.droidlover.xrecyclerview.R.color.x_blue,
                cn.droidlover.xrecyclerview.R.color.x_yellow,
                cn.droidlover.xrecyclerview.R.color.x_green
        );

        contentLayout.getRecyclerView().verticalLayoutManager(context);
        contentLayout.getRecyclerView().setAdapter(getAdapter());
        contentLayout.getRecyclerView().horizontalDivider(R.color.color_e7e7e7, R.dimen.dimen_1);  //设置divider
        //0表示tab的类型
        getP().loadTransactionData(1, pageSize, tabType);

        contentLayout.getRecyclerView()
                .setOnRefreshAndLoadMoreListener(new XRecyclerView.OnRefreshAndLoadMoreListener() {
                    @Override
                    public void onRefresh() {
                        getP().loadTransactionData(1, pageSize, tabType);
                    }

                    @Override
                    public void onLoadMore(int page) {
                        getP().loadTransactionData(page, pageSize, tabType);
                    }
                });

        contentLayout.loadingView(View.inflate(getContext(), R.layout.view_loading, null));
//        contentLayout.showLoading();
        contentLayout.getRecyclerView().useDefLoadMoreView();

    }

    @Override
    public void initEvents() {

    }

    /**
     * 根据tab类型请求相对应类型的基金
     *
     * @param fundTabName
     * @return
     */
    private String fundTabType(String fundTabName) {
        if (Constant.TRANSACTION_TAB_PURCHASE.equals(fundTabName)) {
            //买入
            return "101";
        } else if (Constant.TRANSACTION_TAB_SELLOUT.equals(fundTabName)) {
            //卖出
            return "102";
        } else if (Constant.TRANSACTION_TAB_ONPASSAGE.equals(fundTabName)) {
            //在途交易
            return "103";
        } else if (Constant.TRANSACTION_TAB_BONUS.equals(fundTabName) || "我的分红".equals(fundTabName)) {
            //分红、我的分红
            return "104";
        }
        return "";
    }


    /**
     * 初始化adapter
     *
     * @return
     */
    public RecyclerAdapter getAdapter() {
        if (adapter == null) {
            adapter = new TransactionContentRecycleAdapter(context, fundTabName);
        }
        return adapter;
    }

    /**
     * 数据展示
     *
     * @param pageno
     * @param item
     */
    public void showData(int pageno, List<TransactionResp> item) {

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
