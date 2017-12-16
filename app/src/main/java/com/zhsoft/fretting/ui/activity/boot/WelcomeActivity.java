package com.zhsoft.fretting.ui.activity.boot;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.zhsoft.fretting.ui.activity.MainActivity;
import com.zhsoft.fretting.R;
import com.zhsoft.fretting.ui.adapter.boot.WelcomeViewpagerAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.droidlover.xdroidmvp.mvp.XActivity;
import cn.droidlover.xdroidmvp.router.Router;

/**
 * 作者：sunnyzeng on 2017/12/5
 * 描述：引导页
 */

public class WelcomeActivity extends XActivity {

    private Button btnGo;

    @BindView(R.id.view_pager)
    ViewPager mViewPager;

    private List<View> dataList;

    @Override
    public int getLayoutId() {
        return R.layout.activity_welcome;
    }

    @Override
    public Object newP() {
        return null;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        View viewOne = LayoutInflater.from(context).inflate(R.layout.item_one_welcome, null);
        View viewTwo = LayoutInflater.from(context).inflate(R.layout.item_two_welcome, null);
        View viewThree = LayoutInflater.from(context).inflate(R.layout.item_three_welcome, null);

        btnGo = viewThree.findViewById(R.id.btn_go);

        dataList = new ArrayList<>();
        dataList.add(viewOne);
        dataList.add(viewTwo);
        dataList.add(viewThree);

        WelcomeViewpagerAdapter adapter = new WelcomeViewpagerAdapter(context, dataList);
        mViewPager.setAdapter(adapter);
    }

    @Override
    public void initEvents() {
        //开启APP
        btnGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.launch(context);
                finish();
            }
        });
    }

    public static void launch(Activity activity) {
        Router.newIntent(activity)
                .to(WelcomeActivity.class)
                .launch();
    }
}
