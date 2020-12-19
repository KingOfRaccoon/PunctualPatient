package com.kingofraccoon.punctualpatient.fragment

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.kingofraccoon.punctualpatient.Person
import com.kingofraccoon.punctualpatient.R
import com.kingofraccoon.punctualpatient.User
import com.kingofraccoon.punctualpatient.firebase.FireStore

class FragmentRegSecond: Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.frag_regist_second, container, false)
        val button: Button = view.findViewById(R.id.next_reg)
        val textAdress : EditText = view.findViewById(R.id.address_reg)
        val textEmail : EditText = view.findViewById(R.id.password_reg)
        val textAge : EditText = view.findViewById(R.id.age_reg)
        val textNumber : EditText = view.findViewById(R.id.number)
        val radioMale : RadioButton = view.findViewById(R.id.male)
        val radioFemale : RadioButton = view.findViewById(R.id.female)
        radioMale.setOnClickListener {
            radioFemale.isChecked = false
        }
        radioFemale.setOnClickListener {
            radioMale.isChecked = false
        }

        button.setOnClickListener {
//            User.adress = Adress.instance(textAdress.text.toString().trim())
            User.adress = textAdress.text.toString().trim()
            User.email = textEmail.text.toString().trim()
            User.age = if (!textAge.text.isNullOrBlank()) textAge.text.toString().trim().toInt() else 0
            User.number = textNumber.text.toString()
            if (radioMale.isChecked)
                User.sex = "Мужчина"
            else
                User.sex = "Женщина"
            if(!textNumber.text.isNullOrBlank()) {
                requireActivity().supportFragmentManager.beginTransaction()
                        .replace(R.id.frame, MainFragment())
                        .commit()
                FireStore().registerNewUserCrypt(textNumber.text.toString().trim(),
                        Person(
                                User.adress,
                                User.date,
                                User.email,
                                User.firstName + " " + User.secondName + " " + User.thirdName,
                                User.sex,
                                User.age,
                                User.number
                        ))
            }
            else{
                textNumber.setTextColor(Color.RED)
                textNumber.setHintTextColor(Color.RED)
                Toast.makeText(requireContext(), "Введите свой номер телефона", Toast.LENGTH_SHORT).show()
            }
        }
        return view
    }
}