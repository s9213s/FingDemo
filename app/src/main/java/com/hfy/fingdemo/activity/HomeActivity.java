package com.hfy.fingdemo.activity;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.blankj.utilcode.util.AppUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hfy.fingdemo.MainActivity;
import com.hfy.fingdemo.R;
import com.hfy.fingdemo.base.BaseActivity;
import com.hfy.fingdemo.bean.HomeBean;
import com.hfy.fingdemo.demo.bean.AudioMsg;
import com.hfy.fingdemo.demo.callback.DecodeOperateInterface;
import com.hfy.fingdemo.demo.service.AudioTaskCreator;
import com.hfy.fingdemo.demo.util.AudioEditUtil;
import com.hfy.fingdemo.demo.util.AudioEncodeUtil;
import com.hfy.fingdemo.demo.util.DecodeEngine;
import com.hfy.fingdemo.util.DownloadUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import krt.wid.util.MPermissions;
import krt.wid.util.MToast;

import static com.hfy.fingdemo.util.FingBaseUtil.fileIsExists;

public class HomeActivity extends BaseActivity {
    @BindView(R.id.recycler)
    RecyclerView recycler;

    private List<String> titles = new ArrayList<>();
    private List<Class> activitys = new ArrayList<>();
    private ASRAdapter asrAdapter;
    private List<HomeBean> mList;

    @Override
    public void beforeBindLayout() {

    }

    @Override
    public int bindLayout() {
        return R.layout.activity_home;
    }

    /**
     * 数据
     */
    private void addData() {
        titles.add("下载");//0
        titles.add("语音转文字");
        titles.add("集成包中语音转文字");
        titles.add("mp3转pcm");//3
        titles.add("pcm转wav");
        titles.add("单view操作");
        titles.add("登录页使用SQLite");//6
        titles.add("用户管理");
        titles.add("忘记密码/修改密码");
        titles.add("弹窗");//9
        titles.add("刻度尺");//10
        titles.add("在线播放音频");//11
        titles.add("搜索和多选");


        activitys.add(MainActivity.class);//0
        activitys.add(ASRActivity.class);
        activitys.add(MiniRecogActivity.class);
        activitys.add(MainActivity.class);//3
        activitys.add(MainActivity.class);
        activitys.add(ClickListenerActivity.class);
        activitys.add(LoginActivity.class);//6
        activitys.add(UserAdminActivity.class);
        activitys.add(RePasswordActivity.class);
        activitys.add(RePasswordActivity.class);//9
        activitys.add(HorizontalScrollViewActivity.class);//10
        activitys.add(PlayerActivity.class);//11
        activitys.add(SearchActivity.class);

    }

    @Override
    public void initView() {
        addData();
        recycler.setLayoutManager(new GridLayoutManager(this, 3));
        asrAdapter = new ASRAdapter(null);
        recycler.setAdapter(asrAdapter);

        mList = new ArrayList<>();
        for (int i = 0; i < titles.size(); i++) {
            HomeBean homeBean = new HomeBean();
            homeBean.setTitle(titles.get(i));
            homeBean.setmActivity(activitys.get(i));
            mList.add(homeBean);
        }

        asrAdapter.setNewData(mList);
        asrAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                switch (position) {
                    case 0:
                        final DownloadUtil downloadUtil = new DownloadUtil(HomeActivity.this);
                        String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
                        MPermissions.getInstance().request(HomeActivity.this, permissions, new MPermissions.PermissionListener() {
                            @Override
                            public void callBack(boolean value) {
                                if (value) {
                                    DownloadUtil downloadUtil1 = new DownloadUtil(HomeActivity.this);
                                    downloadUtil.setDialog("Mus.mp3",
                                            "http://sjyh.zhzgq.com:13908/time-bank-zgq/upload/media/20191108120254_410.mp3");
                                } else {
                                    MToast.showToast(HomeActivity.this, "您没有授权该权限，请在设置中打开授权!");
                                }
                            }
                        });
                        break;
                    case 1:
                        String[] permissions1 = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE
                                , Manifest.permission.READ_PHONE_STATE, Manifest.permission.RECORD_AUDIO};
                        MPermissions.getInstance().request(HomeActivity.this, permissions1, new MPermissions.PermissionListener() {
                            @Override
                            public void callBack(boolean value) {
                                if (value) {
                                    startActivity(new Intent(HomeActivity.this, ASRActivity.class));
                                } else {
                                    MToast.showToast(HomeActivity.this, "您没有授权该权限，请在设置中打开授权!");
                                }
                            }
                        });
                        break;
                    case 3:
                        Log.w("hfydemo", "---------- ");
                        DecodeEngine.getInstance().convertMusicFileToPcmFile(Environment.getExternalStorageDirectory() + "/" +
                                        AppUtils.getAppName() + "/DownLoad/MusPcm2M4A.m4a",
                                Environment.getExternalStorageDirectory() + "/" + AppUtils.getAppName() + "/DownLoad/MusPcm2M4A.pcm",
                                new DecodeOperateInterface() {
                                    @Override
                                    public void updateDecodeProgress(int decodeProgress) {
                                        String msg = String.format("解码文件：%s，进度：%d", "AAA喔喔", decodeProgress) + "%";
                                        Log.w("hfydemo", "---------- " + msg);
                                        EventBus.getDefault().post(new AudioMsg(AudioTaskCreator.ACTION_AUDIO_MIX, msg));
                                    }

                                    @Override
                                    public void decodeSuccess() {

                                    }

                                    @Override
                                    public void decodeFail() {

                                    }
                                }
                        );

                        break;
                    case 4:
                        Log.w("AudioEdit", "---------- ");
                        AudioEncodeUtil.convertPcm2Wav(Environment.getExternalStorageDirectory() + "/" + AppUtils.getAppName() +
                                        "/DownLoad/outfile.pcm",
                                Environment.getExternalStorageDirectory() + "/" + AppUtils.getAppName() + "/DownLoad/outfile.wav");
                        break;
                    case 9:
                        View heview = LayoutInflater.from(HomeActivity.this).inflate(R.layout.view_longclick_dialog, null);
                        Dialog dialog = new Dialog(HomeActivity.this);
                        dialog.setContentView(heview);
                        dialog.setCanceledOnTouchOutside(true);//屏幕
                        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                        dialog.setCancelable(true);//物理按键
                        dialog.show();
                        //放在show()之后，不然有些属性是没有效果的，比如height和width
                        Window dialogWindow = dialog.getWindow();
                        WindowManager m = getWindowManager();
                        Display d = m.getDefaultDisplay(); // 获取屏幕宽、高用
                        WindowManager.LayoutParams p = dialogWindow.getAttributes(); // 获取对话框当前的参数值
                        // 设置高度和宽度
//                        p.height = (int) (d.getHeight() * 0.4); // 高度设置为屏幕的0.6
                        p.width = (int) (d.getWidth() * 0.6); // 宽度设置为屏幕的0.65

//                        p.gravity = Gravity.CENTER;//设置位置

//                        p.alpha = 0.8f;//设置透明度
                        dialogWindow.setAttributes(p);
                        break;
                    default:
                        startActivity(new Intent(HomeActivity.this, mList.get(position).getmActivity()));
                }
            }
        });


    }


    @Override
    public void loadData() {

    }

    public class ASRAdapter extends BaseQuickAdapter<HomeBean, BaseViewHolder> {

        public ASRAdapter(@Nullable List<HomeBean> data) {
            super(R.layout.item_home, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, final HomeBean item) {
            helper.setText(R.id.title, item.getTitle());
        }
    }

    /**
     * 双击返回桌面
     */
    private long time = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if ((System.currentTimeMillis() - time > 1000)) {
                Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                time = System.currentTimeMillis();
            } else {
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                startActivity(intent);
            }
            return true;
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }

}
