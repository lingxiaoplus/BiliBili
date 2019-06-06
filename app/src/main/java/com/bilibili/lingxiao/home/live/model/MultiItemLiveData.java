package com.bilibili.lingxiao.home.live.model;

import java.util.List;

public class MultiItemLiveData {
    public static final int RECOMMEND = 1;
    public static final int PARTITION = 2;
    public int itemType;

    public MultiItemLiveData(int type){
        this.itemType = type;
    }

    private List<LiveData.BannerBean> bannerList;
    private LiveData.EntranceIconsBean entranceIconsBean;
    private LiveData.PartitionsBean partitionsBean;
    private List<LiveData.RecommendDataBean.LivesBean> liveList;

    public List<LiveData.BannerBean> getBannerList() {
        return bannerList;
    }

    public void setBannerList(List<LiveData.BannerBean> bannerList) {
        this.bannerList = bannerList;
    }

    public LiveData.EntranceIconsBean getEntranceIconsBean() {
        return entranceIconsBean;
    }

    public void setEntranceIconsBean(LiveData.EntranceIconsBean entranceIconsBean) {
        this.entranceIconsBean = entranceIconsBean;
    }

    public LiveData.PartitionsBean getPartitionsBean() {
        return partitionsBean;
    }

    public void setPartitionsBean(LiveData.PartitionsBean partitionsBean) {
        this.partitionsBean = partitionsBean;
    }

    public List<LiveData.RecommendDataBean.LivesBean> getLiveList() {
        return liveList;
    }

    public void setLiveList(List<LiveData.RecommendDataBean.LivesBean> lives) {
        this.liveList = lives;
    }
}
