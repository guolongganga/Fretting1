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
     * 微银专题
     */
    private ArrayList<String> objList;
    /**
     * 明星基金
     */
    private ProductModel starFound;
    /**
     * 人气产品
     */
    private ArrayList<ProductModel> hotFoundList;
    /**
     * 指数基金1
     */
    private ProductModel firstIndexFound;
    /**
     * 指数基金2
     */
    private ProductModel secondIndexFound;

    /**
     * 优选定投
     */
    private ProductModel preferredVote;

    public ArrayList<BannerModel> getBannerList() {
        return bannerList;
    }

    public void setBannerList(ArrayList<BannerModel> bannerList) {
        this.bannerList = bannerList;
    }

    public ArrayList<String> getObjList() {
        return objList;
    }

    public void setObjList(ArrayList<String> objList) {
        this.objList = objList;
    }

    public ProductModel getStarFound() {
        return starFound;
    }

    public void setStarFound(ProductModel starFound) {
        this.starFound = starFound;
    }

    public ProductModel getFirstIndexFound() {
        return firstIndexFound;
    }

    public void setFirstIndexFound(ProductModel firstIndexFound) {
        this.firstIndexFound = firstIndexFound;
    }

    public ProductModel getSecondIndexFound() {
        return secondIndexFound;
    }

    public void setSecondIndexFound(ProductModel secondIndexFound) {
        this.secondIndexFound = secondIndexFound;
    }

    public ProductModel getPreferredVote() {
        return preferredVote;
    }

    public void setPreferredVote(ProductModel preferredVote) {
        this.preferredVote = preferredVote;
    }

    public ArrayList<ProductModel> getHotFoundList() {
        return hotFoundList;
    }

    public void setHotFoundList(ArrayList<ProductModel> hotFoundList) {
        this.hotFoundList = hotFoundList;
    }
}
