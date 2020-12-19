package com.kingofraccoon.punctualpatient.fragment

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.swipeLeft
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.android21buttons.fragmenttestrule.FragmentTestRule
import com.kingofraccoon.punctualpatient.MainActivity
import com.kingofraccoon.punctualpatient.R
import com.kingofraccoon.punctualpatient.view.fragment.RegisterFragment
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class RegisterFragmentTest{
    @Rule @JvmField
    val fragmentTestRule : FragmentTestRule<MainActivity, RegisterFragment> =
        FragmentTestRule(MainActivity::class.java, RegisterFragment::class.java)

    @Test
    @Throws(Exception::class)
    fun swipe_reg(){
        onView(withId(R.id.viewPager2))
            .perform(swipeLeft())
    }
}