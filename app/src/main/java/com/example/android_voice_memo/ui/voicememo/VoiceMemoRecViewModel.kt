package com.example.android_voice_memo.ui.voicememo

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.android_voice_memo.data.VoiceMemo
import com.example.android_voice_memo.data.VoiceMemoDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class VoiceMemoRecViewModel(application: Application): AndroidViewModel(application) {

    private val repository: VoiceMemoRecRepository

    init {
        val voiceMemoDao = VoiceMemoDatabase.getDatabase(application).voiceMemoDao()
        repository = VoiceMemoRecRepository(voiceMemoDao)
    }

    fun insertDate(voiceMemo: VoiceMemo) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertData(voiceMemo)
        }
    }

}