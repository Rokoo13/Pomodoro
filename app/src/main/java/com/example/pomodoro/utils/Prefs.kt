package com.example.pomodoro.utils

import android.content.Context
import android.content.SharedPreferences
import com.example.pomodoro.model.PomodoroModel
import com.google.gson.Gson

class Prefs (context : Context) {

    private val PREFS_NAME = "com.roko.pomodoro.sharedpreferences"
    val prefs : SharedPreferences = context.getSharedPreferences(PREFS_NAME,0)

    private val POMODORO = "pomodoro"
    var pomodoro : String?
        get() = prefs.getString(POMODORO,"")
        set(value) = prefs.edit().putString(POMODORO,value).apply()

    /** FOR CLASS **/
    fun <T> put(`object`: T, key:String){
        //Convert object to JSON String
        val jsonString = Gson().toJson(`object`)
        //Save that String in SharedPreferences
        prefs.edit().putString(key,jsonString).apply()
    }

    inline fun <reified T> get(key: String) : T?{
        //We read JSON String which was saved
        val value = prefs.getString(key,null)
        return Gson().fromJson(value,T::class.java)
    }

}