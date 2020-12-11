package com.kingofraccoon.punctualpatient

import android.os.Build
import android.content.Context
import android.view.LayoutInflater
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.PopupMenu
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.kingofraccoon.punctualpatient.firebase.FireStore
import kotlin.coroutines.coroutineContext

open class TalonAdapter: RecyclerView.Adapter<TalonViewHolder>() {
    var listTalons = mutableListOf<Talon>()

    fun addList(mutableList: MutableList<Talon>){
        listTalons.addAll(mutableList)
        notifyDataSetChanged()
    }

    fun setList(mutableList: MutableList<Talon>){
        listTalons = mutableList
        notifyDataSetChanged()
    }

    fun addTalon(talon: Talon){
        listTalons.add(talon)
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TalonViewHolder {
        return RegisterTalonViewHolder(LayoutInflater.from(parent.context)
                .inflate(R.layout.recycler_talon, parent, false))
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: TalonViewHolder, position: Int) {
        holder.bind(listTalons[position])
    }

    override fun getItemCount(): Int = listTalons.size

}
class ProfileTalonAdapter: TalonAdapter(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TalonViewHolder {
        return ProfileTalonViewHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.recycler_talon, parent, false)
        )
    }
}

class ProfileTalonViewHolder(view: View): TalonViewHolder(view){
    @RequiresApi(Build.VERSION_CODES.O)
    override fun bind(talon: Talon) {
        super.bind(talon)
        button.text = "Подтвердить талон"
        button.setOnClickListener {

        }
    }
}

class RegisterTalonViewHolder(view: View): TalonViewHolder(view){
    @RequiresApi(Build.VERSION_CODES.O)
    override fun bind(talon: Talon) {
        super.bind(talon)
        button.setOnClickListener {
            FireStore().writeTalon(User.id, talon)
        }
    }
}

open class TalonViewHolder(view: View) : RecyclerView.ViewHolder(view){
    val cabinet : TextView = view.findViewById(R.id.number_cabinet)
    val dateAndTime : TextView = view.findViewById(R.id.date_and_time)
    val doctor : TextView = view.findViewById(R.id.doctor)
    val button : Button = view.findViewById(R.id.get_talon)
    @RequiresApi(Build.VERSION_CODES.O)
    open fun bind(talon: Talon){
        dateAndTime.text = "${talon.date} \n ${talon.time}"
        doctor.text = talon.doctor.toString()
        cabinet.text = "Кабинет №${talon.doctor.number_cabinet}"
    }
}
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
        return DoctorViewHolder(LayoutInflater.from(parent.context)
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
        button.isVisible = false
    }
}

