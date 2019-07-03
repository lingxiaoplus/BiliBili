package com.bilibili.lingxiao.database

import com.raizlabs.android.dbflow.annotation.Database


@Database(name = NetCacheDatabase.NAME,version = NetCacheDatabase.VERSION)
class NetCacheDatabase {
    companion object{
        const val NAME = "NetCache"
        const val VERSION = 1
    }
}