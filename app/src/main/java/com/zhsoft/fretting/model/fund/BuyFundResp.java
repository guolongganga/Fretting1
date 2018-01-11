package com.zhsoft.fretting.model.fund;

import android.os.Parcel;
import android.os.Parcelable;

import com.zhsoft.fretting.model.BaseResp;

/**
 * 作者：sunnyzeng on 2018/1/11 18:14
 * 描述：
 */

public class BuyFundResp extends BaseResp<BuyFundResp> implements Parcelable {
    private String name;
    private String logo;
    private String fundRisk;
    private String info;

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

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
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
        dest.writeString(this.info);
    }

    public BuyFundResp() {
    }

    protected BuyFundResp(Parcel in) {
        this.name = in.readString();
        this.logo = in.readString();
        this.fundRisk = in.readString();
        this.info = in.readString();
    }

    public static final Parcelable.Creator<BuyFundResp> CREATOR = new Parcelable.Creator<BuyFundResp>() {
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
