package cn.com.buyforyou.fund.event;

/**
 * Created by guolonggang on 2018/12/21.
 * Description:
 */

public class InverstBankCardEvent {
    //银行图标
    private String bankLogo;
    //银行名称
    private String  bankName;
    //银行限额
    private String limitPerPay;
    private String lmitPperDay;
    //银行尾号
    private String bankNoTail;
    //交易账号
    private String trade_acco;

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

    public String getLimitPerPay() {
        return limitPerPay;
    }

    public void setLimitPerPay(String limitPerPay) {
        this.limitPerPay = limitPerPay;
    }

    public String getLmitPperDay() {
        return lmitPperDay;
    }

    public void setLmitPperDay(String lmitPperDay) {
        this.lmitPperDay = lmitPperDay;
    }

    public String getBankNoTail() {
        return bankNoTail;
    }

    public void setBankNoTail(String bankNoTail) {
        this.bankNoTail = bankNoTail;
    }

    public String getTrade_acco() {
        return trade_acco;
    }

    public void setTrade_acco(String trade_acco) {
        this.trade_acco = trade_acco;
    }

    public InverstBankCardEvent(String bankLogo, String bankName, String limitPerPay, String lmitPperDay, String bankNoTail, String trade_acco) {
        this.bankLogo = bankLogo;
        this.bankName = bankName;
        this.limitPerPay = limitPerPay;
        this.lmitPperDay = lmitPperDay;
        this.bankNoTail = bankNoTail;
        this.trade_acco = trade_acco;
    }
}
