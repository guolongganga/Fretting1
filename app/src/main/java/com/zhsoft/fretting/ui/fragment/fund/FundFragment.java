package com.zhsoft.fretting.ui.fragment.fund;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.zhsoft.fretting.R;
import com.zhsoft.fretting.ui.activity.user.SwitchAccountActivity;

import butterknife.BindView;
import cn.droidlover.xdroidmvp.mvp.XFragment;

/**
 * 作者：sunnyzeng on 2017/12/5
 * 描述：基金
 */

public class FundFragment extends XFragment {

    @BindView(R.id.tv_fund)
    TextView tvFund;

    @Override
    public void initData(Bundle savedInstanceState) {

    }

    @Override
    public void initEvents() {
        tvFund.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(SwitchAccountActivity.class);
            }
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_fund;
    }

    @Override
    public Object newP() {
        return null;
    }
}
