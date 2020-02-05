package com.example.mooka_customer.extension

import android.content.Context
import com.example.mooka_customer.Config
import com.google.gson.Gson

fun Context.savePref(key: String, value: String){
    this.getSharedPreferences(Config.PREFNAME, 0).edit().putString(key, value).apply()
}

fun Context.savePref(key: String, value: Int){
    this.getSharedPreferences(Config.PREFNAME, 0).edit().putInt(key, value).apply()
}

fun Context.savePref(key: String, value: Boolean){
    this.getSharedPreferences(Config.PREFNAME, 0).edit().putBoolean(key, value).apply()
}

fun Context.savePref(key: String, value: Set<String>){
    this.getSharedPreferences(Config.PREFNAME, 0).edit().putStringSet(key, value).apply()
}

fun Context.savePrefObj(key: String, value: Any){
    val json = Gson().toJson(value)
    this.getSharedPreferences(Config.PREFNAME, 0).edit().putString(key, json).apply()
}

fun Context.getPrefString(key: String): String?{
    return this.getSharedPreferences(Config.PREFNAME, 0).getString(key, null)
}

fun Context.getPrefInt(key: String): Int{
    return this.getSharedPreferences(Config.PREFNAME, 0).getInt(key, -1)
}

fun Context.getPrefBoolean(key: String): Boolean{
    return this.getSharedPreferences(Config.PREFNAME, 0).getBoolean(key, false)
}

fun Context.getPrefSetString(key: String): Set<String>?{
    return this.getSharedPreferences(Config.PREFNAME, 0).getStringSet(key, HashSet<String>())
}

fun Context.clearPref(){
    return this.getSharedPreferences(Config.PREFNAME, 0).edit().clear().apply()
}

fun <T> Context.getPrefObj(key: String, classType: Class<T>): T?{
    val jsonString : String? = this.getSharedPreferences(Config.PREFNAME, 0).getString(key, null)
    return if (jsonString != null) Gson().fromJson(jsonString, classType) else null
}