package info.tkt989.day.model

import android.arch.persistence.room.*

@Dao
interface AppWidgetDayDao {
    @Query("SELECT * FROM appWidgetDay WHERE appWidgetId = :appWidgetId")
    fun getById(appWidgetId: Int): AppWidgetDay?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(appWidgetDay: AppWidgetDay)

    @Query("DELETE FROM appWidgetDay WHERE appWidgetId = :appWidgetId")
    fun deleteById(appWidgetId: Int)
}