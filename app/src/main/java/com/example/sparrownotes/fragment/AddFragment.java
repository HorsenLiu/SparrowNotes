package com.example.sparrownotes.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.sparrownotes.R;
import com.example.sparrownotes.adapter.MyPagerAdapter;
import com.example.sparrownotes.bean.NoteBean;
import com.example.sparrownotes.dao.NoteDao;
import com.example.sparrownotes.util.Constant;
import com.example.sparrownotes.util.NoScrollViewPager;
import com.example.sparrownotes.util.SharedPreferenceUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * 增加note的页面
 *
 * @author Horsen
 */
public class AddFragment extends Fragment {

    @BindView(R.id.view_pager_add)
    NoScrollViewPager viewPager;
    @BindView(R.id.text_reset)
    TextView textReset;
    @BindView(R.id.text_save)
    TextView textSave;
    @BindView(R.id.img_account)
    ImageView imgAccount;
    @BindView(R.id.img_date)
    ImageView imgDate;
    @BindView(R.id.img_link)
    ImageView imgLink;
    @BindView(R.id.img_bill)
    ImageView imgBill;
    @BindView(R.id.img_location)
    ImageView imgLocation;
    @BindView(R.id.img_mood)
    ImageView imgMood;
    @BindView(R.id.img_note)
    ImageView imgNote;
    @BindView(R.id.img_question)
    ImageView imgQuestion;

    TextView[] textViews = new TextView[5];
    String title = "", content = "";
    int imgResource;
    View[] views = new View[8];
    int index = 0;
    private Unbinder unbinder;
    private String category = "";
    private static final String TAG = "AddFragment";
    private List<View> viewList = new ArrayList<>();
    private NoteDao noteDao;
    SharedPreferenceUtil spu = SharedPreferenceUtil.getInstance(getActivity());

    public AddFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add, container, false);
        // 返回一个Unbinder值（进行解绑），注意这里的this不能使用getActivity()
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        noteDao = new NoteDao(getActivity());
        initView();
        // 置顶默认类型
        category = getString(R.string.note);
    }

    public void initView() {
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        // 创建不同的添加页面
        for (int i = 0; i < Constant.LAYOUTS.length; i++) {
            views[i] = inflater.inflate(Constant.LAYOUTS[i], null);
            viewList.add(views[i]);
        }
        MyPagerAdapter adapter = new MyPagerAdapter(viewList);
        viewPager.setOffscreenPageLimit(viewList.size());
        viewPager.setAdapter(adapter);
    }

    /**
     * 存入数据库
     */
    public void insertNoteIntoDB(String category) {
        getTitleAndContents(category);
        NoteBean noteBean = new NoteBean(spu.getInt(Constant.USER_ID),title, content, category, "创建于 " + getToday(), imgResource);
        noteDao.insert(noteBean);
    }

    /**
     * 点击分类图标, 显示不同的添加页面 通过ViewPager
     *
     * @param view 布局
     */
    @OnClick({R.id.text_reset, R.id.text_save, R.id.img_account, R.id.img_date, R.id.img_link, R.id.img_bill, R.id.img_location, R.id.img_mood, R.id.img_note, R.id.img_question})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.text_reset:
                // 清空输入
                clearTitleAndContents(category);
                break;
            case R.id.text_save:
                // 保存输入
                insertNoteIntoDB(category);
                clearTitleAndContents(category);
                Toast.makeText(getContext(), "添加成功", Toast.LENGTH_SHORT).show();
                break;
            case R.id.img_note:
                category = getString(R.string.note);
                index = 0;
                break;
            case R.id.img_link:
                category = getString(R.string.link);
                index = 1;
                break;
            case R.id.img_location:
                category = getString(R.string.location);
                index = 2;
                break;
            case R.id.img_bill:
                category = getString(R.string.bill);
                index = 3;
                break;
            case R.id.img_date:
                category = getString(R.string.date);
                index = 4;
                break;
            case R.id.img_mood:
                category = getString(R.string.mood);
                index = 5;
                break;
            case R.id.img_account:
                category = getString(R.string.account);
                index = 6;
                break;
            case R.id.img_question:
                category = getString(R.string.question);
                index = 7;
                break;
            default:
                break;
        }
        viewPager.setCurrentItem(index);
    }

    /**
     * 获得今天的日期
     *
     * @return
     */
    public String getToday() {
        // 日期格式
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy.MM.dd");
        //获取当前时间
        Date date = new Date(System.currentTimeMillis());
        return simpleDateFormat.format(date);
    }

    /**
     * 获取天数差
     *
     * @param today   今天
     * @param someday 某一天
     * @return
     */
    public String getDayDistance(String today, String someday) {
        try {
            SimpleDateFormat todayFormat = new SimpleDateFormat("yyyy.MM.dd");
            SimpleDateFormat somedayFormat = new SimpleDateFormat("yyyy年MM月dd日");
            long todayTime = todayFormat.parse(today).getTime();
            long somedayTime = somedayFormat.parse(someday).getTime();
            Log.d(TAG, "getDayDistance: " + todayTime);
            Log.d(TAG, "getDayDistance: " + somedayTime);
            // 获得毫秒差 除以 1000*3600*24 得到天数差
            long distance = (todayTime - somedayTime) / (1000 * 3600 * 24);
            // 根据正负判断日期在前还是在后
            if (distance >= 0) {
                return "\n距今已经 " + distance + " 天";
            }
            return "\n距今还有 " + (somedayTime - todayTime) / (1000 * 3600 * 24) + " 天";
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "这个日期...不对吧";
    }

    /**
     * onDestroyView中进行解绑操作
     */
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    /**
     * 通过传入的类型 获得标题和内容
     *
     * @param category 类型
     */
    public void getTitleAndContents(String category) {
        textViews[0] = views[index].findViewById(R.id.title);
        title = textViews[0].getText().toString().trim();
        switch (category) {
            case "便签":
            case "链接":
            case "位置":
            case "心情":
                textViews[1] = views[index].findViewById(R.id.content_1);
                content = textViews[1].getText().toString().trim();
                break;
            case "消费":
                textViews[1] = views[index].findViewById(R.id.year);
                textViews[2] = views[index].findViewById(R.id.month);
                textViews[3] = views[index].findViewById(R.id.day);
                textViews[4] = views[index].findViewById(R.id.content_2);
                content = "时间：" + textViews[1].getText().toString().trim() + "年"
                        + textViews[2].getText().toString().trim() + "月"
                        + textViews[3].getText().toString().trim() + "日"
                        + "\n消费：" + textViews[4].getText().toString().trim() + "元";
                break;
            case "日子":
                textViews[1] = views[index].findViewById(R.id.year);
                textViews[2] = views[index].findViewById(R.id.month);
                textViews[3] = views[index].findViewById(R.id.day);
                String date = textViews[1].getText().toString().trim() + "年"
                        + textViews[2].getText().toString().trim() + "月"
                        + textViews[3].getText().toString().trim() + "日";
                content = date + getDayDistance(getToday(), date);
                break;
            case "账户":
                textViews[1] = views[index].findViewById(R.id.content_1);
                textViews[2] = views[index].findViewById(R.id.content_2);
                content = "账户：" + textViews[1].getText().toString().trim()
                        + "\n密码：" + textViews[2].getText().toString().trim();
                break;
            case "三省":
                textViews[1] = views[index].findViewById(R.id.content_1);
                textViews[2] = views[index].findViewById(R.id.content_2);
                title = "学习否：" + textViews[0].getText().toString().trim();
                content = "学几时：" + textViews[1].getText().toString().trim()
                        + "\n学之何：" + textViews[2].getText().toString().trim();
                break;
            default:
                break;
        }
        imgResource = Constant.IMG_RESOURCES[index];
    }

    /**
     * 清空标题和内容
     *
     * @param category 类型
     */
    public void clearTitleAndContents(String category) {
        textViews[0] = views[index].findViewById(R.id.title);
        title = textViews[0].getText().toString().trim();
        switch (category) {
            case "便签":
            case "链接":
            case "位置":
            case "心情":
                textViews[1] = views[index].findViewById(R.id.content_1);
                for (int i = 0; i < 2; i++) {
                    textViews[i].setText("");
                }
                break;
            case "消费":
                textViews[1] = views[index].findViewById(R.id.year);
                textViews[2] = views[index].findViewById(R.id.month);
                textViews[3] = views[index].findViewById(R.id.day);
                textViews[4] = views[index].findViewById(R.id.content_2);
                for (int i = 0; i < 5; i++) {
                    textViews[i].setText("");
                }
                break;
            case "日子":
                textViews[1] = views[index].findViewById(R.id.year);
                textViews[2] = views[index].findViewById(R.id.month);
                textViews[3] = views[index].findViewById(R.id.day);
                for (int i = 0; i < 4; i++) {
                    textViews[i].setText("");
                }
                break;
            case "账户":
            case "三省":
                textViews[1] = views[index].findViewById(R.id.content_1);
                textViews[2] = views[index].findViewById(R.id.content_2);
                for (int i = 0; i < 3; i++) {
                    textViews[i].setText("");
                }
                break;
            default:
                break;
        }
    }
}