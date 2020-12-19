package com.kingofraccoon.punctualpatient

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.kingofraccoon.punctualpatient.auth.Authorization
import com.kingofraccoon.punctualpatient.tools.encoder.EncryptedSharedPreferencesUser
import com.kingofraccoon.punctualpatient.tools.firebase.FireStore
import com.kingofraccoon.punctualpatient.view.fragment.AuthorizationFragment
import com.kingofraccoon.punctualpatient.view.fragment.MainFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        startService(Intent(this, GenerateTalonService::class.java))

        if (EncryptedSharedPreferencesUser(applicationContext).checkAuth()) {
            var loggAndPass = EncryptedSharedPreferencesUser(this).getLoginAndPass()
            Authorization().singIn(loggAndPass.first!!, loggAndPass.second!!)
                .addOnCompleteListener {
                    if (it.isSuccessful)
                        supportFragmentManager.setFragment(MainFragment(), MainFragment.tag)
                }
        } else
            supportFragmentManager.setFragment(AuthorizationFragment(), AuthorizationFragment.tag)
        val actBar = SpannableString(title)
        actBar.setSpan(
            ForegroundColorSpan(Color.rgb(78, 78, 78)),
            0,
            title.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        supportActionBar?.setTitle(actBar)
        //actBar.setSpan(BackgroundColorSpan(Color.rgb(240, 237, 245)), 0, 0, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
    }

    fun FragmentManager.setFragment(fragment: Fragment, tag: String) {
        this.beginTransaction()
            .add(R.id.frame, fragment)
            .addToBackStack(null)
            .commit()
    }
}


