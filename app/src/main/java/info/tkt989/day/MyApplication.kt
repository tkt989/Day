package info.tkt989.day

import android.app.Application
import android.arch.persistence.room.Room

class MyApplication : Application() {
    lateinit var dateStore: DateStore

    override fun onCreate() {
        super.onCreate()

        val db = Room.databaseBuilder(this, AppDatabase::class.java, "db")
                .allowMainThreadQueries()
                .build()
        dateStore = DateStore(db)
    }
}