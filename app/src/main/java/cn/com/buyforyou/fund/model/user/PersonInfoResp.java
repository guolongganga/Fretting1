package cn.com.buyforyou.fund.model.user;

import cn.com.buyforyou.fund.model.BaseResp;

/**
 * 作者：sunnyzeng on 2017/12/28 15:22
 * 描述：
 */

public class PersonInfoResp extends BaseResp<PersonInfoResp> {
    /** 姓名 */
    private String userName;
    /** 性别 */
    private String sex;
    /** 身份证号 */
    private String certNo;
    /** 身份证有效期 */
    private String id_enddate;
    /** 国籍 */
    private String fund_nationality;
    /** 职业 */
    private OccupationResp occupation;
    /** 联系地址 */
    private String address;
    /** 详细地址 */
    private String detaile_address;
    /** 基金账户 */
    private String ta_acco;
    /** 邮箱 */
    private String email;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getCertNo() {
        return certNo;
    }

    public void setCertNo(String certNo) {
        this.certNo = certNo;
    }

    public String getId_enddate() {
        return id_enddate;
    }

    public void setId_enddate(String id_enddate) {
        this.id_enddate = id_enddate;
    }

    public String getFund_nationality() {
        return fund_nationality;
    }

    public void setFund_nationality(String fund_nationality) {
        this.fund_nationality = fund_nationality;
    }

    public OccupationResp getOccupation() {
        return occupation;
    }

    public void setOccupation(OccupationResp occupation) {
        this.occupation = occupation;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDetaile_address() {
        return detaile_address;
    }

    public void setDetaile_address(String detaile_address) {
        this.detaile_address = detaile_address;
    }

    public String getTa_acco() {
        return ta_acco;
    }

    public void setTa_acco(String ta_acco) {
        this.ta_acco = ta_acco;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
