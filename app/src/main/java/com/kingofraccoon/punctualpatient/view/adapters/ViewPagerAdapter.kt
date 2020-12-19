package com.kingofraccoon.punctualpatient.view.adapters

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.kingofraccoon.punctualpatient.view.fragment.FragmentRegFirst
import com.kingofraccoon.punctualpatient.view.fragment.FragmentRegSecond

class ViewPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = 2
    override fun createFragment(position: Int): Fragment {
        if (position == 0)
            return FragmentRegFirst()
        else
            return FragmentRegSecond()
    }
}