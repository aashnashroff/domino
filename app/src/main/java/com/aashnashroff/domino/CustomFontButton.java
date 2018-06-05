package com.aashnashroff.domino;

import android.support.v7.widget.AppCompatButton;

import android.content.Context;
import android.util.AttributeSet;
import android.graphics.Typeface;

public class CustomFontButton extends AppCompatButton {
    public CustomFontButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomFontButton(Context context, AttributeSet attrs, String font) {
        this(context, attrs);
        String fontPath = "fonts/" + font + ".ttf";
        this.setTypeface(Typeface.createFromAsset(context.getAssets(),fontPath));
    }
}
