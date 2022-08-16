package com.agoal4every1.agoalforeveryone.utils

import android.content.Context
import android.content.res.Resources
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.view.View
import com.agoal4every1.agoalforeveryone.manager.PrefManager

object Extentions {
    val Int.dp: Int
        get() = (this * Resources.getSystem().displayMetrics.density + 0.5f).toInt()

    private fun View.vibratePhone() {
        val vibrator = this.context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        if (PrefManager(this.context).getBoolean("isVibrateOn")) {
            if (Build.VERSION.SDK_INT >= 26) {
                vibrator.vibrate(
                    VibrationEffect.createOneShot(
                        200,
                        VibrationEffect.DEFAULT_AMPLITUDE
                    )
                )
            } else {
                vibrator.vibrate(200)
            }
        }
    }

    fun View.click(myClick: () -> Unit) {
        this.vibratePhone()
        this.setOnClickListener {
            myClick.invoke()
        }
    }

}