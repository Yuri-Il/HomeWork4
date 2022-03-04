package com.example.homework4


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.homework4.databinding.Activity2Binding

class Activity2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = Activity2Binding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    fun firstButtonClick(view: View) {
        setResult(1)
        finish()
    }
    fun secondButtonClick(view: View) {
        setResult(2)
        finish()
    }
    fun thirdButtonClick(view: View) {
        setResult(3)
        finish()
    }

}