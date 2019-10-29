package br.com.cielo.weather.util;

import android.graphics.Color;

public class ColorTemplate {

    private ColorTemplate(){

    }

    public static final int[] CIELO_PASSED_FAILED_COLORS = new int[]{rgb("#00adef"), rgb("#000000")};
    public static final int[] CIELO_TIER_COLORS = new int[]{rgb("#333333")};
    public static final int[] BLACK_AND_WHITE_COLORS = new int[]{rgb("#FFFFFF"), rgb("#000000")};
    public static final int[] CIELO_PASSED_FAILED_REGULAR_COLORS = new int[]{rgb("#888888"), rgb("#000000"), rgb("#00adef")};
    public static final int CIELO_GRAY = rgb("#888888");
    public static final int CIELO_BLACK = rgb("#000000");
    public static final int CIELO_BLUE = rgb("#00adef");

    private static int rgb(String hex) {
        int color = (int)Long.parseLong(hex.replace("#", ""), 16);
        int r = color >> 16 & 255;
        int g = color >> 8 & 255;
        int b = color >> 0 & 255;
        return Color.rgb(r, g, b);
    }

}
