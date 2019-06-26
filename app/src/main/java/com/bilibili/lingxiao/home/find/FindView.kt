package com.bilibili.lingxiao.home.find

import com.bilibili.lingxiao.home.find.model.HotWordsData
import com.bilibili.lingxiao.home.find.model.SearchResultData

interface FindView {
    fun onGetHotWords(wordsData: HotWordsData)
    fun onGetSearchResult(result: SearchResultData)
}