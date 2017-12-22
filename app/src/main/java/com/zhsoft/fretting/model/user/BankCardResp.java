package com.zhsoft.fretting.model.user;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 作者：sunnyzeng on 2017/12/22 15:53
 * 描述：我的银行卡
 */

public class BankCardResp implements Parcelable {
    /** 银行卡logo */
    private String logoUrl;
    /** 银行名称 */
    private String bankName;
    /** 银行卡尾号 */
    private String lastNumber;
    /** 交易账号 */
    private String dealAcccount;
    /** 是否能够更改银行卡  0代表不能 1代表能 */
    private String isCanChange;

    public String getIsCanChange() {
        return isCanChange;
    }

    public void setIsCanChange(String isCanChange) {
        this.isCanChange = isCanChange;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getLastNumber() {
        return lastNumber;
    }

    public void setLastNumber(String lastNumber) {
        this.lastNumber = lastNumber;
    }

    public String getDealAcccount() {
        return dealAcccount;
    }

    public void setDealAcccount(String dealAcccount) {
        this.dealAcccount = dealAcccount;
    }

    public BankCardResp(String logoUrl, String bankName, String lastNumber, String dealAcccount, String isCanChange) {
        this.logoUrl = logoUrl;
        this.bankName = bankName;
        this.lastNumber = lastNumber;
        this.dealAcccount = dealAcccount;
        this.isCanChange = isCanChange;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.logoUrl);
        dest.writeString(this.bankName);
        dest.writeString(this.lastNumber);
        dest.writeString(this.dealAcccount);
        dest.writeString(this.isCanChange);
    }

    protected BankCardResp(Parcel in) {
        this.logoUrl = in.readString();
        this.bankName = in.readString();
        this.lastNumber = in.readString();
        this.dealAcccount = in.readString();
        this.isCanChange = in.readString();
    }

    public static final Creator<BankCardResp> CREATOR = new Creator<BankCardResp>() {
        @Override
        public BankCardResp createFromParcel(Parcel source) {
            return new BankCardResp(source);
        }

        @Override
        public BankCardResp[] newArray(int size) {
            return new BankCardResp[size];
        }
    };
}
