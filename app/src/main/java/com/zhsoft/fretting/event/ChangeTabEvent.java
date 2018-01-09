package com.zhsoft.fretting.event;

/**
 * 作者：sunnyzeng on 2018/1/9 14:29
 * 描述：
 */

public class ChangeTabEvent {
    private String msg;

    public ChangeTabEvent(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }
}
