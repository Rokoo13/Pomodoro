package com.example.pomodoro.viewmodel

import android.os.CountDownTimer
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pomodoro.model.PomodoroModel
import kotlinx.android.synthetic.main.activity_main.*
import java.util.concurrent.TimeUnit
import kotlin.math.round

class PomodoroViewModel : ViewModel() {

    val pomodoroModel = MutableLiveData<PomodoroModel>()

    fun initObject(){
        val pomodoroInit = PomodoroModel(0,20,0,30)
        pomodoroModel.postValue(pomodoroInit)
    }

    fun setTimer(focusTimeMin: Int, focusTimeSec : Int,
                 restTimeMin: Int, restTimeSec: Int){
        val pomodoro = PomodoroModel(focusTimeMin,focusTimeSec,restTimeMin,restTimeSec)
        pomodoroModel.postValue(pomodoro)
    }

    fun startCounter(){
        val minutesStart = pomodoroModel.value!!.focusTimeMin
        val minutesEnd = pomodoroModel.value!!.focusTimeSec
        val timer = TimeUnit.MINUTES.toMillis(minutesStart.toLong()) +
                TimeUnit.SECONDS.toMillis(minutesEnd.toLong())
        object : CountDownTimer(timer,1000){
            override fun onTick(millisUntilFinished: Long) {
                val min = TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished)
                val sec = TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))
                setTimer(min.toInt(),sec.toInt(),pomodoroModel.value!!.restTimeMin, pomodoroModel.value!!.restTimeSec)
            }

            override fun onFinish() {
                cancel()
                startRestCounter()
            }

        }.start()
    }

    fun startRestCounter(){
        val minutesStart = pomodoroModel.value!!.restTimeMin
        val minutesEnd = pomodoroModel.value!!.restTimeSec
        val timer = TimeUnit.MINUTES.toMillis(minutesStart.toLong()) +
                TimeUnit.SECONDS.toMillis(minutesEnd.toLong())
        object : CountDownTimer(timer,1000){
            override fun onTick(millisUntilFinished: Long) {
                val min = TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished)
                val sec = TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))
                setTimer(0, 20,min.toInt(),sec.toInt())
            }

            override fun onFinish() {
                cancel()
                startCounter()
            }
        }.start()
    }
}