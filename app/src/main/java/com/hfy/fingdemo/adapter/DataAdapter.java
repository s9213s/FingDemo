package com.hfy.fingdemo.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;


import com.hfy.fingdemo.R;
import com.hfy.fingdemo.activity.SearchActivity.CheckBean;

import java.util.List;

public class DataAdapter extends BaseAdapter {
    List<CheckBean> mList;
    Context mContext;

    public DataAdapter(List<CheckBean> mList, Context mContext) {
        this.mList = mList;
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        final ViewHolder viewHolder;
        if (view == null) {
            view = View.inflate(mContext, R.layout.item_checkbox_user, null);
            viewHolder = new ViewHolder();
            viewHolder.name = (TextView) view.findViewById(R.id.name);
            viewHolder.orgName = (TextView) view.findViewById(R.id.orgName);
            viewHolder.checkBox = (CheckBox) view.findViewById(R.id.checkbox);
            viewHolder.icon = (ImageView) view.findViewById(R.id.icon);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.name.setText(mList.get(position).getName());

        viewHolder.checkBox.setChecked(mList.get(position).isChe());

        return view;
    }
    public class ViewHolder {
        public TextView name;
        public TextView orgName;
        public CheckBox checkBox;
        public ImageView icon;
    }
}
