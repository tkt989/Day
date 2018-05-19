package info.tkt989.day

import android.app.Activity
import android.app.Fragment
import android.appwidget.AppWidgetManager
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import info.tkt989.day.model.AppWidgetDay
import info.tkt989.day.model.Day
import info.tkt989.day.ui.DayListFragment
import javax.security.auth.login.LoginException

class DayWidgetConfigureActivity : AppCompatActivity(), DayListFragment.DayListFragmentListener {
    val app: MyApplication
    get() {
        if (application is MyApplication) return application as MyApplication
        throw LoginException()
    }

    internal var mAppWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID

    public override fun onCreate(icicle: Bundle?) {
        super.onCreate(icicle)

        setResult(Activity.RESULT_CANCELED)
        setContentView(R.layout.day_widget_configure)

        val fragment = DayListFragment.newInstance(editMode = false)
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.container, fragment)
            commit()
        }

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
    }

    override fun onAdd() {
        throw LoginException()
    }

    override fun onDayClick(day: Day) {
        val appWidgetDay = AppWidgetDay(appWidgetId = mAppWidgetId, dayId = day.id)
        app.dateStore.db.appWidgetDayDao().insert(appWidgetDay)

        val intent = Intent()
        intent.apply {
            putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, mAppWidgetId)
        }
        setResult(Activity.RESULT_OK, intent)

        DayWidget.updateAppWidget(this, AppWidgetManager.getInstance(this), mAppWidgetId)
        finish()
    }
}

