package com.hfy.fingdemo.activity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hfy.fingdemo.R;
import com.hfy.fingdemo.base.BaseActivity;
import com.hfy.fingdemo.bean.Student;
import com.hfy.fingdemo.greendao.DBManager;
import com.hfy.fingdemo.sql.UserOpenHelper;
import com.hfy.fingdemo.test.database.greenDao.db.DaoMaster;
import com.hfy.fingdemo.test.database.greenDao.db.DaoSession;
import com.hfy.fingdemo.test.database.greenDao.db.StudentDao;

import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import krt.wid.util.MToast;



public class RePasswordActivity extends BaseActivity {

    @BindView(R.id.img)
    ImageView img;
    @BindView(R.id.edit_name)
    EditText editName;
    @BindView(R.id.edit_password)
    EditText editPassword;
    @BindView(R.id.login)
    TextView login;
    @BindView(R.id.ll)
    LinearLayout ll;

    private boolean isRe;
    private UserOpenHelper userOpenHelper;
    private SQLiteDatabase db;

    private Student student;
    @Override
    public void beforeBindLayout() {

    }

    @Override
    public int bindLayout() {
        return R.layout.activity_repassword;
    }

    @Override
    public void initView() {

        isRe = getIntent().getBooleanExtra("isRePassword", false);
        if (isRe) {
            login.setText("找回密码");
        } else {
            login.setText("注  册");
        }


    }

    /**
     * 向User表中添加一条记录
     */
    private void addData(String name,String telPhone) {
        DaoMaster daoMaster = DBManager.getDaoMaster(this);
        DaoSession daoSession = daoMaster.newSession();
        StudentDao studentDao = daoSession.getStudentDao();
        student= new Student();
        student.setName(name);
        student.setTelPhone(telPhone);
        studentDao.insert(student);
        finish();
    }

    private void insertUser(String name,String telPhone){
//        DaoMaster daoMaster = DBManager.getDaoMaster(this);
        DaoMaster.DevOpenHelper mDevOpenHelper = new DaoMaster.DevOpenHelper(this, "test.db");
        DaoMaster mDaoMaster = new DaoMaster(mDevOpenHelper.getWritableDb());
        StudentDao userDao = mDaoMaster.newSession().getStudentDao();
//        userDao.insert(user);
        student= new Student();
        student.setName(name);
        student.setTelPhone(telPhone);
//        userDao.insert(student);
        userDao.insertOrReplace(student);
        finish();
    }

    @Override
    public void loadData() {

    }
    @OnClick({R.id.login,})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.login:
                String name = editName.getText().toString();
                String password = editPassword.getText().toString();
                if (name.isEmpty()) {
                    MToast.showToast(RePasswordActivity.this, "账号不能为空");
                    return;
                }
                if (password.isEmpty()) {
                    MToast.showToast(RePasswordActivity.this, "密码不能为空");
                    return;
                }

                if (isRe) {
                    userOpenHelper = new UserOpenHelper(this, "User.db", null, 1);
                    db = userOpenHelper.getWritableDatabase();
                    Cursor c = db.query("UserTB", null, "userId=?",
                            new String[]{name}, null, null, null);
                    //如果有查询到数据，说明账号存在，可以进行密码重置操作
                    if (c != null && c.getCount() >= 1) {
                        ContentValues cv = new ContentValues();
                        cv.put("userPassword", password);//editPhone界面上的控件
                        String[] args = {String.valueOf(name)};
                        db.update("UserTB", cv, "userId=?", args);

                        c.close();
                        db.close();
                        SetToast("密码重置成功！");
                        this.finish();
                    } else {
                        SetToast("用户不存在");
                    }
                } else {
                    //1
//                    addData(name,password);
                    //2
                    Student student=new Student();
                    student.setName(name);
                    student.setTelPhone(password);

                    insertUser(name,password);


//                    userOpenHelper = new UserOpenHelper(this, "User.db", null, 1);
//                    db = userOpenHelper.getWritableDatabase();
//                    //根据输入的账号去数据库中进行查询
//                    Cursor c = db.query("UserTB", null, "userId=?",
//                            new String[]{name}, null, null, null);
//                    //如果有查询到数据，则说明账号已存在
//                    if (c != null && c.getCount() >= 1) {
//                        SetToast("该用户已存在");
//                        c.close();
//                    }
//                    //如果没有查询到数据，则往数据库中insert数据
//                    else {
//                        //insert data
//                        ContentValues values = new ContentValues();
//                        values.put("userId", name);
//                        values.put("userPassword", password);
//                        db.insert("UserTB", null, values);
//                        SetToast("注册成功");//提示信息
//                        this.finish();
//                    }
//                    db.close();
                }
                break;
        }
    }
}
