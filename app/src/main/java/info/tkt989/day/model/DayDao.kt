package info.tkt989.day.model

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*

@Dao
interface DayDao {
    @Query("SElECT * FROM day")
    fun getAll(): LiveData<List<Day>>

    @Query("SELECT * FROM day WHERE id = :id")
    fun findByDayId(id: Long): Day?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(day: Day)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(day: Day)
}
