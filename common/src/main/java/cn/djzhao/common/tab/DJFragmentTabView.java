package cn.djzhao.common.tab;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/**
 * FragmentTabVIew
 *
 * @author djzhao
 * @date 21/01/11
 */
public class DJFragmentTabView extends FrameLayout {

    private DJTabViewAdapter adapter;
    private int currentPosition;

    public DJFragmentTabView(@NonNull Context context) {
        super(context);
    }

    public DJFragmentTabView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public DJFragmentTabView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public DJTabViewAdapter getAdapter() {
        return adapter;
    }

    public void setAdapter(DJTabViewAdapter adapter) {
        if (this.adapter != null || adapter == null) {
            return;
        }
        this.adapter = adapter;
        currentPosition = -1;
    }

    public void setCurrentItem(int position) {
        if (position < 0 || position >= adapter.getCount()) {
            return;
        }
        if (currentPosition != position) {
            currentPosition = position;
            adapter.instantiateItem(this, position);
        }
    }

    public int getCurrentPosition() {
        return currentPosition;
    }

    public Fragment getCurrentFragment() {
        if (adapter == null) {
            throw new IllegalArgumentException("please call method setAdapter first");
        }
        return adapter.getCurFragment();
    }
}
