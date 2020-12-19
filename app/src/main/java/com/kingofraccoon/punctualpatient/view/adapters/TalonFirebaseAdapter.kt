package com.kingofraccoon.punctualpatient.view.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.Query
import com.kingofraccoon.punctualpatient.LocalHospital
import com.kingofraccoon.punctualpatient.R
import com.kingofraccoon.punctualpatient.model.Talon


class TalonFirebaseAdapter(query : Query) : FirestoreAdapter<FirebaseTalonViewHolder>(query) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FirebaseTalonViewHolder {
        return FirebaseTalonViewHolder(LayoutInflater.from(parent.context)
                .inflate(R.layout.recycler_talon, parent, false))
    }

    override fun onBindViewHolder(holder: FirebaseTalonViewHolder, position: Int) {
        holder.bind(snapshots[position])
    }

}
open class FirebaseTalonViewHolder(view: View) : RecyclerView.ViewHolder(view){
    val cabinet : TextView = view.findViewById(R.id.number_cabinet)
    val dateAndTime : TextView = view.findViewById(R.id.date_and_time)
    val doctor : TextView = view.findViewById(R.id.doctor)
    val button : Button = view.findViewById(R.id.get_talon)
    open fun bind(snapshot: DocumentSnapshot) {
        if (snapshot.exists()) {
            val talon = Talon(
                    snapshot.getString("date") as String,
                    LocalHospital.doctors.find {
                       it.doctorID == snapshot.getString("doctorID")
                    }!!,
                    snapshot.getString("time") as String
            )
            dateAndTime.text = "${talon.date} \n ${talon.time}"
            doctor.text = "Врач: ${talon.doctor.name}"
            cabinet.text = "Кабинет №: ${talon.doctor.number_cabinet}"
        }
    }
}