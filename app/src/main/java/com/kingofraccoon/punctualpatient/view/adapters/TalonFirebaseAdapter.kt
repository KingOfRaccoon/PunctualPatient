package com.kingofraccoon.punctualpatient.view.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.*
import com.kingofraccoon.punctualpatient.LocalHospital
import com.kingofraccoon.punctualpatient.R
import com.kingofraccoon.punctualpatient.model.Talon
import com.kingofraccoon.punctualpatient.model.TalonData

interface OnRequestSelectedListener
{
    fun onRequestSelectedListener(requestData: DocumentSnapshot)
}

class TalonFirebaseAdapter(_query : Query)
    : RecyclerView.Adapter<TalonFirebaseAdapter.FirebaseTalonViewHolder>() {


    var talons = mutableListOf<TalonData>()

    var query : Query = _query
        set(value) {
//            stopListening()

//            snapshots.clear()
            notifyDataSetChanged()

            field = value
//            startListening()
        }

    init {
        query.addSnapshotListener(object : EventListener<QuerySnapshot> {
            override fun onEvent(value: QuerySnapshot?, error: FirebaseFirestoreException?) {
                update()
                for (item in value?.documents!!) {
                    var talon = TalonData(
                        item["date"].toString(),
                        item["doctorID"].toString(),
                        item["time"].toString(),
                        item["userID"].toString()
                    )
                    Log.d("TEST", talon.toString())
                    talons.add(talon)

                }
            }
        })
    }

    fun update(){
        talons.clear()
        notifyDataSetChanged()
    }

    override fun getItemCount() = talons.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FirebaseTalonViewHolder {
        return FirebaseTalonViewHolder(LayoutInflater.from(parent.context)
                .inflate(R.layout.recycler_talon, parent, false))
    }

    override fun onBindViewHolder(holder: FirebaseTalonViewHolder, position: Int) {
        holder.bind(talons[position])
    }

    inner class FirebaseTalonViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val cabinet : TextView = view.findViewById(R.id.number_cabinet)
        val dateAndTime : TextView = view.findViewById(R.id.date_and_time)
        val doctor : TextView = view.findViewById(R.id.doctor)
        val button : Button = view.findViewById(R.id.get_talon)

        open fun bind(talon :TalonData) {

            val docName = LocalHospital.hospital.doctors.find {
                it.doctorID == talon.doctorID
            }
            Log.d("Fire", docName.toString())
            dateAndTime.text = "${talon?.date} \n ${talon?.time}"
            doctor.text = "Врач: ${docName?.name ?: "Доктор не успел загрузиться"}"
            cabinet.text = "Кабинет №: ${docName?.number_cabinet ?: "Доктор не успел загрузиться"}"

        }
    }
}

