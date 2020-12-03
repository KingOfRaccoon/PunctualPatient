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
import androidx.recyclerview.widget.RecyclerView
import com.kingofraccoon.punctualpatient.firebase.FireStore
import kotlin.coroutines.coroutineContext

open class TalonAdapter: RecyclerView.Adapter<TalonViewHolder>() {
    var listTalons = mutableListOf<Talon>()

    fun setList(mutableList: MutableList<Talon>){
        listTalons.addAll(mutableList)
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

