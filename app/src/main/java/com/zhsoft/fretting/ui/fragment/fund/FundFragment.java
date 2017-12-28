package com.zhsoft.fretting.ui.fragment.fund;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.zhsoft.fretting.R;
import com.zhsoft.fretting.constant.Constant;
import com.zhsoft.fretting.ui.activity.user.SwitchAccountActivity;
import com.zhsoft.fretting.ui.adapter.fund.FundTabViewPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.droidlover.xdroidmvp.mvp.XFragment;
import cn.droidlover.xdroidmvp.mvp.XLazyFragment;

/**
 * 作者：sunnyzeng on 2017/12/5
 * 描述：基金
 */

public class FundFragment extends XLazyFragment {

    @BindView(R.id.tab_layout)
    TabLayout mTabLayout;
    @BindView(R.id.view_pager)
    ViewPager mViewPager;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_fund;
    }

    @Override
    public Object newP() {
        return null;
    }


    @Override
    public void initData(Bundle savedInstanceState) {
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
    }

    /**
     * 频道数据展示
     */
    public void showChannel() {

        FragmentManager fragmentManager = getFragmentManager();

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
            bundle.putInt(Constant.ACTIVITY_NAME, Constant.FUND_INDEX);
            fragment.setArguments(bundle);
            fragmentList.add(fragment);
        }

        FundTabViewPagerAdapter adapter = new FundTabViewPagerAdapter(fragmentManager, fragmentList, tabName);
        mViewPager.setAdapter(adapter);

        mTabLayout.setupWithViewPager(mViewPager);
    }

}
