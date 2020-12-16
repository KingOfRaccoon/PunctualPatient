package com.kingofraccoon.punctualpatient.fragment

import androidx.test.espresso.Espresso.onData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.android21buttons.fragmenttestrule.FragmentTestRule
import com.kingofraccoon.punctualpatient.MainActivity
import com.kingofraccoon.punctualpatient.R
import org.hamcrest.core.IsAnything.anything
import org.hamcrest.core.StringContains.containsString
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.lang.Exception

@RunWith(AndroidJUnit4::class)
class FilterTalonFragmentTest{
    @Rule @JvmField
    val fragmentTestRule : FragmentTestRule<MainActivity, FilterTalonFragment> =
        FragmentTestRule(MainActivity::class.java, FilterTalonFragment::class.java)

    @Test
    @Throws(Exception::class)
    fun spinnerTypeDoctor(){
        val type_doctor = listOf("Педиатор", "Кардиолог", "Травматолог", "Хирург")
            for (element in type_doctor.indices) {
                onView(withId(R.id.spinner_filter)).perform(click())
                onData(anything()).atPosition(element).perform(click())
                onView(withId(R.id.spinner_filter)).check(matches(withSpinnerText(containsString(type_doctor[element]))))
            }
    }
    @Test
    @Throws(Exception::class)
    fun clickBut_openGetTalonFragment(){
        onView(withId(R.id.take_talon))
                .check(matches(isDisplayed()))
                .perform(click())
    }
}