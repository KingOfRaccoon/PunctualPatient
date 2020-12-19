package com.kingofraccoon.punctualpatient.view.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ExpandableListAdapter
import android.widget.ExpandableListView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.Query
import com.kingofraccoon.punctualpatient.*
import com.kingofraccoon.punctualpatient.tools.firebase.FireStore
import com.kingofraccoon.punctualpatient.model.Doctor
import com.kingofraccoon.punctualpatient.model.Talon
import com.kingofraccoon.punctualpatient.view.adapters.DoctorAdapter
import com.kingofraccoon.punctualpatient.view.adapters.ProfileExpandableListAdapter
import com.kingofraccoon.punctualpatient.view.adapters.ProfileTalonAdapter
import com.kingofraccoon.punctualpatient.view.adapters.TalonFirebaseAdapter

class ProfileFragment: Fragment() {
    lateinit var query : Query
    companion object{
        val tag = "profile"
    }
    init {
        query = FireStore().firebase.collection("talon").whereEqualTo("userID", User.uid)
    }

    internal var adapter: ExpandableListAdapter ?= null
    internal var titleList: List<String> ?= null

    val number = User.number
    val password = User.password
    val age = User.age
    val address = if (User.adress.isBlank()) "ул. Пионерская, дом 103, кв 60" else User.adress

    val data: HashMap<String, List<String>>
        get() {
            val listData = HashMap<String, List<String>>()

            val redmiMobiles = ArrayList<String>()
            redmiMobiles.add("Адрес: $address")
            redmiMobiles.add("Возраст: $age")
            redmiMobiles.add("Телефон: $number")
            redmiMobiles.add("Почта: $password")

            listData["Полная информация"] = redmiMobiles

            return listData
        }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.profile_fragment, container, false)
        val recyclerView : RecyclerView = view.findViewById(R.id.user_talon)
        val talonAdapter = TalonFirebaseAdapter(query)
        val doctorAdapter = DoctorAdapter()
        val nameUser : TextView = view.findViewById(R.id.full_name)
        val dateUser : TextView = view.findViewById(R.id.about)
        val sexUser : TextView = view.findViewById(R.id.sex)
        nameUser.text = if (User.name != "") User.name else "${User.firstName} ${User.secondName}  ${User.thirdName}"
        dateUser.text = if (User.date.isBlank()) "14-02-1981" else User.date
        sexUser.text = if (User.sex.isBlank()) "Мужской" else User.sex
        if (User.typeOfUser != "Doctor") {
            recyclerView.adapter = talonAdapter
//            User.mutableLiveDataTalons.observe(viewLifecycleOwnerLiveData.value!!, {
//                talonAdapter.setList(it)
//            })
            val doctors = mutableListOf<Doctor>()
            var talons = mutableListOf<Talon>()

//            val task = FireStore().firebase.collection("doctors").get()
//
//            task.addOnSuccessListener {
//                it.documents.forEach { value ->
//                    doctors.add(
//                            Doctor(
//                                    value.getString("name") as String,
//                                    (value.get("cabinet") as Long).toInt(),
//                                    FireStore().getEnumDoctor(value.getString("nameType") as String)!!,
//                                    (value.get("start") as Long).toInt(),
//                                    (value.get("end") as Long).toInt(),
//                                    (value.get("duration") as Long).toInt(),
//                                    value.getString("number") as String
//                            )
//                    )
//                }
//                Log.d("Fire", doctors.size.toString())
//            }
//                    .continueWith {
//
//                            FireStore().firebase.collection("talons")
//                                    .whereEqualTo("userID", User.number)
//                                    .get()
//                                    .addOnSuccessListener {
//                                            it.documents.forEach {
//                                                talonAdapter.addTalon(
//                                                        Talon(
//                                                                it.getString("date") as String,
//                                                                doctors.find { doc ->
//                                                                    doc.number == it.getString("doctorID")
//                                                                }!!,
//                                                                it.getString("time") as String
//                                                        ).apply { uuid = it.id }
//                                                )
//                                            }
//
//                                        Log.d("Fire", talonAdapter.listTalons.size.toString())
//                                        view.findViewById<ProgressBar>(R.id.progress)
//                                                .isVisible = false
//                                    }
//                        it.continueWith {
//                        }
//                    }
//            talonAdapter.setList(User.mutableListTalon)
        }
        else{
            recyclerView.adapter = doctorAdapter
            User.mutableLiveDataTalonsDoctor.observe(viewLifecycleOwner, Observer{
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