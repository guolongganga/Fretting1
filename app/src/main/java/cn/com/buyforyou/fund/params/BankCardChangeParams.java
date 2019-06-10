package cn.com.buyforyou.fund.params;

/**
 * 作者：sunnyzeng on 2018/3/28 09:48
 * 描述：
 */

public class BankCardChangeParams {
    //交易密码
    private String trade_password;
    //交易账号
    private String trade_acco;

    public String getTrade_acco() {
        return trade_acco;
    }

    public void setTrade_acco(String trade_acco) {
        this.trade_acco = trade_acco;
    }

    public String getTrade_password() {
        return trade_password;
    }

    public void setTrade_password(String trade_password) {
        this.trade_password = trade_password;
    }
}
