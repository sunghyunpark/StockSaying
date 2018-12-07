package model;

public class ColorModel {
    private int textColorResource;    // 문구 색상 리소스
    private String textColorName;    // 문구 색상 이름
    private int bgColorResource;    // 배경색 리소스
    private String bgColorName;    // 배경색 문구
    private int bgDrawableResource;    // 해당 색상의 위젯 배경
    private int bgDrawableTranslucencyResource;    // 해당 색상의 반투명 위젯 배경

    public ColorModel(int textColorResource, String textColorName, int bgColorResource, String bgColorName, int bgDrawableResource, int bgDrawableTranslucencyResource){
        this.textColorResource = textColorResource;
        this.textColorName = textColorName;
        this.bgColorResource = bgColorResource;
        this.bgColorName = bgColorName;
        this.bgDrawableResource = bgDrawableResource;
        this.bgDrawableTranslucencyResource = bgDrawableTranslucencyResource;
    }

    public int getTextColorResource() {
        return textColorResource;
    }

    public void setTextColorResource(int textColorResource) {
        this.textColorResource = textColorResource;
    }

    public String getTextColorName() {
        return textColorName;
    }

    public void setTextColorName(String textColorName) {
        this.textColorName = textColorName;
    }

    public int getBgColorResource() {
        return bgColorResource;
    }

    public void setBgColorResource(int bgColorResource) {
        this.bgColorResource = bgColorResource;
    }

    public String getBgColorName() {
        return bgColorName;
    }

    public void setBgColorName(String bgColorName) {
        this.bgColorName = bgColorName;
    }

    public int getBgDrawableResource() {
        return bgDrawableResource;
    }

    public void setBgDrawableResource(int bgDrawableResource) {
        this.bgDrawableResource = bgDrawableResource;
    }

    public int getBgDrawableTranslucencyResource() {
        return bgDrawableTranslucencyResource;
    }

    public void setBgDrawableTranslucencyResource(int bgDrawableTranslucencyResource) {
        this.bgDrawableTranslucencyResource = bgDrawableTranslucencyResource;
    }

}
