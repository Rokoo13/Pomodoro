package com.example.pomodoro.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.example.pomodoro.R
import com.example.pomodoro.databinding.ActivityMainBinding
import com.example.pomodoro.viewmodel.PomodoroViewModel
import kotlinx.android.synthetic.main.activity_main.*
import java.text.DecimalFormat
import java.text.NumberFormat
import kotlin.math.round

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val pomodoroViewModel : PomodoroViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
    }

    fun initView(){
        pomodoroViewModel.initObject()
        pomodoroViewModel.pomodoroModel.observe(this, Observer { pomodoro ->
            val f: NumberFormat = DecimalFormat("00")
            val timeFocusLeft = "${f.format(pomodoro.focusTimeMin)} : ${f.format(pomodoro.focusTimeSec)}"
            val timeRestLeft = "${f.format(pomodoro.restTimeMin)} : ${f.format(pomodoro.restTimeSec)}"
            binding.focusTimer.text =  timeFocusLeft
            binding.restTimer.text = timeRestLeft
        })
        binding.startCounter.setOnClickListener {
            pomodoroViewModel.startCounter()
        }
    }

}