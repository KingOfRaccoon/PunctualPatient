package com.kingofraccoon.punctualpatient

import android.os.Build
import android.util.Base64.encodeToString
import android.util.Log
import androidx.annotation.RequiresApi
import com.google.firebase.firestore.DocumentSnapshot
import com.kingofraccoon.punctualpatient.tools.encoder.Cript
import com.kingofraccoon.punctualpatient.model.Person
import java.util.*
import kotlin.collections.HashMap

fun main(){
//    val person = Person("12345", "12345","12345","12345","12345",12345,"12345",)
//    FireStore().firebase
//            .collection("testCript")
//            .document("cript")
//            .set(cryptPerson(person))
//            .addOnSuccessListener {
//                print("Yes")
//            }
//            .addOnFailureListener {
//                print("No")
//            }

}

@RequiresApi(Build.VERSION_CODES.O)
fun cryptPerson(cript: Cript, person: Person): HashMap<String, String>{
    return hashMapOf(
            "adress" to Base64.getEncoder().encodeToString(cript.encrypt(person.adress)),
            "date" to Base64.getEncoder().encodeToString(cript.encrypt(person.date)),
            "email" to Base64.getEncoder().encodeToString(cript.encrypt(person.email)),
            "name" to Base64.getEncoder().encodeToString(cript.encrypt(person.name)),
            "sex" to Base64.getEncoder().encodeToString(cript.encrypt(person.sex)),
            "age" to Base64.getEncoder().encodeToString(cript.encrypt(person.age.toString())),
            "number" to Base64.getEncoder().encodeToString(cript.encrypt(person.number))
    )
}

fun encryptPerson(cript: Cript, doc: DocumentSnapshot): Person {
    return Person(
            cript.decrypt(android.util.Base64.decode(doc.get("adress") as String, android.util.Base64.DEFAULT)),
            cript.decrypt(android.util.Base64.decode(doc.get("date") as String, android.util.Base64.DEFAULT)),
            cript.decrypt(android.util.Base64.decode(doc.get("email") as String, android.util.Base64.DEFAULT)),
            cript.decrypt(android.util.Base64.decode(doc.get("name") as String, android.util.Base64.DEFAULT)),
            cript.decrypt(android.util.Base64.decode(doc.get("sex") as String, android.util.Base64.DEFAULT)),
            cript.decrypt(android.util.Base64.decode(doc.get("age") as String, android.util.Base64.DEFAULT)).toInt(),
            cript.decrypt(android.util.Base64.decode(doc.get("number") as String, android.util.Base64.DEFAULT))
    )
}