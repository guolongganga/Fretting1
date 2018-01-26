package com.zhsoft.fretting.params;

/**
 * 作者：sunnyzeng on 2017/12/29 10:28
 * 描述：
 */

public class TransactionQueryParams extends CommonReqData<TransactionQueryParams> {
    private int pageNum;
    private int pageSize;
    private String type;

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
