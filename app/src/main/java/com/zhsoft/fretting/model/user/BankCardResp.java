package com.zhsoft.fretting.model.user;

import android.os.Parcel;
import android.os.Parcelable;

import com.zhsoft.fretting.model.BaseResp;

/**
 * 作者：sunnyzeng on 2017/12/22 15:53
 * 描述：我的银行卡
 */

public class BankCardResp extends BaseResp<BankCardResp> implements Parcelable {
    /** 银行卡logo */
    private String logoUrl;
    /** 银行名称 */
    private String bankName;
    /** 银行卡尾号 */
    private String bankNoTail;
    /** 交易账号 */
    private String ta_acco;
    /** 是否能够更改银行卡  0代表不能 1代表能 */
    private String isCanChangeBankNo;

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

    public String getBankNoTail() {
        return bankNoTail;
    }

    public void setBankNoTail(String bankNoTail) {
        this.bankNoTail = bankNoTail;
    }

    public String getTa_acco() {
        return ta_acco;
    }

    public void setTa_acco(String ta_acco) {
        this.ta_acco = ta_acco;
    }

    public String getIsCanChangeBankNo() {
        return isCanChangeBankNo;
    }

    public void setIsCanChangeBankNo(String isCanChangeBankNo) {
        this.isCanChangeBankNo = isCanChangeBankNo;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.logoUrl);
        dest.writeString(this.bankName);
        dest.writeString(this.bankNoTail);
        dest.writeString(this.ta_acco);
        dest.writeString(this.isCanChangeBankNo);
    }

    public BankCardResp() {
    }

    protected BankCardResp(Parcel in) {
        this.logoUrl = in.readString();
        this.bankName = in.readString();
        this.bankNoTail = in.readString();
        this.ta_acco = in.readString();
        this.isCanChangeBankNo = in.readString();
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
