package com.example.memorialapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.memorialapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if(savedInstanceState==null){
            val fragment = FirstFragment()
            supportFragmentManager
                .beginTransaction()
                .setCustomAnimations( R.anim.slide_in, R.anim.fade_out,  R.anim.fade_in, R.anim.slide_out)
                .replace(R.id.fragmentContainer, fragment)
                .addToBackStack(null)
                .commit()
        }
    }
}