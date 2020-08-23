package com.example.android_voice_memo.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "voice_memo_table")
data class VoiceMemo(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val content: String,
    val date: Date,
    var deleted: Boolean = false
)
