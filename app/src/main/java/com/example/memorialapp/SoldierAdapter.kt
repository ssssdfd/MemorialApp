package com.example.memorialapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.memorialapp.databinding.SoldierItemBinding
import com.example.memorialapp.model.Soldier

class SoldierAdapter(private val onClickListener: OnClickListener):RecyclerView.Adapter<SoldierAdapter.SoldierViewHolder>() {
    var soldierList = mutableListOf<Soldier>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }
    class SoldierViewHolder(val binding: SoldierItemBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SoldierViewHolder {
        val binding = SoldierItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return SoldierViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SoldierViewHolder, position: Int) {
        val currentSoldier = soldierList[position]
        with(holder.binding){
            surnameTv.text = currentSoldier.surname
            nameTv.text = currentSoldier.name
            patromycTv.text =currentSoldier.patronymic
            dateOfBirthTv.text = currentSoldier.dateOfBirth
            dateOfDeathTv.text = currentSoldier.dateOfDeath
        }
        holder.itemView.setOnClickListener {
            onClickListener.onClick(currentSoldier)
        }
    }

    override fun getItemCount(): Int {
        return soldierList.size
    }

    class OnClickListener(val clickListener:(soldier:Soldier)->Unit){
        fun onClick(soldier: Soldier) = clickListener(soldier)
    }
}