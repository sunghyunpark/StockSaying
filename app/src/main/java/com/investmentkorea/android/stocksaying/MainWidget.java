package com.investmentkorea.android.stocksaying;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

import api.ApiClient;
import api.ApiInterface;
import api.response.LoginResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import util.SettingManager;
import view.FamousSayingListActivity;
import view.SettingActivity;


/**
 * Implementation of App Widget functionality.
 */
public class MainWidget extends AppWidgetProvider {

    public void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId, SettingManager settingManager, String contentsText) {
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.main_widget);

        // 배경색 지정, 반 투명 값 여부 체크하여 분기처리
        if(settingManager.isTranslucency()){
            // 반투명 적용된 상태
            for(int i=0;i<Contents.colorModelArray.length;i++){
                if(settingManager.getBackgroundDrawable() == Contents.colorModelArray[i].getBgDrawableResource()){
                    views.setInt(R.id.main_widget_layout, "setBackgroundResource", Contents.colorModelArray[i].getBgDrawableTranslucencyResource());
                    break;
                }
            }
        }else{
            // 반투명 미적용 상태
            views.setInt(R.id.main_widget_layout, "setBackgroundResource", settingManager.getBackgroundDrawable());
        }

        // 명언 문구 적용
        views.setTextViewText(R.id.contents_tv, contentsText);
        views.setTextColor(R.id.contents_tv, context.getResources().getColor(settingManager.getTextColor()));

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
    public void onUpdate(final Context context, final AppWidgetManager appWidgetManager, final int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        final SettingManager settingManager = new SettingManager(context);

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<LoginResponse> call = apiService.loginApi("xEdrQtezWUgFK3eg4Tnk1J6IJQG2");
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                LoginResponse loginResponse = response.body();
                for (int appWidgetId : appWidgetIds) {
                    updateAppWidget(context, appWidgetManager, appWidgetId, settingManager, loginResponse.getResult().getNickName());
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                // Log error here since request failed
                Log.e("tag", t.toString());
                for (int appWidgetId : appWidgetIds) {
                    updateAppWidget(context, appWidgetManager, appWidgetId, settingManager, "네트워크");
                }
            }
        });
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

