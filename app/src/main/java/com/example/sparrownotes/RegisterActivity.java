package com.example.sparrownotes;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sparrownotes.bean.UserBean;
import com.example.sparrownotes.dao.UserDao;
import com.example.sparrownotes.util.Constant;
import com.example.sparrownotes.util.SharedPreferenceUtil;

import java.util.List;
import java.util.regex.Matcher;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 注册页面
 *
 * @author Horsen
 */
public class RegisterActivity extends AppCompatActivity {

    @BindView(R.id.btn_register)
    Button btnRegister;
    @BindView(R.id.btn_reset)
    Button btnReset;
    @BindView(R.id.check_hide_pwd_reg)
    CheckBox checkHidePwdReg;
    @BindView(R.id.check_hide_pwd_reg2)
    CheckBox checkHidePwdReg2;
    @BindView(R.id.edit_username_reg)
    EditText editUsernameReg;
    @BindView(R.id.edit_confirm_pwd_reg)
    EditText editConfirmPwdReg;
    @BindView(R.id.edit_email_reg)
    EditText editEmailReg;
    @BindView(R.id.edit_password_reg)
    EditText editPasswordReg;

    SharedPreferenceUtil spu;
    private static final String TAG = "RegisterActivity";
    private UserDao userDao;
    private String textUserName;
    private String textPassword;
    private String textConfirmPwd;
    private String textEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        userDao = new UserDao(this);
        spu = SharedPreferenceUtil.getInstance(this);
    }

    @OnClick({R.id.btn_register, R.id.btn_reset, R.id.check_hide_pwd_reg, R.id.check_hide_pwd_reg2})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_register:
                // 检验非空
                if (isNotEmpty()) {
                    // 获得用户输入
                    getEditText();
                    // 检验密码
                    if (comparePwd()) {
                        // 检查邮箱格式
                        if (checkEmail()) {
                            // 检查邮箱是否可用
                            if (checkOnlyEmail()) {
                                insertUserIntoDB();
                                queryAll();
                                Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                                intent.putExtra(Constant.HAS_REGISTERED, true);
                                startActivity(intent);
                                finish();
                            } else {
                                Toast.makeText(this, "邮箱已经被使用", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(this, "邮箱格式错误", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(this, "两次输入的密码不一致", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, "用户名或密码不能为空", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btn_reset:
                editUsernameReg.setText("");
                editPasswordReg.setText("");
                editConfirmPwdReg.setText("");
                editEmailReg.setText("");
                break;
            case R.id.check_hide_pwd_reg:
                if (checkHidePwdReg.isChecked()) {
                    // 将密码显示出来
                    editPasswordReg.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    // 隐藏密码
                    editPasswordReg.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
                // 将光标移动到文本最后
                editPasswordReg.setSelection(editPasswordReg.getText().toString().length());
                break;
            case R.id.check_hide_pwd_reg2:
                if (checkHidePwdReg2.isChecked()) {
                    // 将密码显示出来
                    editConfirmPwdReg.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    // 隐藏密码
                    editConfirmPwdReg.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
                // 将光标移动到文本最后
                editConfirmPwdReg.setSelection(editConfirmPwdReg.getText().toString().length());
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
        return !"".equals(editUsernameReg.getText().toString()) &&
                !"".equals(editPasswordReg.getText().toString()) &&
                !"".equals(editConfirmPwdReg.getText().toString());
    }

    /**
     * 从控件中得到输入值
     */
    public void getEditText() {
        textUserName = editUsernameReg.getText().toString();
        textPassword = editPasswordReg.getText().toString();
        textConfirmPwd = editConfirmPwdReg.getText().toString();
        textEmail = editEmailReg.getText().toString();
    }

    /**
     * 检查邮箱是否可用
     *
     * @return
     */
    public boolean checkOnlyEmail() {
        List<UserBean> userBeans = userDao.queryForWhat("email", textEmail);
        return userBeans.size() == 0;
    }

    /**
     * 验证两次输入的密码是否一致
     *
     * @return
     */
    public boolean comparePwd() {
        return textPassword.equals(textConfirmPwd);
    }

    /**
     * 检查邮箱格式
     *
     * @return
     */
    public boolean checkEmail() {
        Matcher emailMatcher = Constant.EMAIL_PATTERN.matcher(textEmail);
        return emailMatcher.matches();
    }

    /**
     * 向数据库表插入数据
     */
    public void insertUserIntoDB() {
        UserBean userBean = new UserBean(textUserName, textPassword, textEmail);
        userDao.insert(userBean);
        // 同时存到SP中
        spu.putString(Constant.USER_NAME, textUserName);
        spu.putString(Constant.PASSWORD, textPassword);
    }

    /**
     * 查询表中所有数据
     */
    public void queryAll() {
        List<UserBean> userBeans = userDao.queryAll();
        for (int i = 0; i < userBeans.size(); i++) {
            Log.d(TAG, "queryAll: " + userBeans.get(i).toString());
        }
    }
}