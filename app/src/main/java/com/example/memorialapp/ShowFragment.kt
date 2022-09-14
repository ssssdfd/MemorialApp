package com.example.memorialapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.memorialapp.databinding.FragmentShowBinding
import com.example.memorialapp.model.Soldier

class ShowFragment : Fragment() {
    private lateinit var binding: FragmentShowBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentShowBinding.inflate(inflater, container, false)

        binding.apply {
            sSurname.text = getSoldier().surname
            sName.text = getSoldier().name
            sPatronomic.text = getSoldier().patronymic
            sBorn.text = getSoldier().dateOfBirth
            sDeath.text =getSoldier().dateOfDeath
            sContent.text = getSoldier().detailedInformation
            mName.text = getSoldier().mother.m_name
            mSurname.text = getSoldier().mother.m_surname
            mPatronomic.text = getSoldier().mother.m_patronymic
            mNumber.text = getSoldier().mother.m_phoneNumber.toString()
            fName.text = getSoldier().father.f_name
            fSurname.text = getSoldier().father.f_surname
            fPatronomic.text = getSoldier().father.f_patronymic
            fNumber.text = getSoldier().father.f_phoneNumber.toString()
        }

        return binding.root
    }

    private fun getSoldier():Soldier= requireArguments().getParcelable(ARG_SOLDIER)!!


    companion object{
        @JvmStatic
        private val ARG_SOLDIER = "ARG_SOLDIER"

        fun newInstance(soldier:Soldier):ShowFragment{
            val args = Bundle().apply {
                putParcelable(ARG_SOLDIER, soldier)
            }
            val fragment = ShowFragment()
            fragment.arguments = args
            return fragment
        }
    }
}