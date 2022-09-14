package com.example.memorialapp.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.IGNORE
import androidx.room.Query
import com.example.memorialapp.model.MainUser

@Dao
interface MainUserDao {
    @Query("SELECT * FROM main_user_table ORDER BY login ASC")
    fun getAllUsers():LiveData<List<MainUser>>

    @Insert(onConflict = IGNORE)
    suspend fun insertUser(mainUser: MainUser)

    @Query("SELECT * FROM main_user_table WHERE login=:login and password=:password" )
    fun getUser(login:String, password:String):MainUser
}