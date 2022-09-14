package com.example.memorialapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.memorialapp.dao.MainUserDao
import com.example.memorialapp.model.MainUser


@Database(entities = [MainUser::class], version = 1, exportSchema = false)
abstract class MainUserDatabase: RoomDatabase() {
    abstract fun mainUserDao(): MainUserDao

    companion object{
        @Volatile
        private var INSTANCE: MainUserDatabase?=null

        fun getDatabase(context: Context): MainUserDatabase {
            val tempInstance = INSTANCE
            if (tempInstance!=null){
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MainUserDatabase::class.java,
                    "main_user_table"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}