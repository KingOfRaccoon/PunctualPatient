package com.kingofraccoon.punctualpatient.fragment

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.content.Intent
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.fragment.app.Fragment
import com.kingofraccoon.punctualpatient.*
import com.kingofraccoon.punctualpatient.firebase.FireStore

class CheckFragment: Fragment() {
    val CHANEL_ID = 1.toString()
    val kod = (1000..9999).random()
    var doctor : Doctor? = null
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_check, container, false)
        val number_people : EditText = view.findViewById(R.id.phone_check)
        val button_check : Button = view.findViewById(R.id.button_check)
        val button_register : Button = view.findViewById(R.id.create_account)

        button_register.setOnClickListener {
            requireFragmentManager().beginTransaction()
                    .add(R.id.frame, RegisterFragment())
                    .addToBackStack(null)
                    .commit()
        }

        button_check.setOnClickListener {
            var check = false
            if (!number_people.text.isNullOrEmpty()) {
                FireStore().firebase.collection("doctors")
                        .whereEqualTo("number", number_people.text.toString())
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
                            }
                            else {
                                User.typeOfUser = "User"
                                FireStore().firebase
                                        .collection("users")
                                        .document(number_people.text.toString())
                                        .get()
                                        .addOnCompleteListener { userDoc ->
                                            if(userDoc.result?.exists() == true) {
                                                User.name = userDoc.result?.getString("name") as String
                                                User.sex = userDoc.result?.getString("sex") as String
                                                User.id = number_people.text.toString()
                                                User.date = userDoc.result?.getString("date") as String
                                                User.email = userDoc.result?.getString("email") as String
                                                User.adress = userDoc.result?.getString("adress") as String
                                                User.age = (userDoc.result?.getString("age").toString()).toInt()
                                                User.number = number_people.text.toString()
                                                check = User.name != ""
                                                requireActivity().startService(Intent(requireActivity(), GenerateTalonService::class.java))
                                                check(number_people, check)
                                            }
                                            else
                                                check(number_people, check)
                                        }
                                        .addOnFailureListener {
                                            Log.d("Fire", it.message.toString())
                                        }
                            }
                        }
            }
        }
        return view
    }

    fun Context.setToast(message: String){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
    fun check(number_people: EditText, check: Boolean){
        if (check) {
            val notificationManager = requireActivity().getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val channel = NotificationChannel(CHANEL_ID, "My channel",
                        NotificationManager.IMPORTANCE_HIGH)
                channel.description = "My channel description"
                channel.enableLights(true)
                channel.lightColor = Color.RED
                channel.enableVibration(false)
                notificationManager.createNotificationChannel(channel)
            }
            val builder = NotificationCompat.Builder(requireContext(), CHANEL_ID)
                    .setSmallIcon(R.drawable.ic_launcher_foreground)
                    .setContentTitle("Ваш код подверждения")
                    .setContentText("$kod - ваш код для подтверждения")
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .build()
            val nc = requireActivity().applicationContext.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            nc.notify(0, builder)
            requireFragmentManager().beginTransaction()
                .replace(R.id.frame, CheckKodFragment(kod))
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
        return when(string){
            TypeDoctors.CARDIOLOGIST.nameType -> TypeDoctors.CARDIOLOGIST
            TypeDoctors.PEDIATRICIAN.nameType -> TypeDoctors.PEDIATRICIAN
            TypeDoctors.SURGEON.nameType -> TypeDoctors.SURGEON
            TypeDoctors.TRAUMATOLOGIST.nameType -> TypeDoctors.TRAUMATOLOGIST
            else -> null
        }
    }
}