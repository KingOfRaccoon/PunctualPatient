package com.kingofraccoon.punctualpatient.encoder

import android.util.Log
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.SecretKeySpec

class Cript {
    private val cipher: Cipher = Cipher.getInstance("AES")
//    private var kgen = KeyGenerator.getInstance("AES").apply {
//        init(128)
//    }
//    private var key: SecretKey = kgen.generateKey()
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


}