package tm.hazar.myapplication;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

public class StaticMethods {
    public static void showKeyboard(Context context, EditText edtSearch) {
        if (context == null || edtSearch == null) return;
        InputMethodManager imgr = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imgr.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
        edtSearch.requestFocus();

    }
    public static void hideSoftKeyboard(Activity activity) {
        try {
            if (activity == null) return;
            InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);

            if (inputMethodManager.isAcceptingText()) {
                inputMethodManager.hideSoftInputFromWindow(
                        activity.getCurrentFocus().getWindowToken(),
                        0
                );
            }
        } catch (Exception e) {
            Log.d("error", "hideSoftKeyboard: " + e.getMessage());
        }

    }
    public static void setBackgroundDrawable(Context context, View view, int color, int borderColor, int corner, boolean isOval, int border) {
        if (context == null) return;

        GradientDrawable shape = new GradientDrawable();
        if (isOval) {
            shape.setShape(GradientDrawable.OVAL);
        } else {
            shape.setShape(GradientDrawable.RECTANGLE);
            shape.setCornerRadius(dpToPx(context, corner));
        }
        if (color != 0) {
            shape.setColor(context.getResources().getColor(color));
        } else {
            shape.setColor(context.getResources().getColor(R.color.color_transparent));
        }

        if (borderColor != 0) {
            shape.setStroke(dpToPx(border, context), context.getResources().getColor(borderColor));
        }
        view.setBackground(shape);

    }
    public static void setBackgroundAttribute(Context context, View view, int attr, int borderColor, int corner, boolean isOval, int border) {
        if (context == null) return;

        GradientDrawable shape = new GradientDrawable();
        if (isOval) {
            shape.setShape(GradientDrawable.OVAL);
        } else {
            shape.setShape(GradientDrawable.RECTANGLE);
            shape.setCornerRadius(dpToPx(context, corner));
        }
        if (attr != 0) {
            shape.setColor(attr);
        } else {
            shape.setColor(context.getResources().getColor(R.color.color_transparent));
        }

        if (borderColor != 0) {
            shape.setStroke(dpToPx(border, context), context.getResources().getColor(borderColor));
        }
        view.setBackground(shape);

    }

    public static float dpToPx(Context context, int dp) {
        return dp * ((float) context.getResources().getDisplayMetrics().densityDpi / DisplayMetrics.DENSITY_DEFAULT);
    }

    public static int dpToPx(int dp, Context context) {
        if (context == null) return 0;

        float density = context.getResources().getDisplayMetrics().density;
        return Math.round((float) dp * density);
    }
    public static void onActivityCreateSetTheme(Activity activity, Boolean theme) {
        if (theme) {
            activity.setTheme(R.style.Theme_Cartoonify_Dark);

            Window window = activity.getWindow();
            window.setStatusBarColor(activity.getResources().getColor(R.color.color_black));
            window.setNavigationBarColor(activity.getResources().getColor(R.color.color_black));
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);

        } else {
            activity.setTheme(R.style.Theme_Cartoonify);
            Window window = activity.getWindow();
            window.setStatusBarColor(activity.getResources().getColor(R.color.color_white));
            window.setNavigationBarColor(activity.getResources().getColor(R.color.color_white));
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR | View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR);
        }
    }

}
