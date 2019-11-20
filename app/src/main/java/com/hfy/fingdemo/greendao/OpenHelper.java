package com.hfy.fingdemo.greendao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.hfy.fingdemo.test.database.greenDao.db.DaoMaster;
import com.hfy.fingdemo.test.database.greenDao.db.StudentDao;

import org.greenrobot.greendao.database.Database;

public class OpenHelper  extends DaoMaster.OpenHelper {
    public OpenHelper(Context context, String name) {
        super(context, name);
    }

    public OpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory) {
        super(context, name, factory);
    }

    @Override
    public void onUpgrade(Database db, int oldVersion, int newVersion) {
//判断条件
        //操作数据库的更新 有几个表升级都可以传入到下面
        MigrationHelper.getInstance().migrate(db,StudentDao.class);
    }
}