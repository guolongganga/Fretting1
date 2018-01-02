package com.zhsoft.fretting.model.index;

/**
 * 作者：sunnyzeng on 2018/1/2 10:40
 * 描述：广告banner实体
 * "bannerImageUrl": "2",
 * "bannerLink": "3",
 * "bannerName": "4",
 * "bannerRemark": "remark2!",
 * "bannerSort": 6,
 * "bannerType": "7",
 * "id": 1,
 * "isEnable": "0"
 */

public class BannerModel {
    private String bannerImageUrl;
    private String bannerLink;
    private String bannerName;
    private String bannerRemark;
    private Integer bannerSort;
    private String bannerType;
    private Integer id;
    private String isEnable;

    public String getBannerImageUrl() {
        return bannerImageUrl;
    }

    public void setBannerImageUrl(String bannerImageUrl) {
        this.bannerImageUrl = bannerImageUrl;
    }

    public String getBannerLink() {
        return bannerLink;
    }

    public void setBannerLink(String bannerLink) {
        this.bannerLink = bannerLink;
    }

    public String getBannerName() {
        return bannerName;
    }

    public void setBannerName(String bannerName) {
        this.bannerName = bannerName;
    }

    public String getBannerRemark() {
        return bannerRemark;
    }

    public void setBannerRemark(String bannerRemark) {
        this.bannerRemark = bannerRemark;
    }

    public Integer getBannerSort() {
        return bannerSort;
    }

    public void setBannerSort(Integer bannerSort) {
        this.bannerSort = bannerSort;
    }

    public String getBannerType() {
        return bannerType;
    }

    public void setBannerType(String bannerType) {
        this.bannerType = bannerType;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIsEnable() {
        return isEnable;
    }

    public void setIsEnable(String isEnable) {
        this.isEnable = isEnable;
    }
}
