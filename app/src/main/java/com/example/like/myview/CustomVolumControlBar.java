package com.example.like.myview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by like on 15-4-9.
 */
public class CustomVolumControlBar extends View {
    private int mFirstColor;
    private int mSecondColor;
    private int mCircleWidth;
    private Paint mPaint;
    private int mCurrentCount=3;
    private Bitmap mImage;
    private int mSplitSize;
    private int mCount;
    private Rect mRect;
    public CustomVolumControlBar(Context context) {
        super(context,null);
    }

    public CustomVolumControlBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomVolumControlBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.CustomVolumControlBar, defStyleAttr, 0);
        int n = a.getIndexCount();

        for (int i = 0; i < n; i++)
        {
            int attr = a.getIndex(i);
            switch (attr)
            {
                case R.styleable.CustomVolumControlBar_firstColor:
                    mFirstColor = a.getColor(attr, Color.GREEN);
                    break;
                case R.styleable.CustomVolumControlBar_secondColor:
                    mSecondColor = a.getColor(attr, Color.CYAN);
                    break;
                case R.styleable.CustomVolumControlBar_bg:
                    mImage = BitmapFactory.decodeResource(getResources(), a.getResourceId(attr, 0));
                    break;
                case R.styleable.CustomVolumControlBar_circleWidth:
                    mCircleWidth = a.getDimensionPixelSize(attr, (int) TypedValue.applyDimension(
                            TypedValue.COMPLEX_UNIT_PX, 20, getResources().getDisplayMetrics()));
                    break;
                case R.styleable.CustomVolumControlBar_dotCount:
                    mCount = a.getInt(attr, 20);// Ä¬ÈÏ20
                    break;
                case R.styleable.CustomVolumControlBar_splitSize:
                    mSplitSize = a.getInt(attr, 20);
                    break;
            }
        }
        a.recycle();
        mPaint = new Paint();
        mRect = new Rect();
    }
    protected void onDraw(Canvas canvas){
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(mCircleWidth);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStyle(Paint.Style.STROKE);
        int center=getWidth()/2;
        int radius=center-mCircleWidth/2;
        drawOval(canvas,center,radius);
        int relRadius=radius-mCircleWidth/2;
        mRect.left=(int)(relRadius-Math.sqrt(2)*1.0f/2*relRadius)+
                mCircleWidth;
        mRect.top = (int) (relRadius - Math.sqrt(2) * 1.0f / 2 * relRadius) + mCircleWidth;
        mRect.bottom = (int) (mRect.left + Math.sqrt(2) * relRadius);
        mRect.right = (int) (mRect.left + Math.sqrt(2) * relRadius);

        if (mImage.getWidth() < Math.sqrt(2) * relRadius)
        {
            mRect.left = (int) (mRect.left + Math.sqrt(2) * relRadius * 1.0f / 2 - mImage.getWidth() * 1.0f / 2);
            mRect.top = (int) (mRect.top + Math.sqrt(2) * relRadius * 1.0f / 2 - mImage.getHeight() * 1.0f / 2);
            mRect.right = (int) (mRect.left + mImage.getWidth());
            mRect.bottom = (int) (mRect.top + mImage.getHeight());

        }

        canvas.drawBitmap(mImage, null, mRect, mPaint);

    }
    private void drawOval(Canvas canvas,int centre,int radius){
       float itemSize=(360*1.0f-mCount*mSplitSize)/mCount;
        RectF oval=new RectF(centre-radius,centre-radius
        ,centre+radius,centre+radius);
        mPaint.setColor(mFirstColor);
        for(int i=0;i<mCount;i++){
            canvas.drawArc(oval,i*(itemSize+mSplitSize),
                  itemSize,false,mPaint );
        }
        for(int i=0;i<mCurrentCount;i++){
            canvas.drawArc(oval,i*(itemSize+mSplitSize),
                    itemSize,false,mPaint);
        }
    }
    public void up(){
        mCurrentCount++;
        postInvalidate();
    }
    public void down(){
        mCurrentCount--;
        postInvalidate();
    }
    private int xDown,xUp;
    public boolean onTouchEvent(MotionEvent event){
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                xDown=(int)event.getY();
                break;
            case MotionEvent.ACTION_UP:
                xUp=(int)event.getY();
                if(xUp>xDown){
                    down();
                }else{
                    up();
                }
                break;
        }
        return true;
    }
}







