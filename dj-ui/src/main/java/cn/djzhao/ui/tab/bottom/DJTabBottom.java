package cn.djzhao.ui.tab.bottom;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import cn.djzhao.ui.R;
import cn.djzhao.ui.tab.common.IDJTab;

/**
 * 底部Tab对象
 *
 * @author djzhao
 * @date 21/01/06
 */
public class DJTabBottom extends RelativeLayout implements IDJTab<DJTabBottomInfo<?>> {

    private DJTabBottomInfo<?> tabInfo;
    private ImageView tabImageView;
    private TextView tabIconFont;
    private TextView tabTitle;

    public DJTabBottom(Context context) {
        this(context, null);
    }

    public DJTabBottom(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DJTabBottom(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.dj_tab_bottom, this);
        tabImageView = findViewById(R.id.icon_iv);
        tabIconFont = findViewById(R.id.icon_tv);
        tabTitle = findViewById(R.id.title_tv);
    }

    @Override
    public void setTabInfo(@NonNull DJTabBottomInfo<?> data) {
        this.tabInfo = data;
        inflateInfo(false, true);
    }

    /**
     * 初始化数据
     *
     * @param isSelected 是否选中
     * @param isInit     是否初始化
     */
    private void inflateInfo(boolean isSelected, boolean isInit) {
        if (tabInfo.tabType == DJTabBottomInfo.TabType.ICON) {
            if (isInit) {
                tabImageView.setVisibility(GONE);
                tabIconFont.setVisibility(VISIBLE);
                Typeface typeface = Typeface.createFromAsset(getContext().getAssets(), tabInfo.iconFont);
                tabIconFont.setTypeface(typeface);
                if (!TextUtils.isEmpty(tabInfo.name)) {
                    tabTitle.setText(tabInfo.name);
                }
            }
            if (isSelected) {
                tabIconFont.setText(TextUtils.isEmpty(tabInfo.selectedIconName) ? tabInfo.defaultIconName : tabInfo.selectedIconName);
                tabIconFont.setTextColor(getTextColor(tabInfo.tintColor));
                tabTitle.setTextColor(getTextColor(tabInfo.tintColor));
            } else {
                tabIconFont.setText(tabInfo.defaultIconName);
                tabIconFont.setTextColor(getTextColor(tabInfo.defaultColor));
                tabTitle.setTextColor(getTextColor(tabInfo.defaultColor));
            }
        } else if (tabInfo.tabType == DJTabBottomInfo.TabType.BITMAP) {
            if (isInit) {
                tabIconFont.setVisibility(GONE);
                tabImageView.setVisibility(VISIBLE);
                if (!TextUtils.isEmpty(tabInfo.name)) {
                    tabTitle.setText(tabInfo.name);
                }
            }
            if (isSelected) {
                tabImageView.setImageBitmap(tabInfo.selectedBitmap);
                if (tabInfo.tintColor != null) {
                    tabTitle.setTextColor(getTextColor(tabInfo.tintColor));
                }
            } else {
                tabImageView.setImageBitmap(tabInfo.defaultBitmap);
                if (tabInfo.defaultColor != null) {
                    tabTitle.setTextColor(getTextColor(tabInfo.defaultColor));
                }
            }
        }
    }

    /**
     * 获取文本颜色
     *
     * @param color 颜色对象
     * @return ColorInt
     */
    @ColorInt
    private int getTextColor(Object color) {
        if (color instanceof String) {
            return Color.parseColor((String) color);
        } else {
            return (int) color;
        }
    }

    @Override
    public void resetHeight(int height, boolean isShowText) {
        ViewGroup.LayoutParams layoutParams = getLayoutParams();
        layoutParams.height = height;
        setLayoutParams(layoutParams);
        if (isShowText) {
            getTabTitle().setVisibility(VISIBLE);
        } else {
            getTabTitle().setVisibility(GONE);
        }
    }

    @Override
    public void onTabSelectedChange(int index, @Nullable DJTabBottomInfo<?> prevInfo, @NonNull DJTabBottomInfo<?> nextInfo) {
        if (prevInfo != tabInfo && nextInfo != tabInfo || prevInfo == nextInfo) {
            return;
        }
        inflateInfo(prevInfo != tabInfo, false);
    }

    public DJTabBottomInfo<?> getTabInfo() {
        return tabInfo;
    }

    public ImageView getTabImageView() {
        return tabImageView;
    }

    public TextView getTabIconFont() {
        return tabIconFont;
    }

    public TextView getTabTitle() {
        return tabTitle;
    }
}
