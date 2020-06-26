package com.example.sparrownotes;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sparrownotes.bean.UserBean;
import com.example.sparrownotes.dao.UserDao;
import com.example.sparrownotes.util.Constant;
import com.example.sparrownotes.util.SharedPreferenceUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 登录页面
 * @author Horsen
 */
public class MainActivity extends AppCompatActivity {

    @BindView(R.id.img_icon)
    ImageView imgIcon;
    @BindView(R.id.check_auto_login)
    CheckBox checkAutoLogin;
    @BindView(R.id.check_remember_pwd)
    CheckBox checkRememberPwd;
    @BindView(R.id.text_forget_pwd)
    TextView textForgetPwd;
    @BindView(R.id.btn_login)
    Button btnLogin;
    @BindView(R.id.edit_username)
    EditText editUsername;
    @BindView(R.id.edit_password)
    EditText editPassword;
    @BindView(R.id.text_register)
    TextView textRegister;
    @BindView(R.id.check_hide_pwd)
    CheckBox checkHidePwd;

    private static final String TAG = "MainActivity";
    private UserDao userDao;
    private String textUserName;
    private String textPassword;

    private static boolean REMEMBER_PWD = false;
    private static boolean AUTO_LOGIN = false;
    SharedPreferenceUtil spu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        userDao = new UserDao(this);
        spu = SharedPreferenceUtil.getInstance(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = getIntent();
        // 如果注册过
        if (intent.getBooleanExtra(Constant.HAS_REGISTERED, false)) {
            intent.removeExtra(Constant.HAS_REGISTERED);
            editUsername.setText(spu.getString(Constant.USER_NAME));
            editPassword.setText(spu.getString(Constant.PASSWORD));
        } else {
            // 如果勾选记住密码
            if (spu.getBoolean(Constant.REMEMBER_PWD)) {
                checkRememberPwd.setChecked(true);
                editUsername.setText(spu.getString(Constant.USER_NAME));
                editPassword.setText(spu.getString(Constant.PASSWORD));
            } else {
                editUsername.setText("");
                editPassword.setText("");
            }
            // 如果勾选自动登录
            if (spu.getBoolean(Constant.AUTO_LOGIN)) {
                checkAutoLogin.setChecked(true);
                Intent intentLogin = new Intent(MainActivity.this, HomeActivity.class);
                startActivity(intentLogin);
            }
        }
    }

    @OnClick({R.id.check_auto_login, R.id.check_remember_pwd, R.id.btn_login, R.id.text_forget_pwd, R.id.text_register, R.id.check_hide_pwd})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.check_auto_login:
                // 勾选自动登录时自动勾选记住密码
                if (checkAutoLogin.isChecked()) {
                    checkRememberPwd.setChecked(true);
                    AUTO_LOGIN = true;
                    REMEMBER_PWD = true;
                } else {
                    AUTO_LOGIN = false;
                }
                break;
            case R.id.check_remember_pwd:
                // 勾掉记住密码时自动勾掉自动登录
                if (!checkRememberPwd.isChecked()) {
                    checkAutoLogin.setChecked(false);
                    AUTO_LOGIN = false;
                    REMEMBER_PWD = false;
                } else {
                    REMEMBER_PWD = true;
                }
                break;
            case R.id.btn_login:
                // 检查非空
                if (isNotEmpty()) {
                    // 获得用户输入
                    getEditText();
                    // 检查用户名密码
                    if (checkUser()) {
                        // 存入sp 用于接下来页面的显示
                        spu.putBoolean(Constant.REMEMBER_PWD, REMEMBER_PWD);
                        spu.putBoolean(Constant.AUTO_LOGIN, AUTO_LOGIN);
                        spu.putString(Constant.USER_NAME, textUserName);
                        spu.putString(Constant.PASSWORD, textPassword);
                        spu.putInt(Constant.USER_ID, getUserID());
                        Intent intentLogin = new Intent(MainActivity.this, HomeActivity.class);
                        startActivity(intentLogin);
                        finish();
                    } else {
                        Toast.makeText(this, "用户名或密码错误", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, "用户名或密码不能为空", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.text_forget_pwd:
                // 忘记密码
                Intent intentFind = new Intent(MainActivity.this, FindPwdActivity.class);
                startActivity(intentFind);
                break;
            case R.id.text_register:
                // 注册
                Intent intentRegister = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(intentRegister);
                break;
            case R.id.check_hide_pwd:
                if (checkHidePwd.isChecked()) {
                    // 将密码显示出来
                    editPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    // 隐藏密码
                    editPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
                // 将光标移动到文本最后
                editPassword.setSelection(editPassword.getText().toString().length());
                break;
            default:
                break;
        }
    }

    /**
     * 检验输入非空
     *
     * @return
     */
    public boolean isNotEmpty() {
        return !"".equals(editUsername.getText().toString()) &&
                !"".equals(editPassword.getText().toString());
    }

    /**
     * 从控件中得到输入值
     */
    public void getEditText() {
        textUserName = editUsername.getText().toString();
        textPassword = editPassword.getText().toString();
    }

    /**
     * 验证用户名密码
     */
    public boolean checkUser() {
        List<UserBean> userBeans = userDao.queryByUserNameAndPassword(textUserName, textPassword);
        return userBeans.size() != 0;
    }

    /**
     * 获得用户ID
     *
     * @return
     */
    public int getUserID() {
        List<UserBean> userBeans = userDao.queryByUserNameAndPassword(textUserName, textPassword);
        return userBeans.get(0).getUserID();
    }
}