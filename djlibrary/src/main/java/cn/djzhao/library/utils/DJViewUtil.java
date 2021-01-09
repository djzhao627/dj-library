package cn.djzhao.library.utils;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * View工具类
 *
 * @author djzhao
 * @date 21/01/09
 */
public class DJViewUtil {

    /**
     * 获取指定类型的子View
     *
     * @param group 父级ViewGroup
     * @param cls   子View的class，例如RecycleView.class
     * @param <T>   子View类型
     * @return 子View
     */
    public static <T> T findViewByType(@NonNull ViewGroup group, Class<T> cls) {
        if (group == null) {
            return null;
        }
        Deque<View> deque = new ArrayDeque<>();
        deque.add(group);
        while (!deque.isEmpty()) {
            View node = deque.removeFirst();
            if (cls.isInstance(node)) {
                return cls.cast(node);
            } else if (node instanceof ViewGroup) {
                ViewGroup viewGroup = (ViewGroup) node;
                for (int i = 0; i < viewGroup.getChildCount(); i++) {
                    deque.add(viewGroup.getChildAt(i));
                }
            }
        }
        return null;
    }
}
