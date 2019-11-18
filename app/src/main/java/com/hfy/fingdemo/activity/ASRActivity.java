package com.hfy.fingdemo.activity;

import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.baidu.aip.asrwakeup3.core.mini.AutoCheck;
import com.baidu.aip.asrwakeup3.core.recog.MyRecognizer;
import com.baidu.aip.asrwakeup3.core.recog.listener.IRecogListener;
import com.baidu.aip.asrwakeup3.core.recog.listener.MessageStatusRecogListener;
import com.baidu.speech.asr.SpeechConstant;
import com.blankj.utilcode.util.AppUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hfy.fingdemo.R;
import com.hfy.fingdemo.base.BaseActivity;
import com.hfy.fingdemo.bean.HomeBean;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import krt.wid.util.MToast;

import static com.hfy.fingdemo.util.FingBaseUtil.fileIsExists;

public class ASRActivity extends BaseActivity {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_all)
    TextView tvAll;
    @BindView(R.id.bt1)
    Button bt1;
    @BindView(R.id.bt2)
    Button bt2;
    @BindView(R.id.bt3)
    Button bt3;

    String TAG = "hfydemo";
    Handler handler;
    protected MyRecognizer myRecognizer;
    protected boolean enableOffline;

    @Override
    public void beforeBindLayout() {

    }

    @Override
    public int bindLayout() {
        return R.layout.acitivity_asr;
    }

    @Override
    public void initView() {
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                Log.e(TAG, msg.obj.toString());
                tvAll.setText(tvAll.getText().toString() + "\n" + msg.obj.toString() + "\n");
                String message=msg.obj.toString();
                Log.w("hfydemo","***-------"+message.substring(0,8));
                if (message.substring(0,8).equals("识别结束，结果是")){
                    tvTitle.setText(message.replace("识别结束，结果是",""));
                }
            }
        };
        IRecogListener listener = new MessageStatusRecogListener(handler);
        myRecognizer = new MyRecognizer(this, listener);


    }
    protected void start(String fileUrl) {
        // DEMO集成步骤2.1 拼接识别参数： 此处params可以打印出来，直接写到你的代码里去，最终的json一致即可。
        final Map<String, Object> params = new HashMap<>();
        params.put(SpeechConstant.IN_FILE, fileUrl);
        params.put(SpeechConstant.ACCEPT_AUDIO_VOLUME, false);
        // params 也可以根据文档此处手动修改，参数会以json的格式在界面和logcat日志中打印
        Log.i(TAG, "设置的start输入参数：" + params);
        // 复制此段可以自动检测常规错误
        (new AutoCheck(getApplicationContext(), new Handler() {
            public void handleMessage(Message msg) {
                if (msg.what == 100) {
                    AutoCheck autoCheck = (AutoCheck) msg.obj;
                    synchronized (autoCheck) {
                        String message = autoCheck.obtainErrorMessage(); // autoCheck.obtainAllMessage();
                        Log.w(TAG, message + "\n");
                        tvAll.setText("***********\n"+message + "\n");
                        ; // 可以用下面一行替代，在logcat中查看代码
                        // Log.w("AutoCheckMessage", message);
                    }
                }
            }
        }, enableOffline)).checkAsr(params);

        // 这里打印出params， 填写至您自己的app中，直接调用下面这行代码即可。
        // DEMO集成步骤2.2 开始识别
        myRecognizer.start(params);
    }

    protected void stop() {

        myRecognizer.stop();
    }

    protected void cancel() {

        myRecognizer.cancel();
    }
    @Override
    protected void onDestroy() {

        // 如果之前调用过myRecognizer.loadOfflineEngine()， release()里会自动调用释放离线资源
        // 基于DEMO5.1 卸载离线资源(离线时使用) release()方法中封装了卸载离线资源的过程
        // 基于DEMO的5.2 退出事件管理器
        myRecognizer.release();
        Log.i(TAG, "onDestory");
        super.onDestroy();
    }

    @Override
    public void loadData() {
    bt3.setOnLongClickListener(new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View view) {
            if (fileIsExists( Environment.getExternalStorageDirectory() + "/"+AppUtils.getAppName()+"/DownLoad/outfile.pcm")){
                start(Environment.getExternalStorageDirectory() + "/" + AppUtils.getAppName() + "/DownLoad/outfile.pcm");
            }else {
                MToast.showToast(ASRActivity.this,"文件不不不不不不不不不不不存在");
            }
            return false;
        }
    });
    }

    @OnClick({R.id.bt1, R.id.bt2})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt1:
                stop();
                cancel();
                break;
            case R.id.bt2:
                if (fileIsExists( Environment.getExternalStorageDirectory() + "/"+AppUtils.getAppName()+"/DownLoad/outfile.pcm")){
                    start(Environment.getExternalStorageDirectory() + "/" + AppUtils.getAppName() + "/DownLoad/outfile.pcm");
                }else {
                    MToast.showToast(ASRActivity.this,"文件不不不不不不不不不不不存在");
                }

                break;
            default:
        }
    }
}
