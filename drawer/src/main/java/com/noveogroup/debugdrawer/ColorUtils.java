package com.noveogroup.debugdrawer;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.annotation.AttrRes;
import android.support.annotation.ColorInt;
import android.util.TypedValue;

public final class ColorUtils {

    private ColorUtils() {
    }

    public static void colorize(final Drawable drawable, @ColorInt final int color) {
        if (drawable != null) {
            drawable.setColorFilter(color, PorterDuff.Mode.SRC_ATOP);
        }
    }

    @ColorInt
    public static int getThemeColor(final Context context, @AttrRes final int attribute) {
        final TypedValue color = new TypedValue();
        context.getTheme().resolveAttribute(attribute, color, true);
        return color.data;
    }

}
