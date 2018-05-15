package info.tkt989.day.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import org.joda.time.DateTime

@Entity
data class Day(var title: String,
               var date: DateTime) {
    @PrimaryKey(autoGenerate = true) var id: Long = 0
}
