package com.aashnashroff.domino;

import android.support.v7.widget.AppCompatTextView;

import android.content.Context;
import android.util.AttributeSet;
import android.graphics.Typeface;

public class CustomFontTextView extends AppCompatTextView {
    public CustomFontTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomFontTextView(Context context, AttributeSet attrs, String font) {
        this(context, attrs);
        String fontPath = "fonts/" + font + ".ttf";
        this.setTypeface(Typeface.createFromAsset(context.getAssets(),fontPath));
    }
}
