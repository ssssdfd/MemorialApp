package com.example.memorialapp

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.memorialapp.databinding.FragmentAdminListBinding
import com.example.memorialapp.model.Soldier
import com.example.memorialapp.viewModel.MainUserViewModel
import com.example.memorialapp.viewModel.SoldierViewModel

class AdminListFragment : Fragment(), SearchView.OnQueryTextListener {
    private lateinit var soldierViewModel: SoldierViewModel
    private lateinit var binding: FragmentAdminListBinding
    private lateinit var soldierAdapter : SoldierAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAdminListBinding.inflate(inflater, container, false)
        soldierViewModel = ViewModelProvider(this).get(SoldierViewModel::class.java)

        soldierAdapter = SoldierAdapter(SoldierAdapter.OnClickListener{soldier ->
            Toast.makeText(requireContext(), "Soldier:$soldier", Toast.LENGTH_SHORT).show()
            val fragment = ShowFragment.newInstance(soldier)
            parentFragmentManager
                .beginTransaction()
                .setCustomAnimations( R.anim.slide_in, R.anim.fade_out,  R.anim.fade_in, R.anim.slide_out)
                .replace(R.id.fragmentContainer, fragment)
                .addToBackStack(null)
                .commit()

        })

        binding.apply {
            rcView.adapter = soldierAdapter
            rcView.layoutManager = LinearLayoutManager(requireContext())
        }

        soldierViewModel.getAllSoldiers.observe(viewLifecycleOwner){
            soldierAdapter.soldierList = it as MutableList<Soldier>
        }

        binding.addBtn.setOnClickListener {
            navigate(AddSoldierFragment())
        }

        setHasOptionsMenu(true)

        binding.backBtn.setOnClickListener {
            val fragment = FirstFragment()
            parentFragmentManager
                .beginTransaction()
                .setCustomAnimations( R.anim.slide_in, R.anim.fade_out,  R.anim.fade_in, R.anim.slide_out)
                .replace(R.id.fragmentContainer, fragment)
                .addToBackStack(null)
                .commit()
        }

        return binding.root
    }
    private fun navigate(fragment: Fragment){
        val nav = fragment
        parentFragmentManager
            .beginTransaction()
            .setCustomAnimations( R.anim.slide_in, R.anim.fade_out,  R.anim.fade_in, R.anim.slide_out)
            .replace(R.id.fragmentContainer, nav)
            .addToBackStack(null)
            .commit()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.list_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
        val search = menu.findItem(R.id.search_item)
        val searchView = search?.actionView as? SearchView
        searchView?.isSubmitButtonEnabled = true
        searchView?.setOnQueryTextListener(this)

    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if (query!=null){
            searchDatabase(query)
        }
        return true
    }

    override fun onQueryTextChange(query: String?): Boolean {
        if (query!=null){
            searchDatabase(query)
        }
        return true
    }

    private fun searchDatabase(query: String){
        val searchQuery = "%$query%"
        soldierViewModel.searchSoldier(searchQuery).observe(viewLifecycleOwner){
            it.let {
                soldierAdapter.soldierList = it as MutableList<Soldier>
            }
        }
    }
}