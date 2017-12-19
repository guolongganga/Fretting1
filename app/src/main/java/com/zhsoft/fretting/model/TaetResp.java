package com.zhsoft.fretting.model;

import java.util.List;

/**
 * 作者：sunnyzeng on 2017/12/7 17:44
 * 描述：
 */

public class TaetResp extends BaseResp<TaetResp> {

    //如果外面data是[] 那么就可以直接在这类里面定义参数了
    //public class TaetResp extends BaseResp<List<TaetResp>>
    private String id;
    //TODO 增加dalog弹出框

    private List<Happ> dictData;

    public List<Happ> getDictData() {
        return dictData;
    }

    public void setDictData(List<Happ> dictData) {
        this.dictData = dictData;

    }
}
