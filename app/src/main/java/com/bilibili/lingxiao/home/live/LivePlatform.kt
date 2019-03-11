package com.bilibili.lingxiao.home.live

import dagger.Component

interface LivePlatform{
    fun getLiveList(): LivePresenter
}