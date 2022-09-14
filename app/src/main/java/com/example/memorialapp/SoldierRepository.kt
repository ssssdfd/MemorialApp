package com.example.memorialapp

import androidx.lifecycle.LiveData
import com.example.memorialapp.dao.SoldierDao
import com.example.memorialapp.model.Soldier
import kotlinx.coroutines.flow.Flow

class SoldierRepository(private val dao: SoldierDao) {
    val readSoldiers:LiveData<List<Soldier>>  = dao.readAllSoldiers()

    suspend fun insertSoldier(soldier: Soldier){
        dao.insertSoldier(soldier)
    }

     fun searchSoldier(searchQuery:String):Flow<List<Soldier>>{
        return dao.searchSoldier(searchQuery)
    }
}