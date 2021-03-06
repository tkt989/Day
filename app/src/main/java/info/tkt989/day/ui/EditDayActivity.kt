package info.tkt989.day.ui

import android.appwidget.AppWidgetManager
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog
import info.tkt989.day.*
import info.tkt989.day.model.Day
import kotlinx.android.synthetic.main.activity_edit_day.*
import org.joda.time.DateTime
import java.util.*

class EditDayActivity : AppCompatActivity() {
    lateinit var viewModel: EditDayViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_day)

        viewModel = ViewModelProviders.of(this).get(EditDayViewModel::class.java)
        viewModel.dayDao = (application as MyApplication).dateStore.db.dayDao()

        viewModel.date.observe(this) { dateText.text = it?.toString("yyyy/MM/dd") }

        viewModel.validation.observe(this) {
            it?.forEach {
                when (it.key) {
                    viewModel.title -> titleText.error = it.value
                }
            }
        }

        intent?.apply {
            (getSerializableExtra(DAY) as? Day)?.let {
                titleText.setText(it.title)
                viewModel.id.value = it.id
                viewModel.date.value = it.date
            }
        }

        dateText.setOnClickListener {
            val date = viewModel.date.value ?: return@setOnClickListener

            val dialog = DatePickerDialog.newInstance(null, date.year, date.monthOfYear - 1, date.dayOfMonth)
            dialog.minDate = DateTime.now().toCalendar(Locale.JAPAN)
            dialog.setOnDateSetListener { _, year, month, day ->
                viewModel.date.value = DateTime(year, month + 1, day, 0, 0)
            }
            dialog.vibrate(false)
            dialog.show(fragmentManager, "dialog")
        }

        titleText.addTextChangedListener { viewModel.title.postValue(it) }

        doneButton.setOnClickListener {
            if (viewModel.save()) {
                viewModel.id.value?.let {
                    val idList = (application as? MyApplication)?.dateStore?.db?.appWidgetDayDao()?.findByDayId(viewModel.id.value!!)
                    idList?.forEach {
                        DayWidget.updateAppWidget(this, AppWidgetManager.getInstance(this), it.appWidgetId)
                    }
                }
                finish()
            }
        }
    }

    companion object {
        private val DAY = "DAY"

        fun createIntent(context: Context, day: Day): Intent {
            val intent = Intent(context, EditDayActivity::class.java)
            intent.putExtra(DAY, day)
            return intent
        }
    }
}
