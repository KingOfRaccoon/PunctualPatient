package com.kingofraccoon.punctualpatient.view.adapters

import android.app.TimePickerDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kingofraccoon.punctualpatient.R
import com.kingofraccoon.punctualpatient.User
import com.kingofraccoon.punctualpatient.model.TalonUser
import com.kingofraccoon.punctualpatient.tools.firebase.FireStore

class DoctorAdapter : RecyclerView.Adapter<DoctorViewHolder>() {
    var listTalons = mutableListOf<TalonUser>(
        TalonUser(
            "04-12-2020",
            "Звездаков Александр Васильевич",
            "13:00"
        ),
        TalonUser(
            "04-12-2020",
            "Иванов Иван Иванович",
            "19:00"
        ),
        TalonUser(
            "05-12-2020",
            "Данилова Полина Сергеевна",
            "14:00"
        ),
        TalonUser(
            "05-12-2020",
            "Черников Федор Сергеевич",
            "15:00"
        ),
        TalonUser(
            "06-12-2020",
            "Пузанов Иван Васильевич",
            "17:00"
        )
    )

    fun setList(mutableList: MutableList<TalonUser>){
        listTalons.addAll(mutableList)
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DoctorViewHolder {
        return DoctorViewHolder(
            LayoutInflater.from(parent.context)
            .inflate(R.layout.recycler_talon, parent, false))
    }

    override fun onBindViewHolder(holder: DoctorViewHolder, position: Int) {
        holder.bind(listTalons[position])
    }

    override fun getItemCount(): Int = listTalons.size

}

class DoctorViewHolder(view: View): RecyclerView.ViewHolder(view){
    val cabinet : TextView = view.findViewById(R.id.number_cabinet)
    val dateAndTime : TextView = view.findViewById(R.id.date_and_time)
    val doctor : TextView = view.findViewById(R.id.doctor)
    val button : Button = view.findViewById(R.id.get_talon)
    fun bind(talon: TalonUser){
        doctor.text = talon.name
        dateAndTime.text = "${talon.date} \n ${talon.time}"

        button.text = "Отошел"
        button.setOnClickListener {
            var timeDamp = 0
            val timePicker = TimePickerDialog(it.context,
                { view, hourOfDay, minute ->
                    timeDamp = minute

                    FireStore().translationDoctorTalons(timeDamp, User.uid)

                }, 0, 10, false)
                .show()

        }
    }
}