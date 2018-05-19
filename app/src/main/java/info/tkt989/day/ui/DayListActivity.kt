package info.tkt989.day.ui

import android.arch.lifecycle.*
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import info.tkt989.day.R
import info.tkt989.day.model.Day

class DayListActivity : AppCompatActivity(), DayListFragment.DayListFragmentListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onAdd() {
        startActivity(Intent(this, EditDayActivity::class.java))
    }

    override fun onDayClick(day: Day) {
        startActivity(EditDayActivity.createIntent(this, day))
    }
}
