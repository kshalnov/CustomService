package ru.gb.course1.customservice

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.gb.course1.customservice.databinding.ActivityMainBinding
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.startButton.setOnClickListener {
            // todo
        }

        binding.stopButton.setOnClickListener {
            // todo
        }

        binding.nextButton.setOnClickListener {
            ReferenceIntentService.startToastJob(
                this,
                UUID.randomUUID().toString(),
                3_000L
            )
        }
    }
}