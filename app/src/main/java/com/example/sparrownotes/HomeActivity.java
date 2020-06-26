package com.example.sparrownotes;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.sparrownotes.bean.UserBean;
import com.example.sparrownotes.dao.UserDao;
import com.example.sparrownotes.fragment.AddFragment;
import com.example.sparrownotes.fragment.AllFragment;
import com.example.sparrownotes.fragment.TopFragment;
import com.example.sparrownotes.util.Constant;
import com.example.sparrownotes.util.DialogUtil;
import com.example.sparrownotes.util.SharedPreferenceUtil;
import com.google.android.material.navigation.NavigationView;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;


/**
 * 主页
 *
 * @author Horsen
 */
public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.btn_top)
    RadioButton btnTop;
    @BindView(R.id.btn_add)
    RadioButton btnAdd;
    @BindView(R.id.btn_all)
    RadioButton btnAll;
    @BindView(R.id.dock_radio_group)
    RadioGroup dockRadioGroup;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @BindView(R.id.nav_view)
    NavigationView navView;

    private static final String TAG = "HomeActivity";
    ActionBar actionBar;
    Fragment fragment;
    FragmentManager manager = getSupportFragmentManager();
    FragmentTransaction transaction = manager.beginTransaction();
    SharedPreferenceUtil spu;
    private UserDao userDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        userDao = new UserDao(this);
        spu = SharedPreferenceUtil.getInstance(this);
        initView();
    }

    /**
     * 初始化控件
     */
    public void initView() {
        // 工具栏
        toolbar.setTitle(R.string.app_name);
        setTitleCenter(toolbar);
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.icon_setting);
        }
        // 抽屉
        ViewGroup.LayoutParams params = navView.getLayoutParams();
        params.width = getResources().getDisplayMetrics().widthPixels / 2;
        navView.setLayoutParams(params);
        navView.setNavigationItemSelectedListener(this);
        // 抽屉中的header
        View headerView = navView.getHeaderView(0);
        TextView textUserName = headerView.findViewById(R.id.header_user_name);
        TextView textEmail = headerView.findViewById(R.id.header_email);
        textUserName.setText(spu.getString(Constant.USER_NAME));
        textEmail.setText(getEmail());
        // 默认加载置顶Fragment
        fragment = new TopFragment();
        transaction.replace(R.id.view_pager, fragment, Constant.TOP_TAG);
        transaction.commit();
    }

    /**
     * 单选按钮监听器 用来切换Fragment
     *
     * @param view
     * @param isChanged
     */
    @OnCheckedChanged({R.id.btn_add, R.id.btn_all, R.id.btn_top})
    public void onCheckedChanged(CompoundButton view, boolean isChanged) {
        transaction = manager.beginTransaction();
        switch (view.getId()) {
            case R.id.btn_top:
                if (isChanged) {
                    fragment = new TopFragment();
                    transaction.replace(R.id.view_pager, fragment, Constant.TOP_TAG);
                    transaction.commit();
                    Constant.PAGE_STATE = "top";
                }
                break;
            case R.id.btn_add:
                if (isChanged) {
                    fragment = new AddFragment();
                    transaction.replace(R.id.view_pager, fragment, Constant.ADD_TAG);
                    transaction.commit();
                }
                break;
            case R.id.btn_all:
                if (isChanged) {
                    fragment = new AllFragment();
                    transaction.replace(R.id.view_pager, fragment, Constant.LIST_TAG);
                    transaction.commit();
                    Constant.PAGE_STATE = "all";
                }
                break;
            default:
                break;
        }
    }

    /**
     * 获得登录用户的邮箱
     *
     * @return
     */
    public String getEmail() {
        List<UserBean> userBeans = userDao
                .queryByUserNameAndPassword(spu.getString(Constant.USER_NAME), spu.getString(Constant.PASSWORD));
        return userBeans.get(0).getEmail();
    }

    /**
     * 抽屉中菜单的点击事件
     *
     * @param item
     * @return
     */
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        drawerLayout.closeDrawers();
        switch (item.getItemId()) {
            case R.id.nav_change_pwd:
                DialogUtil.editInfoDialog(this, spu.getInt(Constant.USER_ID));
                break;
            case R.id.nav_dismiss:
                DialogUtil.dismissDialog(this, spu.getInt(Constant.USER_ID));
                break;
            case R.id.nav_exit:
                spu.putBoolean(Constant.AUTO_LOGIN, false);
                Intent intent = new Intent(HomeActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.nav_github:
                // 跳转到我的项目页面
                Uri uri = Uri.parse("https://github.com/HorsenLiu/SparrowNotes");
                Intent intentUri = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intentUri);
                break;
            default:
                break;
        }
        return true;
    }

    /**
     * ToolBar的menu
     *
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        setIconsVisible(menu, true);
        return true;
    }

    /**
     * 重写ToolBar上菜单点击事件
     * 按照条件查找
     *
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.search_title:
                DialogUtil.searchTitleDialog(this);
                break;
            case R.id.search_content:
                DialogUtil.searchContentDialog(this);
                break;
            case R.id.search_category:
                DialogUtil.searchCategoryDialog(this);
                break;
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                break;
            default:
                break;
        }
        return true;
    }

    /**
     * ToolBar标题居中
     *
     * @param toolbar
     */
    public void setTitleCenter(Toolbar toolbar) {
        String title = "title";
        final CharSequence originalTitle = toolbar.getTitle();
        toolbar.setTitle(title);
        for (int i = 0; i < toolbar.getChildCount(); i++) {
            View view = toolbar.getChildAt(i);
            if (view instanceof TextView) {
                TextView textView = (TextView) view;
                if (title.equals(textView.getText())) {
                    textView.setGravity(Gravity.CENTER);
                    Toolbar.LayoutParams params = new Toolbar.LayoutParams(Toolbar.LayoutParams.WRAP_CONTENT, Toolbar.LayoutParams.MATCH_PARENT);
                    params.gravity = Gravity.CENTER;
                    textView.setLayoutParams(params);
                }
            }
            toolbar.setTitle(originalTitle);
        }
    }

    /**
     * menu显示图标
     *
     * @param menu
     * @param flag
     */
    private void setIconsVisible(Menu menu, boolean flag) {
        // 判断menu是否为空
        if (menu != null) {
            try {
                // 如果不为空,就反射拿到menu的setOptionalIconsVisible方法
                Method method = menu.getClass().getDeclaredMethod("setOptionalIconsVisible", Boolean.TYPE);
                // 暴力访问该方法
                method.setAccessible(true);
                // 调用该方法显示icon
                method.invoke(menu, flag);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}