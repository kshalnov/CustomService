package ru.gb.course1.customservice

import android.content.Context
import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.annotation.WorkerThread

private const val MESSAGE_EXTRA_KEY = "MESSAGE_EXTRA_KEY"
private const val DELAY_MS_EXTRA_KEY = "DELAY_EXTRA_KEY"

class CustomToastIntentService : CustomIntentService() {

    override fun onHandleIntent(intent: Intent?) {
        intent?.let {
            showToastWithDelay(it)
        }
    }

    private fun showToastWithDelay(intent: Intent) {
        val message: String = intent.getStringExtra(MESSAGE_EXTRA_KEY) ?: ""
        val delay: Long = intent.getLongExtra(DELAY_MS_EXTRA_KEY, 0)

        Thread.sleep(delay)

        Handler(Looper.getMainLooper()).post {
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        }
        Log.d("@@@", message)
    }

    companion object {
        fun startToastJob(context: Context, message: String, delay: Long) {
            val intent = Intent(context, CustomToastIntentService::class.java)
            intent.putExtra(MESSAGE_EXTRA_KEY, message)
            intent.putExtra(DELAY_MS_EXTRA_KEY, delay)

            context.startService(intent)
        }
    }
}