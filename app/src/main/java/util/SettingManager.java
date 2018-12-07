package util;

import android.content.Context;
import android.content.SharedPreferences;

import com.investmentkorea.android.stocksaying.R;

public class SettingManager {
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private int PRIVATE_MODE = 0;

    private static final String PREF_NAME = "stocksaying";
    private static final String KEY_BACKGROUND_COLOR = "bgColor";
    private static final String KEY_TEXT_COLOR = "textColor";
    private static final String KEY_IS_TRANSLUCENCY = "isTranslucency";

    public SettingManager(Context context){
        preferences = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = preferences.edit();
    }

    /*
    배경 색상 설정
     */
    public void setKeyBackgroundColor(int backgroundColor){
        editor.putInt(KEY_BACKGROUND_COLOR, backgroundColor);
        editor.commit();
    }

    /*
    배경 색상 반환
     */
    public int getBackgroundColor(){
        return preferences.getInt(KEY_BACKGROUND_COLOR, R.color.colorBlack);
    }

    /*
    글자 색 설정
     */
    public void setKeyTextColor(int textColor){
        editor.putInt(KEY_TEXT_COLOR, textColor);
        editor.commit();
    }

    /*
    글자 색 반환
     */
    public int getTextColor(){
        return preferences.getInt(KEY_TEXT_COLOR, R.color.colorBlack);
    }

    /*
    투명색 설정
     */
    public void setKeyIsTranslucency(boolean isTranslucency){
        editor.putBoolean(KEY_IS_TRANSLUCENCY, isTranslucency);
        editor.commit();
    }

    /*
    투명 설정인지 true / false 반환
     */
    public boolean isTranslucency(){
        return preferences.getBoolean(KEY_IS_TRANSLUCENCY, false);
    }
}
