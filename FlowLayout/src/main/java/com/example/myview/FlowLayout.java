package com.example.myview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by like on 15-4-9.
 */
public class FlowLayout extends ViewGroup {
    private static final String TAG="flowLayout";
    public FlowLayout(Context context) {
        super(context);
    }
    protected  ViewGroup.LayoutParams generateLayoutParams(LayoutParams p){
        return new MarginLayoutParams(p);
    }
    protected ViewGroup.LayoutParams generateLayoutParmas(AttributeSet attrs){
        return new MarginLayoutParams(getContext(),attrs);
    }
    protected LayoutParams generateDefaultLayoutParmas(){
        return new MarginLayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT);
    }
    public FlowLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public FlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    protected void onMeasure(int widthMeasureSpec,int heightMeasureSpec){
        super.onMeasure(widthMeasureSpec,heightMeasureSpec);
        int sizeWidth=MeasureSpec.getSize(widthMeasureSpec);
        int sizeHeight=MeasureSpec.getSize(heightMeasureSpec);
        int modeWidth=MeasureSpec.getMode(widthMeasureSpec);
        int modeHeight=MeasureSpec.getMode(heightMeasureSpec);
        //如果时wrap_content
        int width=0;
        int height=0;
        int lineWidth=0;
        int lineHeight=0;
        int cCount=getChildCount();
        for(int i=0;i<cCount;i++){
            View child=getChildAt(i);
            measureChild(child,widthMeasureSpec,heightMeasureSpec);
            MarginLayoutParams lp=(MarginLayoutParams)child.getLayoutParams();
            int childWidth=child.getMeasuredWidth()+lp.leftMargin
                    +lp.rightMargin;
            int childHeight=child.getMeasuredHeight()+lp.topMargin
                    +lp.bottomMargin;
            if(lineWidth+childWidth>sizeWidth){
                width=Math.max(lineWidth,childWidth);
                lineWidth=childWidth;
                height+=lineHeight;
                lineHeight=childHeight;
            }else{
                lineWidth+=childWidth;
                lineHeight=Math.max(lineHeight,childHeight);
            }
            if(i==cCount-1){
                width=Math.max(width,lineWidth);
                height+=lineHeight;
            }
        }
        setMeasuredDimension((modeWidth==MeasureSpec.EXACTLY)?
        sizeWidth:width,(modeHeight==MeasureSpec.EXACTLY)?sizeHeight:height);
    }
    /**
     * 存储所有的View，按行记录
     * **/
    private List<List<View>>mAllViews=new ArrayList<List<View>>();
    private List<Integer>mLineHeight=new ArrayList<Integer>();
     @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        mAllViews.clear();
        mLineHeight.clear();
        int width=getWidth();
        int lineWidth=0;
        int lineHeight=0;
        //存储每一行所有的childView
        List<View>lineViews=new ArrayList<View>();
        int cCount=getChildCount();
        //遍历所有的孩子
        for(int i=0;i<cCount;i++){
            View child=getChildAt(i);
            MarginLayoutParams lp=(MarginLayoutParams)child.getLayoutParams();
            int childWidth=child.getMeasuredWidth();
            int childHeight=child.getMeasuredHeight();
            //如国已经换行
            if(childWidth+lp.leftMargin+lp.rightMargin+lineWidth>width){
                mLineHeight.add(lineHeight);
                mAllViews.add(lineViews);
                lineWidth=0;
                lineViews=new ArrayList<View>();
            }
            /**
             *
             * */
            lineWidth+=childWidth+lp.leftMargin+lp.rightMargin;
            lineHeight=Math.max(lineHeight,childHeight
            +lp.topMargin+lp.bottomMargin);
            lineViews.add(child);
         }
         mLineHeight.add(lineHeight);
         mAllViews.add(lineViews);
         int left=0;
         int top=0;
         int lineNums=mAllViews.size();
         for(int i=0;i<lineNums;i++){
             //每一行的所有views
             lineViews=mAllViews.get(i);
             lineHeight=mLineHeight.get(i);
             for(int j=0;j<lineViews.size();j++){
                 View child=lineViews.get(j);
                 if(child.getVisibility()==View.GONE){
                     continue;
                 }
                 MarginLayoutParams lp=(MarginLayoutParams)child.
                         getLayoutParams();
                 int lc=left+lp.leftMargin;
                 int tc=top+lp.topMargin;
                 int rc=lc+child.getMeasuredWidth();
                 int bc=tc+child.getMeasuredHeight();
                 child.layout(lc,tc,rc,bc);
                 left+=child.getMeasuredWidth()+lp.rightMargin
                         +lp.leftMargin;
             }
             left=0;
             top+=lineHeight;
         }

    }
}
















