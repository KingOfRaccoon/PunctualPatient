package com.kingofraccoon.punctualpatient.view.fragment

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.EditText
import androidx.annotation.RequiresApi

import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import com.kingofraccoon.punctualpatient.R
import com.kingofraccoon.punctualpatient.User

class FragmentRegFirst : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.frag_register_first, container, false)
        val textName : EditText = view.findViewById(R.id.name)
        val textSecondName : EditText = view.findViewById(R.id.fam)
        val textLastName : EditText = view.findViewById(R.id.second_name)
        val textData : EditText = view.findViewById(R.id.editText)

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
                        pickingDate = "$dayOfMonth.$monthOfYear.$year"
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



            return view
        }
}