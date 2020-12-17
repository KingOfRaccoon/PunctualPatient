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
    private lateinit var functions : FirebaseFunctions
    lateinit var retrofit: RetrofitApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        retrofit = RetroFitClient.instance()!!
        functions = FirebaseFunctions.getInstance()
//        functions.getHttpsCallable("addMessage").call(hashMapOf(
//                "number1" to 3,
//                "number2" to 6
//        )).addOnCompleteListener {
//            Log.d("Fire", it.result.toString())
//        }
//                .addOnCompleteListener {
//            OnCompleteListener<Answer> {
//            Log.d("Fire", it.result.toString())
//            }
//        }
//                .addOnSuccessListener {
//            Log.d("Fire", it.toString())
//        }.addOnFailureListener {
//            Log.d("Fire", it.message.toString())
//        }
//        addMessage("Привет!")
//            .addOnCompleteListener {
//                if (!it.isSuccessful){
//                    val e = it.exception
//                    if (e is FirebaseFunctionsException){
//                        val code = e.code
//                        val details = e.details
//                        Log.d("Fire", code.toString() + " " + details.toString())
//                    }
//                }
//                else
//                    Log.d("Fire", it.result.toString())
//            }
//        retrofit.addMessage("1").enqueue(
//                object : Callback<Answer>{
//                    override fun onResponse(call: Call<Answer>, response: Response<Answer>) {
//                        Log.d("Fire", response.body().toString())
//                    }
//
//                    override fun onFailure(call: Call<Answer>, t: Throwable) {
//                        Log.d("Fire", t.message.toString())
//                    }
//                }
//        )
        retrofit.check().enqueue(
                object : Callback<MutableList<DataPerson>>{
                    override fun onResponse(call: Call<MutableList<DataPerson>>, response: Response<MutableList<DataPerson>>) {
                        response.body()?.forEach {
                            Log.d("Fire", it.toString())
                        }
                    }

                    override fun onFailure(call: Call<MutableList<DataPerson>>, t: Throwable) {
                        Log.d("Fire", t.message.toString())
                    }
                }
        )
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
    private fun addMessage(text: String): Task<String> {
        // Create the arguments to the callable function.
        val data = hashMapOf(
            "text" to text,
            "push" to true
        )

        return functions
            .getHttpsCallable("randomNum")
            .call()
            .continueWith { task ->
                // This continuation runs on either success or failure, but if the task
                // has failed then result will throw an Exception which will be
                // propagated down.
                val result = task.result?.data as String
                result
            }
    }


}