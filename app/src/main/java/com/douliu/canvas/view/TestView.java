package com.douliu.canvas.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by douliu on 2016/10/27.
 */
public class TestView extends View{

    private int mWidth;
    private int mHeight;
    private Paint mPaint;
    private PointF mStartP;
    private PointF mEndP;
    private PointF mControlP;
    private Path mPath ;

    public TestView(Context context) {
        super(context,null);
    }

    public TestView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initPaint();
        init();
    }

    private void init() {
        mStartP = new PointF();
        mEndP = new PointF();
        mControlP = new PointF();
        mPath = new Path();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mControlP.x = event.getX();
        mControlP.y = event.getY();
        invalidate();
        return true;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;

        mStartP.x = 0;
        mStartP.y = 2 * mHeight / 3;

        mEndP.x = mWidth;
        mEndP.y = mHeight / 3;


    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        mPaint.setColor(Color.RED);
        canvas.drawCircle(mControlP.x,mControlP.y,20,mPaint);
        canvas.drawLine(mStartP.x,mStartP.y,mControlP.x,mControlP.y,mPaint);
        canvas.drawLine(mControlP.x,mControlP.y,mEndP.x,mEndP.y,mPaint);

        mPaint.setTextSize(20);
        canvas.drawText("x:"+mControlP.x,0,200,mPaint);
        canvas.drawText("y:"+mControlP.y,200,200,mPaint);

        mPath.reset();
        mPaint.setColor(Color.BLACK);
        mPath.moveTo(mStartP.x,mStartP.y);
        mPath.quadTo(mControlP.x,mControlP.y,mEndP.x,mEndP.y);

        canvas.drawPath(mPath,mPaint);
    }

    private void initPaint() {
        mPaint = new Paint();
        mPaint.setColor(Color.BLACK);
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(3);
    }

    public PointF getStartPoint(){
        return mStartP;
    }

    public PointF getEndPoint(){
        return mEndP;
    }

    public PointF getControlPoint(){
        return mControlP;
    }
}
