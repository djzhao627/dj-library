package cn.djzhao.common.tab;

import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.util.List;

import cn.djzhao.ui.tab.bottom.DJTabBottomInfo;

/**
 * Tab视图适配器
 *
 * @author djzhao
 * @date 21/01/11
 */
public class DJTabViewAdapter {

    private List<DJTabBottomInfo<?>> mInfoList;
    private Fragment mCurFragment;
    private FragmentManager mFragmentManager;

    public DJTabViewAdapter(FragmentManager mFragmentManager, List<DJTabBottomInfo<?>> mInfoList) {
        this.mInfoList = mInfoList;
        this.mFragmentManager = mFragmentManager;
    }

    /**
     * 实例化并显示指定位置的fragment
     *
     * @param container 父容器
     * @param position  fragment位置
     */
    public void instantiateItem(View container, int position) {
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        if (mCurFragment != null) {
            transaction.hide(mCurFragment);
        }
        String tag = container.getId() + ":" + position;
        Fragment fragmentByTag = mFragmentManager.findFragmentByTag(tag);
        if (fragmentByTag != null) {
            transaction.show(fragmentByTag);
        } else {
            fragmentByTag = getItem(position);
            if (fragmentByTag != null && !fragmentByTag.isAdded()) {
                transaction.add(container.getId(), fragmentByTag, tag);
            }
        }
        mCurFragment = fragmentByTag;
        transaction.commitAllowingStateLoss();
    }

    /**
     * 使用mInfoList获取Fragment
     *
     * @param position 索引
     * @return fragment
     */
    private Fragment getItem(int position) {
        try {
            return mInfoList.get(position).fragment.newInstance();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取当前选中的Fragment
     *
     * @return fragment
     */
    public Fragment getCurFragment() {
        return mCurFragment;
    }

    /**
     * 获取Item数量
     *
     * @return 总数量
     */
    public int getCount() {
        return mInfoList == null ? 0 : mInfoList.size();
    }
}
