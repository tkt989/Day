package info.tkt989.day

import info.tkt989.day.model.AppDatabase
import info.tkt989.day.model.Day


class DateStore(val db: AppDatabase) {
    val dao = db.dayDao()

    fun save(day: Day) {
    }

    fun load(appWidgetId: Int): Day? {
        return null
    }
}
