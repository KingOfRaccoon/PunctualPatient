package com.kingofraccoon.punctualpatient.tools.encoder

import android.util.Base64
import com.kingofraccoon.punctualpatient.model.Person
import javax.crypto.Cipher
import javax.crypto.SecretKey
import javax.crypto.spec.SecretKeySpec

class Cript {
    private val cipher: Cipher = Cipher.getInstance("AES")

    private val key: SecretKey = SecretKeySpec("Bar12345Bar12345".toByteArray(), "AES")

    fun encrypt(t: String): ByteArray{
        cipher.init(Cipher.ENCRYPT_MODE, key)
        val byte: ByteArray = cipher.doFinal(t.toByteArray())
        return byte
    }

    fun decrypt(t: ByteArray): String{
        cipher.init(Cipher.DECRYPT_MODE, key)
        val decriptbyte: ByteArray = cipher.doFinal(t)
        return String(decriptbyte)
    }

    fun cryptPersonForFireStore(person: Person):String{
        val encryptPerson = encrypt(CriptConverter().toJson(person))
        return Base64.encodeToString(encryptPerson, Base64.DEFAULT)
    }

    fun decryptPersonForFireStore(string: String): Person {
        val encryptPerson = Base64.decode(string, Base64.DEFAULT)
        return CriptConverter().fromJsontoPerson(decrypt(encryptPerson))
    }

}