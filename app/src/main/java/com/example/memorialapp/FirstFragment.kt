package com.example.memorialapp

import android.animation.LayoutTransition
import android.os.Bundle
import android.text.TextUtils.replace
import android.transition.TransitionManager
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.memorialapp.databinding.FragmentFirstBinding
import com.example.memorialapp.model.MainUser
import com.example.memorialapp.viewModel.MainUserViewModel

class FirstFragment : Fragment() {
    private lateinit var binding: FragmentFirstBinding
    private lateinit var mainUserViewModel: MainUserViewModel
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentFirstBinding.inflate(inflater, container, false)
        binding.adminCard.layoutTransition.enableTransitionType(LayoutTransition.CHANGING)
        mainUserViewModel = ViewModelProvider(this).get(MainUserViewModel::class.java)

        binding.searchBtn.setOnClickListener {
            val fragment = ListFragment()
            parentFragmentManager
                .beginTransaction()
                .setCustomAnimations(R.anim.slide_in, R.anim.fade_out, R.anim.fade_in, R.anim.slide_out)
                .replace(R.id.fragmentContainer, fragment)
                .commit()
        }

        binding.adminTextView.setOnClickListener {
            TransitionManager.beginDelayedTransition(binding.adminCard, android.transition.Slide())
            binding.apply {
                mainFirstLayout.setBackgroundResource(R.color.darken_layout)
                searchBtn.isEnabled = false
                adminCard.visibility = View.VISIBLE
            }
        }

        binding.closeAdminBtn.setOnClickListener {
            TransitionManager.beginDelayedTransition(binding.adminCard, android.transition.Slide())
            binding.apply {
                mainFirstLayout.setBackgroundResource(R.color.white)
                searchBtn.isEnabled = true
                adminCard.visibility = View.GONE
            }
        }
        binding.addAdminBtn.setOnClickListener {
            addMainUser()
        }

        binding.nextAdminBtn.setOnClickListener {
            getUser()
        }
        return binding.root
    }

    fun addMainUser() = with(binding) {
        val login = loginEd.text.toString()
        val password = passwordEd.text.toString()
        if (login.isNotEmpty() && password.isNotEmpty()) {
            val mainUser = MainUser(login, password)
            mainUserViewModel.insert(mainUser)
            Toast.makeText(requireContext(), "User added", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(requireContext(), "User not added", Toast.LENGTH_SHORT).show()
        }
    }

    fun getUser() = with(binding) {
        val login = loginEd.text.toString()
        val password = passwordEd.text.toString()
        if (login.isNotEmpty() && password.isNotEmpty()) {
            val user = mainUserViewModel.getUser(login, password)
           if (user!=null) {
                Toast.makeText(requireContext(), "User received", Toast.LENGTH_SHORT).show()
                val fragment = AdminListFragment()
                parentFragmentManager
                    .beginTransaction()
                    .setCustomAnimations(R.anim.slide_in, R.anim.fade_out, R.anim.fade_in, R.anim.slide_out)
                    .replace(R.id.fragmentContainer, fragment)
                    .commit()
            } else {
                Toast.makeText(requireContext(), "User not received", Toast.LENGTH_SHORT).show()
            }
        }
    }

}