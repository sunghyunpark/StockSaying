package view;

import android.app.WallpaperManager;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.investmentkorea.android.stocksaying.R;

import base.BaseActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/*
 * 위젯 설정 화면
 */
public class SettingActivity extends BaseActivity {

    private Drawable textColorDrawable, bgColorDrawable;

    @BindView(R.id.background_iv) ImageView backGroundIv;
    @BindView(R.id.main_widget_layout) ViewGroup mainWidgetLayout;
    @BindView(R.id.text_color_select_iv) ImageView textColorSelectIv;
    @BindView(R.id.background_color_select_iv) ImageView backgroundColorSelectIv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        ButterKnife.bind(this);
        init();
    }

    private void init(){
        initWidgetPreview(R.drawable.half_black_widget);
        initBtn(R.color.colorBlack, R.color.colorBlack);
    }

    /*
    위젯 미리보기 초기화
     */
    private void initWidgetPreview(int drawableResource){
        Drawable roundDrawable = getResources().getDrawable(drawableResource);
        mainWidgetLayout.setBackground(roundDrawable);
        backGroundIv.setBackground(WallpaperManager.getInstance(this).getDrawable());
    }

    /*
    하단 위젯 설정 버튼 초기화
     */
    private void initBtn(int textColor, int bgColor){
        // 글자 색 선택 초기화
        textColorDrawable = getResources().getDrawable(R.drawable.select_color_thumb_shape);
        textColorDrawable.setColorFilter(getApplicationContext().getResources().getColor(textColor), PorterDuff.Mode.SRC_ATOP);
        textColorSelectIv.setBackground(textColorDrawable);

        // 배경 색 선택 초기화
        bgColorDrawable = getResources().getDrawable(R.drawable.select_color_thumb_shape);
        bgColorDrawable.setColorFilter(getApplicationContext().getResources().getColor(bgColor), PorterDuff.Mode.SRC_ATOP);
        backgroundColorSelectIv.setBackground(bgColorDrawable);
    }

    @OnClick({R.id.back_btn, R.id.select_text_color_layout, R.id.select_background_color_layout}) void onClick(View v){
        switch (v.getId()){
            case R.id.back_btn:
                finish();
                break;
            case R.id.select_background_color_layout:
                break;
            case R.id.select_text_color_layout:
                break;
        }
    }
}
