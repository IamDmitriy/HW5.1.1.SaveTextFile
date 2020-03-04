package com.example.hw422itemevents;

import android.content.Context;
import android.graphics.drawable.Drawable;

public class ItemData {

    private Drawable image;
    private String title;
    private String subtitle;
    private boolean checked;
    private int imageId;

    public ItemData(Drawable image, String title, String subtitle, boolean checked) {
        this.image = image;
        this.title = title;
        this.subtitle = subtitle;
        this.checked = checked;
    }

    public ItemData(String title, String subtitle, boolean checked, int imageId, Context context) {
        this.imageId = imageId;
        this.title = title;
        this.subtitle = subtitle;
        this.checked = checked;

        image = context.getDrawable(imageId);
    }

    public Drawable getImage() {
        return image;
    }

    public String getTitle() {
        return title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public int getImageId() {
        return imageId;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }
}
