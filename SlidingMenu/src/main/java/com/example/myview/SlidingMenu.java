package com.example.myview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

/**
 * Created by like on 15-4-10.
 */
public class SlidingMenu extends HorizontalScrollView {
    /**
    * 屏幕宽
    * **/
    private int mScreenWidth;
    /**
     *dp
     * */
    private int mMenuRightPadding;
    /**
     * 菜单的宽度
     * */
    private int mMenuWidth;
    private int mHalfMenuWidth;
    private boolean isOpen;
    private boolean once;
    public SlidingMenu(Context context) {
        super(context);
    }

    public SlidingMenu(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }
    public SlidingMenu(Context context,AttributeSet attrs,int defStyle){
        super(context,attrs,defStyle);
        mScreenWidth=ScreenUtils.getScreenWidth(context);

    }
    protected void onMeasure(int widthMeasureSpec,int heightMeasureSpec){
        if(!once){
            LinearLayout wrapper=(LinearLayout)getChildAt(0);
            ViewGroup menu=(ViewGroup)wrapper
                    .getChildAt(0);
            mMenuWidth=mScreenWidth-mMenuRightPadding;
            mHalfMenuWidth=mMenuWidth/2;
            menu.getLayoutParams().width=mScreenWidth;
        }
        super.onMeasure(widthMeasureSpec,heightMeasureSpec);
    }
    protected void onLayout(boolean changed,int l,int t,int r,int b){
        super.onLayout(changed,l,t,r,b);
        if(changed){
            this.scrollTo(mMenuWidth,0);
            once=true;
        }
    }
    public boolean onTouchEvent(MotionEvent ev){
        int action=ev.getAction();
        switch (action){
            case MotionEvent.ACTION_UP:
                int scrollX=getScrollX();
                if(scrollX>mHalfMenuWidth){
                    this.smoothScrollTo(mMenuWidth,0);
                    isOpen=false;
                }else{
                    this.smoothScrollTo(0,0);
                }
                return true;
        }
        return super.onTouchEvent(ev);
    }

    /**
     * 打开菜单
     */
    public void openMenu()
    {
        if (isOpen)
            return;
        this.smoothScrollTo(0, 0);
        isOpen = true;
    }

    /**
     * 关闭菜单
     */
    public void closeMenu()
    {
        if (isOpen)
        {
            this.smoothScrollTo(mMenuWidth, 0);
            isOpen = false;
        }
    }

    /**
     * 切换菜单状态
     */
    public void toggle()
    {
        if (isOpen)
        {
            closeMenu();
        } else
        {
            openMenu();
        }
    }

}



