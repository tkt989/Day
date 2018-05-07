package info.tkt989.day

import android.app.Application
import android.arch.persistence.room.Room
import com.facebook.stetho.Stetho

class MyApplication : Application() {
    lateinit var dateStore: DateStore

    override fun onCreate() {
        super.onCreate()
        Stetho.initializeWithDefaults(this)

        val db = Room.databaseBuilder(this, AppDatabase::class.java, "db")
                .allowMainThreadQueries()
                .build()
        dateStore = DateStore(db)
    }
}