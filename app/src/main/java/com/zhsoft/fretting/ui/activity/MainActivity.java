package com.zhsoft.fretting.ui.activity;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.TextView;

import com.zhsoft.fretting.R;
import com.zhsoft.fretting.ui.fragment.fund.FundFragment;
import com.zhsoft.fretting.ui.fragment.user.UserFragment;
import com.zhsoft.fretting.ui.fragment.index.IndexFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.droidlover.xdroidmvp.mvp.XActivity;
import cn.droidlover.xdroidmvp.router.Router;

/**
 * 作者：sunnyzeng on 2017/12/5
 * 描述：主页面
 */

public class MainActivity extends XActivity {

    //菜单按钮
    /** 首页 */
    @BindView(R.id.tv_index)
    TextView tvIndex;
    /** 基金 */
    @BindView(R.id.tv_fund)
    TextView tvFund;
    /** 我的 */
    @BindView(R.id.tv_user)
    TextView tvUser;

    /** 碎片集合 */
    private List<Fragment> fragments;
    /** 碎片 */
    private Fragment fragment;
    /** 碎片事务 */
    private FragmentTransaction ft;
    /** 当前Tab页面索引 */
    private int currentTab;
    /** 底部tab */
    private List<TextView> textViews;

    private long firstTime; //用于点击两次返回退出程序

    @Override
    public void initData(Bundle savedInstanceState) {
        fragments = new ArrayList<>();
        fragments.add(new IndexFragment());
        fragments.add(new FundFragment());
        fragments.add(new UserFragment());

        textViews = new ArrayList<>();
        textViews.add(tvIndex);
        textViews.add(tvFund);
        textViews.add(tvUser);

        //默认显示主页
        show(tvIndex, 0);
    }

    @Override
    public void initEvents() {
        //切换底部菜单
        tvIndex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                show(tvIndex, 0);
            }
        });

        tvFund.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                show(tvFund, 1);
            }
        });

        tvUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                show(tvUser, 2);
            }
        });
    }

    /**
     * 获取当前Fragment
     *
     * @return
     */
    public Fragment getCurrentFragment() {
        return fragments.get(currentTab);
    }

    /**
     * 切换底部菜单栏
     *
     * @param textView 点击的tab
     * @param idx      当前位置
     */
    public void show(TextView textView, int idx) {
        for (int i = 0; i < fragments.size(); i++) {
            fragment = fragments.get(i);
            ft = getSupportFragmentManager().beginTransaction();

            getCurrentFragment().onPause(); // 暂停当前tab
            if (idx == i) {
                if (fragment.isAdded()) {
                    fragment.onResume(); // 启动目标tab的onResume()
                } else {
                    ft.add(R.id.fl_content, fragment);
                }

                Drawable drawable = null;
                if (idx == 0) {
                    drawable = getResources().getDrawable(R.drawable.tab_index);

                } else if (idx == 1) {
                    drawable = getResources().getDrawable(R.drawable.tab_ticket);
                } else if (idx == 2) {
                    drawable = getResources().getDrawable(R.drawable.tab_user);
                }
                // 这一步必须要做,否则不会显示.
                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                textView.setCompoundDrawables(null, drawable, null, null);
                textView.setTextColor(getResources().getColor(R.color.color_main));
                textView.setSelected(true);
                ft.show(fragment);
            } else {
                textViews.get(i).setTextColor(getResources().getColor(R.color.color_666666));
                textViews.get(i).setSelected(false);
                ft.hide(fragment);
            }

            ft.commit();
            currentTab = idx; // 更新目标tab为当前tab
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public Object newP() {
        return null;
    }

    /**
     * 跳转
     * @param activity
     */
    public static void launch(Activity activity) {
        Router.newIntent(activity)
                .to(MainActivity.class)
                .launch();
    }

    /**
     * 返回按钮
     */
    @Override
    public void onBackPressed() {
        long secondTime = System.currentTimeMillis();
        if (secondTime - firstTime > 1500) {
            showToast(R.string.click_logout);
            firstTime = secondTime;
        } else {
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(0);
        }
    }
}

