package com.kingofraccoon.punctualpatient.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Button
import android.widget.Spinner
import androidx.fragment.app.Fragment
import com.kingofraccoon.punctualpatient.CustomAdapter
import com.kingofraccoon.punctualpatient.LocalHospital
import com.kingofraccoon.punctualpatient.R
import com.kingofraccoon.punctualpatient.TypeDoctors

class FilterTalonFragment: Fragment() {
    var def_type_doctor = "Введите специализацию врача:"
    var typeDoctors = ""
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.filter_talon_fragment, container, false)
        val spinner : Spinner = view.findViewById(R.id.spinner_filter)
        val types = resources.getStringArray(R.array.typesDoctors).toMutableList()
        val customAdapter = CustomAdapter(requireContext())
        val button : Button = view.findViewById(R.id.button)

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
            requireFragmentManager().beginTransaction()
                    .replace(R.id.frame, GetTalonFragment(getEnumDoctor()!!))
                    .commit()
        }

        return view
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