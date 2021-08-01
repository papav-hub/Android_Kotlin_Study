package com.example.flashlight

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews

/**
 * Implementation of App Widget functionality.
 */
class TorchAppWidget : AppWidgetProvider() { // 위젯을 업데이트 해야할 경우 호출
    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        // There may be multiple widgets active, so update all of them
        for (appWidgetId in appWidgetIds) { // 배치되어 있는 모든 위젯을 업데이트
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }

    override fun onEnabled(context: Context) { // 위젯이 처음 생성될 때 호출
        // Enter relevant functionality for when the first widget is created
    }

    override fun onDisabled(context: Context) { // 여러개의 위젯에서 마지막 위젯이 제거될 때 호출
        // Enter relevant functionality for when the last widget is disabled
    }

    companion object {
        // 위젯이 업데이트 될 때 실행되는 메소드
        internal fun updateAppWidget(
            context: Context,
            appWidgetManager: AppWidgetManager,
            appWidgetId: Int
        ) {
            val widgetText = context.getString(R.string.appwidget_text)
            val views = RemoteViews(context.packageName, R.layout.torch_app_widget) // RemoteViews는 위젯에 배치하는 전용 뷰
            views.setTextViewText(R.id.appwidget_text, widgetText) // RemoteViews에서 텍스트 값을 설정하는 메소드

            val intent = Intent(context, TorchService::class.java)
            val pendingIntent = PendingIntent.getService(context, 0, intent, 0)//리퀘스트 코드와 플래그는 사용하지 않음
            // 위젯을 클릭하면 위에서 정의된 인텐트 실행
            views.setOnClickPendingIntent(R.id.appwidget_layout, pendingIntent)
            // 레이아웃을 모두 수정했다면 AppWidgetManager를 사용하여 위젯을 업데이트
            appWidgetManager.updateAppWidget(appWidgetId, views)
        }
    }
}

internal fun updateAppWidget(
    context: Context,
    appWidgetManager: AppWidgetManager,
    appWidgetId: Int
) {
    val widgetText = context.getString(R.string.appwidget_text)
    // Construct the RemoteViews object
    val views = RemoteViews(context.packageName, R.layout.torch_app_widget)
    views.setTextViewText(R.id.appwidget_text, widgetText)

    // Instruct the widget manager to update the widget
    appWidgetManager.updateAppWidget(appWidgetId, views)
}