package com.kingofraccoon.punctualpatient

import android.app.StatusBarManager
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.BackgroundColorSpan
import android.text.style.ForegroundColorSpan
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.widget.PopupMenu
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.kingofraccoon.punctualpatient.fragment.CheckFragment
import com.kingofraccoon.punctualpatient.fragment.FilterTalonFragment
import com.kingofraccoon.punctualpatient.fragment.GetTalonFragment
import com.kingofraccoon.punctualpatient.fragment.ProfileFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportFragmentManager.setFragment(CheckFragment())
//        val bottomNavigationView : BottomNavigationView = findViewById(R.id.bnv)
//        bottomNavigationView.setOnNavigationItemSelectedListener {
//            when(it.itemId){
//                R.id.writeTalon -> supportFragmentManager.setFragment(FilterTalonFragment())
//                R.id.profile -> supportFragmentManager.setFragment(ProfileFragment())
//            }
//            return@setOnNavigationItemSelectedListener true
//        }

        val actBar = SpannableString(title)
        actBar.setSpan(ForegroundColorSpan(Color.rgb(78, 78, 78)), 0, title.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        supportActionBar?.setTitle(actBar)
        //actBar.setSpan(BackgroundColorSpan(Color.rgb(240, 237, 245)), 0, 0, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)



    }
    fun FragmentManager.setFragment(fragment: Fragment){
        this.beginTransaction()
                .add(R.id.frame, fragment)
                .addToBackStack(null)
                .commit()
    }


}