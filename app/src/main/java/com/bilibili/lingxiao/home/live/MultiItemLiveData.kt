package com.bilibili.lingxiao.home.live

import com.chad.library.adapter.base.entity.MultiItemEntity

class MultiItemLiveData(private val itemType: Int) : MultiItemEntity {

    var bannerList: List<LiveData.BannerBean>? = null
    var entranceIconsBean: LiveData.EntranceIconsBean? = null
    var partitionsBean: LiveData.PartitionsBean? = null
    var liveList: List<LiveData.RecommendDataBean.LivesBean>? = null

    override fun getItemType(): Int {
        return itemType
    }

    companion object {

        val BANNER = 1
        val CATEGORY = 2
        val RECOMMEND = 3
        val PARTITION = 4
    }
}
