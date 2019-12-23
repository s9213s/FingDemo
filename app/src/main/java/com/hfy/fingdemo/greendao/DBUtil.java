package com.hfy.fingdemo.greendao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.hfy.fingdemo.bean.Student;
import com.hfy.fingdemo.test.database.greenDao.db.DaoMaster;
import com.hfy.fingdemo.test.database.greenDao.db.DaoSession;
import com.hfy.fingdemo.test.database.greenDao.db.StudentDao;

import java.util.List;

public class DBUtil {
    private static DBUtil sDB;
    private final static String DB_NAME = "student_db";
    private DaoMaster.DevOpenHelper mOpenHelper;
    private StudentDao mWritableInfo;
    private StudentDao mReadableInfo;

    private Context mContext;
    public DBUtil(Context context) {
        this.mContext = context;
        // 通过 DaoMaster 的内部类 DevOpenHelper,得到一个便利的SQLiteOpenHelper对象.
        // 这里并不需要去编写「CREATE TABLE」这样的 SQL 语句，因为 greenDAO 已经帮你做了。
        mOpenHelper = new DaoMaster.DevOpenHelper(
                context, DB_NAME, null);
    }

    // 单例模式获取实例
    public static DBUtil getInstance(Context context) {
        if (sDB == null) {
            synchronized (DBUtil.class) {
                if (sDB == null) {
                    sDB = new DBUtil(context);
                }
            }
        }
        return sDB;
    }

    // 写入数据
    private StudentDao getWritableInfo() {
        if (mWritableInfo == null) {
            SQLiteDatabase db = mOpenHelper.getWritableDatabase();
            DaoMaster daoMaster = new DaoMaster(db);
            DaoSession daoSession = daoMaster.newSession();
            mWritableInfo = daoSession.getStudentDao();
        }
        return mWritableInfo;
    }

    // 读数据
    private StudentDao getReadableInfo() {
        if (mReadableInfo == null) {
            SQLiteDatabase db = mOpenHelper.getReadableDatabase();
            DaoMaster daoMaster = new DaoMaster(db);
            DaoSession daoSession = daoMaster.newSession();
            mReadableInfo = daoSession.getStudentDao();
        }
        return mReadableInfo;
    }

    // 增
    public void save(Student info) {
        if (queryById(info.getId()) == null) {
            getWritableInfo().insert(info);
        } else {
            getWritableInfo().update(info);
        }
    }

    // 删
    protected void delete(Student info) {
        getWritableInfo().delete(info);
    }

    // 改
    protected void update(Student info) {
        getWritableInfo().update(info);
    }

    // 查
    public Student queryById(long Id) {
        List<Student> list = getReadableInfo().queryBuilder()
                .where(StudentDao.Properties.Id.eq(Id))
                .build()
                .list();
        return list.isEmpty() ? null : list.get(0);
    }
}
