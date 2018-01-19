package com.zhsoft.fretting.ui.activity.boot;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhsoft.fretting.R;
import com.zhsoft.fretting.constant.Constant;
import com.zhsoft.fretting.model.fund.NewestFundResp;
import com.zhsoft.fretting.net.Api;
import com.zhsoft.fretting.net.HttpContent;
import com.zhsoft.fretting.present.boot.SearchPersent;
import com.zhsoft.fretting.ui.adapter.boot.SearchHotListAdapter;
import com.zhsoft.fretting.ui.adapter.boot.SearchRecycleAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import cn.droidlover.xdroidmvp.base.SimpleRecAdapter;
import cn.droidlover.xdroidmvp.log.XLog;
import cn.droidlover.xdroidmvp.mvp.XActivity;
import cn.droidlover.xrecyclerview.RecyclerItemCallback;
import cn.droidlover.xrecyclerview.XRecyclerView;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

/**
 * 作者：sunnyzeng on 2018/1/10 17:15
 * 描述：搜索页面
 */

public class SearchActivity extends XActivity<SearchPersent> {
    /** 搜索关键字 */
    @BindView(R.id.et_search_key) EditText etSearchKey;
    /** 取消 */
    @BindView(R.id.tv_cancle) TextView tvCancle;
    /** 线 */
    @BindView(R.id.view_line) View viewLine;
//    /** 近期热搜 */
//    @BindView(R.id.xrv_hot_list) XRecyclerView xrvHotList;
    /** 搜索结果 */
    @BindView(R.id.xrv_search_list) XRecyclerView xrvSearchList;
    /** 近期热搜视图 */
    @BindView(R.id.ll_hot) LinearLayout llHot;
    /** 没有找到相关内容，试试别的吧 */
    @BindView(R.id.tv_empty) TextView tvEmpty;
    /** 删除 */
    @BindView(R.id.search_iv_delete) ImageView ivDelete;


//    private OptionSearch mOptionSearch = new OptionSearch(Looper.myLooper());

    @Override
    public int getLayoutId() {
        return R.layout.activity_boot_search;
    }

    @Override
    public SearchPersent newP() {
        return new SearchPersent();
    }

    @Override
    public void initData(Bundle bundle) {
//        mOptionSearch.setListener(this);
//        xrvHotList.verticalLayoutManager(context);//设置RecycleView类型 - 不设置RecycleView不显示
        xrvSearchList.verticalLayoutManager(context);//设置RecycleView类型 - 不设置RecycleView不显示
        //设置热搜显示
        llHot.setVisibility(VISIBLE);
        //获取热搜数据
//        getP().hotListData();
    }

    @Override
    public void initEvents() {
        tvCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        etSearchKey.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            //当输入框内容改变时调用
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!"".equals(charSequence.toString())) {
                    //如果输入的内容不为空，则根据关键字查询数据
                    ivDelete.setVisibility(VISIBLE);
                    xrvSearchList.setVisibility(VISIBLE);
                    llHot.setVisibility(GONE);
                    getP().searchData(getText(etSearchKey));
                } else {
                    //如果输入的内容为空，则显示热搜视图
                    ivDelete.setVisibility(GONE);
                    tvEmpty.setVisibility(GONE);
                    xrvSearchList.setVisibility(GONE);
                    llHot.setVisibility(VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
//                String searchword = editable.toString().trim();
//                //我们可以把每次输入框改变的字符串传给一个工具类，并让它来判断是否进行搜索
//                mOptionSearch.optionSearch(searchword);
//                Log.e("tianzhu", searchword);
            }
        });
        ivDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                etSearchKey.setText("");

            }
        });
    }

    /**
     * 初始化 近期热搜 adapter
     *
     * @return
     */
    public SimpleRecAdapter getHotListAdapter() {
        SearchHotListAdapter hotListAdapter = new SearchHotListAdapter(context);
//        xrvHotList.setAdapter(hotListAdapter);
        hotListAdapter.setRecItemClick(new RecyclerItemCallback<NewestFundResp, SearchHotListAdapter.ViewHolder>() {
            @Override
            public void onItemClick(int position, NewestFundResp model, int tag, SearchHotListAdapter.ViewHolder holder) {
                super.onItemClick(position, model, tag, holder);
                switch (tag) {
                    //点击
                    case SearchHotListAdapter.ITEM_CLICK:
                        showToast(model.getFund_name());
                        break;
                }
            }
        });
        return hotListAdapter;
    }

    /**
     * 初始化 搜索 adapter
     *
     * @return
     */
    public SimpleRecAdapter getSearchListAdapter() {
        SearchRecycleAdapter searchAdapter = new SearchRecycleAdapter(context);
        xrvSearchList.setAdapter(searchAdapter);
        searchAdapter.setRecItemClick(new RecyclerItemCallback<NewestFundResp, SearchRecycleAdapter.ViewHolder>() {
            @Override
            public void onItemClick(int position, NewestFundResp model, int tag, SearchRecycleAdapter.ViewHolder holder) {
                super.onItemClick(position, model, tag, holder);
                switch (tag) {
                    //点击
                    case SearchRecycleAdapter.ITEM_CLICK:
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
        return searchAdapter;
    }

//    /**
//     * 开始P层搜索
//     */
//    private void startSearch(String keyword) {
//        if (!isNotEmpty(keyword)) {
//            return;
//        }
//        getP().searchData(keyword);
//        etSearchKey.clearFocus();
//    }
//
//    @Override
//    public void getKeyword(String keyword) {
//        //开始搜索
//        if (isNotEmpty(keyword)) {
//            startSearch(keyword);
//        }
//    }

    /**
     * 获取 近期热搜 成功
     *
     * @param data
     */
    public void requestHotListSuccess(ArrayList<NewestFundResp> data) {
        if (data != null && data.size() > 0) {
            getHotListAdapter().addData(data);
        } else {

        }
    }

    /**
     * 获取 近期热搜 失败
     */
    public void requestHotListFail() {
    }

    /**
     * 搜索成功
     *
     * @param list
     */
    public void requestSearchDataSuccess(final ArrayList<NewestFundResp> list) {

        XLog.e("size" + list.size());
        //如果数据为空则显示空视图
        if (list != null && list.size() > 0) {
            tvEmpty.setVisibility(GONE);
            getSearchListAdapter().addData(list);
        } else {
            xrvSearchList.setVisibility(GONE);
            tvEmpty.setVisibility(VISIBLE);
        }
    }

    /**
     * 搜索失败
     */
    public void requestSearchDataFail() {
    }
}
