package com.example.memorialapp.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Embedded
import kotlinx.parcelize.Parcelize

@Entity(tableName = "soldier_table")
@Parcelize
data class Soldier(
    @PrimaryKey
    val surname:String,
    val name:String,
    val patronymic:String,
    val dateOfBirth:String,
    val dateOfDeath:String,
    val detailedInformation:String,
    @Embedded
    val mother: Mother,
    @Embedded
    val father: Father
):Parcelable

@Parcelize
data class Mother(
    var m_surname:String,
    var m_name:String,
    var m_patronymic:String,
    var m_phoneNumber:Int
):Parcelable

@Parcelize
data class Father(
    var f_surname:String,
    var f_name:String,
    var f_patronymic:String,
    var f_phoneNumber:Int
):Parcelable



