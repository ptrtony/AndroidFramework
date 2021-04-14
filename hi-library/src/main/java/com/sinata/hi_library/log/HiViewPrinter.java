package com.sinata.hi_library.log;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sinata.hi_library.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * @author cjq
 * @Date 7/4/2021
 * @Time 9:15 PM
 * @Describe:
 */


public class HiViewPrinter implements HiLogPrinter {

    private RecyclerView recyclerView;
    private LogAdapter adapter;
    private HiViewPrinterProvider viewProvider;

    public HiViewPrinter(Activity activity) {
        FrameLayout rootView = activity.findViewById(android.R.id.content);

        recyclerView = new RecyclerView(activity);
        adapter = new LogAdapter(LayoutInflater.from(recyclerView.getContext()));
        LinearLayoutManager layoutManager = new LinearLayoutManager(recyclerView.getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        viewProvider = new HiViewPrinterProvider(rootView,recyclerView);

    }



    public HiViewPrinterProvider getViewProvider(){
        return viewProvider;
    }

    @Override
    public void print(@NotNull HiLogConfig config, int level, String tag, @NotNull String printString) {
        // 将log展示添加到recycleView
        adapter.addItem(new HiLogMo(System.currentTimeMillis(), level, tag, printString));

        // 滚动到对应的位置
        recyclerView.smoothScrollToPosition(adapter.getItemCount() - 1);
    }


    private static class LogAdapter extends RecyclerView.Adapter<LogViewHolder> {

        private LayoutInflater mInflater;
        private List<HiLogMo> mLogs = new ArrayList<>();

        public LogAdapter(LayoutInflater inflater) {
            this.mInflater = inflater;
        }

        public void addItem(HiLogMo logItem) {
            mLogs.add(logItem);
            notifyItemInserted(mLogs.size() - 1);
        }

        @NonNull
        @Override
        public LogViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView = mInflater.inflate(R.layout.hilog_item, parent, false);
            return new LogViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull LogViewHolder holder, int position) {
            HiLogMo logItem = mLogs.get(position);

            int color = getHighlightColor(logItem.level);
            holder.tag.setTextColor(color);
            holder.message.setTextColor(color);
            holder.tag.setText(logItem.getFlattened());
            holder.message.setText(logItem.log);
        }


        /**
         * 根据log级别获取不同的亮的颜色
         *
         * @param logLevel 日志级别
         * @return 高亮颜色
         */
        private int getHighlightColor(int logLevel) {
            int highlight;
            switch (logLevel) {
                case HiLogType.V:
                    highlight = 0xffbbbbbb;
                    break;
                case HiLogType.D:
                    highlight = 0xffffffff;
                    break;
                case HiLogType.I:
                    highlight = 0xff6a8759;
                    break;
                case HiLogType.W:
                    highlight = 0xffbbb529;
                    break;
                case HiLogType.E:
                    highlight = 0xffff6b68;
                    break;
                default:
                    highlight = 0xffffff00;
                    break;
            }
            return highlight;
        }

        @Override
        public int getItemCount() {
            return mLogs.size();
        }
    }

    private static class LogViewHolder extends RecyclerView.ViewHolder {
        private TextView tag;
        private TextView message;

        public LogViewHolder(@NonNull View itemView) {
            super(itemView);
            tag = itemView.findViewById(R.id.tag);
            message = itemView.findViewById(R.id.message);
        }
    }
}
