package com.hfy.fingdemo.activity;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hfy.fingdemo.R;
import com.hfy.fingdemo.adapter.DataAdapter;
import com.hfy.fingdemo.adapter.SearchDialoAdapter;
import com.hfy.fingdemo.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import krt.wid.util.MTitle;
import krt.wid.util.ParseJsonUtil;

/**
 * 搜索以及多选
 *
 * @author Administrator
 */
public class SearchActivity extends BaseActivity {
    @BindView(R.id.title)
    MTitle title;
    @BindView(R.id.tv_search)
    SearchView tvSearch;
    @BindView(R.id.Check_all)
    TextView CheckAll;
    @BindView(R.id.Check_reverse)
    TextView CheckReverse;
    @BindView(R.id.del)
    TextView del;
    @BindView(R.id.listview)
    ListView listview;
    @BindView(R.id.number)
    TextView number;
    @BindView(R.id.bt)
    TextView bt;

    private List<CheckBean> mList;
    private int checkNumber = 0;
    private DataAdapter dataAdapter;
    private List<CheckBean> selectIds;//选择的用户
    private List<String> nameList;//记录搜索到的name
    private List<String> positionList;//记录搜索到的name对应的位置

    private String[] names = {"西瓜", "美人瓜", "甜瓜", "香瓜", "黄河蜜", "哈密瓜", "木瓜", "乳瓜", "草莓", "蓝莓", "黑莓", "桑葚", "覆盆子", "葡萄"
            , "青提", "红提", "水晶葡萄", "马奶子", "蜜橘", "砂糖橘", "金橘", "蜜柑", "甜橙", "脐橙", "西柚", "柚子", "葡萄柚", "柠檬"
            , "文旦", "莱姆", "油桃", "蟠桃", "水蜜桃", "黄桃", "李子", "樱桃", "杏", "梅子", "杨梅", "西梅", "乌梅"
            , "大枣", "沙枣", "海枣", "蜜枣"};

    @Override
    public void beforeBindLayout() {

    }

    @Override
    public int bindLayout() {
        return R.layout.activity_search;
    }

    @Override
    public void initView() {
        SearchUI();
    }

    /**
     * 搜索的UI
     */
    private void SearchUI() {
        tvSearch.setQueryHint("请输入您要搜索的联系人");
        //焦点
        tvSearch.clearFocus();
        tvSearch.setIconified(true);//设置searchView处于展开状态
        tvSearch.setIconifiedByDefault(false);//默认为true在框内，设置false则在框外
//        Search.onActionViewExpanded();// 当展开无输入内容的时候，没有关闭的图标
        tvSearch.setSubmitButtonEnabled(false);//右边显示提交按钮

        EditText editText = (EditText) tvSearch.findViewById(android.support.v7.appcompat.R.id.search_src_text);
        editText.setTextColor(getResources().getColor(R.color.white));
        editText.setTextSize(14);

        //editText 下划线
        View underline = tvSearch.findViewById(R.id.search_plate);
        underline.setBackgroundColor(ContextCompat.getColor(this, R.color.color_search));
        //不显示搜索默认图片
        ImageView searchViewIcon = (ImageView) tvSearch.findViewById(R.id.search_mag_icon);
        ViewGroup linearLayoutSearchView = (ViewGroup) searchViewIcon.getParent();
        linearLayoutSearchView.removeView(searchViewIcon);
        tvSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //输入完成点击提交
                nameList = new ArrayList<>();
                positionList = new ArrayList<>();
                //方法search2name 不仅会得到搜索的内容，还会得到 nameList 和 positionList
                List<CheckBean> myQuery = search2name(query, mList, nameList, positionList);
                if (myQuery.size() > 0) {
                    if (myQuery.size() == 1) {
                        listview.post(new Runnable() {
                            @Override
                            public void run() {
                                int po = Integer.parseInt(positionList.get(0));
                                listview.setSelection(po);
                            }
                        });

                    } else {
                        setSearchDialog(nameList, positionList);
                    }

                } else {
                    Toast.makeText(SearchActivity.this, "未搜索到相关内容", Toast.LENGTH_SHORT).show();
                }
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (!TextUtils.isEmpty(newText)) {
                    //正在输入中，搜索内容不为空的时候

                } else {
                    //正在输入中，搜索内容为空得时候

                }
                return false;
            }
        });
    }

    public void setSearchDialog(List<String> nameList, final List<String> positionList) {
        View heview = LayoutInflater.from(SearchActivity.this).inflate(R.layout.view_dialog_bm, null);
        final Dialog dialog = new Dialog(SearchActivity.this);
        dialog.setContentView(heview);
        dialog.setCanceledOnTouchOutside(true);//屏幕
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.setCancelable(true);//物理按键
        dialog.show();
        RecyclerView rv = (RecyclerView) heview.findViewById(R.id.recycler);
        TextView textView = (TextView) heview.findViewById(R.id.tv_title);
        textView.setText("请选择您搜索的内容");
        rv.setLayoutManager(new LinearLayoutManager(this));
        SearchDialoAdapter searchDialoAdapter = new SearchDialoAdapter(nameList);
        rv.setAdapter(searchDialoAdapter);
        searchDialoAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, final int position) {
                dialog.dismiss();
                //焦点
                tvSearch.clearFocus();

                //滚动到对应内容的item
                listview.post(new Runnable() {
                    @Override
                    public void run() {
                        int po = Integer.parseInt(positionList.get(position));
                        listview.setSelection(po);
                    }
                });

            }
        });

    }

    /**
     * 模糊查询
     *
     * @param name
     * @param list
     * @return
     */
    public List<CheckBean> search2name(String name, List<CheckBean> list, List<String> nameList, List<String> positionList) {

        List<CheckBean> results = new ArrayList();
        Pattern pattern = Pattern.compile(name);
        for (int i = 0; i < list.size(); i++) {
            Matcher matcher = pattern.matcher(((CheckBean) list.get(i)).getName());
            //matcher.find()即可以进行模糊匹配
            //matcher.matches() 精确查询
            if (matcher.find()) {
                //核心代码
                results.add(list.get(i));
                //记录name
                nameList.add(list.get(i).getName());
                //记录位置
                positionList.add(i + "");
            }
        }
        return results;
    }

    @Override
    protected void onResume() {
        super.onResume();
        getHttp();

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                DataAdapter.ViewHolder viewHolder = (DataAdapter.ViewHolder) view.getTag();
                viewHolder.checkBox.toggle();
                // 将CheckBox的选中状况记录下来
                mList.get(position).setChe(viewHolder.checkBox.isChecked());
                // 调整选定条目
                if (viewHolder.checkBox.isChecked() == true) {
                    checkNumber++;
                    selectIds.add(mList.get(position));
                } else {
                    checkNumber--;
                    selectIds.remove(mList.get(position));
                }
                number.setText("已选择：" + checkNumber + "/" + mList.size());
            }
        });
    }

    private void getHttp() {
        checkNumber = 0;
        selectIds = new ArrayList<>();
        mList = new ArrayList<>();

        for (int i = 0; i < names.length; i++) {
            CheckBean checkBean = new CheckBean();
            checkBean.setName(names[i]);
            mList.add(checkBean);
        }
        dataAdapter = new DataAdapter(mList, this);
        listview.setAdapter(dataAdapter);
        number.setText("已选择：" + checkNumber + "/" + mList.size());


    }

    @Override
    public void loadData() {

    }


    @OnClick({R.id.Check_all, R.id.Check_reverse, R.id.del, R.id.bt})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.Check_all:
                checkNumber = 0;
                selectIds = new ArrayList<>();
                for (int i = 0; i < mList.size(); i++) {
                    // 改变boolean
                    mList.get(i).setChe(true);
                    // 如果为选中
                    if (mList.get(i).isChe()) {
                        checkNumber++;
                        selectIds.add(mList.get(i));
                    }
                }
                // 刷新
                dataAdapter.notifyDataSetChanged();
                // 显示
                number.setText("已选择：" + checkNumber + "/" + mList.size());
                break;
            case R.id.Check_reverse:
                //反选
                checkNumber = 0;
                selectIds = new ArrayList<>();
                for (int i = 0; i < mList.size(); i++) {
                    // 改值
                    if (mList.get(i).isChe()) {
                        mList.get(i).setChe(false);
                    } else {
                        mList.get(i).setChe(true);
                    }
                    // 刷新
                    dataAdapter.notifyDataSetChanged();
                    // 如果为选中
                    if (mList.get(i).isChe()) {
                        checkNumber++;
                        selectIds.add(mList.get(i));
                    }
                }

                number.setText("已选择：" + checkNumber + "/" + mList.size());
                break;
            case R.id.del:
                break;
            case R.id.bt:
                Log.w("hfydemo", "您选择了：" + selectIds.size() + "\n" + ParseJsonUtil.toJson(selectIds));
                break;
            default:
        }
    }

    public static class CheckBean {
        String name;
        boolean che;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public boolean isChe() {
            return che;
        }

        public void setChe(boolean che) {
            this.che = che;
        }
    }

}
