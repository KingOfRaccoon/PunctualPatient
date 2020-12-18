package com.kingofraccoon.punctualpatient.fragment

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.android21buttons.fragmenttestrule.FragmentTestRule
import com.kingofraccoon.punctualpatient.MainActivity
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import com.kingofraccoon.punctualpatient.R
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CheckFragmentTest{
    @Rule
    @JvmField
    val fragmentTestRule : FragmentTestRule<MainActivity, AuthorizationFragment> =
            FragmentTestRule(MainActivity::class.java, AuthorizationFragment::class.java)

    @Test
    @Throws(Exception::class)
    fun text_view(){
        onView(withId(R.id.text_enter))
                .check(matches(withText("Вход")))
    }

    @Test
    @Throws(Exception::class)
    fun input_number(){
        onView(withId(R.id.phone_check))
                .check(matches(withHint("Введите номер телефона")))
                .perform(replaceText("89096753423"))
                .check(matches(withText("89096753423")))
        closeSoftKeyboard()
    }

    @Test
    @Throws(Exception::class)
    fun create_account_but(){
        onView(withId(R.id.button_check))
                .check(matches(isDisplayed()))
                .perform(click())
    }
}