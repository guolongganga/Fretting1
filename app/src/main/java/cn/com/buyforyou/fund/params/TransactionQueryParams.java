package cn.com.buyforyou.fund.params;

/**
 * 作者：sunnyzeng on 2017/12/29 10:28
 * 描述：
 */

public class TransactionQueryParams extends CommonReqData<TransactionQueryParams> {
    private int pageNum;
    private int pageSize;
    private String transactionCategory;
    private String fundCode;
    //交易账号
    private String trade_acco;


    public String getFundCode() {
        return fundCode;
    }

    public void setFundCode(String fundCode) {
        this.fundCode = fundCode;
    }

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

    public String getTransactionCategory() {
        return transactionCategory;
    }

    public void setTransactionCategory(String transactionCategory) {
        this.transactionCategory = transactionCategory;
    }

    public String getTrade_acco() {
        return trade_acco;
    }

    public void setTrade_acco(String trade_acco) {
        this.trade_acco = trade_acco;
    }
}
