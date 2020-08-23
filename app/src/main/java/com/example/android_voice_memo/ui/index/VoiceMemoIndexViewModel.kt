package com.example.android_voice_memo.ui.index

import android.app.Application
import android.speech.tts.Voice
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.android_voice_memo.data.VoiceMemo
import com.example.android_voice_memo.data.VoiceMemoDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class VoiceMemoIndexViewModel(application: Application): AndroidViewModel(application) {

    var readAllData: LiveData<List<VoiceMemo>>
    private val repository: VoiceMemoIndexRepository

    init {
        val voiceMemoDao = VoiceMemoDatabase.getDatabase(application).voiceMemoDao()
        repository = VoiceMemoIndexRepository(voiceMemoDao)
        readAllData = repository.readAllData
    }

    fun updateData(voiceMemo: VoiceMemo) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateData(voiceMemo)
        }
    }

    fun getAll() {
        repository.getAll()
        readAllData = repository.readAllData
    }

    fun findByContent(query: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.findByContent(query)
            readAllData = repository.readAllData
        }
    }

}
