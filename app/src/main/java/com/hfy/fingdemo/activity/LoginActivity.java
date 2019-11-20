package com.hfy.fingdemo.activity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.hfy.fingdemo.R;
import com.hfy.fingdemo.base.BaseActivity;
import com.hfy.fingdemo.sql.UserOpenHelper;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import krt.wid.util.MToast;

public class LoginActivity extends BaseActivity {
    @BindView(R.id.edit_name)
    EditText editName;
    @BindView(R.id.edit_password)
    EditText editPassword;
    @BindView(R.id.login)
    TextView login;
    @BindView(R.id.RePassword)
    TextView RePassword;
    @BindView(R.id.getUser)
    TextView getUser;

    private UserOpenHelper userOpenHelper;
    private SQLiteDatabase db;

    @Override
    public void beforeBindLayout() {

    }

    @Override
    public int bindLayout() {
        return R.layout.activity_login;
    }

    @Override
    public void initView() {
        setStatusBarFullTransparent();
        userOpenHelper = new UserOpenHelper(this, "User.db", null, 1);
        db = userOpenHelper.getWritableDatabase();

        if (spUtil.isLogin()) {
            startActivity(new Intent(LoginActivity.this, HomeActivity.class));
            finish();
        } else {

        }
    }

    @Override
    public void loadData() {


    }

    @OnClick({R.id.login, R.id.RePassword, R.id.getUser})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.login:
                String name = editName.getText().toString();
                String password = editPassword.getText().toString();
                if (name.isEmpty()) {
                    MToast.showToast(LoginActivity.this, "账号不能为空");
                    return;
                }
                if (password.isEmpty()) {
                    MToast.showToast(LoginActivity.this, "密码不能为空");
                    return;
                }
                Cursor cursor = db.query("UserTB", null, "userId=? and userPassword=?",
                        new String[]{name, password}, null, null, null);
                //如果查询到数据
                if (cursor != null && cursor.getCount() >= 1) {
                    SetToast("登录成功");
                } else {
                    SetToast("账号或密码有误");
                }

                break;
            case R.id.RePassword:
                Intent intent = new Intent(LoginActivity.this, RePasswordActivity.class);
                intent.putExtra("isRePassword", true);
                startActivity(intent);
                break;
            case R.id.getUser:
                Intent intent2 = new Intent(LoginActivity.this, RePasswordActivity.class);
                intent2.putExtra("isRePassword", false);
                startActivity(intent2);
                break;
            default:
        }
    }

}
