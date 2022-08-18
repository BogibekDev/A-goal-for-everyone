package com.agoal4every1.agoalforeveryone

import android.app.Application
import com.agoal4every1.agoalforeveryone.manager.PrefManager
import dev.b3nedikt.app_locale.AppLocale
import dev.b3nedikt.reword.RewordInterceptor
import dev.b3nedikt.viewpump.ViewPump
import java.util.*

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        ViewPump.init(RewordInterceptor)
        if (PrefManager(this).getString("language") == "ru")
            AppLocale.desiredLocale = Locale("ru")
    }
}