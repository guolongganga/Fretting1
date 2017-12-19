package com.zhsoft.fretting.ui.fragment.index;


import android.os.Bundle;

import com.zhsoft.fretting.R;
import com.zhsoft.fretting.model.index.PopularityResp;
import com.zhsoft.fretting.present.index.IndexPresent;
import com.zhsoft.fretting.ui.adapter.index.PopularityRecycleAdapter;
import com.zhsoft.fretting.ui.adapter.user.SwitchAccountRecycleAdapter;

import java.util.List;

import butterknife.BindView;
import cn.droidlover.xdroidmvp.banner.FlyBanner;
import cn.droidlover.xdroidmvp.base.SimpleRecAdapter;
import cn.droidlover.xdroidmvp.mvp.XFragment;
import cn.droidlover.xrecyclerview.RecyclerItemCallback;
import cn.droidlover.xrecyclerview.XRecyclerView;

/**
 * 作者：sunnyzeng on 2017/12/5
 * 描述：主页
 */

public class IndexFragment extends XFragment<IndexPresent> {

    //轮播图
    @BindView(R.id.banner)
    FlyBanner banner;
    //人气产品
    @BindView(R.id.xrv_popularity)
    XRecyclerView xrvPopularity;

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

    }

    /**
     * 展示banner
     *
     * @param bannerList
     */
    public void showBanner(List<String> bannerList) {
        banner.setImagesUrl(bannerList);

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
        adapter.setRecItemClick(new RecyclerItemCallback<PopularityResp, PopularityRecycleAdapter.ViewHolder>() {
            @Override
            public void onItemClick(int position, PopularityResp model, int tag, PopularityRecycleAdapter.ViewHolder holder) {
                super.onItemClick(position, model, tag, holder);
                switch (tag) {
                    //点击
                    case SwitchAccountRecycleAdapter.ITEM_CLICK:
                        showToast(model.getTitle());
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


}
