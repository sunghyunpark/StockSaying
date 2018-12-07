package model;

import android.graphics.drawable.Drawable;

public class ColorPowerMenuModel {
    private Drawable colorDrawable;
    private String colorName;

    public ColorPowerMenuModel(Drawable colorDrawable, String colorName){
        this.colorDrawable = colorDrawable;
        this.colorName = colorName;
    }

    public Drawable getColorDrawable() {
        return colorDrawable;
    }

    public void setColorDrawable(Drawable colorDrawable) {
        this.colorDrawable = colorDrawable;
    }

    public String getColorName() {
        return colorName;
    }

    public void setColorName(String colorName) {
        this.colorName = colorName;
    }
}
