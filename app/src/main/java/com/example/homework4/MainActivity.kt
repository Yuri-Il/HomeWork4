package com.example.homework4

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import com.example.homework4.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var imageNumber: Int = 1
    private val images = buildMap {
        put(1, R.drawable.one)
        put(2, R.drawable.two)
        put(3, R.drawable.three)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    private val getResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        imageNumber = it.resultCode
        binding.imageViewId.setImageResource(images.getValue(imageNumber))
    }

    fun openSecondActivity(view: View) {
        val intent = Intent(this, Activity2::class.java)
        getResult.launch(intent)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putInt("IMAGE_KEY", imageNumber)
        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        imageNumber = savedInstanceState.getInt("IMAGE_KEY")
        binding.imageViewId.setImageResource(images.getValue(imageNumber))
    }
}