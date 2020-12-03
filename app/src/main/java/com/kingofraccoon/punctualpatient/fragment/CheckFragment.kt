package com.kingofraccoon.punctualpatient.fragment

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
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
import com.google.firebase.firestore.FirebaseFirestore
import com.kingofraccoon.punctualpatient.R
import com.kingofraccoon.zik.CheckKodFragment

class CheckFragment: Fragment() {
    val CHANEL_ID = 1.toString()
    val kod = (1000..9999).random()
    val db = FirebaseFirestore.getInstance()
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_check, container, false)
        val number_people : EditText = view.findViewById(R.id.phone_check)
        val button_check : Button = view.findViewById(R.id.button_check)
        button_check.setOnClickListener {
            var check = false
            if (!number_people.text.isNullOrEmpty()) {
                db.collection("users")
                        .document("workers")
                        .get()
                        .addOnCompleteListener { doc ->
                            check = doc.result?.getBoolean(number_people.text.toString()) == true
                            check(number_people, check)
                        }.addOnFailureListener {
                            Log.d("FIRE", it.message.toString())
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
//            requireContext().setToast("True")
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
            number_people.background.setColorFilter(
                    resources.getColor(R.color.red),
                    PorterDuff.Mode.SRC_ATOP
            )
            if (number_people.text.isNullOrEmpty()) {
                number_people.setHintTextColor(Color.RED)
                requireContext().setToast("Введите свой номер")
            } else {
                number_people.setTextColor(Color.RED)
                requireContext().setToast("Проверьте правильность введенного номера")
            }
        }
    }
}