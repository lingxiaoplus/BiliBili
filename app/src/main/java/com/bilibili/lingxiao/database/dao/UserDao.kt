package com.bilibili.lingxiao.database.dao

import androidx.room.*
import com.bilibili.lingxiao.database.UserInfoTable

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)  //发生冲突后 取代旧数据同时继续事务
    fun insertUser(userInfoTable: UserInfoTable)

    @Delete
    fun deleteUser(userInfoTable: UserInfoTable)

    @Update
    fun update(userInfoTable: UserInfoTable)

    @Query("SELECT * FROM UserInfoTable WHERE uid IS :mid LIMIT 1")
    fun findById(mid:Long):UserInfoTable?

    @Query("SELECT * FROM UserInfoTable LIMIT 1")
    fun findUser():UserInfoTable?

}