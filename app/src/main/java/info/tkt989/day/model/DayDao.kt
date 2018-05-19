package info.tkt989.day.model

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*

@Dao
interface DayDao {
    @Query("SElECT * FROM day")
    fun getAll(): LiveData<List<Day>>

    @Query("SELECT * FROM day WHERE id = :id")
    fun findById(id: Long): Day?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(day: Day)
}
