package com.zhsoft.fretting.model.user;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 作者：sunnyzeng on 2017/12/19 10:21
 * 描述：银行列表
 */

public class BankResp implements Parcelable {

    private String bankImage;
    private String bankName;
    private String bankLimit;

    public BankResp(String bankImage, String bankName, String bankLimit) {
        this.bankImage = bankImage;
        this.bankName = bankName;
        this.bankLimit = bankLimit;
    }

    public String getBankImage() {
        return bankImage;
    }

    public void setBankImage(String bankImage) {
        this.bankImage = bankImage;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankLimit() {
        return bankLimit;
    }

    public void setBankLimit(String bankLimit) {
        this.bankLimit = bankLimit;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.bankImage);
        dest.writeString(this.bankName);
        dest.writeString(this.bankLimit);
    }

    protected BankResp(Parcel in) {
        this.bankImage = in.readString();
        this.bankName = in.readString();
        this.bankLimit = in.readString();
    }

    public static final Parcelable.Creator<BankResp> CREATOR = new Parcelable.Creator<BankResp>() {
        @Override
        public BankResp createFromParcel(Parcel source) {
            return new BankResp(source);
        }

        @Override
        public BankResp[] newArray(int size) {
            return new BankResp[size];
        }
    };
}
