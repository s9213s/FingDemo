package com.hfy.fingdemo.base;

import android.view.View;

import com.hfy.fingdemo.util.SpUtil;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import krt.wid.base.MBaseFragment;

/**
 * author:Marcus
 * create on:2019/5/8 9:23
 * description
 */
public abstract class BaseFragment extends MBaseFragment {
    private Unbinder unbinder;
    public SpUtil spUtil;

    @Override
    public void init() {
        spUtil = ((BaseActivity) mContext).spUtil;
    }

    @Override
    public void bindButterKnife(View view) {
        unbinder = ButterKnife.bind(this, view);
    }

    @Override
    public void unBindButterKnife() {
        unbinder.unbind();
    }
}
