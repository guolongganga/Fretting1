package com.zhsoft.fretting.params;

/**
 * 作者：sunnyzeng on 2017/12/29 10:28
 * 描述：
 */

public class NewestFundParams extends CommonReqData<NewestFundParams> {
    private int pageno;
    private int pagesize;
    private String type;
    private String orderBy;

    public int getPageno() {
        return pageno;
    }

    public void setPageno(int pageno) {
        this.pageno = pageno;
    }

    public int getPagesize() {
        return pagesize;
    }

    public void setPagesize(int pagesize) {
        this.pagesize = pagesize;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }
}
