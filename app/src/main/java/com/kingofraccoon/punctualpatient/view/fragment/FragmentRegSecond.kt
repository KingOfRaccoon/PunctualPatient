package com.kingofraccoon.punctualpatient.view.fragment

import android.app.AlertDialog
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.ktx.Firebase
import com.kingofraccoon.punctualpatient.model.Person
import com.kingofraccoon.punctualpatient.R
import com.kingofraccoon.punctualpatient.User
import com.kingofraccoon.punctualpatient.User.password
import com.kingofraccoon.punctualpatient.auth.Authorization
import com.kingofraccoon.punctualpatient.tools.firebase.FireStore

class FragmentRegSecond: Fragment() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.frag_regist_second, container, false)
        val button: Button = view.findViewById(R.id.next_reg)
        val textAdress : EditText = view.findViewById(R.id.address_reg)
        val textName : EditText = view.findViewById(R.id.name)
        val textSecondName : EditText = view.findViewById(R.id.fam)
        val textLastName : EditText = view.findViewById(R.id.second_name)
        val textData : EditText = view.findViewById(R.id.editText)
        val radioMale : RadioButton = view.findViewById(R.id.male)
        val radioFemale : RadioButton = view.findViewById(R.id.female)
        radioMale.setOnClickListener {
            radioFemale.isChecked = false
        }
        radioFemale.setOnClickListener {
            radioMale.isChecked = false
        }

        textSecondName.addTextChangedListener { it: Editable? ->
            User.firstName = it.toString().trim()
        }

        textName.addTextChangedListener { it: Editable? ->
            User.secondName = it.toString().trim()
        }

        textLastName.addTextChangedListener { it: Editable? ->
            User.thirdName = it.toString().trim()
        }

        textData.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                val builder = AlertDialog.Builder(requireContext());
                val picker = DatePicker(requireContext());
                var pickingDate = ""
                picker.setOnDateChangedListener { view, year, monthOfYear, dayOfMonth ->
                    pickingDate = "$dayOfMonth.${monthOfYear + 1}.$year"
                }

                picker.calendarViewShown = false;

                builder.setTitle("Create Year");
                builder.setView(picker);
                builder.setNegativeButton("Cancel", null);
                builder.setPositiveButton("Set"
                ) { dialog, which ->
                    textData.setText(pickingDate)
                };

                builder.show();
            }
        }
        textData.addTextChangedListener { it : Editable? ->
            User.date = it.toString()
        }

        button.setOnClickListener {
            User.adress = textAdress.text.toString().trim()
            if (radioMale.isChecked)
                User.sex = "Мужчина"
            else
                User.sex = "Женщина"

                requireActivity().supportFragmentManager.beginTransaction()
                        .replace(R.id.frame, MainFragment())
                        .commit()
                FireStore().registerNewUserCrypt(User.uid,
                        Person(
                                User.adress,
                                User.date,
                                User.firstName + " " + User.secondName + " " + User.thirdName,
                                User.sex,
                                User.age,
                                User.number
                        )
                )
//                requireActivity().supportFragmentManager.beginTransaction()
//                        .replace(R.id.frame, MainFragment())
//                        .commit()
            }
        return view
    }

    fun updateUI(user:FirebaseUser?){ }
}