package com.kingofraccoon.punctualpatient.view.fragment

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.kingofraccoon.punctualpatient.LocalHospital
import com.kingofraccoon.punctualpatient.view.adapters.CustomAdapter
import com.kingofraccoon.punctualpatient.R
import com.kingofraccoon.punctualpatient.model.TypeDoctors
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class FilterTalonFragment: Fragment() {

    companion object{
        val tag = "filtertalon"
    }
    var talondate = ""
    var def_type_doctor = "Введите специальность врача:"
    var typeDoctors = ""
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.filter_talon_fragment, container, false)
        val calendar: CalendarView = root.findViewById(R.id.calendarView)
        val spinner : Spinner = root.findViewById(R.id.spinner_filter)
        val types = resources.getStringArray(R.array.typesDoctors).toMutableList()
        val customAdapter = CustomAdapter(requireContext())
        val button : Button = root.findViewById(R.id.take_talon)

        calendar.setOnDateChangeListener { view, year, month, dayOfMonth ->
            talondate = "$dayOfMonth-${month + 1}-$year"
        }

        customAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item)
        customAdapter.addAll(types)
        customAdapter.add(def_type_doctor)
        spinner.adapter = customAdapter
        spinner.setSelection(customAdapter.count)



        spinner.onItemSelectedListener = object :AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if (spinner.selectedItem != def_type_doctor)
                    typeDoctors = types[position]
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        button.setOnClickListener {
            if (typeDoctors != "") {
                LocalHospital.hospital.createTalons(LocalDate.parse(talondate, DateTimeFormatter.ofPattern("dd-MM-yyyy")))
                requireFragmentManager().beginTransaction()
                        .replace(R.id.main_frame, GetTalonFragment(getEnumDoctor()!!, LocalDate.parse(talondate, DateTimeFormatter.ofPattern("dd-MM-yyyy"))))
                        .commit()
            }
            else{
                Toast.makeText(requireContext(), "Выберете специальность врача", Toast.LENGTH_SHORT).show()
            }
        }

        return root
    }
    private fun getEnumDoctor(): TypeDoctors? {
        return when(typeDoctors){
            TypeDoctors.CARDIOLOGIST.nameType -> TypeDoctors.CARDIOLOGIST
            TypeDoctors.PEDIATRICIAN.nameType -> TypeDoctors.PEDIATRICIAN
            TypeDoctors.SURGEON.nameType -> TypeDoctors.SURGEON
            TypeDoctors.TRAUMATOLOGIST.nameType -> TypeDoctors.TRAUMATOLOGIST
            else -> null
        }
    }
}