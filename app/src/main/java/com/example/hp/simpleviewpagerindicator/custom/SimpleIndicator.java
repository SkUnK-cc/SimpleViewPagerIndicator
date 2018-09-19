package com.example.hp.simpleviewpagerindicator.custom;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SimpleIndicator extends LinearLayout {

    private String[] titles = new String[]{"番茄","土豆","西红柿"};
    private Paint mPaint;
    private int mTabCount;
    private float mTranslationX = 0;
    private int position = 0;
    private IndicatorListener mListener;
    private SparseArray<TextView> mList = new SparseArray<>(); //存放标题的TextView

    public SimpleIndicator(Context context) {
        this(context,null);
    }

    public SimpleIndicator(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint();
        mPaint.setColor(Color.RED);
        mPaint.setStrokeWidth(8);
    }

    //listener监听标题项的点击事件
    public void setListener(IndicatorListener listener){
        mListener = listener;
    }

    public void setTitles(String[] titles){
        this.titles = titles;
        mTabCount = titles.length;
        generateTitleView();
    }

    //添加标题项
    private void generateTitleView() {
        if(getChildCount()!=0)removeAllViews();

        for(int i=0;i<mTabCount;i++){
            TextView textView = new TextView(getContext());
            textView.setText(titles[i]);
            if(i==0){
                textView.setTextColor(Color.RED);
            }else {
                textView.setTextColor(Color.BLACK);
            }
            textView.setTag(i);
            LayoutParams layoutParams = new LayoutParams(0,LayoutParams.MATCH_PARENT,1);
            textView.setLayoutParams(layoutParams);
            textView.setGravity(Gravity.CENTER);
            //设置点击监听，回调listener的方法
            textView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mListener!=null)mListener.onClick((Integer) v.getTag());
                }
            });
            addView(textView);
            mList.put(i,textView);
        }
    }

    //更新标题的字体颜色
    public void refreshTextColor(int k){
        for(int i=0;i<mTabCount;i++){
            TextView textView = mList.get(i);
            if(i!=k){
                textView.setTextColor(Color.BLACK);
            }else{
                textView.setTextColor(Color.RED);
            }
        }
        position = k;
    }

    public void vpScroll(int pos, float offset){
        mTranslationX = (pos+offset)*getWidth()/mTabCount;
        //invalidate方法会执行draw过程，重绘View树。
        // View（非容器类）调用invalidate方法只会重绘自身，ViewGroup调用则会重绘整个View树。
        invalidate();
        int current = Math.round(pos+offset);
        if(current!=position)refreshTextColor(current);
    }

    /**
     * View组件的绘制会调用draw(Canvas canvas)方法，draw过程中主要是先画Drawable背景，
     * 对drawable调用setBounds()然后是draw(Canvas c)方法.注意的是背景drawable的实际大小会影响view组件的大小
     * drawable的实际大小通过getIntrinsicWidth()和getIntrinsicHeight()获取，
     * 当背景比较大时view组件大小等于背景drawable的大小
     * 画完背景后，draw过程会调用onDraw(Canvas canvas)方法，然后就是dispatchDraw(Canvas canvas)方法,
     * dispatchDraw()主要是分发给子组件进行绘制，我们通常定制组件的时候重写的是onDraw()方法。
     * 值得注意的是ViewGroup容器组件的绘制，当它没有背景时直接调用的是dispatchDraw()方法, 而绕过了draw()方法
     * 当它有背景的时候就调用draw()方法，而draw()方法里包含了dispatchDraw()方法的调用。
     * 因此要在ViewGroup上绘制东西的时候往往重写的是dispatchDraw()方法而不是onDraw()方法，
     * 或者自定制一个Drawable，重写它的draw(Canvas c)和 getIntrinsicWidth(),
     */
    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        canvas.drawLine(mTranslationX,getMeasuredHeight()-5,mTranslationX+getMeasuredWidth()/mTabCount,getMeasuredHeight()-5,mPaint);
    }

    public void setIndicatorListener(IndicatorListener listener){
        this.mListener = listener;
    }
    public interface IndicatorListener{
        void onClick(int position);
    }
}
