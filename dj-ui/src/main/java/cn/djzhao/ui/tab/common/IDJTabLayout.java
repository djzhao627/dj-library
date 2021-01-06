package cn.djzhao.ui.tab.common;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

/**
 * Tab布局的接口(底部Tab和底部Tab通用)
 *
 * @author djzhao
 * @date 21/01/06
 */
public interface IDJTabLayout<Tab extends ViewGroup, D> {
    /**
     * 查找Tab
     *
     * @param data Tab数据
     * @return Tab对象
     */
    Tab findTab(@NonNull D data);

    /**
     * 添加Tab选中变化监听器
     *
     * @param listener 监听器
     */
    void addTabSelectedChangeListener(OnTabSelectedListener<D> listener);

    /**
     * 设置默认选中的Tab
     *
     * @param defaultInfo Tab数据
     */
    void defaultSelected(@NonNull D defaultInfo);

    /**
     * Tab数据初始化
     *
     * @param infoList Tab数据
     */
    void inflateInfo(@NonNull List<D> infoList);

    /**
     * 选中变化监听器
     *
     * @param <D> Tab数据
     */
    interface OnTabSelectedListener<D> {
        /**
         * Tab选中状态发生改变
         *
         * @param index    Tab索引
         * @param prevInfo 上一个选中的Tab
         * @param nextInfo 下一个选中的Tab
         */
        void onTabSelectedChange(int index, @Nullable D prevInfo, @NonNull D nextInfo);
    }
}
