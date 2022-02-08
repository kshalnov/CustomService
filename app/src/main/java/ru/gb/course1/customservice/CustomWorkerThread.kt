package ru.gb.course1.customservice

import androidx.annotation.WorkerThread
import java.util.*

// Цикл событий / Событийный цикл / Event Looper
class CustomWorkerThread : Thread() {
    private val queue: Queue<Runnable> = LinkedList()
    private var isLoopWorking = false
    private val lock: Object = Object()

    // todo заменить на потокобезопасную очередь
    // todo отказаться от объекта Java и заменить на Kotlin-way подход

    // Что-то одно на выбор
    // todo придумать как вынести свой аналог handler из класса потока
    // todo сделать в MainActivity кнопки для тестирования нагрузки WorkerThread из разных потоков
    // todo сделать приоритетную очередь с разным приоритетом задач
    // todo сделать postDelayed с отправкой спустя какое-то время

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