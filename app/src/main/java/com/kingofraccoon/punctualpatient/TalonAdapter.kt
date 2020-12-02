package com.kingofraccoon.punctualpatient

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TalonAdapter: RecyclerView.Adapter<TalonAdapter.Companion.TalonViewHolder>() {
    var listTalons = mutableListOf<Talon>()

    fun setList(mutableList: MutableList<Talon>){
        listTalons.addAll(mutableList)
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TalonViewHolder {
        return TalonViewHolder(LayoutInflater.from(parent.context)
                .inflate(R.layout.recycler_talon, parent, false))
    }

    override fun onBindViewHolder(holder: TalonViewHolder, position: Int) {
        holder.bind(listTalons[position])
    }

    override fun getItemCount(): Int = listTalons.size

    companion object{
        class TalonViewHolder(view: View) : RecyclerView.ViewHolder(view){
            //val number : TextView = view.findViewById(R.id.number)
            val dateAndTime : TextView = view.findViewById(R.id.date_and_time)
            val doctor : TextView = view.findViewById(R.id.doctor)
            fun bind(talon: Talon){
                //number.text = talon.number.toString()
                dateAndTime.text = "${talon.date} \n ${talon.time}"
                doctor.text = talon.doctor.toString()
            }
        }
    }
}