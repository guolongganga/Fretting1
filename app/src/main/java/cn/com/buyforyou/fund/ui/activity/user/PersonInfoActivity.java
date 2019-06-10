package cn.com.buyforyou.fund.ui.activity.user;

import android.app.AlertDialog;
import android.app.DatePickerDialog;

import android.content.DialogInterface;

import android.os.Bundle;

import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import cn.com.buyforyou.fund.App;
import cn.com.buyforyou.fund.R;
import cn.com.buyforyou.fund.constant.Constant;
import cn.com.buyforyou.fund.event.BankCardMessageEvent;

import cn.com.buyforyou.fund.event.ChangeBankCardEvent;
import cn.com.buyforyou.fund.event.ChangeUserMessageEvent;
import cn.com.buyforyou.fund.model.ApplyBaseInfo;
import cn.com.buyforyou.fund.model.user.OccupationResp;
import cn.com.buyforyou.fund.model.user.PersonInfoResp;
import cn.com.buyforyou.fund.model.user.UserInforResp;
import cn.com.buyforyou.fund.model.user.UserInformationResp;
import cn.com.buyforyou.fund.present.user.PersonInfoPresent;


import com.zhsoft.fretting.ui.widget.CustomDialog;
import com.zhsoft.fretting.ui.widget.FundBuyDialog;
import com.zhsoft.fretting.ui.widget.PopShow;


import cn.com.buyforyou.fund.utils.KeyBoardUtils;

import com.zhsoft.fretting.ui.widget.ChenJingET;
import com.zhsoft.fretting.ui.widget.SelectPopupWindow;
import com.zhsoft.fretting.ui.widget.wheel.MessageDialog;

import cn.com.buyforyou.fund.utils.RuntimeHelper;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import cn.droidlover.xdroidmvp.dialog.httploadingdialog.HttpLoadingDialog;
import cn.droidlover.xdroidmvp.mvp.XActivity;

/**
 * 作者：sunnyzeng on 2017/12/13 10:13
 * 描述：个人信息
 */

public class PersonInfoActivity extends XActivity<PersonInfoPresent> {
    //时间类
    private Calendar calendar = Calendar.getInstance();
    //时间选择器
    private DatePickerDialog timeDialog;

    /**
     * 返回按钮
     */
    @BindView(R.id.head_back)
    ImageButton headBack;
    /**
     * 标题
     */
    @BindView(R.id.head_title)
    TextView headTitle;
    /**
     * 用户名
     */
    @BindView(R.id.name)
    TextView name;
    /**
     * 身份证号
     */
    @BindView(R.id.identity)
    TextView identity;
    /**
     * 账户编号
     */
    @BindView(R.id.accountid)
    TextView accountid;
    /**
     * 邮箱
     */
    @BindView(R.id.email)
    EditText email;
    /**
     * 小红点
     */
    @BindView(R.id.identity_image)
    ImageView imageView;
    /**
     * 性别
     */
    @BindView(R.id.sex)
    TextView sex;
    /**
     * 身份证期限
     */
    @BindView(R.id.limit_time)
    TextView limitTime;
    /**
     * 选择 永久有效
     */
    @BindView(R.id.iv_selector)
    ImageView ivSelector;
    /**
     * 国籍
     */
    @BindView(R.id.nationality)
    TextView nationality;
    /**
     * 选择职业
     */
    @BindView(R.id.linearlayout_duty)
    LinearLayout linearlayout_duty;
    /**
     * 职业
     */
    @BindView(R.id.duty)
    TextView duty;
    /**
     * 居民税收选择框
     */
    @BindView(R.id.residents_tax)
    TextView residentsTax;
    /**
     * 居民涉税信息
     */
    @BindView(R.id.residents_tax_info)
    TextView residentsTaxInfo;
//    /** 选择地址 */
//    @BindView(R.id.linearlayout_area) LinearLayout linearlayoutArea;
//    /** 地址 */
//    @BindView(R.id.address) TextView address;
    /**
     * 详细地址
     */
    @BindView(R.id.address_detail)
    TextView addressDetail;
    /*
    *  上传身份证以及银行卡照片
    *
    */
    @BindView(R.id.relative_ll)
    RelativeLayout relativeLayout;

    /**
     * 是否存在他人实际控制关系
     */
//    @BindView(R.id.rg_control)
//    RadioGroup rgControl;
//
//    @BindView(R.id.radio_no)
//    RadioButton radioControl;
//
//    @BindView(R.id.radio_yes)
//    RadioButton radioButtonControl;
//
//    @BindView(R.id.ll_control)
//    LinearLayout llControl;
//
//    @BindView(R.id.control_name)
//    EditText editControl;
//
//    @BindView(R.id.tv_control)
//    TextView tvControl;
//
//
//    /**
//     * 交易的实际受益人
//     */
//    @BindView(R.id.rg_favoree)
//    RadioGroup rgFavoree;
//
//    @BindView(R.id.radio_favoree_no)
//    RadioButton radioFacoreeNo;
//
//
//    @BindView(R.id.radio_favoree_yes)
//    RadioButton radioFacoreeYes;
//
//    @BindView(R.id.ll_favoree)
//    LinearLayout llFavoree;
//
//    @BindView(R.id.favoree_name)
//    EditText editFavoree;
//
//    @BindView(R.id.tv_favoree)
//    TextView tvFavoree;
//
//
//
//    /**
//     * 是否有不良信用记录
//     */
//    @BindView(R.id.rg_recored)
//    RadioGroup  rgRecored;
//
//    @BindView(R.id.radio_recored_no)
//    RadioButton radioRecoredNo;
//
//    @BindView(R.id.radio_record_yes)
//    RadioButton radioRecordYes;
//
//    @BindView(R.id.ll_record)
//    LinearLayout llRecord;
//
//    @BindView(R.id.record_edit)
//    EditText editRecord;
//
//    @BindView(R.id.tv_recored)
//    TextView tvRecored;
    /**
     * 保存按钮
     */
    @BindView(R.id.btn_save)
    Button btnSave;


    /**
     * 点击保存弹框
     */
    private MessageDialog alertDialog;



    /**
     * 用户编号
     */
    private String userId;
    /**
     * 登录标识
     */
    private String token;
//    /** 地址弹出框 */
//    private CitySelectPopupWindow popupWindow;
    /**
     * 加载框
     */
    private HttpLoadingDialog httpLoadingDialog;
    /**
     * 职业集合
     */
    private ArrayList<OccupationResp> listOccupation;
    /**
     * 选择的职业
     */
    private OccupationResp selectOccupationResp;
    /**
     * 输入密码弹框
     */
    private FundBuyDialog fundBuyDialog;
    /**
     * 密码错误弹框
     */
    private CustomDialog errorDialog;
   private  int years;
   private int mouths;
   private int day;
//    private boolean flag=true;
//    private boolean flag1=true;
//    private boolean flag2=true;

    @Override
    public int getLayoutId() {
        return R.layout.activity_user_personinfo;
    }

    @Override
    public PersonInfoPresent newP() {
        return new PersonInfoPresent();
    }

    @Override
    public void initData(Bundle bundle) {
        //解决键盘弹出遮挡不滚动问题
        ChenJingET.assistActivity(context);
        //设置标题
        headTitle.setText("个人信息");
        ivSelector.setSelected(false);
        httpLoadingDialog = new HttpLoadingDialog(context);
      //  EventBus.getDefault().register(this);
        //获取用户缓存的userid 和 token
        userId = App.getSharedPref().getString(Constant.USERID, "");
        token = App.getSharedPref().getString(Constant.TOKEN, "");

        //请求职业集合接口
        httpLoadingDialog.visible();
        getP().getOccupation(token, userId);

        //请求个人信息接口
        getP().getUserInfo(token, userId);


    }

    @Override
    public void initEvents() {

        headBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

//        rgControl.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(RadioGroup group, int checkedId) {
//                flag=false;
//                switch (checkedId)
//                {
//                    case R.id.radio_no:
//                        radioControl.setChecked(true);
//                        llControl.setVisibility(View.GONE);
//                        break;
//                    case R.id.radio_yes:
//                        radioButtonControl.setChecked(true);
//                        llControl.setVisibility(View.VISIBLE);
//                        //exitControlName = editControl.getText().toString();
//                        break;
//                }
//            }
//        });
//        rgFavoree.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//
//            @Override
//            public void onCheckedChanged(RadioGroup group, int checkedId) {
//                flag1=false;
//                switch (checkedId)
//                {
//                    case R.id.radio_favoree_no:
//                        radioFacoreeNo.setChecked(true);
//                        llFavoree.setVisibility(View.GONE);
//                        break;
//                    case R.id.radio_favoree_yes:
//                        radioFacoreeYes.setChecked(true);
//                        llFavoree.setVisibility(View.VISIBLE);
//                        break;
//                }
//            }
//        });
//        rgRecored.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(RadioGroup group, int checkedId) {
//                flag2=false;
//                switch (checkedId)
//                {
//                    case R.id.radio_recored_no:
//                        radioRecoredNo.setChecked(true);
//                        llRecord.setVisibility(View.GONE);
//                        break;
//                    case R.id.radio_record_yes:
//                        radioRecordYes.setChecked(true);
//                        llRecord.setVisibility(View.VISIBLE);
//                        break;
//                }
//            }
//        });

        limitTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //如果未勾选永久有效 就可以选择时间
                if (!ivSelector.isSelected()) {
                    timeDialog = new DatePickerDialog(context,AlertDialog.THEME_HOLO_LIGHT, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                            years = calendar.get(Calendar.YEAR);
                            mouths = calendar.get(Calendar.MONTH);
                            day = calendar.get(Calendar.DAY_OF_MONTH);


                            //月份未满10 前面添加0
                            String strMonth;
                            int month = monthOfYear + 1;
                            if (month < 10) {
                                strMonth = "0" + month;
                            } else {
                                strMonth = "" + month;
                            }
                            //天数未满10 前面添加0
                            String strDay;
                            if (dayOfMonth < 10) {
                                strDay = "0" + dayOfMonth;
                            } else {
                                strDay = "" + dayOfMonth;
                            }
                            limitTime.setText(year + strMonth + strDay);
                        }
                    }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                    timeDialog.show();

                }


            }
        });
        /**
         * 上传身份证以及银行卡
         *
         */

        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(PhotoActivity.class);

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
                //关闭当前输入框
                KeyBoardUtils.closeKeybord(PersonInfoActivity.this);
                final List<String> list = new ArrayList<>();
                for (OccupationResp occupation : listOccupation) {
                    list.add(occupation.getCaption());
                }
                PopShow popShow = new PopShow(context, linearlayout_duty);
                popShow.showText(list);
                popShow.setOnClickPop(new PopShow.OnClickPop() {
                    @Override
                    public void setRange(int position) {
                        duty.setText(list.get(position));
                        selectOccupationResp = listOccupation.get(position);
                    }
                });
            }
        });



        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //表单验证通过才弹出pop
                if (noNetWork()) {
                    showNetWorkError();
                    return;
                }
                if (!ivSelector.isSelected() && !isNotEmpty(getText(limitTime))) {
                    showToast("身份证有效期不能为空");
                    return;
                }
                if (!isNotEmpty(getText(duty))) {
                    showToast("职业不能为空");
                    return;
                }
//                if (!isNotEmpty(getText(address))) {
//                    showToast("联系地址不能为空");
//                    return;
//                }
                if (!isNotEmpty(getText(addressDetail))) {
                    showToast("详细地址不能为空");
                    return;
                }
                if (!isNotEmpty(getText(email))) {
                    showToast("邮箱不能为空");
                    return;
                }
                if((getText(residentsTax)).equals("请选择"))
                {
                    showToast("请选择居民税收类型");
                    return;
                }
//                if(flag)
//                {
//                    showToast("请选择是否存在他人实际控制关系");
//                    return;
//                }
//                else
//
//                {
//                    if(radioButtonControl.isChecked())
//                    {
//                        if(!isNotEmpty(getText(editControl)))
//                        {
//                            showToast("实际控制人姓名不能为空");
//                            return;
//                        }
//                    }
//
//
//                }
//                if(flag1)
//                {
//                    showToast("请选择交易的实际受益人");
//                    return;
//                }
//                else
//                {
//                    if(radioFacoreeYes.isChecked())
//                    {
//                        if(!isNotEmpty(getText(editFavoree)))
//                        {
//                            showToast("交易的实际受益人姓名不能为空");
//                            return;
//                        }
//                    }
//                }
//                if(flag2)
//                {
//                    showToast("请选择是否有不良诚信记录");
//                    return;
//                }
//                else
//                {
//                    if(radioRecordYes.isChecked())
//                    {
//
//                        if(!isNotEmpty(getText(editRecord)))
//                        {
//                            showToast("不良诚信记录不能为空");
//                            return;
//                        }
//
//                    }
//                }

                //身份证有限期
                final String id_enddate;
                if (ivSelector.isSelected()) {
                    id_enddate = "1";
                } else {
                    id_enddate = getText(limitTime);
                }
//                httpLoadingDialog.visible("保存中...");
//                alertDialog = new MessageDialog.Builder(context)
//                        .setMessage("本人保证提供的信息真实、准确、完整，知晓并确认若提供的信息不真实、不准确、不完整的，应该依法承担相应法律责任，基金销售机构将不承担由此导致的关于适当性不匹配的任何后果，且有权拒绝销售产品或提供服务。本人已知晓并确认提供信息发生重要变化、可能影响投资者分类的，应当及时进行更新并告知基金销售机构")
//                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialogInterface, int i) {
//                                alertDialog.dismiss();
//                            }
//                        }).setPositiveButton("确定", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialogInterface, int i) {
//                                alertDialog.dismiss();
//                                //弹出交易密码框
//                                fundBuyDialog = new FundBuyDialog
//                                        .Builder(context)
//                                        .setOnTextFinishListener(new FundBuyDialog.OnTextFinishListener() {
//                                            @Override
//                                            public void onFinish(String str) {
//                                            }
//                                        }).setPositiveButton("确定", new FundBuyDialog.OnPositiveButtonListener() {
//                                            @Override
//                                            public void onButtonClick(DialogInterface dialog, String str) {
//                                                //验证是否符合更换条件
//                                                dialog.dismiss();
//                                                httpLoadingDialog.visible("保存中...");
//                                                getP().changeMyInformation(token, userId, id_enddate, null, getText(addressDetail), getText(email), selectOccupationResp, str, currentItemTax);
//
//
//                                            }
//                                        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
//                                            @Override
//                                            public void onClick(DialogInterface dialog, int which) {
//                                                dialog.dismiss();
//                                            }
//                                        }).create();
//
//                                fundBuyDialog.show();
//
//
//                            }
//                        }).create();
//                alertDialog.show();
//
//
//
//
//
//            }
//        });
                //弹出交易密码框
                fundBuyDialog = new FundBuyDialog
                        .Builder(context)
                        .setOnTextFinishListener(new FundBuyDialog.OnTextFinishListener() {
                            @Override
                            public void onFinish(String str) {
                            }
                        }).setPositiveButton("确定", new FundBuyDialog.OnPositiveButtonListener() {
                            @Override
                            public void onButtonClick(DialogInterface dialog, String str) {
                                //验证是否符合更换条件
                                dialog.dismiss();
                                httpLoadingDialog.visible("保存中...");
                                getP().changeMyInformation(token, userId, id_enddate, null, getText(addressDetail), getText(email), selectOccupationResp, str, currentItemTax);


                            }
                        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).create();

                fundBuyDialog.show();


            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        //判断小红点接口
        getP().getUserMessage(token,userId);
    }

    private String getTime(Date date) {//可根据需要自行截取数据显示
        //"YYYY-MM-DD HH:MM:SS"        "yyyy-MM-dd"
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }
    /**
     * 判断小红点是否显示请求接口成功
     *
     */
    public void requestUserMessageSuccess(UserInformationResp informationResp)
    {
        httpLoadingDialog.dismiss();
        if(informationResp.getData()
                !=null) {
            String idCardF = informationResp.getData().getIdCardF();
            String idCardZ = informationResp.getData().getIdCardZ();
            String bankCardZ = informationResp.getData().getBankCardZ();
            String bankCardF = informationResp.getData().getBankCardF();
            if (idCardF == null || idCardZ == null || bankCardZ == null || bankCardF == null)
            {
                imageView.setVisibility(View.VISIBLE);
            }

            else if(idCardF!=null&&idCardZ!=null&&bankCardZ!=null&&bankCardF!=null)
            {
                imageView.setVisibility(View.GONE);

            }
        }
        else
        {
              imageView.setVisibility(View.VISIBLE);

        }
       // EventBus.getDefault().post(new ChangeUserMessageEvent());
    }

    /**
     * 请求用户信息成功
     *
     * @param personInfoResp
     */
    public void requestUserInfoSuccess(PersonInfoResp personInfoResp) {
        httpLoadingDialog.dismiss();
        if (personInfoResp != null) {
            //姓名
            name.setText(personInfoResp.getUserName());
            //性别
            sex.setText(personInfoResp.getSex());
            //身份证号
            identity.setText(personInfoResp.getCertNo());
            //身份证有效期 如果是0说明永久有效
            if ("1".equals(personInfoResp.getId_enddate())) {
                ivSelector.setSelected(true);
                limitTime.setHint("");
                limitTime.setText("");
            } else {
                ivSelector.setSelected(false);
                if (isNotEmpty(personInfoResp.getId_enddate())) {
                    limitTime.setText(personInfoResp.getId_enddate());
                } else {
                    limitTime.setHint("请选择");
                }
            }
            //国籍
            nationality.setText(personInfoResp.getFund_nationality());
            //职业
            if (personInfoResp.getOccupation() != null) {
                selectOccupationResp = personInfoResp.getOccupation();
                duty.setText(personInfoResp.getOccupation().getCaption());
            } else {
                duty.setHint("请选择");
            }
//            //地址
//            if (isNotEmpty(personInfoResp.getAddress())) {
//                address.setText(personInfoResp.getAddress());
//            } else {
//                address.setHint("请选择");
//            }
            //详细地址
            addressDetail.setText(personInfoResp.getDetaile_address());
            //基金账号
            accountid.setText(personInfoResp.getTa_acco());
            //邮箱
            email.setText(personInfoResp.getEmail());

            //显示居民税收类型
            if (null != personInfoResp.getCust_flag_info()) {

                isShowTax(personInfoResp.getCust_flag_info().getKeyvalue(), personInfoResp.getCust_flag_info().getCaption());
            }

            initPopWindow(personInfoResp.getTaxflag());

            residentsTaxInfo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Bundle bundle = new Bundle();
                    bundle.putString("caption", residentsTax.getText().toString());
                    bundle.putString("keyvalue", currentItemTax);
                    startActivityDelay(ResidentsTaxInfoActivity.class, bundle);
                }
            });

        }
        EventBus.getDefault().post(new BankCardMessageEvent(personInfoResp.getUserName(), personInfoResp.getCertNo()));
    }

    /**
     * 是否显示居民涉税信息
     *
     * @param keyvalue key值
     * @param caption  内容
     */
    private void isShowTax(String keyvalue, String caption) {

        if (null == keyvalue || keyvalue.equals("null")) {
            keyvalue = "0";
        }
        if (null == caption || caption.equals("null")) {
            caption = "请选择";
        }

        residentsTax.setText(caption);
        currentItemTax = keyvalue + "";

        residentsTaxInfo.setVisibility(View.GONE);
        switch (keyvalue) {
            case "0":
                residentsTaxInfo.setVisibility(View.GONE);
                break;
            case "1":
                residentsTaxInfo.setVisibility(View.VISIBLE);
                break;
            case "2":
                residentsTaxInfo.setVisibility(View.VISIBLE);
                break;
        }


    }

    /**
     * 居民税收弹框
     */
    private SelectPopupWindow cyclePopupWindow;
    /**
     * 选择完的涉税信息
     */
    private String currentItemTax = "0";

    /**
     * 初始化弹出框
     *
     * @param list 居民税收类型集合
     */
    private void initPopWindow(List<OccupationResp> list) {

        if (null == list || list.size() == 0) {
            return;
        }

        ArrayList<ApplyBaseInfo> cycleList = new ArrayList<>();
        for (OccupationResp occupationResp : list) {
            cycleList.add(new ApplyBaseInfo(occupationResp.getKeyvalue(), occupationResp.getCaption()));
        }
        //初始化周期弹出框
        cyclePopupWindow = new SelectPopupWindow(this, cycleList);
        cyclePopupWindow.setCallBack(new SelectPopupWindow.CallBack() {
            @Override
            public void onSelectChange(int currentItem, String name) {
                isShowTax(currentItem + "", name);
            }
        });

        residentsTax.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //设置layout在PopupWindow中显示的位置
                cyclePopupWindow.showAtLocation(residentsTax, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
            }
        });

    }


    /**
     * 请求用户信息失败
     */
    public void requestUserInfoFail() {
        httpLoadingDialog.dismiss();
    }

    /**
     * 获取职业集合成功
     *
     * @param data
     */
    public void getOccupationSuccess(ArrayList<OccupationResp> data) {
        httpLoadingDialog.dismiss();
        listOccupation = data;
    }
//    /**
//     * 发送Eventbus事件
//     * 如果用户信息已补全  就隐藏小红点
//     *
//     * @param event
//     */
//    @Subscribe(threadMode = ThreadMode.POSTING)
//    public void changeMessageEvent(ChangeUserMessageEvent event) {
//
//        httpLoadingDialog.visible();
//        getP().getUserMessage(token,userId);
//
//}

    /**
     * 获取职业集合失败
     */
    public void getOccupationFail() {
        httpLoadingDialog.dismiss();
    }

    /**
     * 保存我的信息成功
     */
    public void changeMyInformationSuccess() {
        httpLoadingDialog.dismiss();
        showToast("保存成功");
    }

    /**
     * 保存我的信息失败
     */
    public void changeMyInformationFail() {
        httpLoadingDialog.dismiss();
    }

    /**
     * 更换银行卡 密码错误
     *
     * @param message
     */
    public void passwordError(String message) {
        httpLoadingDialog.dismiss();
        if (errorDialog == null) {
            errorDialog = new CustomDialog.Builder(context)
                    .setMessage(message)
                    .setNegativeButton("忘记密码", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            errorDialog.dismiss();
                            startActivity(FindPwdTradeFirstActivity.class);
                        }
                    }).setPositiveButton("再试一次", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            errorDialog.dismiss();
                            fundBuyDialog.show();
                        }
                    }).create();
        }
        errorDialog.show();
    }

    /**
     * 已经登出系统，请重新登录
     */
    public void areadyLogout() {
        httpLoadingDialog.dismiss();
//        EventBus.getDefault().post(new InvalidTokenEvent());
        //清除本地缓存，设置成未登录
        RuntimeHelper.getInstance().isInvalidToken();
        //跳转登录界面
        Bundle bundle = new Bundle();
        bundle.putString(Constant.SKIP_SIGN, Constant.SKIP_INDEX_ACTIVITY);
        startActivity(LoginActivity.class, bundle);
    }

    @Override
    protected void onDestroy() {
       // EventBus.getDefault().unregister(this);
        if (errorDialog != null) {
            errorDialog.dismiss();
            errorDialog = null;
        }
        if (fundBuyDialog != null) {
            fundBuyDialog.dismiss();
            fundBuyDialog = null;
        }
        super.onDestroy();
    }
}
