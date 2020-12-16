package com.kingofraccoon.punctualpatient.encoder

import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey

class Cript {
    private val cipher: Cipher = Cipher.getInstance("AES")
    private var kgen = KeyGenerator.getInstance("AES").apply {
        init(128)
    }
    private var key: SecretKey = kgen.generateKey()

    fun encrypt(t: String): ByteArray{
        cipher.init(Cipher.ENCRYPT_MODE, key)
        var byte: ByteArray = cipher.doFinal(t.toByteArray())
        return byte
    }

    fun decrypt(t: ByteArray): String{
        cipher.init(Cipher.DECRYPT_MODE, key)
        var decriptbyte: ByteArray = cipher.doFinal(t)
        return String(decriptbyte)
    }
}