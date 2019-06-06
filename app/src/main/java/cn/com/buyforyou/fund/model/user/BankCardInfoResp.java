package cn.com.buyforyou.fund.model.user;

import android.os.Parcel;
import android.os.Parcelable;

import java.math.BigDecimal;
import java.util.ArrayList;

import cn.com.buyforyou.fund.model.BaseResp;
import cn.com.buyforyou.fund.model.fund.InvestResp;

/**
 * Created by guolonggang on 2018/12/20.
 * Description:
 */

public class BankCardInfoResp extends BaseResp<ArrayList<BankCardInfoResp>> implements Parcelable {

    //银行logo
    private String bankLogo;
    //银行名字
    private String bankName;
    //银行编号
    private String bankNo;
    //银行卡尾号
    private String bankNoTail;
    //扣款星期几
    private String exchWeek;
    //扣款日期
    private  String exchdate;
    private String first_trade_month;
    //选择默认的银行卡
    private String select;



    //是否切换银行卡
    private String isCanChangeBankNo;
    private String is_support_fast_mode;
    private String limit_per_day;


    //
    private String limit_per_month;
    //银行限额
    private String limit_per_payment;
    private String ta_acco;
    //交易账号
    private String trade_acco;


    public String getLimit_per_day() {
        return limit_per_day;
    }

    public void setLimit_per_day(String limit_per_day) {
        this.limit_per_day = limit_per_day;
    }

    public String getFirst_trade_month() {
        return first_trade_month;
    }

    public void setFirst_trade_month(String first_trade_month) {
        this.first_trade_month = first_trade_month;
    }



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

    public String getBankNo() {
        return bankNo;
    }

    public void setBankNo(String bankNo) {
        this.bankNo = bankNo;
    }

    public String getBankNoTail() {
        return bankNoTail;
    }

    public void setBankNoTail(String bankNoTail) {
        this.bankNoTail = bankNoTail;
    }

    public String getExchWeek() {
        return exchWeek;
    }

    public void setExchWeek(String exchWeek) {
        this.exchWeek = exchWeek;
    }

    public String getExchdate() {
        return exchdate;
    }

    public void setExchdate(String exchdate) {
        this.exchdate = exchdate;
    }

    public String getIsCanChangeBankNo() {
        return isCanChangeBankNo;
    }

    public void setIsCanChangeBankNo(String isCanChangeBankNo) {
        this.isCanChangeBankNo = isCanChangeBankNo;
    }

    public String getIs_support_fast_mode() {
        return is_support_fast_mode;
    }

    public void setIs_support_fast_mode(String is_support_fast_mode) {
        this.is_support_fast_mode = is_support_fast_mode;
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
    public String getSelect() {
        return select;
    }

    public void setSelect(String select) {
        this.select = select;
    }

    public String getTa_acco() {
        return ta_acco;
    }

    public void setTa_acco(String ta_acco) {
        this.ta_acco = ta_acco;
    }

    public String getTrade_acco() {
        return trade_acco;
    }

    public void setTrade_acco(String trade_acco) {
        this.trade_acco = trade_acco;
    }

    public static Creator<BankCardInfoResp> getCREATOR() {
        return CREATOR;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
      dest.writeString(this.bankLogo);
      dest.writeString(this.bankName);
      dest.writeString(this.bankNo);
      dest.writeString(this.bankNoTail);
      dest.writeString(this.exchdate);
      dest.writeString(this.exchWeek);
      dest.writeString(this.first_trade_month);
      dest.writeString(this.isCanChangeBankNo);
      dest.writeString(this.is_support_fast_mode);
      dest.writeString(this.limit_per_day);
      dest.writeString(this.limit_per_month);
      dest.writeString(this.limit_per_payment);
        dest.writeString(this.select);
      dest.writeString(this.ta_acco);
      dest.writeString(this.trade_acco);

    }


    public BankCardInfoResp() {
    }

    protected BankCardInfoResp(Parcel in) {
        this.bankLogo = in.readString();
        this.bankName = in.readString();
        this.bankNo = in.readString();
        this.bankNoTail = in.readString();
        this.exchdate = in.readString();
        this.exchWeek = in.readString();

        this.first_trade_month = in.readString();
        this.isCanChangeBankNo = in.readString();
        this.is_support_fast_mode = in.readString();
        this.limit_per_day = in.readString();
        this.limit_per_month = in.readString();
        this.limit_per_payment = in.readString();
        this.select=in.readString();
        this.ta_acco = in.readString();
        this.trade_acco = in.readString();


    }

    public static final Creator<BankCardInfoResp> CREATOR = new Creator<BankCardInfoResp>() {
        @Override
        public BankCardInfoResp createFromParcel(Parcel source) {
            return new BankCardInfoResp(source);
        }

        @Override
        public BankCardInfoResp[] newArray(int size) {
            return new BankCardInfoResp[size];
        }
    };
}
