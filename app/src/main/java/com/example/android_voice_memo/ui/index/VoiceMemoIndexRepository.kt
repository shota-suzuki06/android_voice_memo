package com.example.android_voice_memo.ui.index

import androidx.lifecycle.LiveData
import com.example.android_voice_memo.data.VoiceMemo
import com.example.android_voice_memo.data.VoiceMemoDao
import java.text.SimpleDateFormat
import java.util.*

class VoiceMemoIndexRepository(private val voiceMemoDao: VoiceMemoDao) {

    var readAllData: LiveData<List<VoiceMemo>> = voiceMemoDao.getAllData()

    suspend fun updateData(voiceMemo: VoiceMemo) {
        voiceMemoDao.updateData(voiceMemo)
    }

    fun getAll() {
        readAllData = voiceMemoDao.getAllData()
    }

    fun findByContent(query: String) {
        readAllData = voiceMemoDao.findByContent(false, query)
    }

}
