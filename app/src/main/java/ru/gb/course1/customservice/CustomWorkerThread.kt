package ru.gb.course1.customservice

import androidx.annotation.WorkerThread
import java.util.*

// Цикл событий / Событийный цикл / Event Looper
class CustomWorkerThread : Thread() {
    private val queue: Queue<Runnable> = LinkedList()
    private var isLoopWorking = false
    private val lock: Object = Object()

    @WorkerThread
    override fun run() {
        isLoopWorking = true

        while (isLoopWorking) {
            try {
                var runnable: Runnable?
                synchronized(lock) {
                    runnable = queue.poll()
                    if (runnable == null) {
                        lock.wait()
                    }
                }

                runnable?.run()
            } catch (ie: InterruptedException) {
                ie.printStackTrace()
            }
        }
    }

    fun post(runnable: Runnable) {
        synchronized(lock) {
            queue.offer(runnable)
            lock.notify()
        }
    }

    fun quit() {
        isLoopWorking = false
        synchronized(lock) {
            lock.notify()
        }
    }
}