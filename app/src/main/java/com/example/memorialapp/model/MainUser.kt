package com.example.memorialapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "main_user_table")
data class MainUser(
    @PrimaryKey
    val login: String,
    val password: String
)
