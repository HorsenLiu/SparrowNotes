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

import java.util.List;
import java.util.regex.Matcher;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 找回密码
 * @author Horsen
 */
public class FindPwdActivity extends AppCompatActivity {

    @BindView(R.id.btn_confirm_find)
    Button btnConfirmFind;
    @BindView(R.id.btn_reset_find)
    Button btnResetFind;
    @BindView(R.id.edit_confirm_pwd_find)
    EditText editConfirmPwdFind;
    @BindView(R.id.edit_password_find)
    EditText editPasswordFind;
    @BindView(R.id.edit_email_find)
    EditText editEmailFind;
    @BindView(R.id.check_hide_pwd_find)
    CheckBox checkHidePwdFind;
    @BindView(R.id.check_hide_pwd_find2)
    CheckBox checkHidePwdFind2;

    private static final String TAG = "FindPwdActivity";
    private UserDao userDao;
    private String textPassword;
    private String textConfirmPwd;
    private String textEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_pwd);
        ButterKnife.bind(this);
        userDao = new UserDao(this);
    }

    @OnClick({R.id.btn_confirm_find, R.id.btn_reset_find, R.id.check_hide_pwd_find, R.id.check_hide_pwd_find2})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_confirm_find:
                // 检查非空
                if (isNotEmpty()) {
                    // 获得用户输入
                    getEditText();
                    // 检查邮箱格式
                    if (checkEmail()) {
                        // 检查两次输入的密码是否一致
                        if (comparePwd()) {
                            // 更新密码
                            if (updatePwdFromDB()) {
                                // 跳转登录界面
                                Intent intent = new Intent(FindPwdActivity.this, MainActivity.class);
                                startActivity(intent);
                                FindPwdActivity.this.finish();
                            }
                        } else {
                            Toast.makeText(this, "两次输入的密码不一致", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(this, "邮箱格式错误", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, "邮箱或密码不能为空", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btn_reset_find:
                // 清空输入
                editPasswordFind.setText("");
                editConfirmPwdFind.setText("");
                editEmailFind.setText("");
                break;
            case R.id.check_hide_pwd_find:
                if (checkHidePwdFind.isChecked()) {
                    // 将密码显示出来
                    editPasswordFind.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    // 隐藏密码
                    editPasswordFind.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
                // 将光标移动到文本最后
                editPasswordFind.setSelection(editPasswordFind.getText().toString().length());
                break;
            case R.id.check_hide_pwd_find2:
                if (checkHidePwdFind2.isChecked()) {
                    // 将密码显示出来
                    editConfirmPwdFind.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    // 隐藏密码
                    editConfirmPwdFind.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
                // 将光标移动到文本最后
                editConfirmPwdFind.setSelection(editConfirmPwdFind.getText().toString().length());
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
        return !"".equals(editPasswordFind.getText().toString()) &&
                !"".equals(editConfirmPwdFind.getText().toString()) &&
                !"".equals(editEmailFind.getText().toString());
    }

    /**
     * 从控件中得到输入值
     */
    public void getEditText() {
        textPassword = editPasswordFind.getText().toString();
        textConfirmPwd = editConfirmPwdFind.getText().toString();
        textEmail = editEmailFind.getText().toString();
        Log.d(TAG, "getEditText: " + textEmail);
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
     * 验证两次输入的密码是否一致
     *
     * @return
     */
    public boolean comparePwd() {
        return textPassword.equals(textConfirmPwd);
    }

    /**
     * 修改数据库中的密码
     *
     * @return 邮箱是否存在
     */
    public boolean updatePwdFromDB() {
        List<UserBean> userBeans = userDao.queryForWhat("email", textEmail);
        if (userBeans.size() != 0) {
            UserBean user = userBeans.get(0);
            user.setPassword(textPassword);
            userDao.update(user);
            return true;
        } else {
            Toast.makeText(this, "邮箱错误", Toast.LENGTH_SHORT).show();
            return false;
        }
    }
}