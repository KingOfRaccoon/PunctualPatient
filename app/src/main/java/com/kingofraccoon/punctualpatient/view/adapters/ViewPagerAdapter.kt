package com.kingofraccoon.punctualpatient.view.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.kingofraccoon.punctualpatient.fragment.FragmentRegSecond
import com.kingofraccoon.punctualpatient.view.fragment.FragmentRegFirst

class ViewPagerAdapter(fragment: Fragment): FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = 2
    override fun createFragment(position: Int): Fragment {
        if (position == 0)
            return FragmentRegFirst()
        else
            return FragmentRegSecond()
    }
}