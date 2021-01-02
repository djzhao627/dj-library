package cn.djzhao.library.log;

import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import cn.djzhao.library.utils.DJDisplayUtil;

/**
 * 屏幕日志打印器提供者
 *
 * @author djzhao
 * @date 21/01/02
 */
public class DJScreenPrinterProvider {
    private FrameLayout rootView;
    private View floatingView;
    private boolean isOpen;
    private FrameLayout logView;
    private RecyclerView recyclerView;

    public static final String TAG_FLOATING_VIEW = "TAG_FLOATING_VIEW";
    public static final String TAG_LOG_VIEW = "TAG_LOG_VIEW";

    public DJScreenPrinterProvider(FrameLayout rootView, RecyclerView recyclerView) {
        this.rootView = rootView;
        this.recyclerView = recyclerView;
    }

    /**
     * 展示悬浮框
     */
    public void showFloatingView() {
        if (recyclerView.findViewWithTag(TAG_FLOATING_VIEW) != null) {
            return;
        }
        FrameLayout.LayoutParams layoutParams =
                new FrameLayout.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        Gravity.BOTTOM | Gravity.END);
        View floatingView = generateFloatingView();
        floatingView.setTag(TAG_FLOATING_VIEW);
        layoutParams.bottomMargin = DJDisplayUtil.dp2px(100, rootView.getResources());
        rootView.addView(floatingView, layoutParams);
    }

    /**
     * 展示Log视图
     */
    private void showLogView() {
        if (rootView.findViewWithTag(TAG_LOG_VIEW) != null) {
            return;
        }
        FrameLayout.LayoutParams layoutParams =
                new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        DJDisplayUtil.dp2px(160, rootView.getResources()),
                        Gravity.BOTTOM);
        View logView = generateLogView();
        logView.setTag(TAG_LOG_VIEW);
        isOpen = true;
        rootView.addView(logView, layoutParams);
    }

    /**
     * 隐藏悬浮框
     */
    public void hideFloatingVIew() {
        rootView.removeView(generateLogView());
    }

    /**
     * 创建悬浮框
     *
     * @return 悬浮框视图
     */
    private View generateFloatingView() {
        if (floatingView != null) {
            return floatingView;
        }
        TextView openLogViewTv = new TextView(rootView.getContext());
        openLogViewTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isOpen) {
                    showLogView();
                }
            }
        });
        openLogViewTv.setText("DJLog");
        openLogViewTv.setBackgroundColor(Color.BLACK);
        openLogViewTv.setAlpha(0.8f);
        return floatingView = openLogViewTv;
    }

    /**
     * 生成Log视图
     *
     * @return Log视图
     */
    private View generateLogView() {
        if (logView != null) {
            return logView;
        }
        FrameLayout logView = new FrameLayout(rootView.getContext());
        logView.setPadding(15, 10, 15, 25);
        logView.setBackgroundColor(Color.BLACK);
        logView.addView(recyclerView);

        FrameLayout.LayoutParams layoutParams =
                new FrameLayout.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        Gravity.END);
        TextView closeLogViewTV = new TextView(rootView.getContext());
        closeLogViewTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeLogView();
            }
        });
        closeLogViewTV.setText("Hide");
        logView.addView(closeLogViewTV, layoutParams);
        return this.logView = logView;
    }


    /**
     * 关闭log视图
     */
    private void closeLogView() {
        isOpen = false;
        rootView.removeView(generateLogView());
    }
}
