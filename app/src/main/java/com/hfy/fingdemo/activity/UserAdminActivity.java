package com.hfy.fingdemo.activity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.hfy.fingdemo.R;
import com.hfy.fingdemo.adapter.StudentAdapter;
import com.hfy.fingdemo.base.BaseActivity;
import com.hfy.fingdemo.bean.Student;
import com.hfy.fingdemo.greendao.DBManager;
import com.hfy.fingdemo.sql.UserOpenHelper;
import com.hfy.fingdemo.test.database.greenDao.db.DaoMaster;
import com.hfy.fingdemo.test.database.greenDao.db.DaoSession;
import com.hfy.fingdemo.test.database.greenDao.db.StudentDao;
import com.yanzhenjie.recyclerview.swipe.SwipeMenu;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuBridge;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuCreator;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItem;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItemClickListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import krt.wid.util.ParseJsonUtil;

public class UserAdminActivity extends BaseActivity {
    @BindView(R.id.add)
    ImageView add;
    @BindView(R.id.rv_User)
    RecyclerView rvUser;

    private int position,menuPosition ;
    private UserOpenHelper userOpenHelper;
    private SQLiteDatabase db;


    @Override
    public void beforeBindLayout() {

    }

    @Override
    public int bindLayout() {
        return R.layout.activity_user_admin;
    }

    /**
     * 查询用户列表
     */
    public List<Student> queryUserList() {
        DaoMaster daoMaster = DBManager.getDaoMaster(this);
        DaoSession daoSession = daoMaster.newSession();
        StudentDao userDao = daoSession.getStudentDao();
        QueryBuilder<Student> qb = userDao.queryBuilder();
        List<Student> list = qb.list();
        return list;
    }

    /**
     * 查询用户列表
     */
    public Student queryUserModel(String curName) {
        DaoMaster daoMaster = DBManager.getDaoMaster(this);
        DaoSession daoSession = daoMaster.newSession();
        StudentDao userDao = daoSession.getStudentDao();
        QueryBuilder<Student> qb = userDao.queryBuilder();
        qb.where(StudentDao.Properties.Name.like("%" + curName + "%")).build();
        List<Student> list = qb.list();
        if (list != null && list.size() > 0) {
            SetToast(ParseJsonUtil.toJson(list.get(0)));
            return list.get(0);
        } else {
            return null;
        }
    }


    @Override
    public void initView() {
        Log.w("hfydemo","*****list*****"+ParseJsonUtil.toJson(queryUserList()));
        StudentAdapter studentAdapter= new StudentAdapter(queryUserList());
        rvUser.setLayoutManager(new LinearLayoutManager(this));
        rvUser.setAdapter(studentAdapter);

//        rvUser.setSwipeMenuCreator(swipeMenuCreator);//菜单创建器 侧滑删除
//        rvUser.setSwipeMenuItemClickListener(mMenuItemClickListener);//删除监听
//        rvUser.setLayoutManager(new LinearLayoutManager(this));

//        userOpenHelper = new UserOpenHelper(this, "User.db", null, 1);
//        db = userOpenHelper.getWritableDatabase();
//        //显示数据库
//        Cursor cursor1 = db.query("UserTB", new String[]{"_id", "userId", "userPassword"},
//                null, null, null, null, null);
//
//        //SimpleCursorAdapter  必须要有一列是 _id ；
//        final SimpleCursorAdapter adapter = new SimpleCursorAdapter(this,
//                R.layout.item_user_admin, cursor1, new String[]{"_id", "userId", "userPassword"}, new int[]{R.id.tv_number, R.id.tv_userId,
//                R.id.tv_userPassword}, 0);

//        rvUser.setAdapter(adapter);
//        Log.w("hfydemo","*******adapter******"+ParseJsonUtil.toJson(adapter));
//        Log.w("hfydemo","*******rvUser******"+ParseJsonUtil.toJson(rvUser.getAdapter()));
//        rvUser.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                switch (i) {
//                    case 0:
//                        SetToast("00000000000000");
//                        break;
//                    case 1:
//                        SetToast("1111"+adapter.getCursor().getColumnName(i));
//                        break;
//                }
//            }
//        });
    }


    @Override
    public void loadData() {

    }

    @OnClick({R.id.add})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.add:
                queryUserModel("hfy001");
                break;
            default:
        }
    }

//    /**
//     * 菜单创建器，在Item要创建菜单的时候调用。
//     */
//    private SwipeMenuCreator swipeMenuCreator = new SwipeMenuCreator() {
//        @Override
//        public void onCreateMenu(SwipeMenu swipeLeftMenu, SwipeMenu swipeRightMenu, int viewType) {
////            int width = getResources().getDimensionPixelSize(R.dimen.dp_70);
//            int width =200;
//
//            // 1. MATCH_PARENT 自适应高度，保持和Item一样高;
//            // 2. 指定具体的高，比如80;
//            // 3. WRAP_CONTENT，自身高度，不推荐;
//            int height = ViewGroup.LayoutParams.MATCH_PARENT;
//            {
//                SwipeMenuItem deleteItem = new SwipeMenuItem(UserAdminActivity.this)
//                        .setBackground(R.color.color_f41708)
//                        .setImage(R.mipmap.myinfo_del)
//                        .setText("删除")
//                        .setTextColor(Color.WHITE)
//                        .setWidth(width)
//                        .setHeight(height);
//                // 添加菜单到右侧。
//                swipeRightMenu.addMenuItem(deleteItem);
//                SwipeMenuItem dItem1 = new SwipeMenuItem(UserAdminActivity.this)
//                        .setBackground(R.color.color_f41708)
//                        .setImage(R.mipmap.myinfo_del)
//                        .setText("怎么样")
//                        .setTextColor(Color.WHITE)
//                        .setWidth(width)
//                        .setHeight(height);
//                swipeRightMenu.addMenuItem(dItem1);
//            }
//        }
//    };
//    /**
//     * RecyclerView的Item的Menu点击监听。
//     */
//    private SwipeMenuItemClickListener mMenuItemClickListener = new SwipeMenuItemClickListener() {
//        @Override
//        public void onItemClick(SwipeMenuBridge menuBridge) {
//            menuBridge.closeMenu();
//            // 左侧还是右侧菜单。
//            int direction = menuBridge.getDirection();
//            // RecyclerView的Item的position。
//            position = menuBridge.getAdapterPosition();
//            //menuPosition 菜单栏里的position
//            menuPosition=menuBridge.getPosition();
//            if (direction == SwipeMenuRecyclerView.RIGHT_DIRECTION) {
//                switch (menuPosition ){
//                    case 0:
////                        list.remove(position);
////                        adapter.notifyDataSetChanged();
//                        SetToast("点击了删除");
//                        break;
//                    case 1:
//                        SetToast("看看这个是什么");
//                        break;
//                }
//
//            }
//        }
//    };

}
