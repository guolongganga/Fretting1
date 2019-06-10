package cn.com.buyforyou.fund.model.user;

import java.util.Date;

import cn.com.buyforyou.fund.model.BaseResp;

/**
 * Created by guolonggang on 2019/4/26.
 * Description:
 */

public class UserInforResp extends BaseResp<UserInforResp> {
     private Long id;
    private String userId;
    //身份证正面
    private String idCardZ;
    //身份证反面
    private String idCardF;
    //银行卡正面
    private String bankCardZ;
    //银行卡反面
    private String bankCardF;
    private Date uploadTime;
    private Date createTime;
    private String delFlag;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getIdCardZ() {
        return idCardZ;
    }

    public void setIdCardZ(String idCardZ) {
        this.idCardZ = idCardZ;
    }

    public String getIdCardF() {
        return idCardF;
    }

    public void setIdCardF(String idCardF) {
        this.idCardF = idCardF;
    }

    public String getBankCardZ() {
        return bankCardZ;
    }

    public void setBankCardZ(String bankCardZ) {
        this.bankCardZ = bankCardZ;
    }

    public String getBankCardF() {
        return bankCardF;
    }

    public void setBankCardF(String bankCardF) {
        this.bankCardF = bankCardF;
    }

    public Date getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(Date uploadTime) {
        this.uploadTime = uploadTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

}
