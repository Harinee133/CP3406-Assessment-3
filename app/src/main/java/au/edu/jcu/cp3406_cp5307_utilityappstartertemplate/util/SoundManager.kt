package au.edu.jcu.cp3406_cp5307_utilityappstartertemplate.util

import android.content.Context
import android.media.AudioManager
import android.media.ToneGenerator
import dagger.hilt.android.qualifiers.ApplicationContext
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SoundManager @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val toneGenerator = ToneGenerator(AudioManager.STREAM_MUSIC, 100)
    private val vibrator = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        val vibratorManager = context.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager
        vibratorManager.defaultVibrator
    } else {
        @Suppress("DEPRECATION")
        context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
    }

    fun playSuccess() {
        toneGenerator.startTone(ToneGenerator.TONE_CDMA_PIP, 150)
        vibrate(100)
    }

    fun playError() {
        toneGenerator.startTone(ToneGenerator.TONE_SUP_ERROR, 200)
        vibrate(300)
    }

    fun playClick() {
        toneGenerator.startTone(ToneGenerator.TONE_PROP_BEEP, 100)
        vibrate(50)
    }

    private fun vibrate(duration: Long) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrator.vibrate(VibrationEffect.createOneShot(duration, VibrationEffect.DEFAULT_AMPLITUDE))
        } else {
            @Suppress("DEPRECATION")
            vibrator.vibrate(duration)
        }
    }
}
