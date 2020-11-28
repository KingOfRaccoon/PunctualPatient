package com.kingofraccoon.punctualpatient.fragment

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kingofraccoon.punctualpatient.*
import java.time.LocalDate

class GetTalonFragment : Fragment(){
    val doctor5 = Doctor(
            "Костлев Алексей Владимирович",
            6,
            TypeDoctors.CARDIOLOGIST,
            6, 10, 30
    )
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_get_talon, container, false)
        val recyclerView : RecyclerView = view.findViewById(R.id.recycler)
        val talonAdapter = TalonAdapter()
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = talonAdapter
        val hospital = Hospital(mutableListOf(doctor5), "Больница №1")
        hospital.createTalons(LocalDate.now())
        hospital.timetables.forEach {
            talonAdapter.setList(it.talons)
        }

        return view
    }
}