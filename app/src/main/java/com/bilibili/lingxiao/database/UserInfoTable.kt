package com.bilibili.lingxiao.database

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.hiczp.bilibili.api.app.model.MyInfo

@Entity
data class UserInfoTable(
    @PrimaryKey val uid: Long,
    var birthday: String, //生日
    var coins: Int, // 硬币
    var face: String,//头像
    var level: Int, // 等级
    var name: String, // czp3009
    var sex: Int, // 0
    var sign: String,
    var silence: Int, // 0
    var telStatus: Int, // 1
    @Embedded var official: MyInfo.Data.Official,
    @Embedded var vip: MyInfo.Data.Vip
) {

}