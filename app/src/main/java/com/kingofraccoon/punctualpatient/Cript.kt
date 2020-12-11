package com.kingofraccoon.punctualpatient

import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey

class Cript {
    var cipher: Cipher = Cipher.getInstance("AES")
    var kgen = KeyGenerator.getInstance("AES").apply {
        init(16)
    }
    var key: SecretKey = kgen.generateKey()

    fun encrypt(t: String){
        cipher.init(Cipher.ENCRYPT_MODE, key)
        var byte: ByteArray = cipher.doFinal(t.toByteArray())
    }
}