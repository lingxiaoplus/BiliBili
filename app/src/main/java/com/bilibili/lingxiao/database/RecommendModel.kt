package com.bilibili.lingxiao.database

import com.dbflow5.annotation.Column
import com.dbflow5.annotation.PrimaryKey
import com.dbflow5.annotation.Table
import com.dbflow5.structure.BaseModel
@Table(database = NetCacheDatabase::class)
class RecommendModel :BaseModel(){
    @PrimaryKey
    var id: Int = 0
    @Column
    var coin: Int = 0

}