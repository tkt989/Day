package info.tkt989.day

import android.app.Activity
import android.appwidget.AppWidgetManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog
import kotlinx.android.synthetic.main.day_widget_configure.*
import org.joda.time.DateTime
import org.joda.time.MutableDateTime
import java.util.*
import javax.security.auth.login.LoginException

class DayWidgetConfigureActivity : Activity(), DatePickerDialog.OnDateSetListener {
    override fun onDateSet(view: DatePickerDialog?, year: Int, monthOfYear: Int, dayOfMonth: Int) {
        dateTime = DateTime(year, monthOfYear + 1, dayOfMonth, 0, 0)
        dateText.text = dateTime.toString("yyyy/MM/dd")
    }

    val app: MyApplication
    get() {
        if (application is MyApplication) return application as MyApplication
        throw LoginException()
    }

    lateinit var dateTime: DateTime
    internal var mAppWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID
    internal lateinit var mAppWidgetText: EditText

    public override fun onCreate(icicle: Bundle?) {
        super.onCreate(icicle)

        val date = MutableDateTime.now()
        date.addDays(7)

        setResult(Activity.RESULT_CANCELED)

        setContentView(R.layout.day_widget_configure)

        // Find the widget id from the intent.
        val intent = intent
        val extras = intent.extras
        if (extras != null) {
            mAppWidgetId = extras.getInt(
                    AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID)
        }

        // If this activity was started with an intent without an app widget ID, finish with an error.
        if (mAppWidgetId == AppWidgetManager.INVALID_APPWIDGET_ID) {
            finish()
            return
        }

        dateTime = date.toDateTime()
        dateText.text = date.toString("yyyy/MM/dd")
        dateText.setOnClickListener {
            val dialog = DatePickerDialog.newInstance(this, date.year, date.monthOfYear-1, date.dayOfMonth)
            dialog.minDate = DateTime.now().toCalendar(Locale.JAPAN)
            dialog.show(fragmentManager, "dialog")
        }

        add.setOnClickListener {
            val day = Day(0, "tet", dateTime)
            app.dateStore.save(day)

            val resultValue = Intent()
            resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, mAppWidgetId)
            setResult(Activity.RESULT_OK, resultValue)

            DayWidget.updateAppWidget(this, AppWidgetManager.getInstance(this), mAppWidgetId)
            finish()
        }
    }

    companion object {
        private val PREFS_NAME = "info.tkt989.day.DayWidget"
        private val PREF_PREFIX_KEY = "appwidget_"

        // Write the prefix to the SharedPreferences object for this widget
        internal fun saveTitlePref(context: Context, appWidgetId: Int, text: String) {
            val prefs = context.getSharedPreferences(PREFS_NAME, 0).edit()
            prefs.putString(PREF_PREFIX_KEY + appWidgetId, text)
            prefs.apply()
        }

        // Read the prefix from the SharedPreferences object for this widget.
        // If there is no preference saved, get the default from a resource
        internal fun loadTitlePref(context: Context, appWidgetId: Int): String {
            val prefs = context.getSharedPreferences(PREFS_NAME, 0)
            val titleValue = prefs.getString(PREF_PREFIX_KEY + appWidgetId, null)
            return titleValue ?: context.getString(R.string.appwidget_text)
        }

        internal fun deleteTitlePref(context: Context, appWidgetId: Int) {
            val prefs = context.getSharedPreferences(PREFS_NAME, 0).edit()
            prefs.remove(PREF_PREFIX_KEY + appWidgetId)
            prefs.apply()
        }
    }
}

