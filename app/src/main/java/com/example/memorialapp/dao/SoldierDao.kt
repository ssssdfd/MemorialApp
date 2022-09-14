package com.example.memorialapp.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.memorialapp.model.Soldier
import kotlinx.coroutines.flow.Flow

@Dao
interface SoldierDao {

    @Query("SELECT * FROM soldier_table ORDER BY surname ASC")
    fun readAllSoldiers():LiveData<List<Soldier>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertSoldier(soldier: Soldier)

    @Query("SELECT * FROM soldier_table WHERE surname LIKE :searchQuery OR name LIKE :searchQuery or patronymic LIKE:searchQuery")
    fun searchSoldier(searchQuery: String):Flow<List<Soldier>>
}