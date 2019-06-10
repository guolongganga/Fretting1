package cn.com.buyforyou.fund.model.user;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import cn.com.buyforyou.fund.model.BaseResp;

/**
 * Created by guolonggang on 2018/12/10.
 * Description:
 * {"data":{"otherSerial":"00720689","originalAppno":"20181210230110592653"},"message":"成功","status":200}
 */

public class OpenAccountResp extends BaseResp<OpenAccountResp> implements Parcelable{


    private String otherSerial;
    private String originalAppno;

    public String getOtherSerial() {
        return otherSerial;
    }

    public void setOtherSerial(String otherSerial) {
        this.otherSerial = otherSerial;
    }

    public String getOriginalAppno() {
        return originalAppno;
    }

    public void setOriginalAppno(String originalAppno) {
        this.originalAppno = originalAppno;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.otherSerial);
        dest.writeString(this.originalAppno);


    }
    public OpenAccountResp() {
    }

    protected OpenAccountResp(Parcel in) {
        this.otherSerial = in.readString();
        this.originalAppno = in.readString();

    }

    public static final Creator<OpenAccountResp> CREATOR = new Creator<OpenAccountResp>() {
        @Override
        public OpenAccountResp createFromParcel(Parcel source) {
            return new OpenAccountResp(source);
        }

        @Override
        public OpenAccountResp[] newArray(int size) {
            return new OpenAccountResp[size];
        }
    };
}
