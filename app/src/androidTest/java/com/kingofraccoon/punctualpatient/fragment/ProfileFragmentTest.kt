package com.kingofraccoon.punctualpatient.fragment

import androidx.recyclerview.widget.RecyclerView
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.android21buttons.fragmenttestrule.FragmentTestRule
import com.kingofraccoon.punctualpatient.CustomAdapter
import com.kingofraccoon.punctualpatient.MainActivity
import com.kingofraccoon.punctualpatient.ProfileTalonViewHolder
import com.kingofraccoon.punctualpatient.R
import junit.framework.TestCase
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ProfileFragmentTest{
    @Rule
    @JvmField
    val fragmentTestRule : FragmentTestRule<MainActivity, ProfileFragment> =
            FragmentTestRule(MainActivity::class.java, ProfileFragment::class.java)

    @Test
    @Throws(Exception::class)
    fun recycler_view_talon(){
        onView(withId(R.id.user_talon))
                .perform(actionOnItemAtPosition<ProfileTalonViewHolder>(0, click()))
    }
}