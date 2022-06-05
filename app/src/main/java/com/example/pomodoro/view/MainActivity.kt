package com.example.pomodoro.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import com.example.pomodoro.R
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.math.round

class MainActivity : AppCompatActivity() {

    private lateinit var contador : Unit

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
    }

    fun initView(){
        start_counter.setOnClickListener {
            contador = startCounter(1500000)
        }
    }
    private fun startCounter(focusTime : Long){
        object : CountDownTimer(focusTime,1000){
            override fun onTick(millisUntilFinished: Long) {
                val segundosTot = (millisUntilFinished / 1000).toDouble()
                var minSec = segundosTot / 60
                val minutos =  (segundosTot / 60).toInt()
                var segundos = (minSec - minutos) * 60
                segundos = round(segundos)
                timer.text = "$minutos : ${segundos.toInt()}"
            }

            override fun onFinish() {
            }

        }.start()
    }
}