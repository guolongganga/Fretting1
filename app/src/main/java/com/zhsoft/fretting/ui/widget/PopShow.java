package com.zhsoft.fretting.ui.widget;

import android.app.Activity;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.zhsoft.fretting.R;
import com.zhsoft.fretting.ui.adapter.boot.PopBottomSelectorRecycleAdapter;
import com.zhsoft.fretting.ui.adapter.boot.PopDropSelectorRecycleAdapter;

import java.util.ArrayList;
import java.util.List;

import cn.droidlover.xrecyclerview.RecyclerItemCallback;
import cn.droidlover.xrecyclerview.XRecyclerView;

/**
 * PoP展示
 * Created by ${sunny}
 * data: 2017/12/19
 */

public class PopShow {

    private Activity activity;
    private View view;

    public PopShow(Activity activity, View view) {
        this.activity = activity;
        this.view = view;
    }

    /**
     * 涨幅选择
     */
    public void showRangeSelector(final List<String> list,int isSelector) {
        //设置contentView
        View contentView = LayoutInflater.from(activity).inflate(R.layout.pop_drop_selector, null);
        final PopupWindow mPopWindow = new PopupWindow(contentView,
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, true);
        mPopWindow.setContentView(contentView);
        mPopWindow.setFocusable(true);
        //外部是否可以点击
        mPopWindow.setBackgroundDrawable(new BitmapDrawable());
        mPopWindow.setOutsideTouchable(true);

        FrameLayout flPopContent = contentView.findViewById(R.id.fl_content);
        flPopContent.getBackground().setAlpha(150);

        XRecyclerView xrvDropSelector = contentView.findViewById(R.id.xrv_drop_selector);
        xrvDropSelector.verticalLayoutManager(activity);
        final PopDropSelectorRecycleAdapter popAdapter = new PopDropSelectorRecycleAdapter(activity);
        xrvDropSelector.setAdapter(popAdapter);
        popAdapter.setData(list);
        popAdapter.setIsSelector(isSelector);//默认选中项

        popAdapter.setRecItemClick(new RecyclerItemCallback<String, PopDropSelectorRecycleAdapter.ViewHolder>() {
            @Override
            public void onItemClick(int position, String model, int tag, PopDropSelectorRecycleAdapter.ViewHolder holder) {
                switch (tag) {
                    case PopDropSelectorRecycleAdapter.ITEM_CLICK:
                        popAdapter.setIsSelector(position);
                        popAdapter.notifyDataSetChanged();
                        onClickPop.setRange(position);
                        mPopWindow.dismiss();
                        break;
                }
            }
        });
        mPopWindow.showAsDropDown(view, 0, 0);
    }

    /**
     * 纯数字显示 在底部
     * @param list
     */
    public void showText(final List<String> list) {
        //设置contentView
        View contentView = LayoutInflater.from(activity).inflate(R.layout.pop_bottom_selector, null);
        final PopupWindow mPopWindow = new PopupWindow(contentView,
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, true);
        mPopWindow.setContentView(contentView);
        mPopWindow.setFocusable(true);
        //外部是否可以点击
        mPopWindow.setBackgroundDrawable(new BitmapDrawable());
        mPopWindow.setOutsideTouchable(true);
        //设置PopupWindow弹出窗体动画效果
        mPopWindow.setAnimationStyle(R.style.AnimBottom);

//        FrameLayout flPopContent = contentView.findViewById(R.id.fl_content);
//        flPopContent.getBackground().setAlpha(150);
        //取消按钮
        Button cancle = contentView.findViewById(R.id.cancle);
        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPopWindow.dismiss();
            }
        });

        XRecyclerView xrvDropSelector = contentView.findViewById(R.id.xrv_drop_selector);
        xrvDropSelector.verticalLayoutManager(activity);
        final PopBottomSelectorRecycleAdapter popAdapter = new PopBottomSelectorRecycleAdapter(activity);
        xrvDropSelector.setAdapter(popAdapter);
        popAdapter.setData(list);

        popAdapter.setRecItemClick(new RecyclerItemCallback<String, PopBottomSelectorRecycleAdapter.ViewHolder>() {
            @Override
            public void onItemClick(int position, String model, int tag, PopBottomSelectorRecycleAdapter.ViewHolder holder) {
                switch (tag) {
                    case PopBottomSelectorRecycleAdapter.ITEM_CLICK:
                        popAdapter.notifyDataSetChanged();
                        onClickPop.setRange(position);
                        mPopWindow.dismiss();
                        break;
                }
            }
        });
        mPopWindow.showAtLocation(view, Gravity.CENTER| Gravity.BOTTOM,0,0);
    }

    public OnClickPop onClickPop;

    public void setOnClickPop(OnClickPop onClickPop) {
        this.onClickPop = onClickPop;
    }

    public interface OnClickPop {
        void setRange(int position);
    }

}
