package com.kingofraccoon.punctualpatient.tools.encoder

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys

class EncryptedSharedPreferencesUser(context: Context) {

    companion object{
        val IS_AUTH = "Auth"
        val LOGIN_REFERENCE = "LOGIN"
        val PASS_REFERENCE = "PASS"
    }
    val masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)
    var sharedPreferences = EncryptedSharedPreferences.create(
        "PreferencesFilename",
        masterKeyAlias,
        context,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )
    fun editor() = sharedPreferences.edit()

    fun checkAuth(): Boolean = sharedPreferences.getBoolean(IS_AUTH, false)

    fun auth(login: String, password : String){
        editor().putString(LOGIN_REFERENCE,login).commit()
        editor().putString(PASS_REFERENCE, password).commit()
        editor().putBoolean(IS_AUTH, true)
    }

    fun deauth(){
        editor().putBoolean(IS_AUTH, false)
    }

    fun getLoginAndPass():Pair<String?, String?>{
        return Pair(sharedPreferences.getString(LOGIN_REFERENCE,""),
            sharedPreferences.getString(
            PASS_REFERENCE,""))
    }
}