package com.example.memorialapp.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.memorialapp.database.MainUserDatabase
import com.example.memorialapp.model.MainUser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class MainUserViewModel(application: Application): AndroidViewModel(application) {
    private val dao = MainUserDatabase.getDatabase(application).mainUserDao()
    val allAdmins:LiveData<List<MainUser>> = dao.getAllUsers()

    fun insert(mainUser: MainUser){
        viewModelScope.launch(Dispatchers.IO) {
            dao.insertUser(mainUser)
        }
    }


    fun getUser(login:String, password:String):MainUser = runBlocking(Dispatchers.IO) {
        return@runBlocking dao.getUser(login, password)
    }

    }

