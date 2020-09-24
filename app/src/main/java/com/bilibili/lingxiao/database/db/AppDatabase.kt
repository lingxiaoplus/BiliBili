package com.bilibili.lingxiao.database.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.bilibili.lingxiao.database.UserInfoTable
import com.bilibili.lingxiao.database.dao.UserDao

@Database(entities = arrayOf(UserInfoTable::class), version = 1)
abstract class AppDatabase :RoomDatabase(){
    abstract fun userDao(): UserDao
}