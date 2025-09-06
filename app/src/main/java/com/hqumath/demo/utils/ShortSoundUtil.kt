package com.hqumath.demo.utils

import android.media.AudioAttributes
import android.media.SoundPool
import kotlin.collections.set

/**
 * 播放音效
 */
object ShortSoundUtil {

    private val soundPool: SoundPool
    private val soundMap = mutableMapOf<Int, Int>()

    init {
        //创建 SoundPool
        val attrs = AudioAttributes.Builder()
            .setUsage(AudioAttributes.USAGE_MEDIA)
            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
            .build()

        soundPool = SoundPool.Builder()
            .setMaxStreams(1) // 最多同时播放 5 个音效
            .setAudioAttributes(attrs)
            .build()

        //预加载音效
        //load(R.raw.alarm)
    }

    /**
     * 预加载音效
     * @param resId 资源 id，例如 R.raw.click
     */
    private fun load(resId: Int) {
        val soundId = soundPool.load(CommonUtil.getContext(), resId, 1)
        soundMap[resId] = soundId
    }

    /**
     * 播放音效
     */
    fun play(resId: Int) {
        val soundId = soundMap[resId] ?: return
        soundPool.play(soundId, 1f, 1f, 1, 0, 1f)
    }

    /**
     * 释放资源
     */
    fun release() {
        soundPool.release()
    }
}
