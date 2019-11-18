package com.hfy.fingdemo.util;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.blankj.utilcode.util.AppUtils;
import com.hfy.fingdemo.R;
import com.hfy.fingdemo.activity.FileDisplay2Activity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import krt.wid.util.MToast;

/**
 * Created by Administrator on 2018/12/5.
 */

public class DownloadUtil {
    private ProgressDialog mProgressDialog;
    // 下载失败
    public static final int DOWNLOAD_ERROR = 2;
    // 下载成功
    public static final int DOWNLOAD_SUCCESS = 1;
    private File file1;
    private Context context;

    public DownloadUtil(Context context) {
        this.context = context;
    }

    /**
     * 创建文件夹
     *
     * @param filePath 文件夹路径
     */
    public static void makeRootDirectory(String filePath) {
        File file = null;
        String[] dirs = filePath.split("/");
        String mdpath = Environment.getExternalStorageDirectory() + "/";
        for (int i = 0; i < dirs.length; i++) {
            mdpath = mdpath + dirs[i] + "/";
            Log.w("path", mdpath);
            file = new File(mdpath);
            if (!file.exists()) {
                file.mkdir();
            }
        }
    }

    public void setDialog(final String name, final String path) {
        //文件下载路径默认设置为：App同名文件/DownLoad  下；
        makeRootDirectory(AppUtils.getAppName() + "/DownLoad");
        file1 = new File(Environment.getExternalStorageDirectory() + "/"+AppUtils.getAppName()+"/DownLoad", getFileName(name));
        File haha = new File(file1.getAbsolutePath());
        //判断是否有此文件
        if (haha.exists()) {
            //有缓存文件,拿到路径 直接打开
            Message msg = Message.obtain();
            msg.obj = haha;
            msg.what = DOWNLOAD_SUCCESS;
            handler.sendMessage(msg);
//            mProgressDialog.dismiss();
            return;
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setIcon(R.mipmap.p_jg);
            builder.setTitle("下载提示")
                    .setMessage("是否下载：" + name + " 文件？");
            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    getdate(name, path);
                }
            });
            builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });
            builder.show();
        }
    }

    /**
     * 文件下载
     */
    public void getdate(String name, final String path) {

        mProgressDialog = new ProgressDialog(context);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();
        //文件存储
        file1 = new File(Environment.getExternalStorageDirectory() + "/" + AppUtils.getAppName() + "/DownLoad", getFileName(name));
        new Thread() {
            public void run() {

                File haha = new File(file1.getAbsolutePath());
                //判断是否有此文件
                if (haha.exists()) {
                    //有缓存文件,拿到路径 直接打开
                    Message msg = Message.obtain();
                    msg.obj = haha;
                    msg.what = DOWNLOAD_SUCCESS;
                    handler.sendMessage(msg);
                    mProgressDialog.dismiss();
                    return;
                }
                //    本地没有此文件 则从网上下载打开
                File downloadfile = downLoad(path, file1.getAbsolutePath(), mProgressDialog);
                Message msg = Message.obtain();
                if (downloadfile != null) {
                    // 下载成功,安装....
                    msg.obj = downloadfile;
                    msg.what = DOWNLOAD_SUCCESS;
                } else {
                    // 提示用户下载失败.
                    msg.what = DOWNLOAD_ERROR;
                }
                handler.sendMessage(msg);
                mProgressDialog.dismiss();
            }
        }.start();
    }


    /**
     * 下载完成后 直接打开文件
     */
    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case DOWNLOAD_SUCCESS:
                    File file = (File) msg.obj;
//
//                    String path = file.getPath();
//                    String name = path.substring(path.length() - 3, path.length());
//                    if (name.equals("rar") || name.equals("zip")) {
                        MToast.showToast(context, "该，文件存储在 "+AppUtils.getAppName()+"/DownLoad 文件夹");
//                    } else {
//                        FileDisplay2Activity.show(context,
//                                file.getPath());
//                    }


//                    Intent.ACTION_VIEW
//                    Intent intent = new Intent("android.intent.action.VIEW");
//                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//                        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//                        Uri contentUri = FileProvider.getUriForFile(context, BuildConfig.APPLICATION_ID + ".fileprovider", file);
//                        intent.setDataAndType(contentUri, "application/vnd.ms-powerpoint");
//                    } else {
//                        intent.setDataAndType(Uri.fromFile(file), "application/vnd.ms-powerpoint");
//                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                    }
//                    if (context.getPackageManager().queryIntentActivities(intent, 0).size() > 0) {
//                        context.startActivity(intent);
//                    }
                    /**
                     * 弹出选择框 把本activity销毁
                     */
                    break;
                case DOWNLOAD_ERROR:
                    MToast.showToast(context, "文件下载失败");
                    break;
            }
        }
    };


    /**
     * 传入文件 url 文件路径 和 弹出的dialog 进行 下载文档
     */
    public static File downLoad(String serverpath, String savedfilepath, ProgressDialog pd) {
        try {
            URL url = new URL(serverpath);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(5000);
            if (conn.getResponseCode() == 200) {
                int max = conn.getContentLength();
                pd.setMax(max);
                InputStream is = conn.getInputStream();
                File file = new File(savedfilepath);
                FileOutputStream fos = new FileOutputStream(file);
                int len = 0;
                byte[] buffer = new byte[1024];
                int total = 0;
                while ((len = is.read(buffer)) != -1) {
                    fos.write(buffer, 0, len);
                    total += len;
                    pd.setProgress(total);
                }
                fos.flush();
                fos.close();
                is.close();
                return file;
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    public static String getFileName(String serverurl) {
        return serverurl.substring(serverurl.lastIndexOf("/") + 1);
    }

    //截取文件名
    public static String getFileName_lastIndexOf(String pathandname) {

        int start = pathandname.lastIndexOf("/");
        int end = pathandname.lastIndexOf(".");
        if (start != -1 && end != -1) {
            return pathandname.substring(start + 1, pathandname.length());
        } else {
            return null;
        }

    }
}
