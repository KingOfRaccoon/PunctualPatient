package com.kingofraccoon.punctualpatient.view.adapters

import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.*

abstract class FirestoreAdapter<VH : RecyclerView.ViewHolder>(_query : Query)
    : RecyclerView.Adapter<VH>(),
    EventListener<QuerySnapshot> {
    companion object{
        val TAG = "Firestore Adapter"
    }

    var query : Query = _query
        set(value) {
            // Stop listening
            stopListening()

            // Clear existing data
            snapshots.clear()
            notifyDataSetChanged()

            // Listen to new query
            field = value
            startListening()
        }

    var registration : ListenerRegistration? = null
    var snapshots : ArrayList<DocumentSnapshot> = arrayListOf()

    override fun onEvent(value: QuerySnapshot?, error: FirebaseFirestoreException?) {
        // handle error
        if (error != null){
            Log.w(TAG, "onEvent:error", error);
            return
        }

        for (change in value?.documentChanges!!){
            var snapshot = change.document
            when (change.type){
                DocumentChange.Type.ADDED -> {
                    onDocumentAdded(change)
                }
                DocumentChange.Type.MODIFIED -> {
                    onDocumentModified(change)
                }
                DocumentChange.Type.REMOVED -> {
                    onDocumentRemoved(change)
                }
            }
        }
        onDataChanged();
    }
    protected open fun onDocumentAdded(change : DocumentChange){
        snapshots.add(change.newIndex, change.document)
        notifyItemInserted(change.newIndex)
    }
    protected open fun onDocumentModified(change: DocumentChange) {
        if (change.oldIndex == change.newIndex) {
            // Item changed but remained in same position
            snapshots.set(change.oldIndex, change.document)
            notifyItemChanged(change.oldIndex)
        } else {
            // Item changed and changed position
            snapshots.removeAt(change.oldIndex)
            snapshots.add(change.newIndex, change.document)
            notifyItemMoved(change.oldIndex, change.newIndex)
        }
    }
    protected open fun onDocumentRemoved(change: DocumentChange) {
        snapshots.removeAt(change.oldIndex)
        notifyItemRemoved(change.oldIndex)
    }

    open fun startListening(){
        if (registration == null)
            registration = query.addSnapshotListener(this)
    }
    open fun stopListening(){
        registration?.let {
            it.remove()
            registration = null
        }

        snapshots.clear()
        notifyDataSetChanged()
    }

    override fun getItemCount() = snapshots.size

    protected open fun onError(e: FirebaseFirestoreException?) {}
    protected open fun onDataChanged(){}
}