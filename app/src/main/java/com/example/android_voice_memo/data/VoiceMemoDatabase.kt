package com.example.android_voice_memo.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.android_voice_memo.common.DateConverter

@Database(entities = [VoiceMemo::class], version = 1, exportSchema = false)
@TypeConverters(DateConverter::class)
abstract class VoiceMemoDatabase: RoomDatabase() {

    abstract fun voiceMemoDao(): VoiceMemoDao

    companion object {
        @Volatile
        private var INSTANCE: VoiceMemoDatabase? = null

        fun getDatabase(context: Context): VoiceMemoDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    VoiceMemoDatabase::class.java,
                    "voice_memo_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }

    }

}
