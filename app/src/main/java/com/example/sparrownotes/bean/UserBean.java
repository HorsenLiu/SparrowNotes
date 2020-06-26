package com.example.sparrownotes.bean;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * 用户实体类
 * userID   用户ID
 * userName 用户名
 * password 密码
 * email    邮箱
 *
 * @author Horsen
 */
@DatabaseTable(tableName = "tb_user")
public class UserBean {

    @DatabaseField(generatedId = true, columnName = "user_id")
    private int userID;

    @DatabaseField(columnName = "user_name")
    private String userName;

    @DatabaseField
    private String password;

    @DatabaseField
    private String email;

    public UserBean() {
    }

    public UserBean(String userName, String password, String email) {
        this.userName = userName;
        this.password = password;
        this.email = email;
    }

    @Override
    public String toString() {
        return "UserBean{" +
                "userID=" + userID +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

    public int getUserID() {
        return userID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
