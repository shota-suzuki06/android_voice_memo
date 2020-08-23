package com.example.android_voice_memo.ui.delete

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.android_voice_memo.data.VoiceMemo
import com.example.android_voice_memo.data.VoiceMemoDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class VoiceMemoDeleteViewModel(application: Application): AndroidViewModel(application) {

    val readAllDeleteData: LiveData<List<VoiceMemo>>
    private val repository: VoiceMemoDeleteRepository

    init {
        val voiceMemoDao = VoiceMemoDatabase.getDatabase(application).voiceMemoDao()
        repository = VoiceMemoDeleteRepository(voiceMemoDao)
        readAllDeleteData = repository.readAllDeleteData
    }

    fun updateData(voiceMemo: VoiceMemo) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateData(voiceMemo)
        }
    }

    fun deleteData(voiceMemo: VoiceMemo) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteData(voiceMemo)
        }
    }

}