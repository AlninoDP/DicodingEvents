 package com.example.dicodingevents.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.dicodingevents.data.local.entity.EventEntity

@Dao
interface EventsDao {

    @Query ("SELECT * from events ORDER BY id ASC")
    fun getAllEvents(): LiveData<List<EventEntity>>

    @Query("SELECT * from events where isFinished = 1")
    fun getFinishedEvents(): LiveData<List<EventEntity>>

    @Query("SELECT * from events where isFinished = 0")
    fun getUpcomingEvents(): LiveData<List<EventEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertEvents(events: List<EventEntity>)

    @Update
    suspend fun updateEvents(events: EventEntity)

    @Query ("DELETE FROM events")
    suspend fun deleteAll()

}