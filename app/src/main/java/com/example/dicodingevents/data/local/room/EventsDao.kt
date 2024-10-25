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

    @Query("SELECT * from events ORDER BY beginTime DESC")
    fun getAllEvents(): LiveData<List<EventEntity>>

    @Query("SELECT * from events where isFinished = 1 ORDER BY beginTime DESC LIMIT :limit")
    fun getFinishedEvents(limit: Int? = 40): LiveData<List<EventEntity>>

    @Query("SELECT * from events where isFinished = 0 ")
    fun getUpcomingEvents(): LiveData<List<EventEntity>>

    @Query("SELECT * FROM events WHERE name LIKE :nameQuery AND isFinished = :isFinished")
    fun searchEvent(nameQuery: String?, isFinished: Int? = 0): LiveData<List<EventEntity>>

    @Query("SELECT * FROM events WHERE eventId = :eventId")
    fun getEventDetails(eventId: Int) :LiveData<EventEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEvents(events: List<EventEntity>)

    @Update
    suspend fun updateEvents(events: EventEntity)

    @Query("DELETE FROM events")
    suspend fun deleteAll()

}