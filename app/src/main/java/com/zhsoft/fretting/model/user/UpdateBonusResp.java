package com.zhsoft.fretting.model.user;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 作者：sunnyzeng on 2018/1/25 10:48
 * 描述：修改分红方式
 */

public class UpdateBonusResp implements Parcelable {
    /**
     * 基金名称
     */
    private String fundName;
    /**
     * 基金的编号
     */
    private String fundCode;
    /**
     * 分红方式
     */
    private String bonusType;

    public UpdateBonusResp(String fundName, String fundCode, String bonusType, String bonusId) {
        this.fundName = fundName;
        this.fundCode = fundCode;
        this.bonusType = bonusType;
        this.bonusId = bonusId;
    }

    /**
     * 这一单的编号

     */
    private String bonusId;

    public String getFundName() {
        return fundName;
    }

    public void setFundName(String fundName) {
        this.fundName = fundName;
    }

    public String getFundCode() {
        return fundCode;
    }

    public void setFundCode(String fundCode) {
        this.fundCode = fundCode;
    }

    public String getBonusType() {
        return bonusType;
    }

    public void setBonusType(String bonusType) {
        this.bonusType = bonusType;
    }

    public String getBonusId() {
        return bonusId;
    }

    public void setBonusId(String bonusId) {
        this.bonusId = bonusId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.fundName);
        dest.writeString(this.fundCode);
        dest.writeString(this.bonusType);
        dest.writeString(this.bonusId);
    }

    protected UpdateBonusResp(Parcel in) {
        this.fundName = in.readString();
        this.fundCode = in.readString();
        this.bonusType = in.readString();
        this.bonusId = in.readString();
    }

    public static final Parcelable.Creator<UpdateBonusResp> CREATOR = new Parcelable.Creator<UpdateBonusResp>() {
        @Override
        public UpdateBonusResp createFromParcel(Parcel source) {
            return new UpdateBonusResp(source);
        }

        @Override
        public UpdateBonusResp[] newArray(int size) {
            return new UpdateBonusResp[size];
        }
    };
}
