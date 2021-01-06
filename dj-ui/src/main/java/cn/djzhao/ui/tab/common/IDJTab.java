package cn.djzhao.ui.tab.common;

import androidx.annotation.NonNull;
import androidx.annotation.Px;

/**
 * Tab对外接口
 *
 * @author djzhao
 * @date 21/01/06
 */
public interface IDJTab<D> extends IDJTabLayout.OnTabSelectedListener<D> {
    /**
     * 设置Tab数据
     *
     * @param data Tab数据
     */
    void setTabInfo(@NonNull D data);

    /**
     * 动态重置Tab的高度
     *
     * @param height 高度值(px)
     */
    void resetHeight(@Px int height, boolean isShowText);
}
