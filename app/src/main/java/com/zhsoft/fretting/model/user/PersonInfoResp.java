package com.zhsoft.fretting.model.user;

import com.zhsoft.fretting.model.BaseResp;

/**
 * 作者：sunnyzeng on 2017/12/28 15:22
 * 描述：
 */

public class PersonInfoResp extends BaseResp<PersonInfoResp> {
    /** 姓名 */
    private String name;
    /** 性别 */
    private String gender;
    /** 身份证号 */
    private String certNo;
    /** 身份证有效期 */
    private String limitTime;
    /** 是否永久有效 */
    private String isPermanent;
    /** 国籍 */
    private String nationality;
    /** 职业 */
    private String occupation;
    /** 联系地址 */
    private String address;
    /** 基金账户 */
    private String fundAccount;
    /** 邮箱 */
    private String email;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getCertNo() {
        return certNo;
    }

    public void setCertNo(String certNo) {
        this.certNo = certNo;
    }

    public String getLimitTime() {
        return limitTime;
    }

    public void setLimitTime(String limitTime) {
        this.limitTime = limitTime;
    }

    public String getIsPermanent() {
        return isPermanent;
    }

    public void setIsPermanent(String isPermanent) {
        this.isPermanent = isPermanent;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getFundAccount() {
        return fundAccount;
    }

    public void setFundAccount(String fundAccount) {
        this.fundAccount = fundAccount;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
