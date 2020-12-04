package com.kingofraccoon.punctualpatient.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.kingofraccoon.punctualpatient.R
import com.kingofraccoon.punctualpatient.ViewPagerAdapter

class RegisterFragment: Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_register, container, false)

        val viewPager2 : ViewPager2 = view.findViewById(R.id.viewPager2)
        viewPager2.adapter = ViewPagerAdapter(this)

        return view
    }
}