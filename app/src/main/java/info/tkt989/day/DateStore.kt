package info.tkt989.day

import android.arch.persistence.room.*
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

@Entity
class Day(@PrimaryKey var id: Int, var date: DateTime)

class DateStore(val db: AppDatabase) {
    val dao = db.dayDao()

    fun save(day: Day) {
        dao.insert(day)
    }

    fun load(appWidgetId: Int): Day? {
        return dao.findById(appWidgetId)
    }
}

@Dao
interface DayDao {
    @Query("SELECT * FROM day WHERE id = :id")
    fun findById(id: Int): Day?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(day: Day)
}

@Database(version = 1, entities = arrayOf(Day::class))
@TypeConverters(DateTimeConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun dayDao(): DayDao
}