package cn.djzhao.library.log.printer;

import android.app.Activity;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import cn.djzhao.library.R;
import cn.djzhao.library.log.DJLogConfig;
import cn.djzhao.library.log.DJLogModel;
import cn.djzhao.library.log.DJLogType;

/**
 * log日志的屏幕打印器(显示在界面上)
 *
 * @author djzhao
 * @Date 21/01/02
 */
public class DJScreenPrinter implements DJLogPrinter {

    private RecyclerView recyclerView;
    private LogAdapter adapter;
    private DJScreenPrinterProvider printerProvider;

    public DJScreenPrinter(Activity activity) {
        FrameLayout rootView = activity.findViewById(android.R.id.content);
        recyclerView = new RecyclerView(activity);
        adapter = new LogAdapter(LayoutInflater.from(recyclerView.getContext()));
        LinearLayoutManager layoutManager = new LinearLayoutManager(recyclerView.getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        printerProvider = new DJScreenPrinterProvider(rootView, recyclerView);
    }

    /**
     * 获取DJScreenPrinterProvider，用以控制DJScreenPrinterProvider的展示与隐藏
     *
     * @return DJScreenPrinterProvider
     */
    @NonNull
    public DJScreenPrinterProvider getPrinterProvider() {
        return printerProvider;
    }

    @Override
    public void print(@NonNull DJLogConfig config, int level, String tag, @NonNull String message) {
        // 将log展示到recyclerView
        DJLogModel logMo = new DJLogModel(System.currentTimeMillis(), level, tag, message);
        adapter.addItem(logMo);
        // 滚动视图位置
        recyclerView.smoothScrollToPosition(adapter.getItemCount() - 1);
    }

    private static class LogAdapter extends RecyclerView.Adapter<LogAdapter.LogViewHolder> {

        private LayoutInflater inflater;
        private List<DJLogModel> logs = new ArrayList<>();

        public LogAdapter(LayoutInflater inflater) {
            this.inflater = inflater;
        }

        public void addItem(DJLogModel logMo) {
            logs.add(logMo);
            notifyItemChanged(logs.size() - 1);
        }

        @NonNull
        @Override
        public LogViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView = inflater.inflate(R.layout.dj_screen_log_item, parent, false);
            return new LogViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull LogViewHolder holder, int position) {
            DJLogModel logItem = logs.get(position);
            int color = getHighlightColor(logItem.getLevel());
            holder.tagView.setTextColor(color);
            holder.messageView.setTextColor(color);
            holder.tagView.setText(logItem.getFlattened());
            holder.messageView.setText(logItem.getMessage());
        }

        /**
         * 根据log日志级别获取log颜色
         *
         * @param logLevel 日志级别
         * @return 日志颜色
         */
        private int getHighlightColor(int logLevel) {
            switch (logLevel) {
                case DJLogType.V:
                    return 0xFFBBBBBB;
                case DJLogType.D:
                    return 0xFFFFFFFF;
                case DJLogType.I:
                    return 0xFF6A8759;
                case DJLogType.W:
                    return 0xFFBBB529;
                case DJLogType.E:
                    return 0xFFFF6B68;
                default:
                    return 0xFFFFFF00;
            }
        }

        @Override
        public int getItemCount() {
            return logs.size();
        }

        private static class LogViewHolder extends RecyclerView.ViewHolder {

            TextView tagView;
            TextView messageView;

            public LogViewHolder(@NonNull View itemView) {
                super(itemView);
                tagView = itemView.findViewById(R.id.log_tag_tv);
                messageView = itemView.findViewById(R.id.log_message_tv);
            }
        }
    }
}
