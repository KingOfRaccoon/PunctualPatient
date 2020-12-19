package com.kingofraccoon.punctualpatient.view.fragment

import android.os.Build
import android.os.Bundle
import android.view.*
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kingofraccoon.punctualpatient.*
import com.kingofraccoon.punctualpatient.LocalHospital.hospital
import com.kingofraccoon.punctualpatient.model.Talon
import com.kingofraccoon.punctualpatient.model.TypeDoctors
import com.kingofraccoon.punctualpatient.view.adapters.TalonAdapter

class GetTalonFragment(var typeDoctors: TypeDoctors) : Fragment() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_get_talon, container, false)
        val recyclerView : RecyclerView = root.findViewById(R.id.recycler)

        val talonAdapter = TalonAdapter()
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        val mutableListTalon = mutableListOf<Talon>()

        hospital.timetables.forEach {
            mutableListTalon.addAll(it.talons)
        }

        talonAdapter.addList(
                mutableListTalon.filter
                {
                    it.doctor.typeDoctor == typeDoctors
                }.toMutableList())

        recyclerView.adapter = talonAdapter

        return root
    }

//    fun calendarListener():{
//        var calendarView = R.layout.calendarView
//        calendarView.setOnDateChangeListener { view; var year: Int; var month: Int; var dayOfMonth: Int ->
//            "$dayOfMonth.${month + 1}.$year"
//        }
//        return 0
//    }
//
//
//    calendarView.setOnClickListener {
//        val selectedDate = calendarView.date
//        val calendar = Calendar.getInstance()
//        calendar.timeInMillis = selectedDate
//        val dateFormatter = DateFormat.getDateInstance(DateFormat.MEDIUM)
//        var date = dateFormatter.format(calendar.time)
//    }




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