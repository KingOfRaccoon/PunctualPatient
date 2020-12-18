package com.kingofraccoon.punctualpatient

import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.util.Base64
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.functions.FirebaseFunctions
import com.google.firebase.functions.FirebaseFunctionsException
import com.kingofraccoon.punctualpatient.encoder.Cript
import com.kingofraccoon.punctualpatient.encoder.CriptConverter
import com.kingofraccoon.punctualpatient.firebase.FireStore
import com.kingofraccoon.punctualpatient.fragment.CheckFragment
import com.kingofraccoon.punctualpatient.retrofit.Answer
import com.kingofraccoon.punctualpatient.retrofit.DataPerson
import com.kingofraccoon.punctualpatient.retrofit.RetroFitClient
import com.kingofraccoon.punctualpatient.retrofit.RetrofitApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
//    private lateinit var functions : FirebaseFunctions
//    lateinit var retrofit: RetrofitApi
//    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//    FireStore().pullDoctorsOnFireStore()
//        retrofit = RetroFitClient.instance()!!
//        functions = FirebaseFunctions.getInstance()
        supportFragmentManager.setFragment(CheckFragment(), CheckFragment.tag)
        val actBar = SpannableString(title)
        actBar.setSpan(ForegroundColorSpan(Color.rgb(78, 78, 78)), 0, title.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        supportActionBar?.setTitle(actBar)
        //actBar.setSpan(BackgroundColorSpan(Color.rgb(240, 237, 245)), 0, 0, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
    }

    fun FragmentManager.setFragment(fragment: Fragment, tag: String){
        this.beginTransaction()
            .add(R.id.frame, fragment)
            .addToBackStack(null)
            .commit()
    }

}

