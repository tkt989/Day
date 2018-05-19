package info.tkt989.day.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity
data class AppWidgetDay(var appWidgetId: Int,
                        var dayId: Long) {
    @PrimaryKey(autoGenerate = true) var id: Long = 0
}