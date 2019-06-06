package cn.com.buyforyou.fund.event;

/**
 * Created by guolonggang on 2018/12/19.
 * Description:
 */

public class ChangeBankCardMessageEvent {
    //银行图标
     private  String logo;
     //银行名字
    private String name;
    //银行信息
    private String info;
    //银行交易账号
    private  String trade_acco;


    public String getTrade_acco() {
        return trade_acco;
    }

    public void setTrade_acco(String trade_acco) {
        this.trade_acco = trade_acco;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }



    public ChangeBankCardMessageEvent(String logo, String name, String info, String trade_acco) {
        this.logo = logo;
        this.name = name;
        this.info = info;
        this.trade_acco = trade_acco;

    }
}
