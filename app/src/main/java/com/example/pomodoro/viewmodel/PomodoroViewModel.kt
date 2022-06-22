package com.example.pomodoro.viewmodel

import android.os.CountDownTimer
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pomodoro.PmdApplication
import com.example.pomodoro.model.PomodoroModel
import kotlinx.android.synthetic.main.activity_main.*
import java.util.concurrent.TimeUnit
import kotlin.math.round

class PomodoroViewModel : ViewModel() {

    val pomodoroModel = MutableLiveData<PomodoroModel>()
    lateinit var pomodoroStart : PomodoroModel

    fun initObject(){
        val pomodoroInit = PomodoroModel(0,20,0,30)
        PmdApplication.prefs.put(pomodoroInit,"pomodoro")
        pomodoroModel.postValue(pomodoroInit)
        pomodoroStart = PmdApplication.prefs.get<PomodoroModel>("pomodoro")!!
    }

    fun setTimer(focusTimeMin: Int, focusTimeSec : Int,
                 restTimeMin: Int, restTimeSec: Int){
        val pomodoro = PomodoroModel(focusTimeMin,focusTimeSec,restTimeMin,restTimeSec)
        pomodoroModel.postValue(pomodoro)
    }

    fun startCounter(){
        val minutesStart = pomodoroStart.focusTimeMin
        val minutesEnd = pomodoroStart.focusTimeSec
        val timer = TimeUnit.MINUTES.toMillis(minutesStart.toLong()) +
                TimeUnit.SECONDS.toMillis(minutesEnd.toLong())
        object : CountDownTimer(timer,1000){
            override fun onTick(millisUntilFinished: Long) {
                val min = TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished)
                val sec = TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))
                setTimer(min.toInt(),sec.toInt(),pomodoroStart.restTimeMin, pomodoroStart.restTimeSec)
            }

            override fun onFinish() {
                cancel()
                startRestCounter()
            }

        }.start()
    }

    fun startRestCounter(){
        val minutesStart = pomodoroStart.restTimeMin
        val minutesEnd = pomodoroStart.restTimeSec
        val timer = TimeUnit.MINUTES.toMillis(minutesStart.toLong()) +
                TimeUnit.SECONDS.toMillis(minutesEnd.toLong())
        object : CountDownTimer(timer,1000){
            override fun onTick(millisUntilFinished: Long) {
                val min = TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished)
                val sec = TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))
                setTimer(pomodoroStart.focusTimeMin, pomodoroStart.focusTimeSec,min.toInt(),sec.toInt())
            }

            override fun onFinish() {
                cancel()
                startCounter()
            }
        }.start()
    }
}