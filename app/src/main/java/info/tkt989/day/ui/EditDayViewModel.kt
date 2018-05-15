package info.tkt989.day.ui

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import info.tkt989.day.model.Day
import info.tkt989.day.model.DayDao
import org.joda.time.DateTime
import javax.inject.Inject

class EditDayViewModel : ViewModel() {
    @Inject
    lateinit var dayDao: DayDao

    var validation: MutableLiveData<Map<Any, String>> = MutableLiveData()
    var title: MutableLiveData<String> = MutableLiveData()
    var date: MutableLiveData<DateTime> = MutableLiveData()

    init {
        title.value = ""
        date.value = DateTime.now().withTimeAtStartOfDay().withTimeAtStartOfDay()
    }

    fun save(): Boolean {
        if (!validate()) {
            return false
        }

        val day = Day(title.value!!, date.value!!)
        dayDao.insert(day)
        return true
    }

    fun validate(): Boolean {
        val map = mutableMapOf<Any, String>()

        if (title.value.isNullOrBlank()) {
            map[title] = "タイトルを入力してください"
        }

        if (map.isNotEmpty()) {
            validation.postValue(map)
            return false
        }
        return true
    }
}