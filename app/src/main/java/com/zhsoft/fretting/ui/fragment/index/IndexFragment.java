package com.zhsoft.fretting.ui.fragment.index;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zhsoft.fretting.R;
import com.zhsoft.fretting.constant.Constant;
import com.zhsoft.fretting.model.index.BannerModel;
import com.zhsoft.fretting.model.index.IndexResp;
import com.zhsoft.fretting.model.index.PopularityResp;
import com.zhsoft.fretting.model.index.ProductModel;
import com.zhsoft.fretting.present.index.IndexPresent;
import com.zhsoft.fretting.ui.activity.fund.BuyActivity;
import com.zhsoft.fretting.ui.activity.fund.InvestUpdateActivity;
import com.zhsoft.fretting.ui.activity.index.PopularityActivity;
import com.zhsoft.fretting.ui.activity.index.TimingActivity;
import com.zhsoft.fretting.ui.adapter.index.PopularityRecycleAdapter;
import com.zhsoft.fretting.ui.adapter.user.SwitchAccountRecycleAdapter;
import com.zhsoft.fretting.utils.BigDecimalUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.droidlover.xdroidmvp.banner.FlyBanner;
import cn.droidlover.xdroidmvp.base.SimpleRecAdapter;
import cn.droidlover.xdroidmvp.log.XLog;
import cn.droidlover.xdroidmvp.mvp.XFragment;
import cn.droidlover.xrecyclerview.RecyclerItemCallback;
import cn.droidlover.xrecyclerview.XRecyclerView;

/**
 * 作者：sunnyzeng on 2017/12/5
 * 描述：主页
 */

public class IndexFragment extends XFragment<IndexPresent> {

    /** 轮播图 */
    @BindView(R.id.banner)
    FlyBanner banner;
    /** 人气产品 */
    @BindView(R.id.xrv_popularity)
    XRecyclerView xrvPopularity;
    /** 人气产品 更多 */
    @BindView(R.id.popularity_more)
    TextView popularityMore;
    /** 明星基金 收益率 */
    @BindView(R.id.tv_seven_earnings)
    TextView tvSevenEarnings;
    /** 明星基金 名称 */
    @BindView(R.id.tv_wan_earnings)
    TextView tvWanarnings;
    /** 明星基金 购买 */
    @BindView(R.id.btn_buy)
    Button btnBuy;
    /** 指数基金产品1 名称 */
    @BindView(R.id.tv_nvshen)
    TextView tvNvshen;
    /** 指数基金产品1 收益 */
    @BindView(R.id.tv_nvshen_shouyi)
    TextView tvNvshenShouyi;
    /** 指数基金产品2 名称 */
    @BindView(R.id.tv_chihuo)
    TextView tvChihuo;
    /** 指数基金产品2 收益 */
    @BindView(R.id.tv_chihuo_shouyi)
    TextView tvChihuoShouyi;
    /** 优选定投 更多 */
    @BindView(R.id.timing_more)
    TextView timingMore;
    /** 优选定投 名称 */
    @BindView(R.id.preferred_name)
    TextView preferredName;
    /** 优选定投 利率 */
    @BindView(R.id.preferred_rate)
    TextView preferredRate;
    /** 搜索 */
    @BindView(R.id.rl_name_search)
    RelativeLayout rlNameSearch;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_index;
    }

    @Override
    public IndexPresent newP() {
        return new IndexPresent();
    }

    @Override
    public void initData(Bundle savedInstanceState) {

        getPopularityAdapter();

        getP().loadData();

        xrvPopularity.setFocusable(false);
        xrvPopularity.verticalLayoutManager(context);//设置RecycleView类型 - 不设置RecycleView不显示
    }

    @Override
    public void initEvents() {
        popularityMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(PopularityActivity.class);
            }
        });
        timingMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(TimingActivity.class);
            }
        });

        rlNameSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(BuyActivity.class);
//                if (RuntimeHelper.getInstance().isLogin()) {
//                    startActivity(BuyActivity.class);
//                } else {
//                    startActivity(LoginActivity.class);
//                }
            }
        });
        btnBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString(Constant.INVEST_ACTIVITY_TYPE,Constant.INVEST_ACTIVITY);
                startActivity(InvestUpdateActivity.class,bundle);
//                if (RuntimeHelper.getInstance().isLogin()) {
//                    startActivity(InvestActivity.class);
//                } else {
//                    startActivity(LoginActivity.class);
//                }

            }
        });

    }

    /**
     * 展示banner
     *
     * @param bannerList
     */
    public void showBanner(List<BannerModel> bannerList) {
        List<String> bannerUrlList = new ArrayList<>();
        for (BannerModel bannerModel : bannerList) {
            bannerUrlList.add(bannerModel.getBannerImageUrl());
        }
        XLog.e("qqq", bannerUrlList.size() + "");
        banner.setImagesUrl(bannerUrlList);

        banner.setOnItemClickListener(new FlyBanner.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                showToast("点击了第" + position + "张图片");
            }
        });


    }

    /**
     * 初始化人气产品adapter
     *
     * @return
     */
    public SimpleRecAdapter getPopularityAdapter() {
        PopularityRecycleAdapter adapter = new PopularityRecycleAdapter(context);
        xrvPopularity.setAdapter(adapter);
        adapter.setRecItemClick(new RecyclerItemCallback<ProductModel, PopularityRecycleAdapter.ViewHolder>() {
            @Override
            public void onItemClick(int position, ProductModel model, int tag, PopularityRecycleAdapter.ViewHolder holder) {
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

    /**
     * 人气产品数据展示
     *
     * @param resps
     */
    public void showPopularity(List<PopularityResp> resps) {
        if (resps != null && resps.size() > 0) {
            getPopularityAdapter().addData(resps);
        }
    }


    public void showIndexData(IndexResp data) {
        if (data != null) {
            //banner展示
            if (data.getBannerList() != null && data.getBannerList().size() > 0) {
                List<String> bannerUrlList = new ArrayList<>();
                for (BannerModel bannerModel : data.getBannerList()) {
                    XLog.e("qqq", bannerModel.getBannerImageUrl());
                    bannerUrlList.add(bannerModel.getBannerImageUrl());
                }
                XLog.e("qqq", bannerUrlList.size() + "");
                banner.setImagesUrl(bannerUrlList);

                banner.setOnItemClickListener(new FlyBanner.OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        showToast("点击了第" + position + "张图片");
                    }
                });
            }
            //明星基金
            if (data.getStarFound() != null) {
                tvSevenEarnings.setText(BigDecimalUtil.bigdecimalToString(data.getStarFound().getAveg()) + "%");
                tvWanarnings.setText(data.getStarFound().getName());
            }
            //微银专题
            //人气产品
            if (data.getHotFoundList() != null && data.getHotFoundList().size() > 0) {
                getPopularityAdapter().addData(data.getHotFoundList());
            }
            //指数基金1
            if (data.getFirstIndexFound() != null) {
                tvNvshen.setText(data.getFirstIndexFound().getName());
                tvNvshenShouyi.setText("+" + BigDecimalUtil.bigdecimalToString(data.getFirstIndexFound().getAveg()) + "%");

            }
            //指数基金2
            if (data.getSecondIndexFound() != null) {
                tvChihuo.setText(data.getSecondIndexFound().getName());
                tvChihuoShouyi.setText("+" + BigDecimalUtil.bigdecimalToString(data.getSecondIndexFound().getAveg()) + "%");
            }
            //优选定投
            if (data.getPreferredVote() != null) {
                preferredName.setText(data.getPreferredVote().getName());
                preferredRate.setText(BigDecimalUtil.bigdecimalToString(data.getPreferredVote().getAveg()) + "%");
            }

        }
    }
}
