package info.tkt989.day

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import info.tkt989.day.model.AppDatabase
import info.tkt989.day.model.Day
import org.joda.time.DateTime


@Entity
data class AppWidgetDay(@PrimaryKey var id: Long,
                        var appWidgetId: Int,
                        var dayId: Long)

class DateStore(val db: AppDatabase) {
    val dao = db.dayDao()

    fun save(day: Day) {
        dao.insert(day)
    }

    fun load(appWidgetId: Int): Day? {
        return dao.findById(appWidgetId)
    }
}
