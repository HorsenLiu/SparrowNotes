package com.example.sparrownotes.dao;

import android.content.Context;

import com.example.sparrownotes.bean.UserBean;
import com.j256.ormlite.dao.Dao;
import com.example.sparrownotes.util.DBHelper;
import com.j256.ormlite.stmt.QueryBuilder;

import java.sql.SQLException;
import java.util.List;

/**
 * 用户操作类
 *
 * @author Horsen
 */
public class UserDao {
    private Dao<UserBean, Integer> dao;

    public UserDao(Context context) {
        try {
            this.dao = DBHelper.getInstance(context).getDao(UserBean.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 创建数据
     *
     * @param data 数据
     */
    public void create(UserBean data) {
        try {
            dao.createOrUpdate(data);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 创建数据集合
     *
     * @param datas 数据集合
     */
    public void createList(List<UserBean> datas) {
        try {
            dao.create(datas);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 向user表中添加一条数据
     * create:插入一条数据或集合
     * createIfNotExists:如果不存在则插入
     * createOrUpdate:如果指定id则更新
     *
     * @param data
     */
    public void insert(UserBean data) {
        try {
            dao.createIfNotExists(data);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 通过id删除指定数据
     *
     * @param id
     */
    public void delete(int id) {
        try {
            dao.deleteById(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除表中的一条数据
     *
     * @param data
     */
    public void delete(UserBean data) {
        try {
            dao.delete(data);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除表中数据集合
     *
     * @param datas
     */
    public void deleteList(List<UserBean> datas) {
        try {
            dao.delete(datas);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 清空数据
     */
    public void deleteAll() {
        try {
            dao.delete(dao.queryForAll());
        } catch (Exception e) {
        }
    }

    /**
     * 修改表中的一条数据
     *
     * @param data
     */
    public void update(UserBean data) {
        try {
            dao.update(data);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 查询表中的所有数据
     *
     * @return 返回查询结果集
     */
    public List<UserBean> queryAll() {
        List<UserBean> users = null;
        try {
            users = dao.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    /**
     * 根据ID取出用户信息
     *
     * @param id
     * @return
     */
    public UserBean queryById(int id) {
        UserBean user = null;
        try {
            user = dao.queryForId(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    /**
     * 通过列名和数据查找
     *
     * @param columnName 列名
     * @param data       数据
     * @return
     */
    public List<UserBean> queryForWhat(String columnName, String data) {
        List<UserBean> users = null;
        try {
            users = dao.queryForEq(columnName, data);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    /**
     * 通过条件查询集合
     *
     * @param userName 用户名
     * @param password 密码
     * @return
     */
    public List<UserBean> queryByUserNameAndPassword(String userName, String password) {
        try {
            QueryBuilder<UserBean, Integer> builder = dao.queryBuilder();
            builder
                    .where()
                    .eq("user_name", userName)
                    .and()
                    .eq("password", password);
            builder.orderBy("user_id", true);
            return builder.query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
