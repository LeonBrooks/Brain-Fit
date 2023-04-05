package com.groupSeventeen.Util;

import android.content.Context;
import android.util.DisplayMetrics;

import com.groupSeventeen.User;

import java.util.ArrayList;
import java.util.List;

public class HelpMe {

    // Functions to calculate color grading of usernames and questions
    public static int color(int a, int r, int g, int b) {
        return  (a & 0xff) << 24 | (r & 0xff) << 16 | (g & 0xff) << 8 | (b & 0xff);
    }

    public static int scaledColor(int element, int size) {
        int position = (6*200) * element/size;
        int[] rgb = {200, 0, 0};
        int p = 1;
        int op = 1;

        while (position != 0) {
            if (position > 200) {
                position -= 200;
                rgb[p] += op * 200;
                op *= -1;
            } else {
                rgb[p] += op * position;
                position = 0;
            }
            p = (p + 2) % 3;
        }

        return color(255, rgb[0], rgb[1], rgb[2]);
    }

    // source: https://stackoverflow.com/a/9563438
    /*
     * This method converts dp unit to equivalent pixels, depending on device density.
     *
     * @param dp A value in dp (density independent pixels) unit. Which we need to convert into pixels
     * @param context Context to get resources and device specific display metrics
     * @return A float value to represent px equivalent to dp depending on device density
     */
    public static float convertDpToPixel(float dp, Context context){
        return dp * ((float) context.getResources().getDisplayMetrics().densityDpi / DisplayMetrics.DENSITY_DEFAULT);
    }

    public static List<User> sortUserListDesc(List<User> users) {
        List<User> sorted = new ArrayList<>();

        while (users.isEmpty()) {
            User best = users.get(0);
            for (User u : users) {
                if (u.getPoints() > best.getPoints()) {
                    best = u;
                }
            }
            sorted.add(best);
        }

        return sorted;
    }

}
