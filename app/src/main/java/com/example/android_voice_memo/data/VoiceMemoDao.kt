package com.example.android_voice_memo.data

import android.speech.tts.Voice
import androidx.lifecycle.LiveData
import androidx.room.*
import java.util.*

@Dao
interface VoiceMemoDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertData(memo: VoiceMemo)

    @Query("SELECT * FROM voice_memo_table WHERE deleted = :deleted ORDER BY date ASC")
    fun getAllData(deleted: Boolean = false): LiveData<List<VoiceMemo>>

    @Query("SELECT * FROM voice_memo_table WHERE deleted = :deleted ORDER BY date ASC")
    fun getAllDeleteData(deleted: Boolean = true): LiveData<List<VoiceMemo>>

    @Query("SELECT * FROM voice_memo_table WHERE deleted = :deleted AND content LIKE '%' || :query || '%' ORDER BY date ASC")
    fun findByContent(deleted: Boolean, query: String): LiveData<List<VoiceMemo>>

    @Update
    suspend fun updateData(memo: VoiceMemo)

    @Delete
    suspend fun deleteData(memo: VoiceMemo)

}
