package com.kingofraccoon.punctualpatient.fragment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.kingofraccoon.punctualpatient.Person
import com.kingofraccoon.punctualpatient.R
import com.kingofraccoon.punctualpatient.User
import com.kingofraccoon.punctualpatient.firebase.FireStore
import java.util.jar.Attributes

class FragmentRegFirst : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.frag_register_first, container, false)
        val textName : EditText = view.findViewById(R.id.name)
        val textSecondName : EditText = view.findViewById(R.id.fam)
        val textLastName : EditText = view.findViewById(R.id.second_name)
        val textData : EditText = view.findViewById(R.id.editText)

        textSecondName.addTextChangedListener(
                object : TextWatcher{
                    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                    }
                    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    }
                    override fun afterTextChanged(s: Editable?) {
                        User.firstName = s.toString().trim()
                    }
                }
        )

        textName.addTextChangedListener(
                object : TextWatcher{
                    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                    }
                    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    }

                    override fun afterTextChanged(s: Editable?) {
                        User.secondName = s.toString().trim()
                    }
                }
        )

        textLastName.addTextChangedListener(
                object : TextWatcher{
                    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                    }
                    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    }

                    override fun afterTextChanged(s: Editable?) {
                        User.thirdName = s.toString().trim()
                    }
                }
        )

        textData.addTextChangedListener(
                object : TextWatcher{
                    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                    }
                    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    }

                    override fun afterTextChanged(s: Editable?) {
                        User.date = s.toString()
                    }
                }
        )

        return view
    }
}