package com.kingofraccoon.punctualpatient.view.fragment

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Button
import android.widget.CalendarView
import android.widget.Spinner
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.kingofraccoon.punctualpatient.view.adapters.CustomAdapter
import com.kingofraccoon.punctualpatient.R
import com.kingofraccoon.punctualpatient.model.TypeDoctors

class FilterTalonFragment: Fragment() {

    companion object{
        val tag = "filtertalon"
    }
    var def_type_doctor = "Введите специализацию врача:"
    var typeDoctors = ""
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.filter_talon_fragment, container, false)
        val calendar: CalendarView = root.findViewById(R.id.calendarView)
        val spinner : Spinner = root.findViewById(R.id.spinner_filter)
        val types = resources.getStringArray(R.array.typesDoctors).toMutableList()
        val customAdapter = CustomAdapter(requireContext())

        calendar.setOnDateChangeListener { view, year, month, dayOfMonth ->
            var talondate = "$dayOfMonth-${month + 1}calender$year"
        }

        spinner.adapter = customAdapter
        spinner.setSelection(customAdapter.count)
        customAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item)
        customAdapter.addAll(types)
        customAdapter.add(def_type_doctor)

        val button : Button = root.findViewById(R.id.take_talon)


        spinner.onItemSelectedListener = object :AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if (spinner.selectedItem != def_type_doctor)
                    typeDoctors = types[position]
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        button.setOnClickListener {
            requireFragmentManager().beginTransaction()
                    .replace(R.id.main_frame, GetTalonFragment(getEnumDoctor()!!))
                    .commit()
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