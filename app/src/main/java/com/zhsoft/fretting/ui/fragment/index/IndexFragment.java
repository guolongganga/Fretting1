package com.zhsoft.fretting.ui.fragment.index;


import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.zhsoft.fretting.R;
import com.zhsoft.fretting.constant.Constant;
import com.zhsoft.fretting.ui.activity.boot.WebPublicActivity;

import butterknife.BindView;
import cn.droidlover.xdroidmvp.mvp.XFragment;

/**
 * 作者：sunnyzeng on 2017/12/5
 * 描述：主页
 */

public class IndexFragment extends XFragment {

    @BindView(R.id.tv_date)
    TextView tvDate;

    @Override
    public void initData(Bundle savedInstanceState) {

    }

    @Override
    public void initEvents() {
        tvDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putInt(Constant.WEB_TITLE, R.string.app_name);
                bundle.putString(Constant.WEB_LINK, "https://www.baidu.com/?tn=96928074_hao_pg");
                startActivity(WebPublicActivity.class, bundle);
            }
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_index;
    }

    @Override
    public Object newP() {
        return null;
    }
}
