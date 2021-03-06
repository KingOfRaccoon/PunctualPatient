package com.kingofraccoon.punctualpatient.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.kingofraccoon.punctualpatient.R

class MainFragment : Fragment() {

    companion object {
        val tag = "main"
    }

    fun FragmentManager.setFragment(fragment: Fragment, tag: String) {
        val frag = this.findFragmentByTag(tag)
        if (frag != null) {
            this.beginTransaction()
                .replace(R.id.main_frame, fragment)
                .commit()
        } else {
            this.beginTransaction()
                .add(R.id.main_frame, fragment, tag)
                .addToBackStack(null)
                .commit()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.main_fragment, container, false)
        requireFragmentManager().setFragment(ProfileFragment(), ProfileFragment.tag)
        val bottomNavigationView: BottomNavigationView = view.findViewById(R.id.bnv)
        bottomNavigationView.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.writeTalon -> requireFragmentManager().setFragment(
                    FilterTalonFragment(),
                    FilterTalonFragment.tag
                )
                R.id.profile -> requireFragmentManager().setFragment(
                    ProfileFragment(),
                    ProfileFragment.tag
                )
            }
            return@setOnNavigationItemSelectedListener true
        }
        return view
    }
}