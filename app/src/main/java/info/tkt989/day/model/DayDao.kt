package info.tkt989.day.model

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query

@Dao
interface DayDao {
    @Query("SElECT * FROM day")
    fun getAll(): LiveData<List<Day>>

    @Query("SELECT * FROM day WHERE id = :id")
    fun findById(id: Int): Day?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(day: Day)
}
