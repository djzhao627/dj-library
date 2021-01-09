package cn.djzhao.ui.tab.bottom;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import androidx.annotation.DrawableRes;
import androidx.fragment.app.Fragment;

/**
 * 底部Tab信息
 *
 * @author djzhao
 * @date 21/01/06
 */
public class DJTabBottomInfo<Color> {
    public enum TabType {
        BITMAP, ICON
    }

    public Class<? extends Fragment> fragment;
    public TabType tabType;
    public String name;

    public Bitmap defaultBitmap;
    public Bitmap selectedBitmap;

    /**
     * Tips：在Java中直接设置iconfont字符串无效，需要定义在string.xml中
     */
    public String iconFont;
    public String defaultIconName;
    public String selectedIconName;

    public Color defaultColor;
    public Color tintColor;

    public DJTabBottomInfo(String name, Bitmap defaultBitmap, Bitmap selectedBitmap) {
        this.name = name;
        this.defaultBitmap = defaultBitmap;
        this.selectedBitmap = selectedBitmap;
        this.tabType = TabType.BITMAP;
    }

    public DJTabBottomInfo(String name, Bitmap defaultBitmap, Bitmap selectedBitmap, Color defaultColor, Color tintColor) {
        this.name = name;
        this.defaultBitmap = defaultBitmap;
        this.selectedBitmap = selectedBitmap;
        this.defaultColor = defaultColor;
        this.tintColor = tintColor;
        this.tabType = TabType.BITMAP;
    }

    public DJTabBottomInfo(Context context, String name, @DrawableRes int defaultBitmap, @DrawableRes int selectedBitmap) {
        this.name = name;
        this.defaultBitmap = BitmapFactory.decodeResource(context.getResources(), defaultBitmap);
        this.selectedBitmap = BitmapFactory.decodeResource(context.getResources(), selectedBitmap);
        this.tabType = TabType.BITMAP;
    }

    public DJTabBottomInfo(Context context, String name, @DrawableRes int defaultBitmap, @DrawableRes int selectedBitmap, Color defaultColor, Color tintColor) {
        this.name = name;
        this.defaultBitmap = BitmapFactory.decodeResource(context.getResources(), defaultBitmap);
        this.selectedBitmap = BitmapFactory.decodeResource(context.getResources(), selectedBitmap);
        this.defaultColor = defaultColor;
        this.tintColor = tintColor;
        this.tabType = TabType.BITMAP;
    }

    public DJTabBottomInfo(String name, String iconFont, String defaultIconName, String selectedIconName, Color defaultColor, Color tintColor) {
        this.name = name;
        this.iconFont = iconFont;
        this.defaultIconName = defaultIconName;
        this.selectedIconName = selectedIconName;
        this.defaultColor = defaultColor;
        this.tintColor = tintColor;
        this.tabType = TabType.ICON;
    }
}
