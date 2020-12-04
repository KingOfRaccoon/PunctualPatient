package com.kingofraccoon.punctualpatient.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.kingofraccoon.punctualpatient.MainActivity
import com.kingofraccoon.punctualpatient.R

class FragmentRegSecond: Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.frag_regist_second, container, false)
        val button: Button = view.findViewById(R.id.next)

        button.setOnClickListener {
            requireFragmentManager().beginTransaction()
                .add(R.id.frame, MainFragment())
                .addToBackStack(null)
                .commit()
        }

        return view
    }
}