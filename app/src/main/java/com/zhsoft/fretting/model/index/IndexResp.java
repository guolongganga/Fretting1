package com.zhsoft.fretting.model.index;

import com.zhsoft.fretting.model.BaseResp;

import java.util.ArrayList;

/**
 * 作者：sunnyzeng on 2018/1/2 10:49
 * 描述：
 */

public class IndexResp extends BaseResp<IndexResp> {
    /**
     * banner集合
     */
    private ArrayList<BannerModel> bannerList;
    /**
     * 人气产品
     */
    private ArrayList<ProductModel> hotList;
    /**
     * 指数基金
     */
    private ArrayList<ProductModel> indexList;
    /**
     * 微银专题
     */
    private ArrayList<String> objList;
    /**
     * 优选定投
     */
    private ProductModel preferredVote;
    /**
     * 明星基金
     */
    private ProductModel starFound;

    public ArrayList<BannerModel> getBannerList() {
        return bannerList;
    }

    public void setBannerList(ArrayList<BannerModel> bannerList) {
        this.bannerList = bannerList;
    }

    public ArrayList<ProductModel> getHotList() {
        return hotList;
    }

    public void setHotList(ArrayList<ProductModel> hotList) {
        this.hotList = hotList;
    }

    public ArrayList<ProductModel> getIndexList() {
        return indexList;
    }

    public void setIndexList(ArrayList<ProductModel> indexList) {
        this.indexList = indexList;
    }

    public ArrayList<String> getObjList() {
        return objList;
    }

    public void setObjList(ArrayList<String> objList) {
        this.objList = objList;
    }

    public ProductModel getPreferredVote() {
        return preferredVote;
    }

    public void setPreferredVote(ProductModel preferredVote) {
        this.preferredVote = preferredVote;
    }

    public ProductModel getStarFound() {
        return starFound;
    }

    public void setStarFound(ProductModel starFound) {
        this.starFound = starFound;
    }
}
