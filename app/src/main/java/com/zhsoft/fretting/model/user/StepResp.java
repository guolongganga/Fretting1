package com.zhsoft.fretting.model.user;

/**
 * 作者：sunnyzeng on 2018/1/31 16:25
 * 描述：结果页状态
 * "iscpl": "0",
 * "name": "基金公司确认份额",
 * "time": null
 */

public class StepResp {
    /**
     * 0表示不选中 1表示选中
     */
    private String iscpl;
    /**
     * 状态
     */
    private String name;
    /**
     * 时间
     */
    private String time;

    public String getIscpl() {
        return iscpl;
    }

    public void setIscpl(String iscpl) {
        this.iscpl = iscpl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
