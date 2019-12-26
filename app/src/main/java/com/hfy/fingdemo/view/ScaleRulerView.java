package com.hfy.fingdemo.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;


import com.hfy.fingdemo.R;

import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * 时间刻度尺，类似QQ聊天记录查询
 */
public class ScaleRulerView extends View {

    private int mMinVelocity;
    private VelocityTracker mVelocityTracker;
    private int mWidth;
    private int mHeight;

    private float mValue = 50;//float初始指定的位置，开始光标指示的位置
    private float mMaxValue = 100;//float最大刻度
    private float mMinValue = 0;//float最小刻度
    private int mItemSpacing;//item 每个格子的间距
    private int mPerSpanValue = 1;//跨度值
    private int mMaxLineHeight;//最大高度
    private int mMiddleLineHeight;//中等高度
    private int mMinLineHeight;//最低高度
    private int mLineWidth;//画笔宽度
    private int mTextMarginTop;//文本距离刻度线的高度
    private float mDensity;

    private Paint mTextPaint; // 绘制文本的画笔
    private Paint mLinePaint;
    private Paint mTextPaint2;

    private int mTotalLine;//总长
    private int mMaxOffset;
    private float mOffset; // 默认尺起始点在屏幕中心, offset是指尺起始点的偏移值
    private int mLastX, mMove;
    private OnValueChangeListener mListener;
    private OnValueChangeListenerHttp mListenerHttp;

    private Date mStartDate;//最开始的时间


    public ScaleRulerView(Context context) {
        this(context, null);
    }

    public ScaleRulerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);

    }

    public ScaleRulerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public static float getFontHeight(Paint paint) {
        Paint.FontMetrics fm = paint.getFontMetrics();
        return fm.descent - fm.ascent;
    }

    protected void init(Context context) {
        mMinVelocity = ViewConfiguration.get(getContext()).getScaledMinimumFlingVelocity();
        mLineWidth = (int) getResources().getDimension(R.dimen.dp2);
        mMaxLineHeight = (int) getResources().getDimension(R.dimen.dp42);
        mMiddleLineHeight = (int) getResources().getDimension(R.dimen.dp31);
        mMinLineHeight = (int) getResources().getDimension(R.dimen.dp17);
        mTextMarginTop = (int) getResources().getDimension(R.dimen.dp11);

        mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setTextSize((int) getResources().getDimension(R.dimen.dp16));
        mTextPaint.setColor(ContextCompat.getColor(getContext(), R.color.color_textColor));
        mDensity = context.getResources().getDisplayMetrics().density;

        mTextPaint2 = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint2.setTextSize((int) getResources().getDimension(R.dimen.dp16));
        mTextPaint2.setColor(ContextCompat.getColor(getContext(), R.color.color_textColor));


        mLinePaint = new Paint();
        mLinePaint.setStrokeWidth(mLineWidth);
        mLinePaint.setColor(ContextCompat.getColor(getContext(), R.color.color_999999));
    }

    public void setParam(int itemSpacing, int maxLineHeight, int middleLineHeight, int minLineHeight, int textMarginTop, int textSize, Date startDate) {
        mItemSpacing = itemSpacing;//item 每个格子的间距
        mMaxLineHeight = maxLineHeight;//最大高度
        mMiddleLineHeight = middleLineHeight;//中等高度
        mMinLineHeight = minLineHeight;//最低高度
        mTextMarginTop = textMarginTop;//文本距离刻度线的高度
        mTextPaint.setTextSize(textSize);
        mTextPaint2.setTextSize(textSize);
        mStartDate = startDate;
    }

    //initViewParam （float初始指定的位置，float最小刻度，float最大刻度，int跨度值）
    public void initViewParam(float defaultValue, float minValue, float maxValue, int spanValue) {
        this.mValue = defaultValue;
        this.mMaxValue = maxValue;
        this.mMinValue = minValue;
        this.mPerSpanValue = spanValue;
        this.mTotalLine = (int) (maxValue - minValue) / spanValue + 1;
        mMaxOffset = -(mTotalLine - 1) * mItemSpacing;

        mOffset = (minValue - defaultValue) / spanValue * mItemSpacing * 1;
        invalidate();
        setVisibility(VISIBLE);
    }

    /**
     * 设置用于接收结果的监听器
     *
     * @param listener
     */
    public void setValueChangeListener(OnValueChangeListener listener) {
        mListener = listener;
    }

    public void setValueChangeListenerHttp(OnValueChangeListenerHttp listener) {
        mListenerHttp = listener;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (w > 0 && h > 0) {
            mWidth = w;
            mHeight = h;
        }
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        float left, height;
        String value;
        int srcPointX = mWidth / 2; // 默认表尺起始点在屏幕中心
        for (int i = 0; i < mTotalLine; i++) {
            left = srcPointX + mOffset + i * mItemSpacing;

            if (left < 0 || left > mWidth) {
                continue;
            }
            if (i % 24 == 0) {
                height = mMiddleLineHeight;
            } else {
                height = mMinLineHeight;
            }

            canvas.drawLine(left, getHeight() - (int) getResources().getDimension(R.dimen.dp8), left,
                    getHeight() - (int) getResources().getDimension(R.dimen.dp8) - height, mLinePaint);

            if (i % 24 == 0) { // 大指标,要标注文字
                value = String.valueOf((int) (mMinValue + i * mPerSpanValue / 1));
                Long Long3;
                Long3 = Long.valueOf(value) * 1000 * 3600 + mStartDate.getTime();

                canvas.drawText(stampToDate(Long3, "MM/dd"),
                        left - mTextPaint.measureText(value) / 2 ,
                        (int) getResources().getDimension(R.dimen.dp16), mTextPaint);

                canvas.drawText(stampToDate(Long3, "00时"),
//                        left - mTextPaint.measureText(value) / 2 - (int) getResources().getDimension(R.dimen.dp7),
                        left - mTextPaint.measureText(value) / 2 +(int) getResources().getDimension(R.dimen.dp3),
                        (int) getResources().getDimension(R.dimen.dp32), mTextPaint);

            }
        }
    }

    /**
     * 将时间戳转换为时间
     *
     * @param stap
     * @param style 日期格式
     * @return
     */
    public static String stampToDate(long stap, String style) {
        String time;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(style);
        long lt = stap;
        Date date = new Date(lt);
        time = simpleDateFormat.format(date);
        return time;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        int xPosition = (int) event.getX();

        if (mVelocityTracker == null) {
            mVelocityTracker = VelocityTracker.obtain();
        }
        mVelocityTracker.addMovement(event);

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                mLastX = xPosition;
                mMove = 0;
                break;
            case MotionEvent.ACTION_MOVE:
                mMove = (mLastX - xPosition);
                changeMoveAndValue();
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                countMoveEnd();
                countVelocityTracker();
                return false;
            // break;
            default:
                break;
        }

        mLastX = xPosition;
        return true;
    }

    private void countVelocityTracker() {
        mVelocityTracker.computeCurrentVelocity(1000);
        float xVelocity = mVelocityTracker.getXVelocity();
        if (Math.abs(xVelocity) > mMinVelocity) {
//            mScroller.fling(0, 0, (int) xVelocity, 0, Integer.MIN_VALUE, Integer.MAX_VALUE, 0, 0);
        }
    }

    private void countMoveEnd() {
        mOffset -= mMove;
        if (mOffset <= mMaxOffset) {
            mOffset = mMaxOffset;
        } else if (mOffset >= 0) {
            mOffset = 0;
        }

        mLastX = 0;
        mMove = 0;

        mValue = mMinValue + Math.round(Math.abs(mOffset) * 1.0f / mItemSpacing) * mPerSpanValue / 1;
        mOffset = (mMinValue - mValue) * 1 / mPerSpanValue * mItemSpacing; // 矫正位置,保证不会停留在两个相邻刻度之间
        notifyValueChange();
        notifyValueChangeHttp();
        postInvalidate();
    }

    private void changeMoveAndValue() {
        mOffset -= mMove;
        if (mOffset <= mMaxOffset) {
            mOffset = mMaxOffset;
            mMove = 0;
        } else if (mOffset >= 0) {
            mOffset = 0;
            mMove = 0;
        }
        mValue = mMinValue + Math.round(Math.abs(mOffset) * 1.0f / mItemSpacing) * mPerSpanValue / 1;
        notifyValueChange();
        postInvalidate();
    }

    private void notifyValueChangeHttp() {
        if (null != mListenerHttp) {
            mListenerHttp.onValueChangeHttp(mValue);
        }
    }

    private void notifyValueChange() {
        if (null != mListener) {
            mListener.onValueChange(mValue);
        }
    }

    public interface OnValueChangeListener {
        void onValueChange(float value);
    }

    public interface OnValueChangeListenerHttp {
        void onValueChangeHttp(float value);
    }
}