package com.example.sparrownotes.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

import java.util.ArrayList;
import java.util.List;

/**
 * 显示所有note
 *
 * @author Horsen
 */
public class AllFragment extends Fragment {
    RecyclerView recyclerView;
    SwipeRefreshLayout swipeRefresh;
    NoteAdapter adapter;
    private List<NoteBean> notesList = new ArrayList<>();
    private NoteDao noteDao;
    SharedPreferenceUtil spu = SharedPreferenceUtil.getInstance(getActivity());

    public AllFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_all, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        noteDao = new NoteDao(getActivity());
        initData();
        initView();
    }

    /**
     * 初始化界面
     */
    @SuppressLint("ResourceAsColor")
    public void initView() {
        // 下拉刷新组件
        swipeRefresh = getActivity().findViewById(R.id.swipe_refresh_all);
        swipeRefresh.setColorSchemeColors(R.color.colorPrimary);
        swipeRefresh.setOnRefreshListener(this::refreshNotes);
        // 替代ListView
        recyclerView = getActivity().findViewById(R.id.recycler_view_all);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new NoteAdapter(notesList, getActivity());
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    /**
     * 初始化数据
     */
    public void initData() {
        List<NoteBean> noteBeams = noteDao.queryAll();
        notesList.addAll(noteBeams);
    }

    /**
     * 更新数据
     */
    public void updateData() {
        notesList.clear();
        // 获得sp中的查询条件 更新数据源
        String column = spu.getString(Constant.SEARCH_FOR_WHAT);
        String value = spu.getString(Constant.SEARCH_VALUE);
        if (column != "" && value != "") {
            List<NoteBean> noteBeans = noteDao.queryLikeWhat(column, value);
            notesList.addAll(noteBeans);
            // 用后即删
            spu.removeKey(Constant.SEARCH_FOR_WHAT);
            spu.removeKey(Constant.SEARCH_VALUE);
        } else {
            initData();
        }
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
}