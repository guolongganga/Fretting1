package cn.com.buyforyou.fund.model.user;



import android.os.Parcel;
import android.os.Parcelable;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;

import cn.com.buyforyou.fund.model.BaseResp;
import cn.com.buyforyou.fund.model.fund.InvestResp;

/**
 * Created by guolonggang on 2019/4/24.
 * Description:
 */

public class UserInformationResp  extends BaseResp<UserInformationResp> implements Parcelable  {
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
    private String uploadTime;
    private String createTime;
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

    public String getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(String uploadTime) {
        this.uploadTime = uploadTime;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
            dest.writeLong(this.id);
            dest.writeString(this.userId);
            dest.writeString(this.idCardZ);
            dest.writeString(this.idCardF);
            dest.writeString(this.bankCardZ);
            dest.writeString(this.bankCardF);
            dest.writeString(String.valueOf(this.uploadTime));
            dest.writeString(String.valueOf(this.createTime));
            dest.writeString(this.delFlag);
    }

    public UserInformationResp() {
    }

    protected UserInformationResp(Parcel in) {
        this.id = in.readLong();
        this.userId = in.readString();
        this.idCardZ = in.readString();
        this.idCardF = in.readString();
        this.bankCardZ = in.readString();
        this.bankCardF = in.readString();
        this.uploadTime = in.readString();
        this.createTime = in.readString();
        this.delFlag=in.readString();

    }

    public static final Creator<UserInformationResp> CREATOR = new Creator<UserInformationResp>() {
        @Override
        public UserInformationResp createFromParcel(Parcel source) {
            return new UserInformationResp(source);
        }

        @Override
        public UserInformationResp[] newArray(int size) {
            return new UserInformationResp[size];
        }
    };
}
