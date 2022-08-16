package com.agoal4every1.agoalforeveryone.utils

import android.content.Context
import android.media.MediaPlayer
import com.agoal4every1.agoalforeveryone.R

object PlaySound {
    private lateinit var player: MediaPlayer

    fun playTrueSound(context: Context) {
        player = MediaPlayer.create(context, R.raw.the_right_button)
        player.start()
    }

    fun playWrongSound(context: Context) {
        player = MediaPlayer.create(context, R.raw.incorrect_button)
        player.start()
    }
}