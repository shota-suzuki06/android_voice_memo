package com.example.android_voice_memo.ui.voicememo

import com.example.android_voice_memo.data.VoiceMemo
import com.example.android_voice_memo.data.VoiceMemoDao

class VoiceMemoRecRepository(private val voiceMemoDao: VoiceMemoDao) {

    suspend fun insertData(voiceMemo: VoiceMemo) {
        voiceMemoDao.insertData(voiceMemo)
    }

}