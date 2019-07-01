package com.bilibili.lingxiao.database

import com.dbflow5.annotation.Database
import com.dbflow5.config.DBFlowDatabase

@Database(name = NetCacheDatabase.NAME,version = NetCacheDatabase.VERSION)
abstract class NetCacheDatabase :DBFlowDatabase(){
    companion object{
        const val NAME = "NetCache"
        const val VERSION = 1
    }
}