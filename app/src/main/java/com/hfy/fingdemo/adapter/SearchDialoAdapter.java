package com.hfy.fingdemo.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hfy.fingdemo.R;

import java.util.List;

/**
 * 搜索得到的的内容
 */
public class SearchDialoAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    public SearchDialoAdapter(@Nullable List<String> data) {
        super(R.layout.item_dialog_bm, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.name, item);
    }
}
