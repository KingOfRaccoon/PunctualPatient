package com.kingofraccoon.punctualpatient.view.fragment

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseUser
import com.kingofraccoon.punctualpatient.GetTalonDoctorServise
import com.kingofraccoon.punctualpatient.R
import com.kingofraccoon.punctualpatient.User
import com.kingofraccoon.punctualpatient.auth.Authorization
import com.kingofraccoon.punctualpatient.model.Doctor
import com.kingofraccoon.punctualpatient.model.TypeDoctors
import com.kingofraccoon.punctualpatient.tools.encoder.Cript
import com.kingofraccoon.punctualpatient.tools.encoder.EncryptedSharedPreferencesUser
import com.kingofraccoon.punctualpatient.tools.firebase.FireStore

class AuthorizationFragment : Fragment() {
    var doctor: Doctor? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_check, container, false)
        val number_people: EditText = view.findViewById(R.id.phone_check)
        val password_people: EditText = view.findViewById(R.id.password_check)
        val button_check: Button = view.findViewById(R.id.button_check)
        val button_register: Button = view.findViewById(R.id.create_account)
        val text_view: TextView = view.findViewById(R.id.text_enter)
        val check_box: CheckBox = view.findViewById(R.id.check)
        text_view.text = "Вход"

        button_register.setOnClickListener {
            requireFragmentManager().beginTransaction()
                .add(R.id.frame, RegisterFragment())
                .addToBackStack(null)
                .commit()
        }
        number_people.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (android.util.Patterns.PHONE.matcher(number_people.text.toString()).matches())
                    button_check.isEnabled = true
                else {
                    button_check.isEnabled = false
                    number_people.setError("Неверный номер")
                }
            }

            override fun afterTextChanged(p0: Editable?) {}
        })

        button_check.setOnClickListener {

            var user: FirebaseUser? = null

            val login = "${number_people.text}@gmail.com"
            val password = password_people.text.toString()

            Authorization().singIn(login, password)
                .addOnCompleteListener { task ->

                    if (task.isSuccessful) {
                        user = task.result!!.user
                        User.uid = user?.uid.toString()
                        Log.d("Fire", User.uid)
                        Log.d("Fire", "True")

                        var check = false
                        if (user != null) {

                            if (check_box.isChecked)
                                EncryptedSharedPreferencesUser(requireContext())
                                    .auth(login, password)


                            FireStore().firebase.collection("doctors")
                                .document(user?.uid.toString())
                                .get()
                                .addOnSuccessListener { value ->
                                    if (value.exists()) {
                                        doctor = Doctor(
                                            value.getString("name") as String,
                                            (value.get("cabinet") as Long).toInt(),
                                            getEnumDoctor(value.getString("nameType") as String)!!,
                                            (value.get("start") as Long).toInt(),
                                            (value.get("end") as Long).toInt(),
                                            (value.get("duration") as Long).toInt(),
                                            value.getString("number") as String
                                        )
                                    }
                                    check = doctor != null
                                    if (check) {
                                        User.number = doctor?.number.toString()
                                        User.name = doctor?.name.toString()
                                        User.typeOfUser = "Doctor"
                                        requireActivity().startService(
                                            Intent(
                                                requireActivity(),
                                                GetTalonDoctorServise::class.java
                                            )
                                        )
                                        check(number_people, check)
                                    } else {
                                        User.typeOfUser = "User"
                                        FireStore().firebase
                                            .collection("usersCrypt")
                                            .document(user!!.uid)
                                            .get()
                                            .addOnCompleteListener { userDoc ->
                                                if (userDoc.result?.exists() == true) {
                                                    val person = Cript().decryptPersonForFireStore(
                                                        userDoc.result?.getString("text").toString()
                                                    )
                                                    Log.d("decrypt", "$person")
                                                    User.setUser(person)
                                                    check = User.name != ""
                                                    check(number_people, check)
                                                } else
                                                    check(number_people, check)
                                            }
                                            .addOnFailureListener {
                                                Log.d("Fire", it.message.toString())
                                            }
                                    }
                                }
                        }
                    } else {
                        Authorization().checkForMultiFactorFailure(task.exception!!)
                    }
                }
        }


        return view
    }

    fun Context.setToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    fun check(number_people: EditText, check: Boolean) {
        if (check) {
            if (User.typeOfUser == "User")
                requireFragmentManager().beginTransaction()
                    .replace(R.id.frame, MainFragment())
                    .commit()
            else
                requireFragmentManager().beginTransaction()
                    .replace(R.id.frame, ProfileFragment())
                    .commit()
        } else {
            number_people.setTextColor(resources.getColor(R.color.red))
            if (number_people.text.isNullOrEmpty()) {
                number_people.setHintTextColor(Color.RED)
                requireContext().setToast("Введите свой номер")
            } else {
                number_people.setTextColor(Color.RED)
                requireContext().setToast("Проверьте правильность введенного номера")
            }
        }
    }

    private fun getEnumDoctor(string: String): TypeDoctors? {
        return when (string) {
            TypeDoctors.CARDIOLOGIST.nameType -> TypeDoctors.CARDIOLOGIST
            TypeDoctors.PEDIATRICIAN.nameType -> TypeDoctors.PEDIATRICIAN
            TypeDoctors.SURGEON.nameType -> TypeDoctors.SURGEON
            TypeDoctors.TRAUMATOLOGIST.nameType -> TypeDoctors.TRAUMATOLOGIST
            else -> null
        }
    }

    companion object {
        val tag = "AuthorizationFragment"
    }
}