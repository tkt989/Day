package info.tkt989.day

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.widget.RemoteViews
import org.joda.time.DateTime
import org.joda.time.Days
import org.joda.time.Period
import java.util.*

/**
 * Implementation of App Widget functionality.
 * App Widget Configuration implemented in [DayWidgetConfigureActivity]
 */
class DayWidget : AppWidgetProvider() {
    override fun onUpdate(context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray) {
        // There may be multiple widgets active, so update all of them
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }

    override fun onDeleted(context: Context, appWidgetIds: IntArray) {
        // When the user deletes the widget, delete the preference associated with it.
        for (appWidgetId in appWidgetIds) {
            DayWidgetConfigureActivity.deleteTitlePref(context, appWidgetId)
        }
    }

    override fun onEnabled(context: Context) {
        // Enter relevant functionality for when the first widget is created
    }

    override fun onDisabled(context: Context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    companion object {

        internal fun updateAppWidget(context: Context, appWidgetManager: AppWidgetManager,
                                     appWidgetId: Int) {
            val views = RemoteViews(context.packageName, R.layout.day_widget)

            val app = context.applicationContext as MyApplication

            val day = app.dateStore.load(appWidgetId)
            day?.let {
                val random = Random()
                val now = DateTime.now()
                val days = Days.daysBetween(now, day.date)
                views.setTextViewText(R.id.days, (days.days+1).toString() + " " + random.nextInt().toString())
            }

            appWidgetManager.updateAppWidget(appWidgetId, views)
        }
    }
}

