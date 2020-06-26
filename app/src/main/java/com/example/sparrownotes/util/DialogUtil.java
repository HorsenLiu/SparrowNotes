package com.example.sparrownotes.util;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sparrownotes.MainActivity;
import com.example.sparrownotes.R;
import com.example.sparrownotes.bean.NoteBean;
import com.example.sparrownotes.bean.UserBean;
import com.example.sparrownotes.dao.NoteDao;
import com.example.sparrownotes.dao.UserDao;

/**
 * 自定义对话框
 * @author Horsen
 */
public class DialogUtil {
    /**
     * 查找标题消息框
     *
     * @param context 上下文
     */
    public static void searchTitleDialog(Context context) {
        SharedPreferenceUtil spu = SharedPreferenceUtil.getInstance(context);
        // 获得布局管理器
        LayoutInflater inflater = LayoutInflater.from(context);
        // 将布局转换成View
        View view = inflater.inflate(R.layout.search_title_dialog, null);
        final EditText editText = view.findViewById(R.id.edit);
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("按标题查找")
                .setView(view)
                .setIcon(R.drawable.search_icon_title)
                .setPositiveButton("确定", (dialog, which) -> {
                    // 搜索条件传入sp
                    spu.putString(Constant.SEARCH_FOR_WHAT, "title");
                    spu.putString(Constant.SEARCH_VALUE, editText.getText().toString());
                    // 提醒用户下拉刷新
                    Toast.makeText(context, "下拉刷新", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("取消", (dialog, which) -> {
                });
        builder.create().show();
    }

    /**
     * 查找内容消息框
     *
     * @param context 上下文
     */
    public static void searchContentDialog(Context context) {
        SharedPreferenceUtil spu = SharedPreferenceUtil.getInstance(context);
        // 获得布局管理器
        LayoutInflater inflater = LayoutInflater.from(context);
        // 将布局转换成View
        View view = inflater.inflate(R.layout.search_content_dialog, null);
        final EditText editText = view.findViewById(R.id.edit);
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("按内容查找")
                .setView(view)
                .setIcon(R.drawable.search_icon_content)
                .setPositiveButton("确定", (dialog, which) -> {
                    // 搜索条件传入sp
                    spu.putString(Constant.SEARCH_FOR_WHAT, "content");
                    spu.putString(Constant.SEARCH_VALUE, editText.getText().toString());
                    // 提醒用户下拉刷新
                    Toast.makeText(context, "下拉刷新", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("取消", (dialog, which) -> {
                });
        builder.create().show();
    }

    /**
     * 查找分类消息框
     *
     * @param context
     */
    public static void searchCategoryDialog(final Context context) {
        SharedPreferenceUtil spu = SharedPreferenceUtil.getInstance(context);
        final String[] categories = new String[]{"便签", "链接", "位置", "消费", "日子", "心情", "账户", "三省"};
        final String[] category = new String[1];
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("按分类查找")
                .setIcon(R.drawable.search_icon_category)
                .setSingleChoiceItems(categories, 0, (dialog, which) -> category[0] = categories[which])
                .setPositiveButton("确认", (dialog, which) -> {
                    // 搜索条件传入sp
                    spu.putString(Constant.SEARCH_FOR_WHAT, "category");
                    spu.putString(Constant.SEARCH_VALUE, category[0]);
                    // 提醒用户下拉刷新
                    Toast.makeText(context, "下拉刷新", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("取消", null)
                .show();
    }

    /**
     * 修改note消息框
     *
     * @param context
     */
    public static void editNoteDialog(final Context context, int noteID) {
        NoteDao noteDao = new NoteDao(context);
        NoteBean noteItem = noteDao.queryById(noteID);
        // 获得布局管理器
        LayoutInflater inflater = LayoutInflater.from(context);
        // 将布局转换成View
        View view = inflater.inflate(R.layout.edit_note_dialog, null);
        final EditText editTile, editContent;
        editTile = view.findViewById(R.id.edit_title);
        editContent = view.findViewById(R.id.edit_content);
        // 将原本的值显示出来
        editTile.setText(noteItem.getTitle());
        editContent.setText(noteItem.getContent());
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("编辑")
                .setView(view)
                .setIcon(R.drawable.icon_edit)
                .setPositiveButton("确认", (dialog, which) -> {
                    // 将用户输入存入数据库
                    noteItem.setTitle(editTile.getText().toString().trim());
                    noteItem.setContent(editContent.getText().toString().trim());
                    noteDao.update(noteItem);
                    // 提醒用户下拉刷新
                    Toast.makeText(context, "下拉刷新", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("取消", null)
                .show();
    }

    /**
     * 修改信息
     *
     * @param context
     */
    public static void editInfoDialog(final Context context, int userID) {
        SharedPreferenceUtil spu = SharedPreferenceUtil.getInstance(context);
        UserDao userDao = new UserDao(context);
        UserBean userItem = userDao.queryById(userID);
        // 获得布局管理器
        LayoutInflater inflater = LayoutInflater.from(context);
        // 将布局转换成View
        View view = inflater.inflate(R.layout.edit_info_dialog, null);
        final EditText editUsername, editPassword, editEmail;
        editUsername = view.findViewById(R.id.edit_username_info);
        editPassword = view.findViewById(R.id.edit_password_info);
        editEmail = view.findViewById(R.id.edit_email_info);
        // 将原本的值显示出来
        editUsername.setText(userItem.getUserName());
        editPassword.setText(userItem.getPassword());
        editEmail.setText(userItem.getEmail());
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("修改信息")
                .setView(view)
                .setIcon(R.drawable.icon_gear)
                .setPositiveButton("确认", (dialog, which) -> {
                    // 将用户输入存入数据库
                    userItem.setUserName(editUsername.getText().toString().trim());
                    userItem.setPassword(editPassword.getText().toString().trim());
                    userItem.setEmail(editEmail.getText().toString().trim());
                    userDao.update(userItem);
                    // 跳转回登录页面并且清除勾选
                    spu.putBoolean(Constant.AUTO_LOGIN, false);
                    spu.putBoolean(Constant.REMEMBER_PWD, false);
                    Intent intent = new Intent(context, MainActivity.class);
                    context.startActivity(intent);
                })
                .setNegativeButton("取消", null)
                .show();
    }

    /**
     * 修改问候语
     * 实现失败了 功能砍掉了 方法先留着
     *
     * @param context
     */
    public static void editGreetingDialog(final Context context) {
        // 获得布局管理器
        LayoutInflater inflater = LayoutInflater.from(context);
        // 将布局转换成View
        View view = inflater.inflate(R.layout.edit_greeting_dialog, null);
        final EditText editText;
        final TextView textView;
        editText = view.findViewById(R.id.edit);
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("修改问候语")
                .setView(view)
                .setIcon(R.drawable.icon_smile)
                .setPositiveButton("确认", (dialog, which) -> {

                    editText.getText().toString();
                })
                .setNegativeButton("取消", null)
                .show();
    }

    /**
     * 注销账户
     *
     * @param context
     */
    public static void dismissDialog(final Context context, int userID) {
        SharedPreferenceUtil spu = SharedPreferenceUtil.getInstance(context);
        UserDao userDao = new UserDao(context);
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("提示")
                .setMessage("是否要注销账户")
                .setIcon(R.drawable.icon_warning)
                .setPositiveButton("确认", (dialog, which) -> {
                    userDao.delete(userID);
                    // 跳转回登录页面并且清除勾选
                    spu.putBoolean(Constant.AUTO_LOGIN, false);
                    spu.putBoolean(Constant.REMEMBER_PWD, false);
                    Intent intent = new Intent(context, MainActivity.class);
                    context.startActivity(intent);
                })
                .setNegativeButton("取消", null)
                .show();
    }
}
