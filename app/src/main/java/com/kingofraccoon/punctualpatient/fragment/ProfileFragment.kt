package com.kingofraccoon.punctualpatient.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ExpandableListAdapter
import android.widget.ExpandableListView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kingofraccoon.punctualpatient.*
import com.kingofraccoon.punctualpatient.firebase.FireStore
import com.tutorialwing.expandablelistview.ProfileExpandableListAdapter

class ProfileFragment: Fragment() {

    internal var adapter: ExpandableListAdapter ?= null
    internal var titleList: List<String> ?= null

    val number = 89059441402
    val mail = "qwert@mail.ru"
    val age = 18
    val address = "ул. Пионерская, дом 103, кв 60"


    val data: HashMap<String, List<String>>
        get() {
            val listData = HashMap<String, List<String>>()

            val redmiMobiles = ArrayList<String>()
            redmiMobiles.add("Адрес: $address")
            redmiMobiles.add("Возраст: $age")
            redmiMobiles.add("Телефон: $number}")
            redmiMobiles.add("Почта: ${mail}")

            listData["Полная информация"] = redmiMobiles

            return listData
        }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.profile_fragment, container, false)
        val recyclerView : RecyclerView = view.findViewById(R.id.user_talon)
        val talonAdapter = ProfileTalonAdapter()
        val doctorAdapter = DoctorAdapter()
        val nameUser : TextView = view.findViewById(R.id.full_name)
        val dateUser : TextView = view.findViewById(R.id.about)
        val sexUser : TextView = view.findViewById(R.id.sex)
        nameUser.text = User.name
        dateUser.text = "14-02-1981"
        sexUser.text = "Мужской"
        if (User.typeOfUser != "Doctor") {
            recyclerView.adapter = talonAdapter
            User.mutableLiveDataTalons.observe(viewLifecycleOwner, {
                talonAdapter.setList(it)
            })
        }
        else{
            recyclerView.adapter = doctorAdapter
            User.mutableLiveDataTalonsDoctor.observe(viewLifecycleOwner, {
//                doctorAdapter.setList(it)
            })
        }
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        val expandableListView = view.findViewById<ExpandableListView>(R.id.expandableListView)
        if (expandableListView != null) {
            val listData = data
            titleList = ArrayList(listData.keys)
            adapter = ProfileExpandableListAdapter(requireContext(), titleList as ArrayList<String>, listData)
            expandableListView.setAdapter(adapter)
            expandableListView.setOnGroupExpandListener { groupPosition ->  }

            expandableListView.setOnGroupCollapseListener { groupPosition ->  }

            expandableListView.setOnChildClickListener { parent, v, groupPosition, childPosition, id ->
                false
            }
        }
        return view
    }
}