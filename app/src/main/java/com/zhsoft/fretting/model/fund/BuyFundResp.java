package com.zhsoft.fretting.model.fund;

import android.os.Parcel;
import android.os.Parcelable;

import com.zhsoft.fretting.model.BaseResp;

/**
 * 作者：sunnyzeng on 2018/1/11 18:14
 * 描述：
 * "name":"中国银行(3833)",
 * <p>
 * "info1":"15点前买入将按照当净值确认份额",
 * "fundRisk":"基金风险高于您的风险承受能力",
 * "canbuy":"OK",
 * "info":"单笔上限50万，单日限额200万",
 * "info2":"12月18日可查看份额信息，12月19号可查看收益"},
 * "message":"成功","status":200}
 */

public class BuyFundResp extends BaseResp<BuyFundResp> implements Parcelable {
    /** 银行名称及尾号 */
    private String name;
    /** 银行logo */
    private String logo;
    /** 是否能够购买 */
    private String fundRisk;
    /** 风险等级 */
    private String canBuy;
    /** 银行限额 */
    private String info;
    /** 提示1 */
    private String info1;
    /** 提示2 */
    private String info2;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getFundRisk() {
        return fundRisk;
    }

    public void setFundRisk(String fundRisk) {
        this.fundRisk = fundRisk;
    }

    public String getCanBuy() {
        return canBuy;
    }

    public void setCanBuy(String canBuy) {
        this.canBuy = canBuy;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getInfo1() {
        return info1;
    }

    public void setInfo1(String info1) {
        this.info1 = info1;
    }

    public String getInfo2() {
        return info2;
    }

    public void setInfo2(String info2) {
        this.info2 = info2;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.logo);
        dest.writeString(this.fundRisk);
        dest.writeString(this.canBuy);
        dest.writeString(this.info);
        dest.writeString(this.info1);
        dest.writeString(this.info2);
    }

    public BuyFundResp() {
    }

    protected BuyFundResp(Parcel in) {
        this.name = in.readString();
        this.logo = in.readString();
        this.fundRisk = in.readString();
        this.canBuy = in.readString();
        this.info = in.readString();
        this.info1 = in.readString();
        this.info2 = in.readString();
    }

    public static final Creator<BuyFundResp> CREATOR = new Creator<BuyFundResp>() {
        @Override
        public BuyFundResp createFromParcel(Parcel source) {
            return new BuyFundResp(source);
        }

        @Override
        public BuyFundResp[] newArray(int size) {
            return new BuyFundResp[size];
        }
    };
}
