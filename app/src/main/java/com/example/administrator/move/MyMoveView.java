package com.example.administrator.move;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Scroller;

import com.example.administrator.viewtest.R;
import com.nineoldandroids.view.ViewHelper;

/**
 * Created by Administrator on 2018/7/30.
 *
 * 事件分发机制
 *dispatchEvent(){
 *     if (onInterceptEvent(event)) {
 *          if (onTouchListener == null || !onTouchListener(event)) {
 *              if (!onTouchEvent(event){
 *                  if (onClickListener != null) {
 *                      onClickListener(event)
 *                  }
 *              }){
 *                  parent.onTouchEvent(event) //搞不定该事件，抛给上级处理，上级搞不定，再抛给上级的上级处理
 *              }
 *          } else {
 *              onTouchListener(event){
 *                  onTouch(event)
 *              }
 *          }
 *     } else {
 *         child.dispatchEvent(event)
 *     }
 *}
 *
 */

public class MyMoveView extends View {
    private static final String TAG = "MyMoveView";
    private Scroller mScroller;
    private Paint mPaint;

    private int screenWidth;
    private int screenHeight;
    private int mLastX;
    private int mLastY;

    public MyMoveView(Context context) {
        super(context);
    }

    public MyMoveView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initPaint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawCircle(screenWidth / 2, screenHeight / 2, 50, mPaint);
    }

    @Override
    public void layout(int l, int t, int r, int b) {
        super.layout(l, t, r, b);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getRawX();
        int y = (int) event.getRawY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_MOVE:
                int deltaX = x - mLastX;
                int deltaY = y - mLastY;
                int translationX = (int) (ViewHelper.getTranslationX(this) + deltaX);
                int translationY = (int) (ViewHelper.getTranslationY(this) + deltaY);
                ViewHelper.setTranslationX(this, translationX);
                ViewHelper.setTranslationY(this, translationY);
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        mLastX = x;
        mLastY = y;
        return super.onTouchEvent(event);
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            postInvalidate();
        }
    }

    private void initPaint(){
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mPaint.setColor(getResources().getColor(R.color.colorAccent));
        mScroller = new Scroller(getContext());

        DisplayMetrics dm = getResources().getDisplayMetrics();
        screenWidth = dm.widthPixels;
        screenHeight = dm.heightPixels;
    }

    public void smoothScrollTo(int destX, int destY){
        Log.e(TAG, "smoothScrollTo: ----------------");
        int scrollX = getScrollX();
        int deltaX = destX - scrollX;
        int scrollY = getScrollY();
        int deltaY = destY - scrollY;
        mScroller.startScroll(scrollX, scrollY , deltaX, deltaY, 1000);
        invalidate();
    }
}
