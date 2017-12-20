package com.zhsoft.fretting.ui.activity.user;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhsoft.fretting.App;
import com.zhsoft.fretting.R;
import com.zhsoft.fretting.constant.Constant;
import com.zhsoft.fretting.ui.widget.PopShow;
import com.zhsoft.fretting.ui.widget.PostionSelectPopupWindow;
import com.zhsoft.fretting.utils.KeyBoardUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.droidlover.xdroidmvp.mvp.XActivity;

/**
 * 作者：sunnyzeng on 2017/12/13 10:13
 * 描述：个人信息
 */

public class PersonInfoActivity extends XActivity {
    //时间类
    private Calendar calendar = Calendar.getInstance();
    //时间选择器
    private DatePickerDialog timeDialog;
    /** 返回按钮 */
    @BindView(R.id.head_back) ImageButton headBack;
    /** 标题 */
    @BindView(R.id.head_title) TextView headTitle;
    /** 用户名 */
    @BindView(R.id.name) TextView name;
    /** 身份证号 */
    @BindView(R.id.identity) TextView identity;
    /** 账户编号 */
    @BindView(R.id.accountid) TextView accountid;
    /** 邮箱 */
    @BindView(R.id.email) EditText email;
    /** 性别 */
    @BindView(R.id.sex) TextView sex;
    /** 身份证期限 */
    @BindView(R.id.limit_time) TextView limitTime;
    /** 选择 永久有效 */
    @BindView(R.id.iv_selector) ImageView ivSelector;
    /** 国籍 */
    @BindView(R.id.nationality) TextView nationality;
    /** 选择职业 */
    @BindView(R.id.linearlayout_duty) LinearLayout linearlayout_duty;
    /** 职业 */
    @BindView(R.id.duty) TextView duty;
    /** 选择地址 */
    @BindView(R.id.linearlayout_area) LinearLayout linearlayoutArea;
    /** 地址 */
    @BindView(R.id.address) TextView address;
    /** 保存按钮 */
    @BindView(R.id.btn_save) Button btnSave;

//    /** 设置按钮 */
//    @BindView(R.id.head_right) Button headRight;
//    /** 编辑按钮 */
//    @BindView(R.id.click_update) TextView clickUpdate;

    /** 用户编号 */
    String userId;
    /** 登录标识 */
    String token;

    private PostionSelectPopupWindow popupWindow;


    @Override
    public int getLayoutId() {
        return R.layout.activity_user_personinfo;
    }

    @Override
    public Object newP() {
        return null;
    }

    @Override
    public void initData(Bundle bundle) {
        //获取用户缓存的userid 和 token
        userId = App.getSharedPref().getString(Constant.USERID, "");
        token = App.getSharedPref().getString(Constant.TOKEN, "");
        //设置标题
        headTitle.setText("个人信息");
        ivSelector.setSelected(false);
//        headRight.setVisibility(View.VISIBLE);
//        headRight.setText("编辑");
//        if (!isNotEmpty(getText(email))) {
//            clickUpdate.setVisibility(View.VISIBLE);
//            clickUpdate.setText("去填写");
//        }
        popupWindow = new PostionSelectPopupWindow(this);
        popupWindow.setCallBack(new PostionSelectPopupWindow.CallBack() {
            @Override
            public void onSelectChange(String name) {
                address.setText(name);
            }
        });

    }

    @Override
    public void initEvents() {
//        headRight.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if ("完成".equals(headRight.getText().toString())) {
//                    //TODO 保存邮箱信息
//                    showToast("保存该信息");
//                    //然后设置为不可编辑状态
//                    email.setFocusable(false);
//                    email.setFocusableInTouchMode(false);
//                    headRight.setText("编辑");
//                    clickUpdate.setVisibility(View.GONE);
//                } else if ("编辑".equals(headRight.getText().toString())) {
//                    clickUpdate.setVisibility(View.VISIBLE);
//                    clickUpdate.setText("修改");
//
//                }
//
//            }
//        });

//        clickUpdate.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                headRight.setText("完成");
//                email.setSelection(email.getText().toString().trim().length());//将光标移至文字末尾
//                email.setFocusableInTouchMode(true);
//                email.setFocusable(true);
//                email.requestFocus();
//            }
//        });
        headBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        limitTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //如果未勾选永久有效 就可以选择时间
                if (!ivSelector.isSelected()) {

                    timeDialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                            limitTime.setText(year + "/" + (monthOfYear + 1) + "/" + dayOfMonth);
                        }
                    }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                    timeDialog.show();
                }

            }
        });

        ivSelector.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //如果未勾选永久有效 就可以选择时间
                if (ivSelector.isSelected()) {
                    ivSelector.setSelected(false);
                    limitTime.setHint("请选择");
                } else {
                    ivSelector.setSelected(true);
                    limitTime.setHint("");
                    limitTime.setText("");
                }
            }
        });


        linearlayout_duty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                showToast("选择职业");
                final List<String> list = new ArrayList<>();
                list.add("金融从业者");
                list.add("其他");
                //TODO 有BUG
                PopShow popShow = new PopShow(context, linearlayout_duty);
                popShow.showText(list);
                popShow.setOnClickPop(new PopShow.OnClickPop() {
                    @Override
                    public void setRange(int position) {
                        duty.setText(list.get(position));
                    }
                });
            }
        });

        address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //关闭当前输入框
                KeyBoardUtils.closeKeybord(PersonInfoActivity.this);
                //显示窗口
                popupWindow.showAtLocation(linearlayoutArea, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0); //设置layout在PopupWindow中显示的位置
            }
        });


    }

}
