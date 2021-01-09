package cn.djzhao.ui.tab.bottom;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.ScrollView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import cn.djzhao.library.utils.DJDisplayUtil;
import cn.djzhao.library.utils.DJViewUtil;
import cn.djzhao.ui.R;
import cn.djzhao.ui.tab.common.IDJTabLayout;

/**
 * 底部Tab布局
 *
 * @author djzhao
 * @date 21/01/07
 */
public class DJTabBottomLayout extends FrameLayout implements IDJTabLayout<DJTabBottom, DJTabBottomInfo<?>> {

    /**
     * 底部TabLayout的Tag
     */
    private final String TAG_TAB_LAYOUT_BOTTOM = "TAG_TAB_LAYOUT_BOTTOM";

    /**
     * 所有的listener
     */
    private List<OnTabSelectedListener<DJTabBottomInfo<?>>> tabSelectedListeners = new ArrayList<>();
    /**
     * 所有的Tab
     */
    private List<DJTabBottomInfo<?>> tabInfos;
    /**
     * 已选中的Tab信息
     */
    private DJTabBottomInfo<?> selectedTabInfo;
    /**
     * tabLayout的透明读
     */
    private float tabLayoutAlpha = 1f;
    /**
     * tabLayout的高度
     */
    private int tabLayoutHeight = 50;
    /**
     * tabLayout的顶部线条高度
     */
    private float tabLayoutTopLineHeight = 0.5f;
    /**
     * tabLayout的顶部线条颜色
     */
    private String tabLayoutTopLineColor = "#dfe0e1";

    public DJTabBottomLayout(@NonNull Context context) {
        super(context);
    }

    public DJTabBottomLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public DJTabBottomLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public DJTabBottom findTab(@NonNull DJTabBottomInfo<?> data) {
        ViewGroup tabLayout = findViewWithTag(TAG_TAB_LAYOUT_BOTTOM);
        for (int i = 0; i < tabLayout.getChildCount(); i++) {
            View childAt = tabLayout.getChildAt(i);
            if (childAt instanceof DJTabBottom) {
                DJTabBottom tabBottom = (DJTabBottom) childAt;
                if (tabBottom.getTabInfo() == data) {
                    return tabBottom;
                }
            }
        }
        return null;
    }

    @Override
    public void addTabSelectedChangeListener(OnTabSelectedListener<DJTabBottomInfo<?>> listener) {
        tabSelectedListeners.add(listener);
    }

    @Override
    public void defaultSelected(@NonNull DJTabBottomInfo<?> defaultInfo) {
        onSelected(defaultInfo);
    }

    @Override
    public void inflateInfo(@NonNull List<DJTabBottomInfo<?>> infoList) {
        if (infoList.isEmpty()) {
            return;
        }
        this.tabInfos = infoList;
        // 移除之前已经添加的Tab，子孩子0是fragment
        for (int i = getChildCount() - 1; i > 0; i--) {
            removeViewAt(i);
        }
        // 清除之前的监听
        tabSelectedListeners.clear();
        // 已选择置空
        selectedTabInfo = null;
        addBackground();
        addTabLayoutTopLine();
        addTabs();
        fixContentHeight();
    }

    /**
     * 设置TabLayout的背景
     */
    private void addBackground() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.dj_tab_layout_bg, null);
        LayoutParams layoutParams = new LayoutParams(
                LayoutParams.MATCH_PARENT,
                DJDisplayUtil.dp2px(tabLayoutHeight, getResources()),
                Gravity.BOTTOM);
        view.setAlpha(tabLayoutAlpha);
        addView(view, layoutParams);
    }

    /**
     * 添加TabLayout顶部的边框线
     */
    private void addTabLayoutTopLine() {
        View lineView = new View(getContext());
        lineView.setBackgroundColor(Color.parseColor(tabLayoutTopLineColor));
        LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, DJDisplayUtil.dp2px(tabLayoutTopLineHeight, getResources()), Gravity.BOTTOM);
        layoutParams.bottomMargin = DJDisplayUtil.dp2px(tabLayoutHeight - tabLayoutTopLineHeight, getResources());
        lineView.setAlpha(tabLayoutAlpha);
        addView(lineView, layoutParams);
    }

    /**
     * 添加子Tab
     */
    private void addTabs() {
        // 为何选择FrameLayout而不是LinearLayout：动态改变Child的大小，会使Gravity.BOTTOM属性失效
        FrameLayout frameLayout = new FrameLayout(getContext());
        frameLayout.setTag(TAG_TAB_LAYOUT_BOTTOM);
        int displayWidthInPX = DJDisplayUtil.getDisplayWidthInPX(getContext());
        int defaultTabWidth = displayWidthInPX / tabInfos.size();
        int defaultTabHeight = DJDisplayUtil.dp2px(tabLayoutHeight, getResources());
        for (int i = 0; i < tabInfos.size(); i++) {
            DJTabBottomInfo<?> tabBottomInfo = tabInfos.get(i);
            LayoutParams layoutParams = new LayoutParams(defaultTabWidth, defaultTabHeight, Gravity.BOTTOM);
            layoutParams.leftMargin = i * defaultTabWidth;
            DJTabBottom djTabBottom = new DJTabBottom(getContext());
            djTabBottom.setTabInfo(tabBottomInfo);
            djTabBottom.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    onSelected(tabBottomInfo);
                }
            });
            tabSelectedListeners.add(djTabBottom);
            frameLayout.addView(djTabBottom, layoutParams);
        }
        LayoutParams layoutParams = new LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT,
                Gravity.BOTTOM);
        addView(frameLayout, layoutParams);
    }

    /**
     * 修复内容部分底部被TabLayout遮挡的问题
     */
    private void fixContentHeight() {
        View child0 = getChildAt(0);
        if (!(child0 instanceof ViewGroup)) {
            return;
        }
        ViewGroup firstChild = (ViewGroup) child0;
        ViewGroup viewByType = DJViewUtil.findViewByType(firstChild, RecyclerView.class);
        if (viewByType == null) {
            viewByType = DJViewUtil.findViewByType(firstChild, ScrollView.class);
        }
        if (viewByType == null) {
            viewByType = DJViewUtil.findViewByType(firstChild, AbsListView.class);
        }
        if (viewByType != null) {
            viewByType.setPadding(
                    viewByType.getPaddingLeft(),
                    viewByType.getPaddingTop(),
                    viewByType.getPaddingRight(),
                    viewByType.getPaddingBottom() + DJDisplayUtil.dp2px(tabLayoutHeight, getResources()));
            viewByType.setClipToPadding(true);
        }
    }

    /**
     * 选中事件，通知所有监听器
     *
     * @param nextTabInfo 当前点击的Tab信息
     */
    private void onSelected(@NonNull DJTabBottomInfo<?> nextTabInfo) {
        for (OnTabSelectedListener<DJTabBottomInfo<?>> tabSelectedListener : tabSelectedListeners) {
            tabSelectedListener.onTabSelectedChange(tabInfos.indexOf(nextTabInfo), selectedTabInfo, nextTabInfo);
        }
        this.selectedTabInfo = nextTabInfo;
    }

    public void setTabLayoutAlpha(float tabLayoutAlpha) {
        this.tabLayoutAlpha = tabLayoutAlpha;
    }

    public void setTabLayoutHeight(int tabLayoutHeight) {
        this.tabLayoutHeight = tabLayoutHeight;
    }

    public void setTabLayoutTopLineHeight(float tabLayoutTopLineHeight) {
        this.tabLayoutTopLineHeight = tabLayoutTopLineHeight;
    }

    public void setTabLayoutTopLineColor(String tabLayoutTopLineColor) {
        this.tabLayoutTopLineColor = tabLayoutTopLineColor;
    }
}
