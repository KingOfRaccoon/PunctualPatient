package com.kingofraccoon.punctualpatient

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.kingofraccoon.punctualpatient.auth.Authorization
import com.kingofraccoon.punctualpatient.tools.firebase.FireStore
import com.kingofraccoon.punctualpatient.view.fragment.AuthorizationFragment
import java.time.LocalDateTime

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        startService(Intent(this, GenerateTalonService::class.java))
        supportFragmentManager.setFragment(AuthorizationFragment(), AuthorizationFragment.tag)
        val actBar = SpannableString(title)
        actBar.setSpan(ForegroundColorSpan(Color.rgb(78, 78, 78)), 0, title.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        supportActionBar?.setTitle(actBar)
        //actBar.setSpan(BackgroundColorSpan(Color.rgb(240, 237, 245)), 0, 0, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
//        Authorization().register("89831037111@gmail.com", 123456.toString())
//                .addOnSuccessListener {
//                    Log.d("Fire", it.user?.uid.toString())
//                }
//        FireStore().pullDoctorsOnFireStore()

    }
    fun FragmentManager.setFragment(fragment: Fragment, tag:String){
        this.beginTransaction()
                .add(R.id.frame, fragment)
                .addToBackStack(null)
                .commit()
    }
}


