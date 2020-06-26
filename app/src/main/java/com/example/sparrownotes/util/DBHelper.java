package com.example.sparrownotes.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.ArrayMap;

import com.example.sparrownotes.bean.NoteBean;
import com.example.sparrownotes.bean.UserBean;
import com.example.sparrownotes.util.Constant;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

/**
 * 数据库帮助类
 *
 * @author Horsen
 */
@SuppressWarnings("AlibabaRemoveCommentedCode")
public class DBHelper extends OrmLiteSqliteOpenHelper {
    /**
     * 防止多线程同时操作, 实现单例模式, 并锁定线程
     * 数组存储App中所有的Dao对象的Map集合
     */
    private static DBHelper dbHelper = null;
    private ArrayMap<String, Dao> daos = new ArrayMap<>();

    public static synchronized DBHelper getInstance(Context context) {
        if (dbHelper == null) {
            synchronized (DBHelper.class) {
                if (dbHelper == null) {
                    dbHelper = new DBHelper(context);
                }
            }
        }
        return dbHelper;
    }

    private DBHelper(Context context) {
        super(context, Constant.DB_NAME, null, Constant.DB_VERSION);
    }

    /**
     * 包含创建表的语句
     *
     * @param sqLiteDatabase
     * @param connectionSource
     */
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, UserBean.class);
            TableUtils.createTable(connectionSource, NoteBean.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    /**
     * 包含新建表的语句
     * 就是删了再创罢了
     *
     * @param sqLiteDatabase
     * @param connectionSource
     * @param i
     * @param i1
     */
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource, int i, int i1) {
        try {
            TableUtils.dropTable(connectionSource, UserBean.class, false);
            TableUtils.dropTable(connectionSource, NoteBean.class, false);
            onCreate(sqLiteDatabase, connectionSource);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据传入的Dao路径获取这个Dao的单例对象, 要么从daos中获取, 要么新建一个存入daos
     */
    @Override
    public synchronized Dao getDao(Class clazz) throws SQLException {
        Dao dao = null;
        String className = clazz.getSimpleName();
        if (daos.containsKey(className)) {
            dao = daos.get(className);
        }
        if (dao == null) {
            dao = super.getDao(clazz);
            daos.put(className, dao);
        }
        return dao;
    }

    /**
     * 释放资源
     */
    @Override
    public void close() {
        super.close();
        for (String key : daos.keySet()) {
            Dao dao = daos.get(key);
            dao = null;
        }
    }
}
