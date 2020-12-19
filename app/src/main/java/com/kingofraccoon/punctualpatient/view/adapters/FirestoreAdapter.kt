package com.kingofraccoon.punctualpatient.view.adapters

import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.*

abstract class FirestoreAdapter<VH : RecyclerView.ViewHolder>(_query : Query)
    : RecyclerView.Adapter<VH>() {

    companion object{
        val TAG = "Firestore Adapter"
    }

    var query : Query = _query
        set(value) {
//            stopListening()

//            snapshots.clear()
            notifyDataSetChanged()

            field = value
            startListening()
        }

    var registration : ListenerRegistration? = null
    var snapshots : ArrayList<DocumentSnapshot> = arrayListOf()

//    protected open fun onDocumentAdded(change : DocumentSnapshot){
//        snapshots.add(change)
//        notifyDataSetChanged()
////        notifyItemInserted()
//    }
//    protected open fun onDocumentModified(change: DocumentChange) {
//        if (change.oldIndex == change.newIndex) {
//            // Item changed but remained in same position
//            snapshots.set(change.oldIndex, change.document)
//            notifyItemChanged(change.oldIndex)
//        } else {
//            // Item changed and changed position
//            snapshots.removeAt(change.oldIndex)
//            snapshots.add(change.newIndex, change.document)
//            notifyItemMoved(change.oldIndex, change.newIndex)
//        }
//    }
//    protected open fun onDocumentRemoved(change: DocumentChange) {
//        snapshots.removeAt(change.oldIndex)
//        notifyItemRemoved(change.oldIndex)
//    }

    open fun startListening(){
        var listen = query.addSnapshotListener(object : EventListener<QuerySnapshot>{
            override fun onEvent(value: QuerySnapshot?, error: FirebaseFirestoreException?) {
                for(item in value?.documents!!){
                    Log.d("TEST", item.id.toString())
                    snapshots.add(item)
                    notifyDataSetChanged()
                }
            }
        })

//        if (registration == null)
//            registration = query.addSnapshotListener(object : EventListener<QuerySnapshot>{
//                override fun onEvent(value: QuerySnapshot?, error: FirebaseFirestoreException?) {
//                    Log.d("ASD","TODO(Not yet implemented)")
//                }
//            })
    }
//    open fun stopListening(){
//        registration?.let {
//            it.remove()
//            registration = null
//        }
//
//        snapshots.clear()
//        notifyDataSetChanged()
//    }

    override fun getItemCount() = snapshots.size

    protected open fun onError(e: FirebaseFirestoreException?) {}
    protected open fun onDataChanged(){}
}