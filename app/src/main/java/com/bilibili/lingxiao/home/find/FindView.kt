package com.bilibili.lingxiao.home.find

import com.bilibili.lingxiao.home.find.model.HotWordsData
import com.bilibili.lingxiao.home.find.model.SearchResultData
import com.bilibili.lingxiao.home.region.model.RegionData

interface FindView {
    fun onGetHotWords(wordsData: HotWordsData)
    fun onGetSearchResult(result: SearchResultData)
    fun onGetRegion(list: List<RegionData.Data>)
}