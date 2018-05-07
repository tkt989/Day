package info.tkt989.day.ui

import android.arch.lifecycle.*
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import info.tkt989.day.Day
import info.tkt989.day.MyApplication
import info.tkt989.day.R
import kotlinx.android.synthetic.main.activity_main.*

fun <T> LiveData<T>.observe(owner: LifecycleOwner, observer: ((T?) -> Unit)) {
    this.observe(owner, android.arch.lifecycle.Observer<T>(observer))
}

class DayListActivity : AppCompatActivity() {
    lateinit var dayListViewModel: DayListViewModel
    lateinit var adapter: ArrayAdapter<Day>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1)
        dayListView.adapter = adapter

        dayListViewModel = ViewModelProviders.of(this).get(DayListViewModel::class.java)
        dayListViewModel.init((application as MyApplication).dateStore.dao)
        dayListViewModel.dayList.observe(this) {
            adapter.clear()
            adapter.addAll(it)
        }
        System.out.println(dayListViewModel.dayList.value)
    }
}
