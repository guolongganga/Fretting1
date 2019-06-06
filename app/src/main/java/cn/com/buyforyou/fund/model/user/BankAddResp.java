package cn.com.buyforyou.fund.model.user;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

import cn.com.buyforyou.fund.model.BaseResp;

/**
 * Created by guolonggang on 2018/12/12.
 * Description: 添加银行卡以及银行卡列表
 */

public class BankAddResp extends BaseResp<ArrayList<BankAddResp>> implements Parcelable {
    /** 银行卡logo */
    private String bankLogo;
    /** 银行名称 */
    private String bankName;
    /** 银行卡尾号 */
    private String bankNoTail;
    /** 交易账号 */
    private String trade_acco;
    /** 是否能够更改银行卡  0代表不能 1代表能 */
//    private String isCanChangeBankNo;
    private String limit_per_day;
    private String limit_per_month;
    private String limit_per_payment;

    public String getBankLogo() {
        return bankLogo;
    }

    public void setBankLogo(String bankLogo) {
        this.bankLogo = bankLogo;
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

//    public String getTa_acco() {
//        return ta_acco;
//    }
//
//    public void setTa_acco(String ta_acco) {
//        this.ta_acco = ta_acco;
//    }

    public String getTrade_acco() {
        return trade_acco;
    }

    public void setTrade_acco(String trade_acco) {
        this.trade_acco = trade_acco;
    }


//    public String getIsCanChangeBankNo() {
//        return isCanChangeBankNo;
//    }
//
//    public void setIsCanChangeBankNo(String isCanChangeBankNo) {
//        this.isCanChangeBankNo = isCanChangeBankNo;
//    }

    public String getLimit_per_day() {
        return limit_per_day;
    }

    public void setLimit_per_day(String limit_per_day) {
        this.limit_per_day = limit_per_day;
    }

    public String getLimit_per_month() {
        return limit_per_month;
    }

    public void setLimit_per_month(String limit_per_month) {
        this.limit_per_month = limit_per_month;
    }

    public String getLimit_per_payment() {
        return limit_per_payment;
    }

    public void setLimit_per_payment(String limit_per_payment) {
        this.limit_per_payment = limit_per_payment;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.bankLogo);
        dest.writeString(this.bankName);
        dest.writeString(this.bankNoTail);
        dest.writeString(this.trade_acco);
        dest.writeString(this.limit_per_day);
        dest.writeString(this.limit_per_month);
        dest.writeString(this.limit_per_payment);
    }

    public BankAddResp() {
    }

    protected BankAddResp(Parcel in) {
        this.bankLogo = in.readString();
        this.bankName = in.readString();
        this.bankNoTail = in.readString();
        this.trade_acco = in.readString();
        this.limit_per_day = in.readString();
        this.limit_per_month = in.readString();
        this.limit_per_payment = in.readString();
    }

    public static final Creator<BankAddResp> CREATOR = new Creator<BankAddResp>() {
        @Override
        public BankAddResp createFromParcel(Parcel source) {
            return new BankAddResp(source);
        }

        @Override
        public BankAddResp[] newArray(int size) {
            return new BankAddResp[size];
        }
    };
}
