package com.hfy.fingdemo.activity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.zhouwei.library.CustomPopWindow;
import com.hfy.fingdemo.R;
import com.hfy.fingdemo.adapter.StudentAdapter;
import com.hfy.fingdemo.base.BaseActivity;
import com.hfy.fingdemo.bean.IdCard;
import com.hfy.fingdemo.bean.Student;
import com.hfy.fingdemo.greendao.DBManager;
import com.hfy.fingdemo.sql.UserOpenHelper;
import com.hfy.fingdemo.test.database.greenDao.db.DaoMaster;
import com.hfy.fingdemo.test.database.greenDao.db.DaoSession;
import com.hfy.fingdemo.test.database.greenDao.db.IdCardDao;
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
    SwipeMenuRecyclerView rvUser;

    private int position, menuPosition;
    private UserOpenHelper userOpenHelper;
    private SQLiteDatabase db;
    private  StudentAdapter studentAdapter;


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

    public List<IdCard> queryIdCard() {
        DaoMaster daoMaster = DBManager.getDaoMaster(this);
        DaoSession daoSession = daoMaster.newSession();
        IdCardDao idCardDao = daoSession.getIdCardDao();
        QueryBuilder<IdCard> qb = idCardDao.queryBuilder();
        List<IdCard> list = qb.list();
        return list;
    }

    /**
     * 查询用户
     * eq :  等于
     * notEq ： 不等于
     * like:模糊查询 记住模糊查询，string要用夹在%key%中间。
     */
    public Student queryUserModel(String studentNo) {
        DaoMaster daoMaster = DBManager.getDaoMaster(this);
        DaoSession daoSession = daoMaster.newSession();
        StudentDao userDao = daoSession.getStudentDao();
        QueryBuilder<Student> qb = userDao.queryBuilder();
        qb.where(StudentDao.Properties.StudentNo.like("%" + studentNo + "%")).build();
        List<Student> list = qb.list();
        if (list != null && list.size() > 0) {
            SetToast(ParseJsonUtil.toJson(list.get(0)));
            return list.get(0);
        } else {
            SetToast("无");
            return null;
        }
    }

    public Student queryUserMode_eq(String studentNo) {
        DaoMaster daoMaster = DBManager.getDaoMaster(this);
        DaoSession daoSession = daoMaster.newSession();
        StudentDao userDao = daoSession.getStudentDao();
        QueryBuilder<Student> qb = userDao.queryBuilder();
        qb.where(StudentDao.Properties.StudentNo.eq(studentNo)).build();
        List<Student> list = qb.list();
        if (list != null && list.size() > 0) {
            SetToast(ParseJsonUtil.toJson(list.get(0)));
            return list.get(0);
        } else {
            SetToast("无");
            return null;
        }
    }


    @Override
    public void initView() {
        rvUser.setSwipeMenuCreator(swipeMenuCreator);//菜单创建器 侧滑删除
        rvUser.setSwipeMenuItemClickListener(mMenuItemClickListener);//删除监听
        rvUser.setLayoutManager(new LinearLayoutManager(this));

        studentAdapter = new StudentAdapter(null);
        rvUser.setAdapter(studentAdapter);
        studentAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Log.w("hfydemo", ParseJsonUtil.toJson( studentAdapter.getData().get(position).getIdCard())+"*******d对应的IdCard*****"
                        + studentAdapter.getData().get(position).getIdCard()
                +"-------"+studentAdapter.getData().get(position).getName());
            }
        });

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
    protected void onResume() {
        super.onResume();
        Log.w("hfydemo", "*****list*****" + ParseJsonUtil.toJson(queryUserList()));
        Log.w("hfydemo", "*****queryIdCardlist*****" + ParseJsonUtil.toJson(queryIdCard()));
        studentAdapter.setNewData(queryUserList());

    }

    @Override
    public void loadData() {

    }

    @OnClick({R.id.add})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.add:
                showPopMenu();
                break;
            default:
        }
    }

    private CustomPopWindow mCustomPopWindow;

    private void showPopMenu() {
        View contentView = LayoutInflater.from(this).inflate(R.layout.view_diagle, null);
        //处理popWindow 显示内容
        TextView textView1 = contentView.findViewById(R.id.text1);
        TextView textView2 = contentView.findViewById(R.id.text2);
        TextView textView3 = contentView.findViewById(R.id.text3);

        textView1.setText("模糊查100");
        textView2.setText("精确查101");
        textView3.setText("添加数据");
        handleLogic(contentView);
        //创建并显示popWindow
        mCustomPopWindow = new CustomPopWindow.PopupWindowBuilder(this)
                .setView(contentView)
                .enableBackgroundDark(true) //弹出popWindow时，背景是否变暗
                .setBgDarkAlpha(0.9f) // 控制亮度
                .create()
                .showAsDropDown(add, 0, 0);
    }

    /**
     * 处理弹出显示内容、点击事件等逻辑
     *
     * @param contentView
     */
    private void handleLogic(View contentView) {

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCustomPopWindow != null) {
                    mCustomPopWindow.dissmiss();
                }
                switch (v.getId()) {
                    case R.id.text1:
                        queryUserModel("100");
                        break;
                    case R.id.text2:
                        queryUserMode_eq("101");
                        break;
                    case R.id.text3:
                        Intent intent = new Intent(UserAdminActivity.this, RePasswordActivity.class);
                        intent.putExtra("isRePassword", false);
                        startActivity(intent);
                        break;
                    default:
                }
            }
        };
        contentView.findViewById(R.id.text1).setOnClickListener(listener);
        contentView.findViewById(R.id.text2).setOnClickListener(listener);
        contentView.findViewById(R.id.text3).setOnClickListener(listener);
    }

    /**
     * 菜单创建器，在Item要创建菜单的时候调用。
     */
    private SwipeMenuCreator swipeMenuCreator = new SwipeMenuCreator() {
        @Override
        public void onCreateMenu(SwipeMenu swipeLeftMenu, SwipeMenu swipeRightMenu, int viewType) {
//            int width = getResources().getDimensionPixelSize(R.dimen.dp_70);
            int width =150;

            // 1. MATCH_PARENT 自适应高度，保持和Item一样高;
            // 2. 指定具体的高，比如80;
            // 3. WRAP_CONTENT，自身高度，不推荐;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            {
                SwipeMenuItem deleteItem = new SwipeMenuItem(UserAdminActivity.this)
                        .setBackground(R.color.color_f41708)
                        .setImage(R.mipmap.myinfo_del)
                        .setText("删除")
                        .setTextColor(Color.WHITE)
                        .setWidth(width)
                        .setHeight(height);
                // 添加菜单到右侧。
                swipeRightMenu.addMenuItem(deleteItem);
                SwipeMenuItem dItem1 = new SwipeMenuItem(UserAdminActivity.this)
                        .setBackground(R.color.color_f41708)
                        .setImage(R.mipmap.myinfo_del)
                        .setText("怎么样")
                        .setTextColor(Color.WHITE)
                        .setWidth(width)
                        .setHeight(height);
                swipeRightMenu.addMenuItem(dItem1);
            }
        }
    };
    /**
     * RecyclerView的Item的Menu点击监听。
     */
    private SwipeMenuItemClickListener mMenuItemClickListener = new SwipeMenuItemClickListener() {
        @Override
        public void onItemClick(SwipeMenuBridge menuBridge) {
            menuBridge.closeMenu();
            // 左侧还是右侧菜单。
            int direction = menuBridge.getDirection();
            // RecyclerView的Item的position。
            position = menuBridge.getAdapterPosition();
            //menuPosition 菜单栏里的position
            menuPosition=menuBridge.getPosition();
            if (direction == SwipeMenuRecyclerView.RIGHT_DIRECTION) {
                switch (menuPosition ){
                    case 0:
//                        list.remove(position);
//                        adapter.notifyDataSetChanged();

                        Student student=new Student();
                        student.setId(studentAdapter.getData().get(position).getId());
                        student.setStudentNo(studentAdapter.getData().get(position).getStudentNo());
                        student.setName(studentAdapter.getData().get(position).getName());
                        deleteUser(student);
                        studentAdapter.setNewData(queryUserList());
                        studentAdapter.notifyDataSetChanged();
                        break;
                    case 1:
                        Student student2=new Student();
                        student2.setId(studentAdapter.getData().get(position).getId());
                        deleteUser(student2);
                        studentAdapter.setNewData(queryUserList());
                        studentAdapter.notifyDataSetChanged();
                        break;
                }

            }
        }
    };
    /**
     * 删除一条记录
     *
     * @param user
     */
    public void deleteUser(Student user) {
        DaoMaster.DevOpenHelper mDevOpenHelper = new DaoMaster.DevOpenHelper(this, "test.db");
        DaoMaster mDaoMaster = new DaoMaster(mDevOpenHelper.getWritableDb());
        StudentDao userDao = mDaoMaster.newSession().getStudentDao();
        userDao.delete(user);
        SetToast("删除成功");
    }
    //修改
    public void updateUser(Student user) {
        DaoMaster.DevOpenHelper mDevOpenHelper = new DaoMaster.DevOpenHelper(this, "test.db");
        DaoMaster mDaoMaster = new DaoMaster(mDevOpenHelper.getWritableDb());
        StudentDao userDao = mDaoMaster.newSession().getStudentDao();
        userDao.update(user);
    }

}
