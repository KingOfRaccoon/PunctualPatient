package com.kingofraccoon.punctualpatient.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kingofraccoon.punctualpatient.R
import com.kingofraccoon.punctualpatient.User
import com.kingofraccoon.punctualpatient.tools.encoder.EncryptedSharedPreferencesUser
import com.kingofraccoon.punctualpatient.tools.firebase.FireStore
import com.kingofraccoon.punctualpatient.view.adapters.DoctorAdapter
import com.kingofraccoon.punctualpatient.view.adapters.DoctorTalonFirebaseAdapter
import com.kingofraccoon.punctualpatient.view.adapters.ProfileExpandableListAdapter
import com.kingofraccoon.punctualpatient.view.adapters.TalonFirebaseAdapter

class ProfileFragment: Fragment() {
    companion object{
        val tag = "profile"
    }
//    init {
//        query = FireStore().firebase.collection("talons").whereEqualTo("userID", User.uid)
//    }

    internal var adapter: ExpandableListAdapter ?= null
    internal var titleList: List<String> ?= null

    val number = User.number
    val age = User.age
    val address = if (User.adress.isBlank()) "ул. Пионерская, дом 103, кв 60" else User.adress

    val data: HashMap<String, List<String>>
        get() {
            val listData = HashMap<String, List<String>>()

            val redmiMobiles = ArrayList<String>()
            redmiMobiles.add("Адрес: $address")
            redmiMobiles.add("Возраст: $age")
            redmiMobiles.add("Телефон: $number")

            listData["Полная информация"] = redmiMobiles

            return listData
        }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.profile_fragment, container, false)
        val query = FireStore().firebase.collection(FireStore.TALONS)
            .whereEqualTo("flag", true)

        val recyclerView : RecyclerView = view.findViewById(R.id.user_talon)


        val nameUser : TextView = view.findViewById(R.id.full_name)
        val dateUser : TextView = view.findViewById(R.id.about)
        val sexUser : TextView = view.findViewById(R.id.sex)
        val exitButton: ImageButton = view.findViewById(R.id.exit)

        nameUser.text = if (User.name != "") User.name else "${User.firstName} ${User.secondName}  ${User.thirdName}"
        dateUser.text = if (User.date.isBlank()) "14-02-1981" else User.date
        sexUser.text = if (User.sex.isBlank()) "Мужской" else User.sex
        if (User.typeOfUser != "Doctor") {
            val talonAdapter = TalonFirebaseAdapter(query)
            recyclerView.adapter = talonAdapter
            recyclerView.layoutManager = LinearLayoutManager(requireContext())
            view.findViewById<ProgressBar>(R.id.progress).isVisible = false
        }
        else{
            val doctorAdapter = DoctorTalonFirebaseAdapter(query.whereEqualTo("doctorID", User.uid))
            recyclerView.adapter = doctorAdapter
            recyclerView.layoutManager = LinearLayoutManager(requireContext())
            view.findViewById<ProgressBar>(R.id.progress).isVisible = false
        }
            User.mutableLiveDataTalonsDoctor.observe(viewLifecycleOwner, Observer{
//                doctorAdapter.setList(it)
            })

        exitButton.setOnClickListener {
            EncryptedSharedPreferencesUser(requireContext()).deauth()

            fragmentManager!!.beginTransaction()
                    .replace(R.id.frame, AuthorizationFragment())
                    .commit()


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
