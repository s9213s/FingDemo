package com.hfy.fingdemo.activity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hfy.fingdemo.R;
import com.hfy.fingdemo.base.BaseActivity;
import com.hfy.fingdemo.view.DayScaleRulerView;
import com.hfy.fingdemo.view.ScaleRulerView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.hfy.fingdemo.view.ScaleRulerView.stampToDate;

public class TimeActivity extends BaseActivity {
    @BindView(R.id.content)
    TextView content;
    @BindView(R.id.tv_tc_time)
    TextView tvTcTime;
    @BindView(R.id.img_daysj)
    ImageView imgDaysj;
    @BindView(R.id.scaleRulerView)
    ScaleRulerView hourScaleRulerView;
    @BindView(R.id.RelativeLayout_time)
    RelativeLayout RelativeLayoutTime;
    @BindView(R.id.content_day)
    TextView contentDay;
    @BindView(R.id.tv_tc_daytime)
    TextView tvTcDaytime;
    @BindView(R.id.dayscaleRulerView)
    DayScaleRulerView dayscaleRulerView;
    
    private long day, hour, min, ss, allHour;
    private Date now;
    private Date startDate;//设置初始的日期
    private String yyyyMMddHH = "", yyyyMMdd = ""; //读取接口的日期

    @Override
    public void beforeBindLayout() {

    }

    @Override
    public int bindLayout() {
        return R.layout.activity_time;
    }

    @Override
    public void initView() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        startDate = null;
        try {
            //设置初始的日期
            startDate = df.parse("2018-01-01 00:00:00");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //现在的日期
        now = new Date();
        //求时间戳
        Long diff = now.getTime() - startDate.getTime();
        day = diff / (1000 * 60 * 60 * 24);          //以天数为单位取整
        hour = (diff / (60 * 60 * 1000) - day * 24);             //以小时为单位取整
        min = ((diff / (60 * 1000)) - day * 24 * 60 - hour * 60);    //以分钟为单位取整
        ss = ((diff / (1000)) - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);    //以秒为单位取整

        allHour = (diff / (60 * 60 * 1000));
        Long Long_hour;
        Long_hour = allHour * 1000 * 3600 + startDate.getTime();
        yyyyMMddHH = stampToDate(Long_hour, "yyyy-MM-dd HH");
        content.setText("小时数据："+stampToDate(Long_hour, "yyyy-MM-dd HH"));
        tvTcTime.setText(stampToDate(Long_hour, "yyyy/MM/dd HH") + "时");

        Long allDays;
        allDays = day * 1000 * 3600 * 24 + startDate.getTime();
        yyyyMMdd = stampToDate(allDays, "yyyy-MM-dd");
        contentDay.setText("日均数据："+stampToDate(allDays, "yyyy/MM/dd"));
        tvTcDaytime.setText(stampToDate(allDays, "yyyy/MM/dd"));

        //小时数据 刻度尺控件的设置
        setScaleRulerViewData(allHour, startDate);
        //日均数据 刻度尺控件的设置
        setDayScaleRulerViewData(day, startDate);


    }

    /**
     * 日均
     * @param maxValue
     * @param startDate
     */
    private void setDayScaleRulerViewData(float maxValue, final Date startDate) {
        //刻度尺参数设置：
        // setParam(间隔大小，最高高度，*中等高度，低等高度，*文本距离刻度线的高度，*文本字体大小)
        dayscaleRulerView.setParam((int) getResources().getDimension(R.dimen.dp13),
                (int) getResources().getDimension(R.dimen.dp32), (int) getResources().getDimension(R.dimen.dp24),
                (int) getResources().getDimension(R.dimen.dp14), (int) getResources().getDimension(R.dimen.dp9),
                (int) getResources().getDimension(R.dimen.dp12), startDate);

        //initViewParam （float初始指定的位置，float最小刻度，float最大刻度，int跨度值）
        dayscaleRulerView.initViewParam(maxValue, 0, maxValue, 1);
        dayscaleRulerView.setValueChangeListener(new DayScaleRulerView.OnValueChangeListener() {
            @Override
            public void onValueChange(float value) {
                //value为距离最开始日期 的一共多少天数
                // date.getTime()为最开始的日期
                //Long3 为选中日期的时间戳 = 最开始的日期的时间戳 + 天数的时间戳
                Long Long3;
                Long3 = (long) value * 1000 * 3600 * 24 + startDate.getTime();

                tvTcDaytime.setText(stampToDate(Long3, "yyyy/MM/dd"));
            }
        });
        dayscaleRulerView.setValueChangeListenerHttp(new DayScaleRulerView.OnValueChangeListenerHttp() {
            @Override
            public void onValueChangeHttp(float value) {
                Long Long3;
                Long3 = (long) value * 1000 * 3600 * 24 + startDate.getTime();
                yyyyMMdd = stampToDate(Long3, "yyyy-MM-dd");
//                getHttp(mStatus, dischargeCode, yyyyMMdd);
                contentDay.setText("选择得到的是："+yyyyMMdd);
            }
        });
    }

    @Override
    public void loadData() {

    }

    /**
     * 小时
     * @param maxValue
     * @param startDate
     */
    private void setScaleRulerViewData(float maxValue, final Date startDate) {
        //刻度尺参数设置：
        // setParam(间隔大小，最高高度，*中等高度，低等高度，*文本距离刻度线的高度，*文本字体大小，开始时间)
        hourScaleRulerView.setParam((int) getResources().getDimension(R.dimen.dp13),
                (int) getResources().getDimension(R.dimen.dp32), (int) getResources().getDimension(R.dimen.dp24),
                (int) getResources().getDimension(R.dimen.dp14), (int) getResources().getDimension(R.dimen.dp9),
                (int) getResources().getDimension(R.dimen.dp12), startDate);

        //initViewParam （float初始指定的位置，float最小刻度，float最大刻度，int跨度值）
        hourScaleRulerView.initViewParam(maxValue, 0, maxValue, 1);
        //滑动监听
        hourScaleRulerView.setValueChangeListener(new ScaleRulerView.OnValueChangeListener() {
            @Override
            public void onValueChange(float value) {
                //value为距离最开始日期 的一共多少个小时数
                // date.getTime()为最开始的日期
                //Long3 为选中日期的时间戳 = 最开始的日期的时间戳 + 小时数的时间戳
                Long Long3;
                Long3 = (long) value * 1000 * 3600 + startDate.getTime();
                tvTcTime.setText(stampToDate(Long3, "yyyy/MM/dd HH") + "时");

            }
        });
        //抬起监听
        hourScaleRulerView.setValueChangeListenerHttp(new ScaleRulerView.OnValueChangeListenerHttp() {
            @Override
            public void onValueChangeHttp(float value) {
                Long Long3;
                Long3 = (long) value * 1000 * 3600 + startDate.getTime();
                yyyyMMddHH = stampToDate(Long3, "yyyy-MM-dd HH");
//                getHttp(mStatus, dischargeCode, yyyyMMddHH);
                content.setText("选择得到的是："+yyyyMMddHH );
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
