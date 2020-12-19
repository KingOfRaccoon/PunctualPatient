package com.kingofraccoon.punctualpatient.view.fragment

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns.EMAIL_ADDRESS
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.util.PatternsCompat.EMAIL_ADDRESS
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseUser
import com.kingofraccoon.punctualpatient.*
import com.kingofraccoon.punctualpatient.auth.Authorization
import com.kingofraccoon.punctualpatient.model.Doctor
import com.kingofraccoon.punctualpatient.model.TypeDoctors
import com.kingofraccoon.punctualpatient.tools.encoder.Cript
import com.kingofraccoon.punctualpatient.tools.firebase.FireStore

class AuthorizationFragment: Fragment() {
    val CHANEL_ID = 1.toString()
    val kod = (1000..9999).random()
    var doctor : Doctor? = null
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ):View? {
        val view = inflater.inflate(R.layout.fragment_check, container, false)
        val number_people: EditText = view.findViewById(R.id.phone_check)
        val password_people: EditText = view.findViewById(R.id.password_check)
        val button_check: Button = view.findViewById(R.id.button_check)
        val button_register: Button = view.findViewById(R.id.create_account)
        val text_view: TextView = view.findViewById(R.id.text_enter)
        text_view.text = "Вход"

        button_register.setOnClickListener {
            requireFragmentManager().beginTransaction()
                    .add(R.id.frame, RegisterFragment())
                    .addToBackStack(null)
                    .commit()
        }
        number_people.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {  }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (android.util.Patterns.PHONE.matcher(number_people.text.toString()).matches())
                    button_check.isEnabled = true
                else{
                    button_check.isEnabled = false
                    number_people.setError("Неверный номер")
                }
            }
            override fun afterTextChanged(p0: Editable?) { }
        })

        button_check.setOnClickListener {
            var user: FirebaseUser? = null
            Authorization().singIn("${number_people.text}@gmail.com", password_people.text.toString())
                .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    user = task.result!!.user
                    Log.d("Fire", "True")
                    var check = false
                    if (user != null) {
                        FireStore().firebase.collection("doctors")
                                .whereEqualTo("number", user?.uid)
                                .get()
                                .addOnSuccessListener {
                                    it.documents.forEach { value ->
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
                                        requireActivity().startService(Intent(requireActivity(), GetTalonDoctorServise::class.java))
                                        check(number_people, check)
                                    } else {
                                        User.typeOfUser = "User"
                                        FireStore().firebase
                                                .collection("usersCrypt")
                                                .document(user!!.uid)
                                                .get()
                                                .addOnCompleteListener { userDoc ->
                                                    if (userDoc.result?.exists() == true) {
                                                        val person = Cript().decryptPersonForFireStore(userDoc.result?.getString("text").toString())
                                                        User.setUser(person)
                                                        check = User.name != ""
                                                        requireActivity().startService(Intent(requireActivity(), GenerateTalonService::class.java))
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
                requireFragmentManager().beginTransaction()
                        .replace(R.id.frame, MainFragment())
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
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//                val channel = NotificationChannel(CHANEL_ID, "My channel",
//                        NotificationManager.IMPORTANCE_HIGH)
//                channel.description = "My channel description"
//                channel.enableLights(true)
//                channel.lightColor = Color.RED
//                channel.enableVibration(false)
//                notificationManager.createNotificationChannel(channel)
//            }
//
//        } else {
//            number_people.setTextColor(resources.getColor(R.color.red))
//            if (number_people.text.isNullOrEmpty()) {
//                number_people.setHintTextColor(Color.RED)
//                requireContext().setToast("Введите свой номер")
//            } else {
//                number_people.setTextColor(Color.RED)
//                requireContext().setToast("Проверьте правильность введенного номера")
//            }
//        }
//    }
    private fun getEnumDoctor(string: String): TypeDoctors? {
        return when(string){
            TypeDoctors.CARDIOLOGIST.nameType -> TypeDoctors.CARDIOLOGIST
            TypeDoctors.PEDIATRICIAN.nameType -> TypeDoctors.PEDIATRICIAN
            TypeDoctors.SURGEON.nameType -> TypeDoctors.SURGEON
            TypeDoctors.TRAUMATOLOGIST.nameType -> TypeDoctors.TRAUMATOLOGIST
            else -> null
        }
    }