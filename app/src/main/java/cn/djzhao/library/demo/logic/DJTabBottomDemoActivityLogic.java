package cn.djzhao.library.demo.logic;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.FragmentManager;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import cn.djzhao.common.tab.DJFragmentTabView;
import cn.djzhao.common.tab.DJTabViewAdapter;
import cn.djzhao.library.R;
import cn.djzhao.library.demo.fragment.CategoryFragment;
import cn.djzhao.library.demo.fragment.FavoriteFragment;
import cn.djzhao.library.demo.fragment.HomePageFragment;
import cn.djzhao.library.demo.fragment.ProfileFragment;
import cn.djzhao.library.demo.fragment.RecommendFragment;
import cn.djzhao.library.utils.DJDisplayUtil;
import cn.djzhao.ui.tab.bottom.DJTabBottom;
import cn.djzhao.ui.tab.bottom.DJTabBottomInfo;
import cn.djzhao.ui.tab.bottom.DJTabBottomLayout;
import cn.djzhao.ui.tab.common.IDJTabLayout;

/**
 * TabBottomActivity逻辑处理
 *
 * @author djzhao
 * @date 21/01/11
 */
public class DJTabBottomDemoActivityLogic {
    private DJFragmentTabView tabView;
    private DJTabBottomLayout tabBottomLayout;
    private List<DJTabBottomInfo<?>> infoList;
    private final ActivityProvider provider;
    private final static String SAVED_CURRENT_ID = "SAVED_CURRENT_ID";
    private int currentItemIndex;

    public DJTabBottomDemoActivityLogic(ActivityProvider activityProvider, Bundle savedInstanceState) {
        this.provider = activityProvider;
        // 数据恢复
        if (savedInstanceState != null) {
            currentItemIndex = savedInstanceState.getInt(SAVED_CURRENT_ID, 0);
        }
        initTabBottom();
    }

    private void initFragmentTabView() {
        DJTabViewAdapter adapter = new DJTabViewAdapter(provider.getSupportFragmentManager(), infoList);
        tabView.setAdapter(adapter);
    }

    private void initTabBottom() {
        tabBottomLayout = provider.findViewById(R.id.djTabLayout);
        tabView = provider.findViewById(R.id.fragment_tab_view);
        tabBottomLayout.setTabLayoutAlpha(0.8f);
        infoList = new ArrayList<>();
        int defaultColor = provider.getResources().getColor(R.color.tabDefaultColor);
        int tintColor = provider.getResources().getColor(R.color.tabTintColor);

        DJTabBottomInfo<Integer> homeInfo = new DJTabBottomInfo<>(
                "首页",
                "fonts/iconfont.ttf",
                provider.getString(R.string.if_home),
                null,
                defaultColor,
                defaultColor
        );
        homeInfo.fragment = HomePageFragment.class;
        DJTabBottomInfo<Integer> recommendInfo = new DJTabBottomInfo<>(
                "推荐",
                "fonts/iconfont.ttf",
                provider.getString(R.string.if_recommend),
                null,
                defaultColor,
                defaultColor
        );
        recommendInfo.fragment = RecommendFragment.class;
        DJTabBottomInfo<Integer> categoryInfo = new DJTabBottomInfo<>(
                provider.getBaseContext(),
                "分类",
                R.drawable.fire,
                R.drawable.fire,
                defaultColor,
                defaultColor
        );
        categoryInfo.fragment = CategoryFragment.class;
        DJTabBottomInfo<Integer> chatInfo = new DJTabBottomInfo<>(
                "喜欢",
                "fonts/iconfont.ttf",
                provider.getString(R.string.if_chat),
                null,
                defaultColor,
                defaultColor
        );
        chatInfo.fragment = FavoriteFragment.class;
        DJTabBottomInfo<Integer> profileInfo = new DJTabBottomInfo<>(
                "我的",
                "fonts/iconfont.ttf",
                provider.getString(R.string.if_profile),
                null,
                defaultColor,
                defaultColor
        );
        profileInfo.fragment = ProfileFragment.class;
        infoList.add(homeInfo);
        infoList.add(recommendInfo);
        infoList.add(categoryInfo);
        infoList.add(chatInfo);
        infoList.add(profileInfo);

        tabBottomLayout.inflateInfo(infoList);
        initFragmentTabView();
        tabBottomLayout.addTabSelectedChangeListener(new IDJTabLayout.OnTabSelectedListener<DJTabBottomInfo<?>>() {
            @Override
            public void onTabSelectedChange(int index, @Nullable DJTabBottomInfo<?> prevInfo, @NonNull DJTabBottomInfo<?> nextInfo) {
                tabView.setCurrentItem(index);
                currentItemIndex = index;
            }
        });
        tabBottomLayout.defaultSelected(infoList.get(currentItemIndex));
        DJTabBottom tab = tabBottomLayout.findTab(categoryInfo);
        tab.resetHeight(DJDisplayUtil.dp2px(66f, provider.getResources()), false);
    }

    public DJFragmentTabView getTabView() {
        return tabView;
    }

    public DJTabBottomLayout getTabBottomLayout() {
        return tabBottomLayout;
    }

    public List<DJTabBottomInfo<?>> getInfoList() {
        return infoList;
    }

    public void saveInstanceState(@NotNull Bundle outState) {
        outState.putInt(SAVED_CURRENT_ID, currentItemIndex);
    }

    public interface ActivityProvider {
        <T extends View> T findViewById(@IdRes int id);

        Resources getResources();

        FragmentManager getSupportFragmentManager();

        Context getBaseContext();

        String getString(@StringRes int resId);
    }
}
