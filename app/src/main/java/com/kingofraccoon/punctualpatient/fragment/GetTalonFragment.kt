package com.kingofraccoon.punctualpatient.fragment

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kingofraccoon.punctualpatient.*
import com.kingofraccoon.punctualpatient.LocalHospital.hospital
import java.time.LocalDate

class GetTalonFragment(var typeDoctors: TypeDoctors) : Fragment(){
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_get_talon, container, false)
        val recyclerView : RecyclerView = view.findViewById(R.id.recycler)
        val talonAdapter = TalonAdapter()
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = talonAdapter
        val mutableListTalon = mutableListOf<Talon>()
        hospital.timetables.forEach {
            mutableListTalon.addAll(it.talons)
        }
        talonAdapter.setList(
                mutableListTalon.filter
                {
                    it.doctor.typeDoctor == typeDoctors
                }.toMutableList())

        return view
    }
}