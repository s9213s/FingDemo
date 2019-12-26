package com.hfy.fingdemo.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hfy.fingdemo.R;

import java.util.List;

public class PicAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    public PicAdapter(@Nullable List<String> data) {
        super(R.layout.item_addlog_chid_img, data);
    }

    @Override
    protected void convert(final BaseViewHolder helper, String item) {
        Glide.with(mContext).load(item).into((ImageView) helper.getView(R.id.img));
        helper.addOnClickListener(R.id.delete);
    }
}