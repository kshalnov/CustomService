package ru.gb.course1.customservice

import android.app.Service
import android.content.Intent
import android.os.Handler
import android.os.HandlerThread
import android.os.IBinder
import android.util.Log
import androidx.annotation.WorkerThread

abstract class CustomIntentService : Service() {

    private lateinit var handlerThread: HandlerThread
    private lateinit var handler: Handler

    override fun onCreate() {
        super.onCreate()

        handlerThread = HandlerThread("CustomIntentServiceWorkerThread")
        handlerThread.start()

        handler = Handler(handlerThread.looper)
    }

    override fun onDestroy() {
        super.onDestroy()
        handlerThread.quit()
    }

    @WorkerThread
    protected abstract fun onHandleIntent(intent: Intent?)

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        handler.post {
            onHandleIntent(intent)
            stopSelf(startId)
        }

        return START_REDELIVER_INTENT
    }

}