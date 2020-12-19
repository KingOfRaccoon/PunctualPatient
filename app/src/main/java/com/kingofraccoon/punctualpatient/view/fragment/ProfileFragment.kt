package com.kingofraccoon.punctualpatient.view.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ExpandableListAdapter
import android.widget.ExpandableListView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.Query
import com.kingofraccoon.punctualpatient.R
import com.kingofraccoon.punctualpatient.User
import com.kingofraccoon.punctualpatient.tools.firebase.FireStore
import com.kingofraccoon.punctualpatient.view.adapters.ProfileExpandableListAdapter
import com.kingofraccoon.punctualpatient.view.adapters.TalonFirebaseAdapter

class ProfileFragment: Fragment() {
    companion object{
        val tag = "profile"
    }

    var query : Query
    init {
        if (User.typeOfUser != "Doctor") {
            query = FireStore().firebase.collection("talons")//.whereEqualTo("userID", User.uid)
        }else{
            query = FireStore().firebase.collection("talons")//.whereEqualTo("userID", User.uid)
        }
    }

    private var expandableListAdapter: ExpandableListAdapter ?= null
    private var titleList: List<String> ?= null

    val number = User.number
    val age = User.age
    val address = User.adress

    val profileData: HashMap<String, List<String>>
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
        val root = inflater.inflate(R.layout.profile_fragment, container, false)

        val recyclerViewTalons : RecyclerView = root.findViewById(R.id.user_talon)
//        Log.d("CreateFragment", query)
        Log.d("CreateFragment", query.toString())
        val talonFirebaseAdapter = TalonFirebaseAdapter(query/*,
            object : TalonFirebaseAdapter.OnRequestSelectedListener {
                override fun onRequestSelectedListener(requestData: DocumentSnapshot) {

                }
            }*/)


        val nameUser : TextView = root.findViewById(R.id.full_name)
        val dateUser : TextView = root.findViewById(R.id.about)
        val sexUser : TextView = root.findViewById(R.id.sex)

        nameUser.text = if (User.name != "") User.name else "${User.firstName} ${User.secondName}  ${User.thirdName}"
        dateUser.text = if (User.date.isBlank()) "14-02-1981" else User.date
        sexUser.text = if (User.sex.isBlank()) "Мужской" else User.sex


        recyclerViewTalons.adapter = talonFirebaseAdapter
        recyclerViewTalons.layoutManager = LinearLayoutManager(requireContext())


        val expandableListView = root.findViewById<ExpandableListView>(R.id.expandableListView)
        if (expandableListView != null)
            {
                val listData = profileData
                titleList = ArrayList(listData.keys)
                expandableListAdapter =
                    ProfileExpandableListAdapter(requireContext(), titleList as ArrayList<String>, listData)
                expandableListView.setAdapter(expandableListAdapter)
                expandableListView.setOnGroupExpandListener { groupPosition -> }

                expandableListView.setOnGroupCollapseListener { groupPosition -> }

                expandableListView.setOnChildClickListener { parent, v, groupPosition, childPosition, id ->
                    false
                }
            }

        return root
    }
}