package com.zhsoft.fretting.ui.fragment.user;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.zhsoft.fretting.App;
import com.zhsoft.fretting.R;
import com.zhsoft.fretting.constant.Constant;
import com.zhsoft.fretting.event.RefreshBonusEvent;
import com.zhsoft.fretting.model.user.UpdateBonusResp;
import com.zhsoft.fretting.present.user.BonusModePresent;
import com.zhsoft.fretting.ui.activity.user.BonusChangeActivity;
import com.zhsoft.fretting.ui.adapter.user.UpdateBonusRecycleAdapter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;
import cn.droidlover.xdroidmvp.base.SimpleRecAdapter;
import cn.droidlover.xdroidmvp.dialog.httploadingdialog.HttpLoadingDialog;
import cn.droidlover.xdroidmvp.mvp.XFragment;
import cn.droidlover.xrecyclerview.RecyclerItemCallback;
import cn.droidlover.xrecyclerview.XRecyclerView;

/**
 * 作者：sunnyzeng on 2018/1/24 19:00
 * 描述：不用分页 修改分红方式
 */

public class BonusModeFragment extends XFragment<BonusModePresent> {
    @BindView(R.id.xrv_my_recyler) XRecyclerView xrvMyRecyler;
    @BindView(R.id.tv_empty) TextView tvEmpty;
    private HttpLoadingDialog httpLoadingDialog;
    /** 登录标识 */
    private String token;
    /** 用户编号 */
    private String userId;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_user_bonus_content;
    }

    @Override
    public BonusModePresent newP() {
        return new BonusModePresent();
    }

    @Override
    public void initData(Bundle bundle) {
        //注册事件
        EventBus.getDefault().register(this);
        xrvMyRecyler.verticalLayoutManager(context);//设置RecycleView类型 - 不设置RecycleView不显示
        httpLoadingDialog = new HttpLoadingDialog(context);
        token = App.getSharedPref().getString(Constant.TOKEN, "");
        userId = App.getSharedPref().getString(Constant.USERID, "");
        //请求银行卡列表
        httpLoadingDialog.visible("加载中...");
        //请求分红方式列表
        getP().loadBonusTypeData(token, userId);


    }

    @Override
    public void initEvents() {

    }


    /**
     * 初始化adapter
     *
     * @return
     */
    public SimpleRecAdapter getAdapter() {
        UpdateBonusRecycleAdapter adapter = new UpdateBonusRecycleAdapter(context);
        xrvMyRecyler.setAdapter(adapter);
        adapter.setRecItemClick(new RecyclerItemCallback<UpdateBonusResp, UpdateBonusRecycleAdapter.ViewHolder>() {
            @Override
            public void onItemClick(int position, UpdateBonusResp model, int tag, UpdateBonusRecycleAdapter.ViewHolder holder) {
                super.onItemClick(position, model, tag, holder);
                switch (tag) {
                    //点击
                    case UpdateBonusRecycleAdapter.ITEM_CLICK:
                        //"0":可以修改   "1":不可以修改
                        if ("0".equals(model.getForbidModiAutobuyFlag())) {
                            //跳转 分红方式变更 TODO 需要传递参数
                            Bundle bundle = new Bundle();
                            bundle.putParcelable(Constant.ACTIVITY_OBJECT, model);
                            startActivity(BonusChangeActivity.class, bundle);
                        }
                        break;
                }
            }
        });
        return adapter;
    }

    /**
     * 数据展示
     *
     * @param data
     */
    public void showData(List<UpdateBonusResp> data) {

        httpLoadingDialog.dismiss();
        if (data != null && data.size() > 0) {
            xrvMyRecyler.setVisibility(View.VISIBLE);
            tvEmpty.setVisibility(View.GONE);
            getAdapter().addData(data);
        } else {
            xrvMyRecyler.setVisibility(View.GONE);
            tvEmpty.setVisibility(View.VISIBLE);
        }
    }

    public void showError() {
        httpLoadingDialog.dismiss();
    }

    /**
     * 刷新分红方式数据
     *
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.POSTING)
    public void onRefreshBonusEvent(RefreshBonusEvent event) {
        //刷新分红方式数据
        getP().loadBonusTypeData(token, userId);
    }

    @Override
    public void onDestroyView() {
        EventBus.getDefault().unregister(this);
        super.onDestroyView();
    }
}
