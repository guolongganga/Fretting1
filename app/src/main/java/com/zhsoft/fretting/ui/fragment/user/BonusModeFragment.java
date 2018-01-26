package com.zhsoft.fretting.ui.fragment.user;

import android.os.Bundle;
import android.view.View;

import com.zhsoft.fretting.R;
import com.zhsoft.fretting.constant.Constant;
import com.zhsoft.fretting.model.user.TransactionResp;
import com.zhsoft.fretting.model.user.UpdateBonusResp;
import com.zhsoft.fretting.net.Api;
import com.zhsoft.fretting.net.HttpContent;
import com.zhsoft.fretting.present.user.BonusModePresent;
import com.zhsoft.fretting.present.user.TransactionContentPresent;
import com.zhsoft.fretting.ui.activity.boot.FundDetailWebActivity;
import com.zhsoft.fretting.ui.activity.user.BonusActivity;
import com.zhsoft.fretting.ui.activity.user.BonusChangeActivity;
import com.zhsoft.fretting.ui.adapter.fund.FundContentRecycleAdapter;
import com.zhsoft.fretting.ui.adapter.user.UpdateBonusRecycleAdapter;

import java.util.List;

import butterknife.BindView;
import cn.droidlover.xdroidmvp.mvp.XFragment;
import cn.droidlover.xdroidmvp.net.NetError;
import cn.droidlover.xrecyclerview.RecyclerAdapter;
import cn.droidlover.xrecyclerview.RecyclerItemCallback;
import cn.droidlover.xrecyclerview.XRecyclerContentLayout;
import cn.droidlover.xrecyclerview.XRecyclerView;

/**
 * 作者：sunnyzeng on 2018/1/24 19:00
 * 描述：
 */

public class BonusModeFragment extends XFragment<BonusModePresent> {
    @BindView(R.id.content_layout) XRecyclerContentLayout contentLayout;
    private UpdateBonusRecycleAdapter adapter;
    private final int pageSize = 10;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_user_transaction_content;
    }

    @Override
    public BonusModePresent newP() {
        return new BonusModePresent();
    }

    @Override
    public void initData(Bundle bundle) {

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
        getP().loadBonusTypeData(1, pageSize);

        contentLayout.getRecyclerView()
                .setOnRefreshAndLoadMoreListener(new XRecyclerView.OnRefreshAndLoadMoreListener() {
                    @Override
                    public void onRefresh() {
                        getP().loadBonusTypeData(1, pageSize);
                    }

                    @Override
                    public void onLoadMore(int page) {
                        getP().loadBonusTypeData(page, pageSize);
                    }
                });

        contentLayout.loadingView(View.inflate(getContext(), R.layout.view_loading, null));
        contentLayout.getRecyclerView().useDefLoadMoreView();

    }

    @Override
    public void initEvents() {
        adapter.setRecItemClick(new RecyclerItemCallback<UpdateBonusResp, UpdateBonusRecycleAdapter.ViewHolder>() {
            @Override
            public void onItemClick(int position, UpdateBonusResp model, int tag, UpdateBonusRecycleAdapter.ViewHolder holder) {
                super.onItemClick(position, model, tag, holder);
                switch (tag) {
                    //点击
                    case UpdateBonusRecycleAdapter.ITEM_CLICK:
                        //跳转 分红方式变更 TODO 需要传递参数
                        Bundle bundle = new Bundle();
                        bundle.putParcelable(Constant.ACTIVITY_OBJECT,model);
                        startActivity(BonusChangeActivity.class,bundle);
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
            adapter = new UpdateBonusRecycleAdapter(context);
        }
        return adapter;
    }

    /**
     * 数据展示
     *
     * @param pageno
     * @param item
     */
    public void showData(int pageno, List<UpdateBonusResp> item) {

        if (item != null && item.size() > 0) {
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