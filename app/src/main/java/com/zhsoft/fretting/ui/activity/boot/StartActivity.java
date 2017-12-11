package com.zhsoft.fretting.ui.activity.boot;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.zhsoft.fretting.App;
import com.zhsoft.fretting.MainActivity;
import com.zhsoft.fretting.R;
import com.zhsoft.fretting.constant.Constant;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import cn.droidlover.xdroidmvp.mvp.XActivity;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * 作者：sunnyzeng on 2017/12/5
 * 描述：启动页
 */

public class StartActivity extends XActivity {

    @BindView(R.id.iv_jump)
    ImageView ivJump;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;

    public boolean canJump = false;

    @BindView(R.id.iv_start)
    ImageView ivStart;//启动图


    @Override
    public int getLayoutId() {
        return R.layout.activity_start;
    }

    @Override
    public Object newP() {
        return null;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        setTranslucentStatus(false);
        boolean firstUse = App.getSharedPref().getBoolean(Constant.FIRST_USE, true);
        if (firstUse) {
            //加载引导页面
            WelcomeActivity.launch(context);
            finish();
            App.getSharedPref().putBoolean(Constant.FIRST_USE, false);
        } else {
            //启动页面 RxJava启动延迟启动
            Observable.timer(2, TimeUnit.SECONDS).subscribe(new Observer<Long>() {

                @Override
                public void onSubscribe(Disposable d) {

                }

                @Override
                public void onNext(Long value) {

                }

                @Override
                public void onError(Throwable e) {

                }

                @Override
                public void onComplete() {
                    startActivity(MainActivity.class);
                    finish();
                }
            });
        }
    }

    @Override
    public void initEvents() {
        ivJump.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ivJump.setVisibility(View.GONE);
                progressBar.setVisibility(View.VISIBLE);
                MainActivity.launch(context);
                finish();
            }
        });
    }
}
