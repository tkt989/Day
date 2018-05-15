package info.tkt989.day.model

import android.arch.persistence.room.TypeConverter
import org.joda.time.DateTime

class DateTimeConverter {
    @TypeConverter
    fun fromTimestamp(value: Long?): DateTime? {
        return if (value == null) null else DateTime(value)
    }

    @TypeConverter
    fun toTimestamp(date: DateTime?): Long? {
        return date?.toInstant()?.millis
    }
}
