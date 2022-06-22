package com.example.pomodoro

import android.app.Application
import android.content.Context
import androidx.lifecycle.LifecycleObserver
import com.example.pomodoro.utils.Prefs
import timber.log.Timber

class PmdApplication : Application(), LifecycleObserver {

    companion object{
        lateinit var prefs : Prefs
        lateinit var instance :PmdApplication

        fun getContext(): Context {return instance.applicationContext}
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        Timber.plant(Timber.DebugTree())
        prefs = Prefs(applicationContext)
    }
}