package ru.gb.course1.customservice

import android.app.Service
import android.content.Intent
import android.os.Handler
import android.os.HandlerThread
import android.os.IBinder
import android.util.Log
import androidx.annotation.WorkerThread

private const val TAG = "@@@"

abstract class CustomIntentService : Service() {

    private lateinit var handlerThread: HandlerThread
    private lateinit var handler: Handler

    override fun onCreate() {
        super.onCreate()

        handlerThread = HandlerThread("CustomIntentServiceWorkerThread")
        handlerThread.start()

        handler = Handler(handlerThread.looper)

        Log.d(TAG, "onCreate() called")
    }

    override fun onDestroy() {
        super.onDestroy()
        handlerThread.quit()
        Log.d(TAG, "onDestroy() called")
    }

    @WorkerThread
    abstract fun onHandleIntent(intent: Intent?)

    override fun onBind(p0: Intent?): IBinder? {
        Log.d(TAG, "onBind() called with: p0 = $p0")
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d(
            TAG,
            "onStartCommand() called with: intent = $intent, flags = $flags, startId = $startId"
        )
        handler.post {
            onHandleIntent(intent)
            stopSelf(startId)
        }

        return START_REDELIVER_INTENT
    }

}