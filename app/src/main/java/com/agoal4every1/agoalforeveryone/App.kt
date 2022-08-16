package com.agoal4every1.agoalforeveryone

import android.app.Application
import dev.b3nedikt.reword.RewordInterceptor
import dev.b3nedikt.viewpump.ViewPump

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        ViewPump.init(RewordInterceptor)
    }
}