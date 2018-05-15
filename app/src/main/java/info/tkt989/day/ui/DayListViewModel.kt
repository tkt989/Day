package info.tkt989.day.ui

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import info.tkt989.day.model.Day
import info.tkt989.day.model.DayDao

class DayListViewModel : ViewModel() {
    lateinit var dayList: LiveData<List<Day>>

    fun init(dayDao: DayDao) {
        dayList = dayDao.getAll()
    }
}