package cn.com.buyforyou.fund.event;

/**
 * Created by guolonggang on 2018/12/12.
 * Description:
 */

public class BankCardMessageEvent {
    //身份证号
    private String certNo;
    //姓名
    private String UserName;

    public BankCardMessageEvent(String certNo, String userName) {
        this.certNo = certNo;
        UserName = userName;
    }

    public String getCertNo() {
        return certNo;
    }

    public void setCertNo(String certNo) {
        this.certNo = certNo;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }
}
