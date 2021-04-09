package com.sinata.framework.log;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.sinata.framework.R;
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

    private RecyclerView mRecyclerView;
    private LogAdapter mLogAdapter;
    private HiViewPrinterProvider mHiViewPrinterProvider;

    public HiViewPrinter(Activity activity) {
        FrameLayout rootView = activity.findViewById(android.R.id.content);

        mRecyclerView = new RecyclerView(activity);
        mLogAdapter = new LogAdapter(LayoutInflater.from(mRecyclerView.getContext()));
        LinearLayoutManager layoutManager = new LinearLayoutManager(mRecyclerView.getContext());
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mLogAdapter);
        mHiViewPrinterProvider = new HiViewPrinterProvider(rootView,mRecyclerView);

    }



    public HiViewPrinterProvider getViewProvider(){
        return mHiViewPrinterProvider;
    }

    @Override
    public void print(@NotNull HiLogConfig config, int level, String tag, @NotNull String printString) {
        // 将log展示添加到recycleView
        mLogAdapter.addItem(new HiLogMo(System.currentTimeMillis(), level, tag, printString));

        // 滚动到对应的位置
        mRecyclerView.smoothScrollToPosition(mLogAdapter.getItemCount() - 1);
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
            holder.mHiTagView.setTextColor(color);
            holder.mHiLogView.setTextColor(color);
            holder.mHiTagView.setText(logItem.getFlattened());
            holder.mHiLogView.setText(logItem.log);
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
                    highlight = 0xfff6b68;
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
        private TextView mHiTagView;
        private TextView mHiLogView;

        public LogViewHolder(@NonNull View itemView) {
            super(itemView);
            mHiTagView = itemView.findViewById(R.id.tag);
            mHiLogView = itemView.findViewById(R.id.message);
        }
    }
}
