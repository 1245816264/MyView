package com.example.like.myview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

/**
 * Created by like on 15-4-9.
 */
public class CustomProgressBar extends View {
    private int mFirstColor;
    private int mSecondColor;
    private int mCircleWidth;
    private Paint mPaint;
    private int mProgress;
    private int mSpeed;
    private boolean isNext=false;

    public CustomProgressBar(Context context) {
        super(context,null);
    }
    public CustomProgressBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomProgressBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.CustomProgressBar, defStyle, 0);
        int n = a.getIndexCount();

        for (int i = 0; i < n; i++)
        {
            int attr = a.getIndex(i);
            switch (attr)
            {
                case R.styleable.CustomProgressBar_firstColor:
                    mFirstColor = a.getColor(attr, Color.GREEN);
                    break;
                case R.styleable.CustomProgressBar_secondColor:
                    mSecondColor = a.getColor(attr, Color.RED);
                    break;
                case R.styleable.CustomProgressBar_circleWidth:
                    mCircleWidth = a.getDimensionPixelSize(attr, (int) TypedValue.applyDimension(
                            TypedValue.COMPLEX_UNIT_PX, 20, getResources().getDisplayMetrics()));
                    break;
                case R.styleable.CustomProgressBar_speed:
                    mSpeed = a.getInt(attr, 20);// 默认20
                    break;
            }
        }
        a.recycle();
        mPaint=new Paint();
        //绘制
        new Thread(){
            public void run(){
                while(true){
                    mProgress++;
                    if(mProgress==360){
                        mProgress=0;
                        if(!isNext)
                            isNext=true;
                        else
                            isNext=false;
                    }
                    postInvalidate();
                    try{
                        Thread.sleep(mSpeed);
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }
    protected void onDraw(Canvas canvas){
        int center=getWidth()/2;
        int radius=center-mCircleWidth/2;//半径
        mPaint.setStrokeWidth(mCircleWidth);//设置
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
        RectF oval=new RectF(center-radius,
                center-radius,center+radius,center+radius);
        if(!isNext){
            mPaint.setColor(mFirstColor);//设置
            canvas.drawCircle(center,center,radius,mPaint);
            mPaint.setColor(mSecondColor);
            canvas.drawArc(oval,-90,mProgress,false,mPaint);
        }else{
            mPaint.setColor(mSecondColor);
            canvas.drawCircle(center,center,radius,mPaint);
            mPaint.setColor(mFirstColor);
            canvas.drawArc(oval,-90,mProgress,false,mPaint);
        }
    }
}














