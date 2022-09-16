package com.example.memorialapp

import android.animation.LayoutTransition
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.provider.CalendarContract
import android.transition.TransitionManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.memorialapp.databinding.FragmentAddSoldierBinding
import com.example.memorialapp.model.Father
import com.example.memorialapp.model.Mother
import com.example.memorialapp.model.Soldier
import com.example.memorialapp.viewModel.SoldierViewModel
import java.text.SimpleDateFormat
import kotlin.time.Duration.Companion.days

class AddSoldierFragment : Fragment() {
    private lateinit var soldierViewModel: SoldierViewModel
    private lateinit var binding: FragmentAddSoldierBinding

    @SuppressLint("ResourceAsColor")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentAddSoldierBinding.inflate(inflater, container, false)
        soldierViewModel = ViewModelProvider(this).get(SoldierViewModel::class.java)
        binding.parentsCard.layoutTransition.enableTransitionType(LayoutTransition.CHANGING)

        binding.backBtn.setOnClickListener {
            val fragment = AdminListFragment()
            parentFragmentManager
                .beginTransaction()
                .setCustomAnimations(R.anim.slide_in, R.anim.fade_out, R.anim.fade_in, R.anim.slide_out)
                .replace(R.id.fragmentContainer, fragment)
                .commit()
        }

        binding.parentsButton.setOnClickListener {
            parentsBtnPress()
        }

        binding.saveWithParentsBtn.setOnClickListener {
            addSoldierWithParents()
        }

        binding.backParentsButton.setOnClickListener {
           backParentsBtnPress()
        }

        binding.saveToDbBtn.setOnClickListener {
            addSoldier()
        }

        binding.notifyBtn.setOnClickListener {
            createNotify()
        }

        return binding.root
    }

    private fun addSoldier() = with(binding) {
        val s_surname = sSurname.text.toString()
        val s_name = sName.text.toString()
        val s_patronomyc = sPatronomyc.text.toString()
        val date_birth = sBorn.text.toString()
        val date_death = sDeath.text.toString()
        val content = sContent.text.toString()

        //Save
        val mother = Mother("-", "-", "-", 0)
        val father = Father("-", "-", "-", 0)
        val soldier = Soldier(s_surname, s_name, s_patronomyc, date_birth, date_death, content, mother, father)
        soldierViewModel.insertSoldier(soldier)
        Toast.makeText(requireContext(), "Soldier added", Toast.LENGTH_SHORT).show()
    }

   private fun addSoldierWithParents() = with(binding) {
        val s_surname = sSurname.text.toString()
        val s_name = sName.text.toString()
        val s_patronomyc = sPatronomyc.text.toString()
        val date_birth = sBorn.text.toString()
        val date_death = sDeath.text.toString()
        val content = sContent.text.toString()

        //Mother
        val m_surname = mSurname.text.toString()
        val m_name = mName.text.toString()
        val m_patronomyc = mPatronomyc.text.toString()
        val m_phone = mPhone.text.toString()

        //Father
        val f_surname = fSurname.text.toString()
        val f_name = fName.text.toString()
        val f_patronomyc = fPatronomic.text.toString()
        val f_phone = fPhone.text.toString()

        //Save
        val mother = Mother(m_surname, m_name, m_patronomyc, m_phone.toInt())
        val father = Father(f_surname, f_name, f_patronomyc, f_phone.toInt())
        val soldier = Soldier(s_surname, s_name, s_patronomyc, date_birth, date_death, content, mother, father)

        soldierViewModel.insertSoldier(soldier)
        Toast.makeText(requireContext(), "Soldier added", Toast.LENGTH_SHORT).show()
        sSurname.isEnabled = true
        sName.isEnabled = true
        sPatronomyc.isEnabled = true
        sDeath.isEnabled = true
        sBorn.isEnabled = true
    }

    private fun parentsBtnPress(){
        TransitionManager.beginDelayedTransition(binding.parentsCard, android.transition.Slide())
        binding.apply {
            mainLayout.setBackgroundResource(R.color.darken_layout)
            sSurname.isEnabled = false
            sName.isEnabled = false
            sPatronomyc.isEnabled = false
            sDeath.isEnabled = false
            sBorn.isEnabled = false
            parentsButton.visibility = View.GONE
            parentsCard.visibility = View.VISIBLE
        }
    }

    private fun backParentsBtnPress(){
        TransitionManager.beginDelayedTransition(binding.parentsCard, android.transition.Slide())
        binding.apply {
            mainLayout.setBackgroundResource(R.color.light_layout)
            parentsCard.visibility = View.GONE
            parentsButton.visibility = View.VISIBLE
            sSurname.isEnabled = true
            sName.isEnabled = true
            sPatronomyc.isEnabled = true
            sDeath.isEnabled = true
            sBorn.isEnabled = true
        }
    }

    @SuppressLint("SimpleDateFormat")
    private fun createNotify(){
        val simpleDateFormat = SimpleDateFormat("dd.MM.yyyy'T'HH:mm:ss")
        val date = binding.sDeath.text.toString()
        val beginDateWithTime = date+"T07:00:00"
        val endDateWithTime = date+"T23:00:00"
        val beginTime = simpleDateFormat.parse(beginDateWithTime)
        val endTime = simpleDateFormat.parse(endDateWithTime)
        beginTime.year+=1
        endTime.year+=1
        //////////////////////////////////////////////////////////////////////////
        val intent = Intent(Intent.ACTION_INSERT)
        intent.setData(CalendarContract.Events.CONTENT_URI)
        intent.putExtra(CalendarContract.Events.TITLE,"Прошёл год со дня смерти ${binding.sName}")
        intent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, beginTime?.time)
        intent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME, endTime?.time)
        intent.putExtra(CalendarContract.Events.DESCRIPTION,"Описание")
        intent.putExtra(CalendarContract.Events.EVENT_LOCATION,"Локация")
        startActivity(intent)
    }

}