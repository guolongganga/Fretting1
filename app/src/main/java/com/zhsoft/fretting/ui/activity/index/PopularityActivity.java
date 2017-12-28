package com.zhsoft.fretting.ui.activity.index;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.zhsoft.fretting.R;
import com.zhsoft.fretting.constant.Constant;
import com.zhsoft.fretting.ui.adapter.fund.FundTabViewPagerAdapter;
import com.zhsoft.fretting.ui.fragment.fund.FundContentFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.droidlover.xdroidmvp.mvp.XActivity;

/**
 * 作者：sunnyzeng on 2017/12/21 16:15
 * 描述：人气产品 业绩排行
 */

public class PopularityActivity extends XActivity {
    /** 返回按钮 */
    @BindView(R.id.head_back) ImageButton headBack;
    /** 标题 */
    @BindView(R.id.head_title) TextView headTitle;
    /** 右侧图片按钮 */
    @BindView(R.id.head_right_imgbtn) ImageButton headRightImgbtn;
    /** 页卡标题 */
    @BindView(R.id.tab_layout) TabLayout mTabLayout;
    /** 滑动内容 */
    @BindView(R.id.view_pager) ViewPager mViewPager;

    @Override
    public int getLayoutId() {
        return R.layout.activity_index_popularity;
    }

    @Override
    public Object newP() {
        return null;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        headTitle.setText("业绩排行");
        headRightImgbtn.setVisibility(View.VISIBLE);
        headRightImgbtn.setImageResource(R.mipmap.icon_query);
        showChannel();
    }

    @Override
    public void initEvents() {
        //TabLayout监听滑动或点击
        mTabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mViewPager.setCurrentItem(tab.getPosition(), false);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        headBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        headRightImgbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showToast("查找基金");
            }
        });
    }

    /**
     * 频道数据展示
     */
    public void showChannel() {

        FragmentManager fragmentManager = getSupportFragmentManager();

        List<Fragment> fragmentList = new ArrayList<>();

        List<String> tabName = new ArrayList<>();
        tabName.add("股票型");
        tabName.add("混合型");
        tabName.add("债券型");
        tabName.add("指数型");

        int fragmentSize = tabName.size();

        for (int i = 0; i < fragmentSize; i++) {
            FundContentFragment fragment = new FundContentFragment();
            Bundle bundle = new Bundle();
            bundle.putString(Constant.FUND_TAB_NAME, tabName.get(i));
            bundle.putInt(Constant.ACTIVITY_NAME,Constant.POPULARITY);
            fragment.setArguments(bundle);
            fragmentList.add(fragment);
        }

        FundTabViewPagerAdapter adapter = new FundTabViewPagerAdapter(fragmentManager, fragmentList, tabName);
        mViewPager.setAdapter(adapter);

        mTabLayout.setupWithViewPager(mViewPager);
    }


}
