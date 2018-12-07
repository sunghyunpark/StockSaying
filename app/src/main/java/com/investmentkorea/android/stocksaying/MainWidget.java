package com.investmentkorea.android.stocksaying;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import view.FamousSayingListActivity;
import view.SettingActivity;


/**
 * Implementation of App Widget functionality.
 */
public class MainWidget extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        CharSequence widgetText = context.getString(R.string.appwidget_text);
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.main_widget);

        // 배경색 지정
        views.setInt(R.id.main_widget_layout, "setBackgroundResource", R.drawable.half_black_widget);

        // 명언 문구 적용
        views.setTextViewText(R.id.contents_tv, widgetText);

        // 등록 날짜
        views.setTextViewText(R.id.created_at_tv, "2018-12-04");

        // 작가 적용
        views.setTextViewText(R.id.writer_id, "박성현");

        // 더보기 버튼 클릭
        Intent moreIntent = new Intent(context, SettingActivity.class);
        PendingIntent morePendingIntent = PendingIntent.getActivity(context, 0, moreIntent, 0);
        views.setOnClickPendingIntent(R.id.more_btn, morePendingIntent);

        // 문구 클릭
        Intent contentsIntent = new Intent(context, FamousSayingListActivity.class);
        PendingIntent contentsPendingIntent = PendingIntent.getActivity(context, 0, contentsIntent, 0);
        views.setOnClickPendingIntent(R.id.contents_tv, contentsPendingIntent);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

