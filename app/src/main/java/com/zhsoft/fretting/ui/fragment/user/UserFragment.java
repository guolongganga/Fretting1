package com.zhsoft.fretting.ui.fragment.user;


import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.zhsoft.fretting.MainActivity;
import com.zhsoft.fretting.R;
import com.zhsoft.fretting.model.BaseModel;
import com.zhsoft.fretting.model.Happ;
import com.zhsoft.fretting.model.TaetModel;
import com.zhsoft.fretting.present.UserPresent;
import com.zhsoft.fretting.ui.activity.user.RegisterFirstActivity;

import java.util.List;

import butterknife.BindView;
import cn.droidlover.xdroidmvp.mvp.XFragment;
import cn.droidlover.xdroidmvp.utils.EncryptDecrypt;

/**
 * 作者：sunnyzeng on 2017/12/5
 * 描述：我的
 */

public class UserFragment extends XFragment<UserPresent> {

    @BindView(R.id.mytext) TextView mytext;
    @BindView(R.id.head_title) TextView headTitle;
    @BindView(R.id.register) Button register;

    private boolean isLogin = false;//未登录

    @Override
    public void initData(Bundle savedInstanceState) {
        headTitle.setText("我的");
        if (isLogin) {

        }
        getP().loadTestData();
    }


    @Override
    public void initEvents() {
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(RegisterFirstActivity.class);
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
        mytext.setText(data.getDictData().size() + "----" + list.get(0).getDictDesc());
    }
}
