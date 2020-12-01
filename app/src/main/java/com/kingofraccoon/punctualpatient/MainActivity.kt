package com.kingofraccoon.punctualpatient

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.kingofraccoon.punctualpatient.fragment.FilterTalonFragment
import com.kingofraccoon.punctualpatient.fragment.GetTalonFragment
import com.kingofraccoon.punctualpatient.fragment.ProfileFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        startService(Intent(this, GenerateTalonService::class.java))

        val bottomNavigationView : BottomNavigationView = findViewById(R.id.bnv)
        bottomNavigationView.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.writeTalon -> supportFragmentManager.setFragment(FilterTalonFragment())
                R.id.profile -> supportFragmentManager.setFragment(ProfileFragment())
            }
            return@setOnNavigationItemSelectedListener true
        }
    }
    fun FragmentManager.setFragment(fragment: Fragment){
        this.beginTransaction()
                .add(R.id.frame, fragment)
                .addToBackStack(null)
                .commit()
    }
}