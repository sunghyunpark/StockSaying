package util;

import android.arch.lifecycle.LifecycleOwner;
import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;

import com.investmentkorea.android.stocksaying.Contents;
import com.investmentkorea.android.stocksaying.R;
import com.investmentkorea.android.stocksaying.StockSayingApplication;
import com.skydoves.powermenu.CustomPowerMenu;
import com.skydoves.powermenu.OnDismissedListener;
import com.skydoves.powermenu.OnMenuItemClickListener;

import model.ColorPowerMenuModel;
import util.adapter.ColorMenuAdapter;

public class PowerMenuUtil {

    /*
    배경색 선택 팝업
     */
    public static CustomPowerMenu getBgColorListMenu(Context context, LifecycleOwner lifecycleOwner, OnMenuItemClickListener<ColorPowerMenuModel> onMenuItemClickListener, OnDismissedListener onDismissedListener){
        return new CustomPowerMenu.Builder<>(context, new ColorMenuAdapter())
                .addItem(new ColorPowerMenuModel(getColorDrawable(context, Contents.colorModelArray[0].getBgColorResource()), Contents.colorModelArray[0].getBgColorName()))
                .addItem(new ColorPowerMenuModel(getColorDrawable(context, Contents.colorModelArray[1].getBgColorResource()), Contents.colorModelArray[1].getBgColorName()))
                .addItem(new ColorPowerMenuModel(getColorDrawable(context, Contents.colorModelArray[2].getBgColorResource()), Contents.colorModelArray[2].getBgColorName()))
                .addItem(new ColorPowerMenuModel(getColorDrawable(context, Contents.colorModelArray[3].getBgColorResource()), Contents.colorModelArray[3].getBgColorName()))
                .addItem(new ColorPowerMenuModel(getColorDrawable(context, Contents.colorModelArray[4].getBgColorResource()), Contents.colorModelArray[4].getBgColorName()))
                .addItem(new ColorPowerMenuModel(getColorDrawable(context, Contents.colorModelArray[5].getBgColorResource()), Contents.colorModelArray[5].getBgColorName()))
                .addItem(new ColorPowerMenuModel(getColorDrawable(context, Contents.colorModelArray[6].getBgColorResource()), Contents.colorModelArray[6].getBgColorName()))
                .addItem(new ColorPowerMenuModel(getColorDrawable(context, Contents.colorModelArray[7].getBgColorResource()), Contents.colorModelArray[7].getBgColorName()))
                .setWidth(StockSayingApplication.DISPLAY_WIDTH - StockSayingApplication.DISPLAY_WIDTH / 4)
                .setOnMenuItemClickListener(onMenuItemClickListener)
                .setOnDismissListener(onDismissedListener)
                .setLifecycleOwner(lifecycleOwner)
                .build();
    }

    /*
    글자색 선택 팝업
     */
    public static CustomPowerMenu getTextColorListMenu(Context context, LifecycleOwner lifecycleOwner, OnMenuItemClickListener<ColorPowerMenuModel> onMenuItemClickListener, OnDismissedListener onDismissedListener){
        return new CustomPowerMenu.Builder<>(context, new ColorMenuAdapter())
                .addItem(new ColorPowerMenuModel(getColorDrawable(context, Contents.colorModelArray[0].getTextColorResource()), Contents.colorModelArray[0].getTextColorName()))
                .addItem(new ColorPowerMenuModel(getColorDrawable(context, Contents.colorModelArray[1].getTextColorResource()), Contents.colorModelArray[1].getTextColorName()))
                .addItem(new ColorPowerMenuModel(getColorDrawable(context, Contents.colorModelArray[2].getTextColorResource()), Contents.colorModelArray[2].getTextColorName()))
                .addItem(new ColorPowerMenuModel(getColorDrawable(context, Contents.colorModelArray[3].getTextColorResource()), Contents.colorModelArray[3].getTextColorName()))
                .addItem(new ColorPowerMenuModel(getColorDrawable(context, Contents.colorModelArray[4].getTextColorResource()), Contents.colorModelArray[4].getTextColorName()))
                .addItem(new ColorPowerMenuModel(getColorDrawable(context, Contents.colorModelArray[5].getTextColorResource()), Contents.colorModelArray[5].getTextColorName()))
                .addItem(new ColorPowerMenuModel(getColorDrawable(context, Contents.colorModelArray[6].getTextColorResource()), Contents.colorModelArray[6].getTextColorName()))
                .addItem(new ColorPowerMenuModel(getColorDrawable(context, Contents.colorModelArray[7].getTextColorResource()), Contents.colorModelArray[7].getTextColorName()))
                .setWidth(StockSayingApplication.DISPLAY_WIDTH - StockSayingApplication.DISPLAY_WIDTH / 4)
                .setOnMenuItemClickListener(onMenuItemClickListener)
                .setOnDismissListener(onDismissedListener)
                .setLifecycleOwner(lifecycleOwner)
                .build();
    }

    public static Drawable getColorDrawable(Context context, int color){
        Drawable drawable = context.getResources().getDrawable(R.drawable.select_color_thumb_shape);
        drawable.setColorFilter(context.getResources().getColor(color), PorterDuff.Mode.SRC_ATOP);
        return drawable;
    }

}
