package com.kingofraccoon.punctualpatient.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.kingofraccoon.punctualpatient.R
import com.kingofraccoon.punctualpatient.User

class CheckKodFragment(var kod: Int): Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.check_kod_fragment, container, false)
        val editText : EditText = view.findViewById(R.id.kod_check)
        val button : Button = view.findViewById(R.id.button_check_kod)
        val checkBox: CheckBox = view.findViewById(R.id.check)

        button.setOnClickListener {
            if (editText.text.toString().toInt() == kod){
//                Toast.makeText(requireContext(), "True", Toast.LENGTH_SHORT).show()
                if (User.typeOfUser == "User")
                    requireFragmentManager().beginTransaction()
                        .replace(R.id.frame, MainFragment())
                        .commit()
                else
                    requireFragmentManager().beginTransaction()
                            .replace(R.id.frame, ProfileFragment())
                            .commit()
            }
        }

        checkBox.setOnClickListener{

        }

        return view
    }
}