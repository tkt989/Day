package info.tkt989.day

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.widget.RemoteViews
import org.joda.time.DateTime
import org.joda.time.Days

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
            (context.applicationContext as? MyApplication)?.dateStore?.db?.appWidgetDayDao()?.deleteById(appWidgetId)
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

            val appWidgetDay = app.dateStore.db.appWidgetDayDao().getById(appWidgetId)
            val day = appWidgetDay?.let {
                app.dateStore.db.dayDao().findByDayId(appWidgetDay.dayId)
            }

            day?.let {
                val now = DateTime.now().withTimeAtStartOfDay()
                val diff = Days.daysBetween(now, day.date).days

                views.setTextViewText(R.id.titleText, day.title)
                views.setTextViewText(R.id.daysText, diff.toString())
            }

            appWidgetManager.updateAppWidget(appWidgetId, views)
        }
    }
}

