package com.example.sparrownotes.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * 以键值对的方式提供本地文件存储
 *
 * @author liutb
 */
public class SharedPreferenceUtil {
    /**
     * 文件名
     */
    private static final String NAME = "sparrow_share";
    private Context context;
    private SharedPreferences.Editor editor;
    private SharedPreferences share;
    private static SharedPreferenceUtil sharedPreferenceUtil;

    /**
     * 构造方法获取SharedPreferences
     *
     * @param context 上下文
     */
    @SuppressLint("CommitPrefEdits")
    private SharedPreferenceUtil(Context context) {
        this.context = context;
        if (context != null) {
            share = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
            editor = share.edit();
        }
    }

    public static SharedPreferenceUtil getInstance(Context con) {
        if (sharedPreferenceUtil == null) {
            sharedPreferenceUtil = new SharedPreferenceUtil(con);
        }
        return sharedPreferenceUtil;
    }

    /**
     * 保存String类型的数据
     *
     * @param key   键名称
     * @param value 保存的值
     */
    public void putString(String key, String value) {
        if (null == value) {
            value = "";
        }
        editor.putString(key, value);
        editor.apply();
    }

    /**
     * 保存int类型的数据
     *
     * @param key   键名称
     * @param value 保存的值
     * @return
     */
    public void putInt(String key, int value) {
        editor.putInt(key, value);
        editor.apply();
    }

    /**
     * 保存Float类型的数据
     *
     * @param key   键名称
     * @param value 保存的值
     */
    public void putFloat(String key, float value) {
        editor.putFloat(key, value);
        editor.apply();
    }

    /**
     * 保存boolean类型的数据
     *
     * @param key   键名称
     * @param value 保存的值
     */
    public void putBoolean(String key, boolean value) {
        editor.putBoolean(key, value);
        editor.apply();
    }

    /**
     * 保存Long类型的数据
     *
     * @param key   键名称
     * @param value 保存的值
     */
    public void putLong(String key, long value) {
        editor.putLong(key, value);
        editor.apply();
    }


    /**
     * 获取String类型的数据
     *
     * @param key 键名称
     */
    public String getString(String key) {
        return share.getString(key, "");
    }

    /**
     * 获取int类型的数据
     *
     * @param key 键名称
     * @return 返回的int型数据
     */
    public int getInt(String key) {
        return share.getInt(key, 0);
    }

    /**
     * 获取Float类型的数据
     *
     * @param key 键名称
     * @return 返回的float型数据
     */
    public float getFloat(String key) {
        return share.getFloat(key, 0.00f);
    }

    /**
     * 获取Long类型的数据
     *
     * @param key 键名称
     * @return 返回的long型数据
     */
    public long getLong(String key) {
        return share.getLong(key, 0);
    }

    /**
     * 获取boolean类型的数据
     *
     * @param key 键名称
     * @return 返回的boolean型数据
     */
    public boolean getBoolean(String key) {
        return share.getBoolean(key, false);
    }

    /**
     * 删除不需要的数据
     *
     * @param key 键名称
     */
    public void removeKey(String key) {
        editor.remove(key);
        editor.apply();
    }


}