package info.tkt989.day.ui

import android.arch.lifecycle.*
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import info.tkt989.day.MyApplication
import info.tkt989.day.R
import info.tkt989.day.model.Day
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

fun <T> LiveData<T>.observe(owner: LifecycleOwner, observer: ((T?) -> Unit)) {
    this.observe(owner, android.arch.lifecycle.Observer<T>(observer))
}

class DayListActivity : AppCompatActivity() {
    @Inject lateinit var dayListViewModel: DayListViewModel
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

        addButton.setOnClickListener {
            startActivity(Intent(this, EditDayActivity::class.java))
        }
    }
}
