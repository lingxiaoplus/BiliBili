package com.bilibili.lingxiao.database

import com.raizlabs.android.dbflow.annotation.Column
import com.raizlabs.android.dbflow.annotation.PrimaryKey
import com.raizlabs.android.dbflow.annotation.Table
import com.raizlabs.android.dbflow.structure.BaseModel

@Table(database = NetCacheDatabase::class)
class RegionTable : BaseModel(){
    @PrimaryKey
    var reid: Int = 0
    //var children: List<Children>? by oneToMany { select from Children::class }
    @Column
    var logo = ""
    @Column
    var name = ""
    @Column
    var tid: Int? = null
    @Column
    var type: Int? = null
    @Column
    var uri: String? = null
    @Column
    var updateAt :Long = 0L
}