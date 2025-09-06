package com.hqumath.demo.utils

import android.content.Context
import android.content.Intent
import android.speech.tts.TextToSpeech
import com.hqumath.demo.dialog.CommonDialog
import java.util.Locale

/**
 * 文字转语音
 * 注意：Manifest 权限添加 TTS_SERVICE
 */
object TTSUtil {
    private var tts: TextToSpeech? = null

    // 初始化
    fun init() {
        tts = TextToSpeech(CommonUtil.getContext()) { status ->
            if (status != TextToSpeech.SUCCESS) {//监听初始化状态
                CommonUtil.toast("TTS 初始化失败")
            }
        }
    }

    //释放
    fun release() {
        tts?.stop()
        tts?.shutdown()
        tts = null
    }

    //文字转语音
    fun speak(msg: String) {
        if (tts == null)
            return
        //设置语言（中文）
        val result = tts!!.setLanguage(Locale.CHINA)
        if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
            CommonUtil.toast("TTS 不支持中文")
            return
        }
        //开始朗读
        // 第二个参数。QUEUE_FLUSH：中断当前语音并播放新内容；QUEUE_ADD：添加到队列后排队播放
        // 第四个参数 utteranceId 可用于在监听器中区分不同的朗读请求
        tts!!.speak(msg, TextToSpeech.QUEUE_FLUSH, null, "utteranceId")
    }

    //获取当前引擎信息
    fun getEngine(): String? {
        val currentEngine = tts?.defaultEngine
        return currentEngine
    }

    //安装引擎弹窗
    fun showEngineChangeDialog(context: Context) {
        if (tts == null)
            return
        //检查当前引擎是否是讯飞语记（包名通常是 com.iflytek.vflynote）
        val currentEngine = tts!!.defaultEngine
        if ("com.iflytek.vflynote" != currentEngine) { //如果不是，提示用户去切换

            val dialog = CommonDialog(
                context = context,
                title = "提示",
                message = "为了获得更好的语音效果，您可将系统TTS引擎设置为「讯飞语记」。",
                positiveText = "去设置",
                positiveAction = {
                    // 跳转到系统TTS设置界面
                    val intent = Intent()
                    intent.action = "com.android.settings.TTS_SETTINGS"
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    context.startActivity(intent)
                },
                negativeText = "取消",
                negativeAction = {}
            )
            dialog.show()
        }
    }
}