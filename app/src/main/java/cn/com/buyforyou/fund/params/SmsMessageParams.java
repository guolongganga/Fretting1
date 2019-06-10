package cn.com.buyforyou.fund.params;

import cn.com.buyforyou.fund.model.user.BankResp;

/**
 * Created by guolonggang on 2018/12/10.
 * Description:
 * userId,token,strUsername, strIdentity, email, bankResp, strBankNumber, strPhone, strPwd
 */

public class SmsMessageParams  extends CommonReqData<SmsMessageParams>{


    private String userName;
    private String certNo;
    private String email;
    private BankResp selectBank;
    private String bankAccount;
    private String mobile;
    private String tradePassword;
    private String mobileAuthcode;

    private String originalAppno; //原申请单号
    private String otherSerial; //对方流水号

    public String getOriginalAppno() {
        return originalAppno;
    }

    public void setOriginalAppno(String originalAppno) {
        this.originalAppno = originalAppno;
    }

    public String getOtherSerial() {
        return otherSerial;
    }

    public void setOtherSerial(String otherSerial) {
        this.otherSerial = otherSerial;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCertNo() {
        return certNo;
    }

    public void setCertNo(String certNo) {
        this.certNo = certNo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public BankResp getSelectBank() {
        return selectBank;
    }

    public void setSelectBank(BankResp selectBank) {
        this.selectBank = selectBank;
    }

    public String getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getTradePassword() {
        return tradePassword;
    }

    public void setTradePassword(String tradePassword) {
        this.tradePassword = tradePassword;
    }

    public String getMobileAuthcode() {
        return mobileAuthcode;
    }

    public void setMobileAuthcode(String mobileAuthcode) {
        this.mobileAuthcode = mobileAuthcode;
    }
}
