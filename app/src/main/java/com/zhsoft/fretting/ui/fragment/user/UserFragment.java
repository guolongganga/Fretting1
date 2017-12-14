package com.zhsoft.fretting.ui.fragment.user;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhsoft.fretting.R;
import com.zhsoft.fretting.model.Happ;
import com.zhsoft.fretting.model.TaetModel;
import com.zhsoft.fretting.present.UserPresent;
import com.zhsoft.fretting.ui.activity.user.LoginActivity;
import com.zhsoft.fretting.ui.activity.user.RegisterFirstActivity;
import com.zhsoft.fretting.ui.activity.user.SettingActivity;

import java.util.List;

import butterknife.BindView;
import cn.droidlover.xdroidmvp.mvp.XFragment;

/**
 * 作者：sunnyzeng on 2017/12/5
 * 描述：我的
 */

public class UserFragment extends XFragment<UserPresent> {

    @BindView(R.id.head_back) ImageButton headBack;
    @BindView(R.id.head_title) TextView headTitle;
    @BindView(R.id.head_right) Button headRight;
    @BindView(R.id.login) Button login;
    @BindView(R.id.register) Button register;
    @BindView(R.id.self_choose) ImageView selfChoose;
    @BindView(R.id.ll_logout) LinearLayout llLogout;

    private boolean isLogin = false;//未登录

    @Override
    public void initData(Bundle savedInstanceState) {
        headBack.setVisibility(View.GONE);
        headTitle.setText("我的");
        headRight.setText("设置");
        headRight.setVisibility(View.VISIBLE);
        getP().loadTestData();
    }


    @Override
    public void initEvents() {

        headRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(SettingActivity.class);
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(LoginActivity.class);
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(RegisterFirstActivity.class);
            }
        });
        selfChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showToast("hahahha,点击了");
            }
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_user;
    }

    @Override
    public UserPresent newP() {
        return new UserPresent();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    public void showData(TaetModel data) {
        List<Happ> list = data.getDictData();
        list.get(0);
    }

    @Override
    public void onResume() {
        super.onResume();
        if(isLogin){
            llLogout.setVisibility(View.GONE);
        }else{
            llLogout.setVisibility(View.VISIBLE);
        }
    }
}
