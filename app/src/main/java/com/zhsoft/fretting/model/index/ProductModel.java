package com.zhsoft.fretting.model.index;

import java.math.BigDecimal;

/**
 * 作者：sunnyzeng on 2018/1/2 10:44
 * 描述：基金实体类
 * "aveg": 123,
 * "code": "741852",
 * "name": "基金name",
 * "netWorth": 123,
 * "type": "股票型"
 */

public class ProductModel {
    /**
     * 基金名称
     */
    private String name;
    /**
     * 基金代码
     */
    private String code;
    /**
     * 基金类型
     */
    private String type;
    /**
     * 基金收益涨幅
     */
    private BigDecimal aveg;
    /**
     * 基金净值
     */
    private BigDecimal netWorth;
    /**
     * 特色1
     */
    private String featureOne;
    /**
     * 特色2
     */
    private String featureTwo;

    public String getFeatureOne() {
        return featureOne;
    }

    public void setFeatureOne(String featureOne) {
        this.featureOne = featureOne;
    }

    public String getFeatureTwo() {
        return featureTwo;
    }

    public void setFeatureTwo(String featureTwo) {
        this.featureTwo = featureTwo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public BigDecimal getAveg() {
        return aveg;
    }

    public void setAveg(BigDecimal aveg) {
        this.aveg = aveg;
    }

    public BigDecimal getNetWorth() {
        return netWorth;
    }

    public void setNetWorth(BigDecimal netWorth) {
        this.netWorth = netWorth;
    }
}
