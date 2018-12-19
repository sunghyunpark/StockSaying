package com.investmentkorea.android.stocksaying;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.RemoteViews;

import api.ApiClient;
import api.ApiInterface;
import api.response.SayingListResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import util.SettingManager;
import util.Util;
import view.SayingListActivity;
import view.SettingActivity;


/**
 * Implementation of App Widget functionality.
 */
public class MainWidget extends AppWidgetProvider {

    private static final String REFRESH_CLICK = "refresh";

    public void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId, SettingManager settingManager, String contentsText, String createdAt, String authorName, int gravityHorizontal, int gravityVertical, int textSize) {
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
        // 명언 문구, 날짜, 작가 색상 적용
        views.setTextColor(R.id.contents_tv, context.getResources().getColor(settingManager.getTextColor()));
        views.setTextColor(R.id.author_tv, context.getResources().getColor(settingManager.getTextColor()));
        views.setTextColor(R.id.created_at_tv, context.getResources().getColor(settingManager.getTextColor()));

        // 명언 문구 크기 적용
        views.setTextViewTextSize(R.id.contents_tv, TypedValue.COMPLEX_UNIT_DIP, textSize);

        // 명언 정렬 적용
        views.setInt(R.id.contents_layout, "setGravity", getVertical(gravityVertical) | getHorizontal(gravityHorizontal));

        // 등록 날짜
        views.setTextViewText(R.id.created_at_tv, createdAt);

        // 작가 적용
        views.setTextViewText(R.id.author_tv, "by " + authorName);

        // 셋팅 버튼 클릭
        Intent moreIntent = new Intent(context, SettingActivity.class);
        PendingIntent morePendingIntent = PendingIntent.getActivity(context, 0, moreIntent, 0);
        views.setOnClickPendingIntent(R.id.setting_btn, morePendingIntent);

        /*
        Intent moreIntent = new Intent(Intent.ACTION_VIEW);
        moreIntent.setData(Uri.parse("https://m.naver.com"));
        PendingIntent morePendingIntent = PendingIntent.getActivity(context, 0, moreIntent, 0);
        views.setOnClickPendingIntent(R.id.more_btn, morePendingIntent);
        */

        // 리스트 버튼 클릭
        Intent contentsIntent = new Intent(context, SayingListActivity.class);
        PendingIntent contentsPendingIntent = PendingIntent.getActivity(context, 0, contentsIntent, 0);
        views.setOnClickPendingIntent(R.id.list_btn, contentsPendingIntent);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(final Context context, final AppWidgetManager appWidgetManager, final int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        final SettingManager settingManager = new SettingManager(context);

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<SayingListResponse> call = apiService.getSaying();
        call.enqueue(new Callback<SayingListResponse>() {
            @Override
            public void onResponse(Call<SayingListResponse> call, Response<SayingListResponse> response) {
                SayingListResponse sayingListResponse = response.body();
                if(sayingListResponse.getResult().size() > 0){
                    // 등록된 명언이 있을 때
                    for (int appWidgetId : appWidgetIds) {
                        updateAppWidget(context, appWidgetManager, appWidgetId, settingManager, sayingListResponse.getResult().get(0).getContents(), Util.parseTimeWithoutTime(sayingListResponse.getResult().get(0).getCreatedAt()), sayingListResponse.getResult().get(0).getAuthorName(),
                                sayingListResponse.getResult().get(0).getGravityHorizontal(), sayingListResponse.getResult().get(0).getGravityVertical(), sayingListResponse.getResult().get(0).getTextSize());
                    }
                }else{
                    // 등록된 명언이 없을 때
                    for (int appWidgetId : appWidgetIds) {
                        updateAppWidget(context, appWidgetManager, appWidgetId, settingManager, "원데이 주식 명언입니다.", StockSayingApplication.TODAY_YEAR+"-"+StockSayingApplication.TODAY_MONTH+"-"+
                                StockSayingApplication.TODAY_DAY, "준비중 입니다.",
                                2, 2, 15);
                    }
                }
            }

            @Override
            public void onFailure(Call<SayingListResponse> call, Throwable t) {
                // Log error here since request failed
                Log.e("tag", t.toString());

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

    private int getHorizontal(int n){
        if(n == 1){
            return Gravity.START;
        }else if(n == 2){
            return Gravity.CENTER_HORIZONTAL;
        }else{
            return Gravity.END;
        }
    }

    private int getVertical(int n){
        if(n == 1){
            return Gravity.TOP;
        }else if(n == 2){
            return Gravity.CENTER_VERTICAL;
        }else{
            return Gravity.BOTTOM;
        }
    }
}

