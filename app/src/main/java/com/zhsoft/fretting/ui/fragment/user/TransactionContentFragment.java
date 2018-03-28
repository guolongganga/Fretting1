package com.zhsoft.fretting.ui.fragment.user;

import android.os.Bundle;
import android.view.View;

import com.zhsoft.fretting.App;
import com.zhsoft.fretting.R;
import com.zhsoft.fretting.constant.Constant;
import com.zhsoft.fretting.event.InvalidTokenEvent;
import com.zhsoft.fretting.model.user.TransactionResp;
import com.zhsoft.fretting.present.user.TransactionContentPresent;
import com.zhsoft.fretting.ui.activity.user.LoginActivity;
import com.zhsoft.fretting.ui.activity.user.ResultDetailOneActivity;
import com.zhsoft.fretting.ui.adapter.user.TransactionContentRecycleAdapter;
import com.zhsoft.fretting.ui.widget.StateView;
import com.zhsoft.fretting.utils.RuntimeHelper;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.BindView;
import cn.droidlover.xdroidmvp.dialog.httploadingdialog.HttpLoadingDialog;
import cn.droidlover.xdroidmvp.mvp.XFragment;
import cn.droidlover.xrecyclerview.RecyclerAdapter;
import cn.droidlover.xrecyclerview.RecyclerItemCallback;
import cn.droidlover.xrecyclerview.XRecyclerContentLayout;
import cn.droidlover.xrecyclerview.XRecyclerView;

/**
 * 作者：sunnyzeng on 2018/1/24 15:46
 * 描述：交易查询
 */

public class TransactionContentFragment extends XFragment<TransactionContentPresent> {

    @BindView(R.id.content_layout) XRecyclerContentLayout contentLayout;
    private TransactionContentRecycleAdapter adapter;
    private final int pageSize = 40;
    /** 基金类型 */
    private String tabType;
    private String fundTabName;
    /** 登录标识 */
    private String token;
    /** 用户编号 */
    private String userId;
//    private StateView errorView;

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
        token = App.getSharedPref().getString(Constant.TOKEN, "");
        userId = App.getSharedPref().getString(Constant.USERID, "");
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
        contentLayout.getRecyclerView().horizontalDivider(R.color.color_F9F9F9, R.dimen.dimen_1);  //设置divider
        //0表示tab的类型
        requestTranData(1);

        contentLayout.getRecyclerView()
                .setOnRefreshAndLoadMoreListener(new XRecyclerView.OnRefreshAndLoadMoreListener() {
                    @Override
                    public void onRefresh() {
                        requestTranData(1);
                    }

                    @Override
                    public void onLoadMore(int page) {
                        requestTranData(page);
                    }
                });
//        if (errorView == null) {
//            errorView = new StateView(context);
//        }
//        contentLayout.errorView(errorView);

        contentLayout.loadingView(View.inflate(getContext(), R.layout.view_loading, null));
        contentLayout.showLoading();
        contentLayout.getRecyclerView().useDefLoadMoreView();

    }

    private void requestTranData(int pageNo) {
        if (Constant.TRANSACTION_TAB_BONUS.equals(fundTabName)) {
            getP().shareOutBonusTradeQuery(token, userId, pageNo, pageSize, tabType);
        } else {
            getP().loadTransactionData(token, userId, pageNo, pageSize, tabType);
        }
    }


    @Override
    public void initEvents() {
        adapter.setRecItemClick(new RecyclerItemCallback<TransactionResp, TransactionContentRecycleAdapter.ViewHolder>() {
            @Override
            public void onItemClick(int position, TransactionResp model, int tag, TransactionContentRecycleAdapter.ViewHolder holder) {
                super.onItemClick(position, model, tag, holder);
                switch (tag) {
                    //点击
                    case TransactionContentRecycleAdapter.ITEM_CLICK:
//                        //跳转 结果页
//                        Bundle bundle = new Bundle();
//                        //交易流水号
//                        bundle.putString(Constant.INVEST_PROTOCOL_ID, model.getAllot_no());
//                        //TODO 得写动态的
//                        bundle.putString(Constant.INVEST_RECORD_STATUS, "定投成功");
//                        startActivity(ResultDetailOneActivity.class, bundle);
                        break;
                }
            }
        });
//        errorView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                requestTranData(1);
//            }
//        });
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
            return "022";
        } else if (Constant.TRANSACTION_TAB_SELLOUT.equals(fundTabName)) {
            //赎回
            return "024";
        } else if (Constant.TRANSACTION_TAB_ONPASSAGE.equals(fundTabName)) {
            //定投
            return "039";
        } else if (Constant.TRANSACTION_TAB_BONUS.equals(fundTabName)) {
            //分红
            return "";
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

    public void showError() {
        contentLayout.showError();
    }

    /**
     * 已经登出系统，请重新登录
     */
    public void areadyLogout() {
//        EventBus.getDefault().post(new InvalidTokenEvent());
        //清除本地缓存，设置成未登录
        RuntimeHelper.getInstance().isInvalidToken();
        //跳转登录界面
        Bundle bundle = new Bundle();
        bundle.putString(Constant.SKIP_SIGN, Constant.SKIP_INDEX_ACTIVITY);
        startActivity(LoginActivity.class, bundle);
    }

}
