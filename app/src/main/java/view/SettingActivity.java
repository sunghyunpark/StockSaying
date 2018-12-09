package view;

import android.app.WallpaperManager;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RemoteViews;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.investmentkorea.android.stocksaying.Contents;
import com.investmentkorea.android.stocksaying.MainWidget;
import com.investmentkorea.android.stocksaying.R;
import com.skydoves.powermenu.CustomPowerMenu;
import com.skydoves.powermenu.OnDismissedListener;
import com.skydoves.powermenu.OnMenuItemClickListener;

import base.BaseActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import model.ColorModel;
import model.ColorPowerMenuModel;
import util.PowerMenuUtil;
import util.SettingManager;

/*
 * 위젯 설정 화면
 */
public class SettingActivity extends BaseActivity {

    private int mAppWidgetId;
    private RemoteViews remoteView;
    private AppWidgetManager appWidgetManager;

    private ColorModel colorModel;
    private SettingManager settingManager;
    private CustomPowerMenu bgColorPowerMenu, textColorPowerMenu;

    @BindView(R.id.background_iv) ImageView backGroundIv;    // 프리뷰 화면의 배경(단말기 배경화면)
    @BindView(R.id.main_widget_layout) ViewGroup mainWidgetLayout;    // 위젯
    @BindView(R.id.text_color_select_iv) ImageView textColorSelectIv;    // 글자색 썸네일
    @BindView(R.id.text_color_tv) TextView textColorTv;    // 글자색 이름
    @BindView(R.id.background_color_select_iv) ImageView backgroundColorSelectIv;    // 배경색 썸네일
    @BindView(R.id.background_color_tv) TextView backgroundColorTv;    // 배경색 이름
    @BindView(R.id.select_background_color_layout) ViewGroup selectBackgroundColorLayout;    // 배경색 레이아웃
    @BindView(R.id.select_text_color_layout) ViewGroup selectTextColorLayout;     // 글자색 레이아웃
    @BindView(R.id.contents_tv) TextView contentsTv;    // 위젯 문구
    @BindView(R.id.half_select_toggle) ToggleButton halfSelectToggle;    // 반투명 토글 버튼

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            mAppWidgetId = extras.getInt(
                    AppWidgetManager.EXTRA_APPWIDGET_ID,
                    AppWidgetManager.INVALID_APPWIDGET_ID);
        }

        appWidgetManager = AppWidgetManager.getInstance(this);
        remoteView = new RemoteViews(this.getPackageName(),
                R.layout.main_widget);
        appWidgetManager.updateAppWidget(mAppWidgetId, remoteView);

        ButterKnife.bind(this);
        init();
    }

    /*
    설정 화면 초기화
    글자색, 배경색 정보 변수들을 초기화해준다.
     */
    private void init(){
        settingManager = new SettingManager(getApplicationContext());

        // 글자색, 배경색, 반투명 값들을 SettingManager 에서 저장을 하고 ColorModel 에 적용하여 사용한다.
        colorModel = new ColorModel(settingManager.getTextColor(), settingManager.getTextColorName(), settingManager.getBackgroundColor(), settingManager.getBackgroundDrawableName(),
                settingManager.getBackgroundDrawable(), Contents.colorModelArray[0].getBgDrawableTranslucencyResource());

        // 반투명 값의 경우는 Contents 클래스의 배열에서 같은 position 값을 사용하기 때문에 현재 저장된 배경색의 position 값을 추출 후 그에 맞는 반투명이 적용될 position 에 적용한다.
        for(int i=0;i<Contents.colorModelArray.length;i++){
            if(settingManager.getBackgroundDrawable() == Contents.colorModelArray[i].getBgDrawableResource()){
                colorModel.setBgDrawableTranslucencyResource(Contents.colorModelArray[i].getBgDrawableTranslucencyResource());
                break;
            }
        }

        // 글자색, 배경색을 탭 했을 경우 노출되는 PowerMenu 초기화
        bgColorPowerMenu = PowerMenuUtil.getBgColorListMenu(getApplicationContext(), this, onBgMenuItemClickListener, onBgMenuDismissedListener);
        textColorPowerMenu = PowerMenuUtil.getTextColorListMenu(getApplicationContext(), this, onTextMenuItemClickListener, onTextMenuDismissedListener);

        // 미리보기 초기화
        initWidgetPreview(settingManager.getBackgroundDrawable());
        // 하단 글자색, 배경색 버튼 초기화
        initBtn(settingManager.getTextColor(), settingManager.getBackgroundColor());
        // 반투명 토글 버튼 초기화
        initToggleBtn(settingManager.isTranslucency());
    }

    /*
    위젯 미리보기 초기화
     */
    private void initWidgetPreview(int drawableResource){
        Drawable roundDrawable = getResources().getDrawable(drawableResource);
        mainWidgetLayout.setBackground(roundDrawable);
        backGroundIv.setBackground(WallpaperManager.getInstance(this).getDrawable());
        contentsTv.setTextColor(getResources().getColor(settingManager.getTextColor()));
    }

    /*
    하단 위젯 설정 버튼 초기화
     */
    private void initBtn(int textColor, int bgColor){
        // 글자 색 선택 초기화
        Drawable textColorDrawable = getResources().getDrawable(R.drawable.select_color_thumb_shape);
        textColorDrawable.setColorFilter(getApplicationContext().getResources().getColor(textColor), PorterDuff.Mode.SRC_ATOP);
        textColorSelectIv.setBackground(textColorDrawable);
        textColorTv.setText(settingManager.getTextColorName());

        // 배경 색 선택 초기화
        Drawable bgColorDrawable = getResources().getDrawable(R.drawable.select_color_thumb_shape);
        bgColorDrawable.setColorFilter(getApplicationContext().getResources().getColor(bgColor), PorterDuff.Mode.SRC_ATOP);
        backgroundColorSelectIv.setBackground(bgColorDrawable);
        backgroundColorTv.setText(settingManager.getBackgroundDrawableName());
    }

    /*
    배경색 메뉴에서 선택 후 이벤트
     */
    private OnMenuItemClickListener<ColorPowerMenuModel> onBgMenuItemClickListener = new OnMenuItemClickListener<ColorPowerMenuModel>() {
        @Override
        public void onItemClick(int position, ColorPowerMenuModel item) {
            bgColorPowerMenu.dismiss();

            colorModel.setBgColorName(Contents.colorModelArray[position].getBgColorName());
            colorModel.setBgColorResource(Contents.colorModelArray[position].getBgColorResource());
            colorModel.setBgDrawableResource(Contents.colorModelArray[position].getBgDrawableResource());
            // 선택한 배경색의 반투명 값을 ColorModel 에 적용
            colorModel.setBgDrawableTranslucencyResource(Contents.colorModelArray[position].getBgDrawableTranslucencyResource());

            // 배경색 썸네일 및 문구 적용
            backgroundColorSelectIv.setBackground(PowerMenuUtil.getColorDrawable(getApplicationContext(), colorModel.getBgColorResource()));
            backgroundColorTv.setText(colorModel.getBgColorName());
            // 미리보기 > 위젯 배경 색상 적용, 반투명 값 설정 여부 체크하여 분기처리
            if(settingManager.isTranslucency()){
                // 반투명 값이 체크된 상태에서 배경색을 변경한 경우
                mainWidgetLayout.setBackground(getResources().getDrawable(colorModel.getBgDrawableTranslucencyResource()));
            }else{
                mainWidgetLayout.setBackground(getResources().getDrawable(colorModel.getBgDrawableResource()));
            }
        }
    };

    private OnDismissedListener onBgMenuDismissedListener = new OnDismissedListener() {
        @Override
        public void onDismissed() {
        }
    };

    /*
    글자색 메뉴에서 선택 후 이벤트
     */
    private OnMenuItemClickListener<ColorPowerMenuModel> onTextMenuItemClickListener = new OnMenuItemClickListener<ColorPowerMenuModel>() {
        @Override
        public void onItemClick(int position, ColorPowerMenuModel item) {
            textColorPowerMenu.dismiss();

            colorModel.setTextColorResource(Contents.colorModelArray[position].getTextColorResource());
            colorModel.setTextColorName(Contents.colorModelArray[position].getTextColorName());
            // 글자색 썸네일 및 문구 적용
            textColorSelectIv.setBackground(PowerMenuUtil.getColorDrawable(getApplicationContext(), colorModel.getTextColorResource()));
            textColorTv.setText(colorModel.getTextColorName());
            // 미리보기 > 위젯 문구 색상 적용
            contentsTv.setTextColor(getApplicationContext().getResources().getColor(colorModel.getTextColorResource()));
        }
    };

    private OnDismissedListener onTextMenuDismissedListener = new OnDismissedListener() {
        @Override
        public void onDismissed() {
        }
    };

    /*
    반투명 토글 버튼 초기화
     */
    private void initToggleBtn(boolean isAlpha){
        if(isAlpha){
            halfSelectToggle.setChecked(true);
            halfSelectToggle.setBackgroundResource(R.mipmap.checked_img_72);
            // ColorModel 에 적용되어 있는 반투명값 적용
            mainWidgetLayout.setBackground(getResources().getDrawable(colorModel.getBgDrawableTranslucencyResource()));
        }else{
            halfSelectToggle.setChecked(false);
            halfSelectToggle.setBackgroundResource(R.mipmap.check_img_72);
            // ColorModel 에 적용되어 있는 배경값 적용
            mainWidgetLayout.setBackground(getResources().getDrawable(colorModel.getBgDrawableResource()));
        }
    }

    @OnClick({R.id.back_btn, R.id.select_text_color_layout, R.id.select_background_color_layout, R.id.save_btn, R.id.half_select_toggle}) void onClick(View v){
        switch (v.getId()){
            case R.id.back_btn:
                finish();
                break;
            case R.id.select_background_color_layout:
                bgColorPowerMenu.showAtCenter(selectBackgroundColorLayout);
                break;
            case R.id.select_text_color_layout:
                textColorPowerMenu.showAtCenter(selectTextColorLayout);
                break;
            case R.id.save_btn:
                // SharedPreference 에 배경색 저장
                settingManager.setKeyBackgroundDrawable(colorModel.getBgDrawableResource());
                settingManager.setKeyBackgroundDrawableName(colorModel.getBgColorName());
                settingManager.setKeyBackgroundColor(colorModel.getBgColorResource());

                // SharedPreference 에 글자색 저장
                settingManager.setKeyTextColor(colorModel.getTextColorResource());
                settingManager.setKeyTextColorName(colorModel.getTextColorName());

                // SharedPreference 에 반투명 여부 저장
                settingManager.setKeyIsTranslucency(halfSelectToggle.isChecked());

                // 위젯에 브로드캐스트 전송
                Intent update = new Intent(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
                update.setClass(this, MainWidget.class);
                update.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetManager.getAppWidgetIds(new ComponentName(this, MainWidget.class)));
                this.sendBroadcast(update);

                appWidgetManager.updateAppWidget(mAppWidgetId, remoteView);
                Intent resultValue = new Intent();
                resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, mAppWidgetId);
                setResult(RESULT_OK, resultValue);
                finish();
                break;
            case R.id.half_select_toggle:
                initToggleBtn(halfSelectToggle.isChecked());
                break;
        }
    }

    @Override
    public void onBackPressed() {
        try{
            if(bgColorPowerMenu.isShowing()){
                bgColorPowerMenu.dismiss();
            }else if(textColorPowerMenu.isShowing()){
                textColorPowerMenu.dismiss();
            }else{
                finish();
                super.onBackPressed();
            }
        }catch (NullPointerException npe){
            finish();
        }
    }
}
