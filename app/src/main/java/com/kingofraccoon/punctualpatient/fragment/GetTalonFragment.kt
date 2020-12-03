package com.kingofraccoon.punctualpatient.fragment

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.ImageButton
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kingofraccoon.punctualpatient.*
import com.kingofraccoon.punctualpatient.LocalHospital.hospital
import com.kingofraccoon.punctualpatient.firebase.FireStore
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

       /* val duration = Toast.LENGTH_SHORT
        val toast = Toast.makeText(context, "Menu", duration)
        val but_popup: ImageButton = view!!.findViewById(R.id.dropdown_menu)

        val popupMenu = PopupMenu(requireContext(), but_popup)

        but_popup.setOnClickListener {
            fun onClick(v: View){
                popupMenu.inflate(R.menu.talon_menu)
                popupMenu.show()
            }
        }
        fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater): Boolean {
            view = MenuInflater
            inflater.inflate(R.menu.talon_menu, menu)
            return true
        }*/

        /*val collapsibleCalendar : CollapsibleCalendar = view.findViewById(R.id.calendarView)
        collapsibleCalendar.setCalendarListener(CollapsibleCalendar.CalendarListener{
            fun onDaySelect(){
                val day: Day = viewCalendar.getSelectedDay(this)
                Log.i(getClass().getName(), "Selected Day: "
                        + day.getYear() + "/" + (day.getMonth() + 1) + "/" + day.getDay())
            }
            fun onItemClick(view: View) {  }
            fun onDataUpdate(view: View) {  }
            fun onMonthChange(view: View) {  }
            fun onWeekChange(view: View) {  }

        })*/
        return view
    }
    /*popupMenu.setOnMenuItemClickListener {
        when (it.itemId) {
            R.id.in_full -> {
                toast.show()
                true
            }
            R.id.remind -> {
                toast.show()
                true
            }
            else -> false
        }
    }
*/
}