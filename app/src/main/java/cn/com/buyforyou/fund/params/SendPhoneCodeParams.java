package cn.com.buyforyou.fund.params;

import cn.com.buyforyou.fund.model.user.BankResp;
import cn.com.buyforyou.fund.model.user.OpenAccountResp;

/**
 * 作者：sunnyzeng on 2018/1/3 13:24
 * 描述：
 *
 * //(String phone,String bankAccout,String phoneCode,
 * BankResp selectBank,String originalAppno,String otherSerial, String token, String userId)
 */

public class SendPhoneCodeParams {
    private String mobile;
    private String bankAccout;
    private String phoneCode;
    private BankResp selectBank;
    private String originalAppno;
    private String otherSerial;
    private String trade_acco;
    private String bank_name;

    public String getBank_name() {
        return bank_name;
    }

    public void setBank_name(String bank_name) {
        this.bank_name = bank_name;
    }

    public String getTrade_acco() {
        return trade_acco;
    }

    public void setTrade_acco(String trade_acco) {
        this.trade_acco = trade_acco;
    }

    private OpenAccountResp data;
    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getBankAccout() {
        return bankAccout;
    }

    public void setBankAccout(String bankAccout) {
        this.bankAccout = bankAccout;
    }

    public String getPhoneCode() {
        return phoneCode;
    }

    public void setPhoneCode(String phoneCode) {
        this.phoneCode = phoneCode;
    }

    public BankResp getSelectBank() {
        return selectBank;
    }

    public void setSelectBank(BankResp selectBank) {
        this.selectBank = selectBank;
    }

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
}
