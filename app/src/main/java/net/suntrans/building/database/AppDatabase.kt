package net.suntrans.building.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context

import net.suntrans.building.domin.User

/**
 * Created by Looney on 2018/1/31.
 * Des:
 */
@Database(entities = arrayOf(User::class), version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    companion object {
        private var INSTANCE: AppDatabase? = null
        fun getInstance(context: Context): AppDatabase? {
            if (INSTANCE == null) {
                synchronized(AppDatabase::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(context.applicationContext,
                                AppDatabase::class.java, "st_appmanager")
                                .build()
                    }
                }
            }
            return INSTANCE
        }
    }
}
