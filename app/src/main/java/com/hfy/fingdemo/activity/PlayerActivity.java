package com.hfy.fingdemo.activity;

import android.app.Activity;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;

import com.hfy.fingdemo.R;
import com.hfy.fingdemo.util.PlayerUtil;

import java.io.IOException;

public class PlayerActivity extends Activity {

    private SeekBar mSeekBar;
    private String path = "http://223.84.197.211:28082/zg-pc/file/media/20191217/9741b9465af046e3a6a9c7df3ae65e8c.mp3";
    private PlayerUtil mPlayerUtil;
    private EditText mEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);

        mSeekBar = (SeekBar) findViewById(R.id.seekBar);
        mEditText = (EditText) findViewById(R.id.editText);
        Button bt = (Button) findViewById(R.id.bt);
        mEditText.setText(path);

        MediaPlayer mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.setDataSource(path);
            mediaPlayer.prepare();
            int duration = mediaPlayer.getDuration();
            if (0 != duration) {
                //更新 seekbar 长度
//                seekbar.setMax(duration);
                int s = duration / 1000;
                //设置文件时长，单位 "分:秒" 格式
                String total = s / 60 + ":" + s % 60;
                //记得释放资源
                mediaPlayer.release();
                bt.setText(total);


                long ms = duration;
                Integer ss = 1000;
                Integer mi = ss * 60;
                Integer hh = mi * 60;
                Integer dd = hh * 24;

                Long day = ms / dd;
                Long hour = (ms - day * dd) / hh;
                Long minute = (ms - day * dd - hour * hh) / mi;
                Long second = (ms - day * dd - hour * hh - minute * mi) / ss;
                Long milliSecond = ms - day * dd - hour * hh - minute * mi - second * ss;

                StringBuffer sb = new StringBuffer();
                if (day > 0) {
                    sb.append(day + "天");
                }
                if (hour > 0) {
                    sb.append(hour + "小时");
                }
                if (minute > 0) {
                    sb.append(minute + "分");
                }
                if (second > 0) {
                    sb.append(second + "秒");
                }


                Log.w("hfydemo", "********时长duration：" + duration + "-------  " + sb.toString());

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        mPlayerUtil = new PlayerUtil();
    }

    /**
     * 播放
     *
     * @param view
     */
    public void play(View view) {
        new Thread(new Runnable() {

            @Override
            public void run() {
                mPlayerUtil.playUrl(mEditText.getText().toString().trim());
            }
        }).start();
    }

    public static String getMP3Size(String filePath) {
        MediaMetadataRetriever mmr = new MediaMetadataRetriever();
        mmr.setDataSource(filePath);
        String duration = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION); // 播放时长单位为毫秒
        long ms = Long.parseLong(duration);
        Integer ss = 1000;
        Integer mi = ss * 60;
        Integer hh = mi * 60;
        Integer dd = hh * 24;

        Long day = ms / dd;
        Long hour = (ms - day * dd) / hh;
        Long minute = (ms - day * dd - hour * hh) / mi;
        Long second = (ms - day * dd - hour * hh - minute * mi) / ss;
        Long milliSecond = ms - day * dd - hour * hh - minute * mi - second * ss;

        StringBuffer sb = new StringBuffer();
        if (day > 0) {
            sb.append(day + "天");
        }
        if (hour > 0) {
            sb.append(hour + "小时");
        }
        if (minute > 0) {
            sb.append(minute + "分");
        }
        if (second > 0) {
            sb.append(second + "秒");
        }
//        if(milliSecond > 0) {
//            sb.append(milliSecond+"毫秒");
//        }
        return sb.toString();
    }

    private static String getOnlineMusicSize(String url) {
        MediaPlayer mediaPlayer = new MediaPlayer();
        StringBuffer sb = null;
        try {
            mediaPlayer.setDataSource(url);
            mediaPlayer.prepare();
            int duration = mediaPlayer.getDuration();
            if (0 != duration) {
                long ms = duration;
                Integer ss = 1000;
                Integer mi = ss * 60;
                Integer hh = mi * 60;
                Integer dd = hh * 24;
                Long day = ms / dd;
                Long hour = (ms - day * dd) / hh;
                Long minute = (ms - day * dd - hour * hh) / mi;
                Long second = (ms - day * dd - hour * hh - minute * mi) / ss;
                sb = new StringBuffer();
                if (day > 0) {
                    sb.append(day + "天");
                }
                if (hour > 0) {
                    sb.append(hour + "小时");
                }
                if (minute > 0) {
                    sb.append(minute + "分");
                }
                if (second > 0) {
                    sb.append(second + "秒");
                }
                //记得释放资源
                mediaPlayer.release();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }
}