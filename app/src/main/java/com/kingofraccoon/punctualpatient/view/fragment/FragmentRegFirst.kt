package com.kingofraccoon.punctualpatient.view.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseUser
import com.kingofraccoon.punctualpatient.R
import com.kingofraccoon.punctualpatient.User
import com.kingofraccoon.punctualpatient.auth.Authorization

class FragmentRegFirst : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.frag_register_first, container, false)
        val passwordRepeat: EditText = view.findViewById(R.id.password_repeat)
        val password: EditText = view.findViewById(R.id.password_reg)
        val textNumber: EditText = view.findViewById(R.id.number)
        val button_next: Button = view.findViewById(R.id.next)

        button_next.setOnClickListener {
            if (password.text.toString() == passwordRepeat.text.toString() &&
                !password.text.isNullOrBlank()
            ) {

                Authorization().register("${textNumber.text}@gmail.com", password.text.toString())
                    .addOnCompleteListener {
                        if (it.isSuccessful) {
                            val user = it.result?.user
                            updateUI(user)
                            User.uid = user?.uid.toString()
                        } else {
                            updateUI(null)
                            Log.d("Fire", it.exception.toString())
                        }
                    }
                requireActivity().supportFragmentManager.beginTransaction()
                    .replace(R.id.frame, FragmentRegSecond())
                    .commit()
            }
        }
        return view
    }

    fun updateUI(user: FirebaseUser?) {}
}