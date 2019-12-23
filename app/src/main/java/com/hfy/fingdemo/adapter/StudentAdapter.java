package com.hfy.fingdemo.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hfy.fingdemo.R;
import com.hfy.fingdemo.bean.Student;

import java.util.List;

public class StudentAdapter extends BaseQuickAdapter<Student, BaseViewHolder> {
    public StudentAdapter(@Nullable List<Student> data) {
        super(R.layout.item_user_admin, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Student item) {
        helper.setText(R.id.tv_number,item.getId()+"");
        helper.setText(R.id.tv_userId,item.getStudentNo()+"");
        helper.setText(R.id.tv_userPassword,item.getName());
    }
}
