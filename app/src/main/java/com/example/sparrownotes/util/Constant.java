package com.example.sparrownotes.util;

import com.example.sparrownotes.R;

import java.util.regex.Pattern;

/**
 * @author Horsen
 */
public class Constant {
    // 数据库名和版本号
    public static final String DB_NAME = "sparrow.db";
    public static final int DB_VERSION = 1;
    // 用来判断邮箱格式的正则表达式
    public static final String EMAIL_REGEX = "^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$";
    public static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);
    // 用来存储用户登录操作的键
    public static final String HAS_REGISTERED = "has_registered";
    public static final String REMEMBER_PWD = "remember_pwd";
    public static final String AUTO_LOGIN = "auto_login";
    public static final String USER_NAME = "user_name";
    public static final String PASSWORD = "password";
    public static final String USER_ID = "userId";
    // 用来标记fragment的键
    public static final String TOP_TAG = "top";
    public static final String ADD_TAG = "add";
    public static final String LIST_TAG = "list";
    // 标记当前显示的页面
    public static String PAGE_STATE = "top";
    // 用来更新时间的消息
    public static final int UPDATE_TIME = 1;
    // 网络地址前后缀
    public static final String PREFIX_URL = "http://192.168.0.110:8080/sparrow_notes/cate_";
    public static final String SUFFIX_URL = ".png";
    // 存储查找条件的键
    public static final String SEARCH_FOR_WHAT = "what";
    public static final String SEARCH_VALUE = "value";
    // 不同的添加布局 图片地址 图片资源(int)
    public static final int[] LAYOUTS = {R.layout.add_note, R.layout.add_link, R.layout.add_location, R.layout.add_bill,
            R.layout.add_date, R.layout.add_mood, R.layout.add_account, R.layout.add_question};
    public static final String[] IMG_URLS = {"note", "link", "location", "bill", "date", "mood", "account", "question"};
    public static final int[] IMG_RESOURCES = {R.drawable.cate_note, R.drawable.cate_link, R.drawable.cate_location,
            R.drawable.cate_bill, R.drawable.cate_date, R.drawable.cate_mood, R.drawable.cate_account, R.drawable.cate_question};
}
