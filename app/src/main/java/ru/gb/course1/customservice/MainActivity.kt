package ru.gb.course1.customservice

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import ru.gb.course1.customservice.databinding.ActivityMainBinding
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val workerThread = CustomWorkerThread()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.nextButton.setOnClickListener {
            CustomToastIntentService.startToastJob(
                this,
                UUID.randomUUID().toString(),
                3_000L
            )
        }

        binding.startButton.setOnClickListener {
            workerThread.start()
        }
        binding.runButton.setOnClickListener {
            workerThread.post {
                Thread.sleep(1_000)
                Log.d("@@@", UUID.randomUUID().toString())
            }
            workerThread.post {
                Thread.sleep(1_000)
                Log.d("@@@", UUID.randomUUID().toString())
            }
            workerThread.post {
                Thread.sleep(1_000)
                Log.d("@@@", UUID.randomUUID().toString())
            }
        }

        binding.stopButton.setOnClickListener {
            workerThread.quit()
        }
    }
}