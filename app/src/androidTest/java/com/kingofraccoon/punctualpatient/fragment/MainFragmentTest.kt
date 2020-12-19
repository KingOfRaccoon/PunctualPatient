package com.kingofraccoon.punctualpatient.fragment

import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.android21buttons.fragmenttestrule.FragmentTestRule
import com.kingofraccoon.punctualpatient.MainActivity
import com.kingofraccoon.punctualpatient.R
import com.kingofraccoon.punctualpatient.view.fragment.MainFragment
import junit.framework.TestCase
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainFragmentTest{
    @Rule@JvmField
    val fragmentTestRule : FragmentTestRule<MainActivity, MainFragment> =
            FragmentTestRule(MainActivity::class.java, MainFragment::class.java)

    @Test
    fun clickButNavWrite(){
        Espresso.onView(withId(R.id.writeTalon))
                .perform(click())
                .check(matches(isDisplayed()))
    }
    @Test
    fun clickButNavProfile(){
        Espresso.onView(withId(R.id.profile))
                .perform(click())
                .check(matches(isDisplayed()))
    }

}