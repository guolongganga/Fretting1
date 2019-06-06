package cn.com.buyforyou.fund.model.fund;


import android.os.Parcel;
import android.os.Parcelable;

import java.math.BigDecimal;
import java.util.ArrayList;

import cn.com.buyforyou.fund.model.BaseResp;
import cn.com.buyforyou.fund.model.user.BankResp;

/**
 * Created by guolonggang on 2018/12/17.
 * Description:
 */

public class BankCard extends BaseResp<ArrayList<BankCard>>  implements Parcelable {

    /** 银行名称 */
    private String name;
    /**银行尾号*/
    private String bankNoTail;
    /** 银行logo */
    private String logo;

    /** 银行限额 */
    private String info;
    /***
     * 显示默认得银行卡 (0)
     */

    private String select;

    /**
     * 原始申购费率
     * @return
     */

    private String source_rate;

    /**交易账号/
     *
     * @return
     */
    private String trade_acco;
    /**
     * 折扣后申购费率
     */

    private String curr_rate;


    public String getSource_rate() {
        return source_rate;
    }

    public void setSource_rate(String source_rate) {
        this.source_rate = source_rate;
    }

    public String getCurr_rate() {
        return curr_rate;
    }

    public void setCurr_rate(String curr_rate) {
        this.curr_rate = curr_rate;
    }

    public String getTrade_acco() {
        return trade_acco;
    }

    public void setTrade_acco(String trade_acco) {
        this.trade_acco = trade_acco;
    }



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

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getSelect() {
        return select;
    }

    public void setSelect(String select) {
        this.select = select;
    }

    public String getBankNoTail() {
        return bankNoTail;
    }

    public void setBankNoTail(String bankNoTail) {
        this.bankNoTail = bankNoTail;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.logo);
        dest.writeString(this.select);
        dest.writeString(this.info);
        dest.writeString(this.source_rate);
        dest.writeString(this.trade_acco);
        dest.writeString(this.curr_rate);
        dest.writeString(this.bankNoTail);

    }

    public BankCard() {
    }

    protected BankCard(Parcel in) {
        this.name = in.readString();
        this.logo = in.readString();
        this.select = in.readString();
        this.info = in.readString();
        this.source_rate=in.readString();
        this.trade_acco=in.readString();
        this.curr_rate=in.readString();
        this.bankNoTail=in.readString();

    }

    public static final Creator<BankCard> CREATOR = new Creator<BankCard>() {
        @Override
        public BankCard createFromParcel(Parcel source) {
            return new BankCard(source);
        }

        @Override
        public BankCard[] newArray(int size) {
            return new BankCard[size];
        }
    };
}
