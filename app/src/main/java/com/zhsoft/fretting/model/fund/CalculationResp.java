package com.zhsoft.fretting.model.fund;

import com.zhsoft.fretting.model.BaseResp;

/**
 * 作者：sunnyzeng on 2018/3/22 19:54
 * 描述：
 */

public class CalculationResp extends BaseResp<CalculationResp> {
    private String fare_sx;

    public String getFare_sx() {
        return fare_sx;
    }

    public void setFare_sx(String fare_sx) {
        this.fare_sx = fare_sx;
    }
}
