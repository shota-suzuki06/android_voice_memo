package com.example.android_voice_memo.ui.delete

import com.example.android_voice_memo.data.VoiceMemo
import com.example.android_voice_memo.data.VoiceMemoDao

class VoiceMemoDeleteRepository(private val voiceMemoDao: VoiceMemoDao) {

    val readAllDeleteData = voiceMemoDao.getAllDeleteData()

    suspend fun updateData(voiceMemo: VoiceMemo) {
        voiceMemoDao.updateData(voiceMemo)
    }

    suspend fun deleteData(voiceMemo: VoiceMemo) {
        voiceMemoDao.deleteData(voiceMemo)
    }

}