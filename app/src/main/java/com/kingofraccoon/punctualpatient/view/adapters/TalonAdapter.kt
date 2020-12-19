package com.kingofraccoon.punctualpatient.view.adapters

import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.kingofraccoon.punctualpatient.LocalHospital
import com.kingofraccoon.punctualpatient.R
import com.kingofraccoon.punctualpatient.User
import com.kingofraccoon.punctualpatient.model.Talon
import com.kingofraccoon.punctualpatient.tools.firebase.FireStore

open class TalonAdapter : RecyclerView.Adapter<TalonViewHolder>() {
    var listTalons = mutableListOf<Talon>()

    fun addList(mutableList: MutableList<Talon>) {
        listTalons.addAll(mutableList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TalonViewHolder {
        return RegisterTalonViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.recycler_talon, parent, false)
        )
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: TalonViewHolder, position: Int) {
        holder.bind(listTalons[position])
    }

    override fun getItemCount(): Int = listTalons.size

}

class ProfileTalonViewHolder(view: View) : TalonViewHolder(view) {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun bind(talon: Talon) {
        super.bind(talon)
        button.text = "Отменить талон"
        button.setOnClickListener {
            FireStore().firebase
        }
    }
}

class RegisterTalonViewHolder(view: View) : TalonViewHolder(view) {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun bind(talon: Talon) {
        super.bind(talon)
        button.setOnClickListener {
            FireStore().writeTalon(User.uid, talon)
        }
    }
}

open class TalonViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val cabinet: TextView = view.findViewById(R.id.number_cabinet)
    val dateAndTime: TextView = view.findViewById(R.id.date_and_time)
    val doctor: TextView = view.findViewById(R.id.doctor)
    val button: Button = view.findViewById(R.id.get_talon)
    open fun bind(talon: Talon) {

        dateAndTime.text = "${talon.date} \n ${talon.time}"
        doctor.text =
            "Врач: ${LocalHospital.hospital.doctors.find { talon.idDoctor == it.doctorID }?.name}"
        cabinet.text =
            "Кабинет №: ${LocalHospital.hospital.doctors.find { talon.idDoctor == it.doctorID }?.number_cabinet}"
    }
}



