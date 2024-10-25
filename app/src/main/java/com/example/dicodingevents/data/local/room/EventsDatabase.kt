package com.example.dicodingevents.data.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.dicodingevents.data.local.entity.EventEntity

@Database(entities = [EventEntity::class], version = 3, exportSchema = false)
abstract class EventsDatabase : RoomDatabase() {

    abstract fun eventsDao(): EventsDao

    companion object {

        private val MIGRATION_2_3 = object: Migration(2,3){
            override fun migrate(db: SupportSQLiteDatabase) {

                db.execSQL("""
            CREATE TABLE new_events (
                eventId INTEGER PRIMARY KEY NOT NULL,
                name TEXT,
                summary TEXT,
                description TEXT,
                imageLogo TEXT,
                mediaCover TEXT,
                ownerName TEXT,
                cityName TEXT,
                quota INTEGER NOT NULL,
                registrants INTEGER NOT NULL,
                beginTime TEXT,
                endTime TEXT,
                link TEXT,
                isFinished INTEGER NOT NULL
            )
        """.trimIndent())

                db.execSQL("DROP TABLE events")

                db.execSQL("ALTER TABLE new_events RENAME TO events")
            }

        }

        @Volatile
        private var instance: EventsDatabase? = null

        fun getInstance(context: Context): EventsDatabase =
            instance ?: synchronized(this) {
                instance ?: Room.databaseBuilder(
                    context.applicationContext, EventsDatabase::class.java, "Events.db"
                ).addMigrations(MIGRATION_2_3).build()
            }
    }


}