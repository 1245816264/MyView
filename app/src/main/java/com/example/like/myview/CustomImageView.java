package com.example.like.myview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

/**
 * Created by like on 15-4-9.
 */
public class CustomImageView extends View {
    private Bitmap mImage;
    private int mImageScale;
    private String mTitle;
    private int mTextColor;
    private int mTextSize;
    private Paint mPaint;
    private Rect mTextBound,rect;
    private int mWidth;
    private int mHeight;
    public CustomImageView(Context context, AttributeSet attrs,int defStyle) {
        super(context, attrs,defStyle);
        TypedArray a=context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.CustomImageView,
                defStyle,0
        );
        int n=a.getIndexCount();
        for(int i=0;i<n;i++){
            int attr=a.getIndex(i);
            switch (attr){
                case R.styleable.CustomImageView_image:
                     mImage=BitmapFactory
                            .decodeResource(getResources(),
                                    a.getResourceId(attr, 0));
                    break;
                case R.styleable.CustomImageView_iamgeScaleType:
                    mImageScale=a.getInt(attr,0);
                    break;
                case R.styleable.CustomImageView_titleText:
                    mTitle=a.getString(attr);
                    break;
                case R.styleable.CustomImageView_titleTextColor:
                    mTextColor=a.getColor(attr, Color.BLACK);
                    break;
                case R.styleable.CustomImageView_titleTextSize:
                    mTextSize=a.getDimensionPixelSize(attr
                            ,(int) TypedValue.applyDimension(
                              TypedValue.COMPLEX_UNIT_DIP,
                        16,getResources().getDisplayMetrics()
                            ));
                    break;
            }
        }
        a.recycle();
        rect=new Rect();
        mPaint=new Paint();
        mTextBound=new Rect();
        mPaint.setTextSize(mTextSize);
        mPaint.getTextBounds(mTitle,0,mTitle.length(),
                mTextBound);

    }
    protected void onMeasure(int widthMeasureSpec,int heightMeasureSpec){
        int specMode=MeasureSpec.getMode(widthMeasureSpec);
        int specSize=MeasureSpec.getSize(widthMeasureSpec);
        if(specMode==MeasureSpec.EXACTLY){
            mWidth=specSize;
        }else{
            //由图片决定宽度
            int defsireByImg=getPaddingLeft()+getPaddingRight()
                    +mImage.getWidth();
            //由字体决定宽度
            int desireByTitle=getPaddingLeft()+getPaddingRight()+mTextBound.width();
            if(specMode==MeasureSpec.AT_MOST){
                int desire=Math.max(defsireByImg,desireByTitle);
                mWidth=Math.min(desire,specSize);

            }
        }
        /**
         * 设置高度
         * */
         specMode=MeasureSpec.getMode(heightMeasureSpec);
         specSize=MeasureSpec.getSize(heightMeasureSpec);
        if(specMode==MeasureSpec.EXACTLY){
            mHeight=specSize;
        }else{
            int desire=getPaddingTop()+getPaddingBottom()+
                    mImage.getHeight();
            if(specMode==MeasureSpec.AT_MOST) {//WRAP_CONTENT
                    mHeight=Math.min(desire,specSize);
            }
        }
        setMeasuredDimension(mWidth,mHeight);
     }
    protected void onDraw(Canvas canvas){
        mPaint.setStrokeWidth(4);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.CYAN);
        canvas.drawRect(0,0,getMeasuredWidth(),getMeasuredHeight(),mPaint);
        rect.left=getPaddingLeft();
        rect.right=mWidth-getPaddingRight();
        rect.top=getPaddingTop();
        rect.bottom=mHeight-getPaddingBottom();

        mPaint.setColor(mTextColor);
        mPaint.setStyle(Paint.Style.FILL);
        /**
         * 当前设置的宽度小于在
         * */
         if(mTextBound.width()>mWidth){
             TextPaint paint=new TextPaint(mPaint);
             String msg = TextUtils.ellipsize(mTitle, paint, (float) mWidth - getPaddingLeft() - getPaddingRight(),
                     TextUtils.TruncateAt.END).toString();
             canvas.drawText(msg, getPaddingLeft(), mHeight - getPaddingBottom(), mPaint);
         } else
         {
             //正常情况，将字体居中
             canvas.drawText(mTitle, mWidth / 2 - mTextBound.width() * 1.0f / 2, mHeight - getPaddingBottom(), mPaint);
         }
         //取消使用掉的块
         rect.bottom-=mTextBound.height();


    }
}
