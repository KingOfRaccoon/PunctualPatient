package com.kingofraccoon.punctualpatient.fragment

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.android21buttons.fragmenttestrule.FragmentTestRule
import com.kingofraccoon.punctualpatient.MainActivity
import com.kingofraccoon.punctualpatient.R
import junit.framework.TestCase
import org.hamcrest.core.AllOf.allOf
import org.hamcrest.core.StringEndsWith.endsWith
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.lang.Exception

@RunWith(AndroidJUnit4::class)
class FragmentRegFirstTest{
    @Rule @JvmField
    val fragmentTestRule : FragmentTestRule<MainActivity, FragmentRegFirst> =
        FragmentTestRule(MainActivity::class.java, FragmentRegFirst::class.java)

    @Test
    @Throws(Exception::class)
    fun text_view(){
        onView(withId(R.id.text_register)).check(matches(withText("Регистрация")))
    }

    @Test
    @Throws(Exception::class)
    fun editText_input(){
        onView(withId(R.id.name))
                .check(matches(withHint("Имя")))
                .perform(replaceText("Борис"))
                .check(matches(withText("Борис")))
        closeSoftKeyboard()

        onView(withId(R.id.fam))
                .check(matches(withHint("Фамилия")))
                .perform(replaceText("Иванов"))
                .check(matches(withText("Иванов")))
        closeSoftKeyboard()

        onView(withId(R.id.second_name))
                .check(matches(withHint("Отчество")))
                .perform(replaceText("Инокентиевич"))
                .check(matches(withText("Инокентиевич")))
        closeSoftKeyboard()

        onView(withId(R.id.editText))
                .check(matches(withHint("Дата рождения")))
                .perform(replaceText("10.11.2012"))
                .check(matches(withText("10.11.2012")))
        closeSoftKeyboard()
    }
}
