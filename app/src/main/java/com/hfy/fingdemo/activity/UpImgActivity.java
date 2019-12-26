package com.hfy.fingdemo.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hfy.fingdemo.BuildConfig;
import com.hfy.fingdemo.R;
import com.hfy.fingdemo.adapter.PicAdapter;
import com.hfy.fingdemo.base.BaseActivity;
import com.hfy.fingdemo.bean.ImagesBean;
import com.hfy.fingdemo.util.ClassFileSizeUtil;
import com.hfy.fingdemo.util.MGlideEngine;
import com.jakewharton.rxbinding2.view.RxView;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.internal.entity.CaptureStrategy;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.functions.Consumer;
import krt.wid.http.Result;
import krt.wid.inter.UploadCallBack;
import krt.wid.util.MUpload;
import krt.wid.util.ParseJsonUtil;

/**
 * 图片上传，新选择的图片不会覆盖之前选择的
 * @author Administrator
 */
public class UpImgActivity extends BaseActivity {
    @BindView(R.id.recycler)
    RecyclerView recycler;
    @BindView(R.id.bt_zp)
    TextView btZp;
    @BindView(R.id.ll_tp)
    LinearLayout llTp;
    @BindView(R.id.bt)
    Button bt;
    @BindView(R.id.recycler_http)
    RecyclerView recycler_http;

    private RxPermissions rxPermissions;
    private PicAdapter mAdapter;
    private int howMany = 3;//选择照片的数量，数值随操作而改变
    private ArrayList<String> uploadImgs = new ArrayList<>();
    private ArrayList<String> upPathImgs = new ArrayList<>();
    private List<ImagesBean> ImagesList;

    private HttpAdapter httpAdapter;

    @Override
    public void beforeBindLayout() {

    }

    @Override
    public int bindLayout() {
        return R.layout.activity_up_img;
    }

    @Override
    public void initView() {
        recycler_http.setLayoutManager(new LinearLayoutManager(this));
        httpAdapter = new HttpAdapter(null);
        recycler_http.setAdapter(httpAdapter);

        initAddimg();
        mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                //删除操作
                mAdapter.remove(position);
                if (mAdapter.getData().size() == 0) {
                    howMany = 3;
                    btZp.setVisibility(View.VISIBLE);
                } else if (mAdapter.getData().size() == 1) {
                    howMany = 2;
                    btZp.setVisibility(View.VISIBLE);
                } else if (mAdapter.getData().size() == 2) {
                    howMany = 1;
                    btZp.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 105) {
                //选择图片
                mAdapter.addData(Matisse.obtainPathResult(data));
                if (mAdapter.getData().size() == 1) {
                    howMany = 2;
                    btZp.setVisibility(View.VISIBLE);
                } else if (mAdapter.getData().size() == 2) {
                    howMany = 1;
                    btZp.setVisibility(View.VISIBLE);
                } else if (mAdapter.getData().size() == 3) {
                    btZp.setVisibility(View.GONE);
                }
            }
        }
    }

    private void initAddimg() {
        LinearLayoutManager lm = new LinearLayoutManager(UpImgActivity.this);
        lm.setOrientation(LinearLayoutManager.HORIZONTAL);
        recycler.setLayoutManager(lm);
        mAdapter = new PicAdapter(null);
        recycler.setAdapter(mAdapter);
        rxPermissions = new RxPermissions(UpImgActivity.this);
        RxView.clicks(btZp)
                .compose(rxPermissions.ensure(Manifest.permission.READ_EXTERNAL_STORAGE))
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        if (aBoolean) {
                            Matisse.from(UpImgActivity.this)
                                    .choose(MimeType.of(MimeType.JPEG, MimeType.PNG, MimeType.BMP, MimeType.WEBP))
                                    .countable(true)
                                    .capture(true)
                                    .isCrop(false)
                                    .captureStrategy(
                                            new CaptureStrategy(true, BuildConfig.APPLICATION_ID + ".fileprovider"))
                                    .maxSelectable(howMany)
                                    .gridExpectedSize(
                                            getResources().getDimensionPixelSize(R.dimen.grid_expected_size))
                                    .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
                                    .imageEngine(new MGlideEngine())
                                    .forResult(105);
                        } else {
                            Toast.makeText(UpImgActivity.this, "您没有授权该权限，请在设置中打开授权!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }

    @Override
    public void loadData() {

    }


    @OnClick({R.id.ll_tp, R.id.bt})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_tp:
                break;
            case R.id.bt:
                List<String> paths = mAdapter.getData();
                if (paths.size() > 0) {
                    uploadImgs.clear();
                    upPathImgs.clear();
                    for (final String path : paths) {
                        MUpload.<Result<String>>newBuilder(this)
                                .addFile("file", Arrays.asList(path))
                                .addParam("dir", "all")
                                .setUrl("http://223.84.197.211:28082/zg-pc/api/" + "upload/fileUpload")
                                //是否压缩
                                .setNeedCompress(true)
                                .execute(new UploadCallBack<Result<String>>() {
                                    @Override
                                    public void onSuccess(Result<String> data) {
                                        if (data.isSuccess()) {
                                            finishUpload(data.data, path);
                                        }
                                    }
                                });
                    }
                } else {
                    Toast.makeText(UpImgActivity.this, "请添加图片", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
        }
    }


    private void finishUpload(String url, String pathImgs) {
        uploadImgs.add(url);
        upPathImgs.add(pathImgs);
        if (uploadImgs.size() != mAdapter.getData().size()) {
            return;
        }
        ImagesList = new ArrayList<>();
        for (int size = 0; size < uploadImgs.size(); size++) {
            //上传后得到的文件URL
            String ShowName = uploadImgs.get(size).toString();
            //上传之前，本地的path
            String ShowPathName = upPathImgs.get(size).toString();
            File pathFile = new File(ShowPathName);
            //文件大小
            long fileLen = 0;
            try {
                fileLen = ClassFileSizeUtil.getFileSize(pathFile);
            } catch (Exception e) {
                e.printStackTrace();
            }
            ImagesBean bean = new ImagesBean();
            bean.setShowName(ClassFileSizeUtil.getFileName_lastIndexOf(ShowPathName));
            bean.setFileLen(fileLen + "");
            bean.setType("image");
            bean.setUrl(ShowName);
            ImagesList.add(bean);
        }

        Log.w("hfydemo", "图片上传得到的：" + ParseJsonUtil.toJson(ImagesList));
        httpAdapter.setNewData(ImagesList);

    }

    private class HttpAdapter extends BaseQuickAdapter<ImagesBean, BaseViewHolder> {
        public HttpAdapter(@Nullable List<ImagesBean> data) {
            super(R.layout.item_file, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, ImagesBean item) {
            helper.setText(R.id.tv_fileLen, item.getFileLen());
            helper.setText(R.id.tv_showName, item.getShowName());
            helper.setText(R.id.tv_type, item.getType());
            helper.setText(R.id.tv_url, item.getUrl());
        }
    }
}
