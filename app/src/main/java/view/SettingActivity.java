package view;

import android.app.WallpaperManager;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.investmentkorea.android.stocksaying.Contents;
import com.investmentkorea.android.stocksaying.R;
import com.skydoves.powermenu.CustomPowerMenu;
import com.skydoves.powermenu.OnDismissedListener;
import com.skydoves.powermenu.OnMenuItemClickListener;

import base.BaseActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import model.ColorPowerMenuModel;
import util.PowerMenuUtil;
import util.SettingManager;

/*
 * 위젯 설정 화면
 */
public class SettingActivity extends BaseActivity {

    private Drawable textColorDrawable, bgColorDrawable;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        ButterKnife.bind(this);
        init();
    }

    private void init(){
        settingManager = new SettingManager(getApplicationContext());

        bgColorPowerMenu = PowerMenuUtil.getBgColorListMenu(getApplicationContext(), this, onBgMenuItemClickListener, onBgMenuDismissedListener);
        textColorPowerMenu = PowerMenuUtil.getTextColorListMenu(getApplicationContext(), this, onTextMenuItemClickListener, onTextMenuDismissedListener);

        initWidgetPreview(settingManager.getBackgroundDrawable());
        initBtn(settingManager.getTextColor(), settingManager.getBackgroundColor());
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
        textColorDrawable = getResources().getDrawable(R.drawable.select_color_thumb_shape);
        textColorDrawable.setColorFilter(getApplicationContext().getResources().getColor(textColor), PorterDuff.Mode.SRC_ATOP);
        textColorSelectIv.setBackground(textColorDrawable);
        textColorTv.setText(settingManager.getTextColorName());

        // 배경 색 선택 초기화
        bgColorDrawable = getResources().getDrawable(R.drawable.select_color_thumb_shape);
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

            // SharedPreferences 저장
            settingManager.setKeyBackgroundDrawable(Contents.colorModelArray[position].getBgDrawableResource());
            settingManager.setKeyBackgroundDrawableName(Contents.colorModelArray[position].getBgColorName());
            settingManager.setKeyBackgroundColor(Contents.colorModelArray[position].getBgColorResource());
            // 배경색 썸네일 및 문구 적용
            backgroundColorSelectIv.setBackground(PowerMenuUtil.getColorDrawable(getApplicationContext(), Contents.colorModelArray[position].getBgColorResource()));
            backgroundColorTv.setText(Contents.colorModelArray[position].getBgColorName());
            // 미리보기 > 위젯 배경 색상 적용
            mainWidgetLayout.setBackground(getResources().getDrawable(Contents.colorModelArray[position].getBgDrawableResource()));
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

            // SharedPreferences 저장
            settingManager.setKeyTextColor(Contents.colorModelArray[position].getTextColorResource());
            settingManager.setKeyTextColorName(Contents.colorModelArray[position].getTextColorName());
            // 글자색 썸네일 및 문구 적용
            textColorSelectIv.setBackground(PowerMenuUtil.getColorDrawable(getApplicationContext(), Contents.colorModelArray[position].getTextColorResource()));
            textColorTv.setText(Contents.colorModelArray[position].getTextColorName());
            // 미리보기 > 위젯 문구 색상 적용
            contentsTv.setTextColor(getApplicationContext().getResources().getColor(Contents.colorModelArray[position].getTextColorResource()));
        }
    };

    private OnDismissedListener onTextMenuDismissedListener = new OnDismissedListener() {
        @Override
        public void onDismissed() {
        }
    };



    @OnClick({R.id.back_btn, R.id.select_text_color_layout, R.id.select_background_color_layout}) void onClick(View v){
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
