package com.kingofraccoon.punctualpatient.view.fragment

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kingofraccoon.punctualpatient.LocalHospital.hospital
import com.kingofraccoon.punctualpatient.R
import com.kingofraccoon.punctualpatient.model.Talon
import com.kingofraccoon.punctualpatient.model.TypeDoctors
import com.kingofraccoon.punctualpatient.view.adapters.TalonAdapter
import java.time.LocalDate

class GetTalonFragment(var typeDoctors: TypeDoctors, var date: LocalDate) : Fragment() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_get_talon, container, false)
        val recyclerView: RecyclerView = root.findViewById(R.id.recycler)

        val talonAdapter = TalonAdapter()
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        val mutableListTalon = mutableListOf<Talon>()

        hospital.timetables.forEach {
            if (it.day == date)
                mutableListTalon.addAll(it.talons)
        }

        talonAdapter.addList(
            mutableListTalon.filter
            {
                it.doctor.typeDoctor == typeDoctors
            }.toMutableList()
        )

        recyclerView.adapter = talonAdapter

        return root
    }
}