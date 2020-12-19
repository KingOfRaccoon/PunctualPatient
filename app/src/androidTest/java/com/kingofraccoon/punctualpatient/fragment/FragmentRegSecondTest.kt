package com.kingofraccoon.punctualpatient.fragment

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.android21buttons.fragmenttestrule.FragmentTestRule
import com.kingofraccoon.punctualpatient.MainActivity
import com.kingofraccoon.punctualpatient.R
import com.kingofraccoon.punctualpatient.view.fragment.FragmentRegSecond
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.lang.Exception

@RunWith(AndroidJUnit4::class)
class FragmentRegSecondTest{
    @Rule
    @JvmField
    val fragmentTestRule : FragmentTestRule<MainActivity, FragmentRegSecond> =
            FragmentTestRule(MainActivity::class.java, FragmentRegSecond::class.java)

    @Test
    @Throws(Exception::class)
    fun text_view(){
        onView(withId(R.id.text_register)).check(matches(withText("Дополнительная информация")))
    }

    @Test
    @Throws(Exception::class)
    fun editText_input(){
        onView(withId(R.id.address_reg))
                .check(matches(withHint("Адрес")))
                .perform(replaceText("ул Ленина, 150"))
                .check(matches(withText("ул Ленина, 150")))
        closeSoftKeyboard()

        onView(withId(R.id.mail_reg))
                .check(matches(withHint("Почта")))
                .perform(replaceText("1234@mail.ru"))
                .check(matches(withText("1234@mail.ru")))
        closeSoftKeyboard()

        onView(withId(R.id.age_reg))
                .check(matches(withHint("Возраст")))
                .perform(replaceText("15"))
                .check(matches(withText("15")))
        closeSoftKeyboard()

        onView(withId(R.id.number))
                .check(matches(withHint("Номер")))
                .perform(replaceText("89096753423"))
                .check(matches(withText("89096753423")))
        closeSoftKeyboard()
    }
    @Test
    @Throws(Exception::class)
    fun radio_but(){
        val female = onView(withId(R.id.female))
                female.perform(click())
                female.check(matches(isChecked()))

        onView(withId(R.id.male))
                .check(matches(isNotChecked()))
                .perform(click())
        female.check(matches(isNotChecked()))
    }
    @Test
    @Throws(Exception::class)
    fun but_finishRegister(){
        onView(withId(R.id.next_reg))
                .check(matches(isDisplayed()))
                .perform(click())
    }
}