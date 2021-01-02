package cn.djzhao.library.utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.TypedValue;
import android.view.Display;
import android.view.WindowManager;

import androidx.annotation.NonNull;

/**
 * 屏幕展示工具类
 *
 * @author djzhao
 * @date 21/01/02
 */
public class DJDisplayUtil {
    public static int dp2px(float dp, Resources resources) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, resources.getDisplayMetrics());
    }

    public static int getDisplayWidthInPX(@NonNull Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        if (windowManager != null) {
            Rect bounds = windowManager.getCurrentWindowMetrics().getBounds();
            return bounds.width();
        }
        return 0;
    }

    public static int getDisplayHeightInPX(@NonNull Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        if (windowManager != null) {
            Rect bounds = windowManager.getCurrentWindowMetrics().getBounds();
            return bounds.height();
        }
        return 0;
    }
}
