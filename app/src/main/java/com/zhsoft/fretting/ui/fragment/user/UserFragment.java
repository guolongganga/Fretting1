package com.zhsoft.fretting.ui.fragment.user;


import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.zhsoft.fretting.R;
import com.zhsoft.fretting.model.BaseModel;
import com.zhsoft.fretting.model.Happ;
import com.zhsoft.fretting.model.TaetModel;
import com.zhsoft.fretting.present.UserPresent;

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

    @Override
    public void initData(Bundle savedInstanceState) {
//        String string = "ezG8gPH6LJffPr1F9fLQlLUdoyNPd69mYzoLmbfIQfhz9/qQl88d0FFuyAccx5rVFjyoqxJEVHQ68zNUR6s7ftuIgp9ETDYZ/PHAhD/gkHLMovt9QH2B4YCKKE+mrf7pafEk+YFGab5l1GzKWF+fCAt54o7ZcM2g48ntAXn3Iq0qSdxz/FcJz1aM+OTqPhI8/2TWUDXIHv773dSj7rTuPMy6yXbES6CbRpFyO5TRh9Tp2+2PBmlCz/UllIaqTf73Q3x81fPsfrnWSwBdrJ8cwA==";
//        String data = EncryptDecrypt.decrptByAES(string);
//        mytext.setText(data);
        getP().loadTestData();
    }

    @Override
    public void initEvents() {

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
        mytext.setText(data.getDictData().size()+"----"+list.get(0).getDictDesc());
    }
}
