package com.example.administrator.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.example.administrator.customcircle.R;


/**
 * Created by Administrator on 2017/12/13.
 */

public class CustomCircle extends View {

    //圆的直径
    private float mRadius;
    //圆的粗细
    private float mStrokeWidth;
    //圆环的画笔
    private Paint cyclePaint;
    //文字的画笔
    private Paint textPaint;
    //View自身的宽和高
    private int mHeight;
    private int mWidth;

    private Context mContext;

    /**
     * 可以手动设置的相关变量
     *
     * @param context
     */
    //已完成
    private float finishNum;
    //前景色
    private int finishColor;
    //背景色
    private int bgColor;
    //文字大小
    private float textSize;

    public CustomCircle(Context context) {
        super(context);
        this.mContext = context;
    }

    public CustomCircle(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        setMyRoundAttributes(attrs);
    }

    public CustomCircle(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        setMyRoundAttributes(attrs);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
        if (w > h) {
            mRadius = h - mStrokeWidth;
        } else {
            mRadius = w - mStrokeWidth;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //移动画布到圆环的左上角
        canvas.translate(mWidth / 2 - mRadius / 2, mHeight / 2 - mRadius / 2);
        //初始化画笔
        initPaint();
        //画圆环
        drawCycle(canvas);
        //绘制文字
        drawText(canvas);
    }

    /**
     * 初始化画笔
     */
    private void initPaint() {
        //边框画笔
        cyclePaint = new Paint();
        cyclePaint.setAntiAlias(true);
        cyclePaint.setStyle(Paint.Style.STROKE);
        cyclePaint.setStrokeWidth(mStrokeWidth);
        //文字画笔
        textPaint = new Paint();
        textPaint.setAntiAlias(true);
        textPaint.setStyle(Paint.Style.FILL);
        textPaint.setStrokeWidth(mStrokeWidth);
        textPaint.setColor(finishColor);
        textPaint.setStrokeWidth(1);
        textPaint.setTextSize(textSize);
    }

    /**
     * 画圆环
     *
     * @param canvas
     */
    private void drawCycle(Canvas canvas) {
        float startPercent = -90;
        float sweepPercent = 0;
        cyclePaint.setColor(finishColor);
        startPercent = sweepPercent + startPercent;
        sweepPercent = finishNum * 360;
        canvas.drawArc(new RectF(0, 0, mRadius, mRadius), startPercent, sweepPercent, false, cyclePaint);
        cyclePaint.setColor(bgColor);
        startPercent = sweepPercent + startPercent;
        sweepPercent = (1 - finishNum) * 360;
        canvas.drawArc(new RectF(0, 0, mRadius, mRadius), startPercent, sweepPercent, false, cyclePaint);

    }

    /**
     * 画文字
     */
    private void drawText(Canvas canvas) {
        String percent = String.valueOf(finishNum * 100) + "%";
        canvas.drawText(percent, mRadius / 2 - textPaint.measureText(percent)/ 2, mRadius / 2, textPaint);
    }


    /**
     * 可以在xml中对图形的一些属性进行设置
     */
//在这里配置可以在xml中直接引用
    private void setMyRoundAttributes(AttributeSet attrs) {
        @SuppressLint("CustomViewStyleable")
        TypedArray a = mContext.obtainStyledAttributes(attrs,
                R.styleable.customcircle);
        finishNum = a.getFloat(R.styleable.customcircle_finished, 0);
        finishColor = a.getColor(R.styleable.customcircle_finishcolor, 0);
        bgColor = a.getColor(R.styleable.customcircle_bgcolor, 0);
        mStrokeWidth = a.getDimension(R.styleable.customcircle_mstrokewidth, 1);
        textSize = a.getDimension(R.styleable.customcircle_textsize, -1);
        a.recycle();
    }
}

