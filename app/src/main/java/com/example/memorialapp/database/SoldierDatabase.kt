package com.example.memorialapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.memorialapp.dao.SoldierDao
import com.example.memorialapp.model.Soldier

@Database(entities = [Soldier::class], version = 1, exportSchema = false)
abstract class SoldierDatabase: RoomDatabase() {
    abstract fun soldierDao(): SoldierDao

    companion object{
        @Volatile
        private var INSTANCE: SoldierDatabase?=null

        fun getDatabase(context: Context): SoldierDatabase {
            val tempInstance = INSTANCE
            if (tempInstance!=null){
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    SoldierDatabase::class.java,
                    "soldier_table"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}