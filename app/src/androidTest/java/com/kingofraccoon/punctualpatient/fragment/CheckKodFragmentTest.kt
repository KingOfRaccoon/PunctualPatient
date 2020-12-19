package com.kingofraccoon.punctualpatient.fragment

import androidx.test.espresso.Espresso.closeSoftKeyboard
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.replaceText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.android21buttons.fragmenttestrule.FragmentTestRule
import com.kingofraccoon.punctualpatient.MainActivity
import com.kingofraccoon.punctualpatient.R
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

//@DEPRECATED

//@RunWith(AndroidJUnit4::class)
//class CheckKodFragmentTest{
//    @Rule
//    @JvmField
//    val fragmentTestRule : FragmentTestRule<MainActivity, AuthorizationKodFragment> =
//            FragmentTestRule(MainActivity::class.java, AuthorizationKodFragment::class.java)
//
//    @Test
//    @Throws(Exception::class)
//    fun title_view(){
//        onView(withId(R.id.text_check))
//                .check(matches(withText("Подтверждение")))
//    }
//    @Test
//    @Throws(Exception::class)
//    fun input_number(){
//        onView(withId(R.id.kod_check))
//                .check(matches(withHint("Введите код из SMS")))
//                .perform(replaceText("123456"))
//                .check(matches(withText("123456")))
//        closeSoftKeyboard()
//    }
//
//    @Test
//    @Throws(Exception::class)
//    fun create_account_but(){
//        onView(withId(R.id.button_check_kod))
//                .check(matches(isDisplayed()))
//                .perform(click())
//    }
//}