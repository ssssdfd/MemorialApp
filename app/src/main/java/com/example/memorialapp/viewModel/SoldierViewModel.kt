package com.example.memorialapp.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.memorialapp.SoldierRepository
import com.example.memorialapp.database.SoldierDatabase
import com.example.memorialapp.model.Soldier
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SoldierViewModel(application: Application):AndroidViewModel(application) {
    private val dao = SoldierDatabase.getDatabase(application).soldierDao()
    private val repository = SoldierRepository(dao)
    val getAllSoldiers:LiveData<List<Soldier>> = repository.readSoldiers

    fun insertSoldier(soldier: Soldier){
        viewModelScope.launch(Dispatchers.IO){
            repository.insertSoldier(soldier)
        }
    }

    fun searchSoldier(searchQuery:String):LiveData<List<Soldier>>{
        return repository.searchSoldier(searchQuery).asLiveData()
    }
}