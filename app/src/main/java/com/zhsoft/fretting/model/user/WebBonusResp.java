package com.zhsoft.fretting.model.user;

import android.os.Parcel;
import android.os.Parcelable;

import com.zhsoft.fretting.model.BaseResp;

/**
 * 作者：sunnyzeng on 2018/1/25 10:48
 * 描述：从网页跳转分红详情
 *
 */

public class WebBonusResp extends BaseResp<WebBonusResp> implements Parcelable {
    private String autoBuy;
    private String autoBuyVal;
    private String forbidModiAutobuyFlag;
    private String fundcode;
    private String fundName;
    private String fundtype;
    private String melonmethod;
    private String sharetype;
    private String tradeacco;
    private String usableremainshare;

    public String getAutoBuy() {
        return autoBuy;
    }

    public void setAutoBuy(String autoBuy) {
        this.autoBuy = autoBuy;
    }

    public String getAutoBuyVal() {
        return autoBuyVal;
    }

    public void setAutoBuyVal(String autoBuyVal) {
        this.autoBuyVal = autoBuyVal;
    }

    public String getForbidModiAutobuyFlag() {
        return forbidModiAutobuyFlag;
    }

    public void setForbidModiAutobuyFlag(String forbidModiAutobuyFlag) {
        this.forbidModiAutobuyFlag = forbidModiAutobuyFlag;
    }

    public String getFundcode() {
        return fundcode;
    }

    public void setFundcode(String fundcode) {
        this.fundcode = fundcode;
    }

    public String getFundName() {
        return fundName;
    }

    public void setFundName(String fundName) {
        this.fundName = fundName;
    }

    public String getFundtype() {
        return fundtype;
    }

    public void setFundtype(String fundtype) {
        this.fundtype = fundtype;
    }

    public String getMelonmethod() {
        return melonmethod;
    }

    public void setMelonmethod(String melonmethod) {
        this.melonmethod = melonmethod;
    }

    public String getSharetype() {
        return sharetype;
    }

    public void setSharetype(String sharetype) {
        this.sharetype = sharetype;
    }

    public String getTradeacco() {
        return tradeacco;
    }

    public void setTradeacco(String tradeacco) {
        this.tradeacco = tradeacco;
    }

    public String getUsableremainshare() {
        return usableremainshare;
    }

    public void setUsableremainshare(String usableremainshare) {
        this.usableremainshare = usableremainshare;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.autoBuy);
        dest.writeString(this.autoBuyVal);
        dest.writeString(this.forbidModiAutobuyFlag);
        dest.writeString(this.fundcode);
        dest.writeString(this.fundName);
        dest.writeString(this.fundtype);
        dest.writeString(this.melonmethod);
        dest.writeString(this.sharetype);
        dest.writeString(this.tradeacco);
        dest.writeString(this.usableremainshare);
    }

    public WebBonusResp() {
    }

    protected WebBonusResp(Parcel in) {
        this.autoBuy = in.readString();
        this.autoBuyVal = in.readString();
        this.forbidModiAutobuyFlag = in.readString();
        this.fundcode = in.readString();
        this.fundName = in.readString();
        this.fundtype = in.readString();
        this.melonmethod = in.readString();
        this.sharetype = in.readString();
        this.tradeacco = in.readString();
        this.usableremainshare = in.readString();
    }

    public static final Creator<WebBonusResp> CREATOR = new Creator<WebBonusResp>() {
        @Override
        public WebBonusResp createFromParcel(Parcel source) {
            return new WebBonusResp(source);
        }

        @Override
        public WebBonusResp[] newArray(int size) {
            return new WebBonusResp[size];
        }
    };
}
