package com.example.like.myview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

/**
 * Created by like on 15-4-9.
 */
public class CustomTitleView extends View{
    private Paint mPaint;
    private String mTilteText;
    private int mTitleTextColor;
    private int mTitleTextSize;
    private String mTilte;
    //绘制文本时的范围
    private Rect mBound;
    public CustomTitleView(Context context,AttributeSet attrs){
        this(context,attrs,0);
    }
    public CustomTitleView(Context context){
        this(context,null);
    }
    /**
     * 获得我自定义的样式属性
     * @param context
     * @param attrs
     * @param  defStyle
     * */
     public CustomTitleView(Context context,AttributeSet attrs,
                            int defStyle){
         super(context,attrs,defStyle);
         /**
          * 获得我们自己定义的属性
          * */
         TypedArray a=context.getTheme().obtainStyledAttributes(
                 attrs,R.styleable.CustomTitleView,
                 defStyle,0
         );
         int n=a.getIndexCount();
         for(int i=0;i<n;i++){
             int attr=a.getIndex(i);
             switch (attr){
                 case R.styleable.CustomTitleView_titleText:
                     mTilteText=a.getString(attr);
                     break;
                 case R.styleable.CustomTitleView_titleTextColor:
                     mTitleTextColor=a.getColor(attr, Color.BLACK);
                     break;
                 case R.styleable.CustomTitleView_titleTextSize:
                     mTitleTextSize=a.getDimensionPixelSize(attr,
                             (int) TypedValue.applyDimension(
                                     TypedValue.COMPLEX_UNIT_DIP,
                                     16,getResources().getDisplayMetrics()
                             ));
                     break;
             }
         }
         a.recycle();
         /**
          * 获得绘制文本的宽高
          *
          * */
          mPaint=new Paint();
          mPaint.setTextSize(mTitleTextSize);
          mBound=new Rect();
          mPaint.getTextBounds(mTilteText,0,mTilteText.length(),
                  mBound);
       }
       protected  void onMeasure(int widthMeasureSpec,
                                 int heightMeasureSpec){
           int widthMode=MeasureSpec.getMode(widthMeasureSpec);
           int widthSize=MeasureSpec.getSize(widthMeasureSpec);

           int heightMode=MeasureSpec.getMode(heightMeasureSpec);
           int heightSize=MeasureSpec.getSize(heightMeasureSpec);
           int width;
           int height;

           if(widthMode==MeasureSpec.EXACTLY){
               width=widthSize;
           }else{
               mPaint.setTextSize(mTitleTextSize);
               mPaint.getTextBounds(mTilteText,0,mTilteText.length(),
                       mBound);
               float textWidth=mBound.width();
               int desired=(int)(getPaddingLeft()+textWidth+getPaddingRight());
               width=desired;
           }
           if(heightMode==MeasureSpec.EXACTLY){
               height=heightSize;
           }else{
               mPaint.setTextSize(mTitleTextSize);
               mPaint.getTextBounds(mTilteText,0,mTilteText.length(),mBound);
               float textHeight=mBound.height();
               int desired=(int)(getPaddingTop()+textHeight+getPaddingBottom());
               height=desired;
           }
           setMeasuredDimension(width,height);
       }

       protected void onDraw(Canvas canvas){
           mPaint.setColor(Color.YELLOW);
           canvas.drawRect(0,0,getMeasuredWidth(),
                   getMeasuredHeight(),mPaint);
           mPaint.setColor(mTitleTextColor);
           canvas.drawText(mTilteText,
                   getWidth()/2-mBound.width()/2,
                   getHeight()/2+mBound.height()/2,
                   mPaint);
       }
 }




















