package info.tkt989.day.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import org.joda.time.DateTime
import java.io.Serializable

@Entity
data class Day(var title: String,
               var date: DateTime) : Serializable {
    @PrimaryKey(autoGenerate = true) var id: Long = 0
}
