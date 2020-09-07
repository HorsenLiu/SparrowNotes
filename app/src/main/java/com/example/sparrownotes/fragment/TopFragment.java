package com.example.sparrownotes.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.sparrownotes.R;
import com.example.sparrownotes.adapter.NoteAdapter;
import com.example.sparrownotes.bean.NoteBean;
import com.example.sparrownotes.dao.NoteDao;
import com.example.sparrownotes.util.Constant;
import com.example.sparrownotes.util.SharedPreferenceUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 显示置顶note
 *
 * @author Horsen
 */
public class TopFragment extends Fragment {

    RecyclerView recyclerView;
    SwipeRefreshLayout swipeRefresh;
    NoteAdapter adapter;
    TextView textTime, textGreeting;
    private List<NoteBean> notesList = new ArrayList<>();
    private static final String TAG = "TopFragment";
    private NoteDao noteDao;
    SharedPreferenceUtil spu = SharedPreferenceUtil.getInstance(getActivity());

    public TopFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_top, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        noteDao = new NoteDao(getActivity());
        initData();
        initView();
        updateData();
    }

    @Override
    public void onResume() {
        super.onResume();
        // 发送消息 更新时间
        handler.sendEmptyMessage(Constant.UPDATE_TIME);
    }

    @Override
    public void onPause() {
        super.onPause();
        // 停止更新时间
        handler.removeCallbacksAndMessages(null);
    }

    /**
     * 初始化界面
     */
    @SuppressLint("ResourceAsColor")
    public void initView() {
        // 下拉刷新组件
        swipeRefresh = getActivity().findViewById(R.id.swipe_refresh_top);
        swipeRefresh.setColorSchemeColors(R.color.colorPrimary);
        swipeRefresh.setOnRefreshListener(this::refreshNotes);
        // 代替ListView
        recyclerView = getActivity().findViewById(R.id.recycler_view_top);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new NoteAdapter(notesList, getActivity());
        recyclerView.setAdapter(adapter);
        textTime = getActivity().findViewById(R.id.text_time);
        textGreeting = getActivity().findViewById(R.id.text_greeting);
    }

    /**
     * 初始化数据
     */
    public void initData() {
        List<NoteBean> noteBeans = noteDao.queryForWhat("is_top", "1", spu.getInt(Constant.USER_ID));
        notesList.addAll(noteBeans);
    }

    /**
     * 更新数据(假更新)
     */
    public void updateData() {
        notesList.clear();
        initData();
        adapter.notifyDataSetChanged();
    }

    /**
     * 下拉刷新
     */
    public void refreshNotes() {
        new Thread(() -> {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            getActivity().runOnUiThread(() -> {
                updateData();
                swipeRefresh.setRefreshing(false);
            });
        }).start();
    }

    /**
     * 获得今天的日期
     *
     * @return 日期字符串
     */
    public String getToday() {
        // 日期格式
        SimpleDateFormat weekFormat = new SimpleDateFormat("EEEE");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy年MM月dd日");
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
        //获取当前时间
        Date dateTime = new Date(System.currentTimeMillis());
        String week = weekFormat.format(dateTime);
        String date = dateFormat.format(dateTime);
        String time = timeFormat.format(dateTime);
        return week + "\n" + date + "\n" + time;
    }

    /**
     * 实现主线程和子线程之间的通信
     * 通知时间更新
     */
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            if (msg.what == Constant.UPDATE_TIME) {
                textTime.setText(getToday());
                sendEmptyMessageDelayed(Constant.UPDATE_TIME, 1000);
            }
        }
    };
}