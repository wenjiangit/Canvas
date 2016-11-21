package com.douliu.canvas.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by douliu on 2016/10/31.
 */
public class MagicCircle extends View {

    private Path mPath;

    private Paint mFillCirclePaint;

    private int radius;

    private int mWidth, mHeight;

    private float centerX, centerY;

    private float c = 0.551915024494f;

    private float cDistance;

    private PointF left,top,right,bottom;

    private VPoint vp1,vp3;

    private HPoint hp2,hp4;


    public MagicCircle(Context context) {
        super(context, null);
    }

    public MagicCircle(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mFillCirclePaint = new Paint();
        mFillCirclePaint.setAntiAlias(true);
        mFillCirclePaint.setColor(0xFFfe626d);
        mFillCirclePaint.setStyle(Paint.Style.FILL);
        mFillCirclePaint.setStrokeWidth(5);

        mPath = new Path();
        left = new PointF();
        right = new PointF();
        top = new PointF();
        bottom = new PointF();




    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
        centerX = w / 2;
        centerY = h / 2;

        radius = 50;
        cDistance = c * radius;
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.translate(centerX, centerY);
        canvas.scale(1,-1);       //翻转坐标轴

        left.x = -radius;
        left.y = 0;

        right.x = radius;
        right.y = 0;

        top.x = 0;
        top.y = radius;

        bottom.x = 0;
        bottom.y = -radius;

        mPath.moveTo(right.x,right.y);
        mPath.cubicTo(right.x,cDistance,cDistance,top.y,top.x,top.y);
        mPath.cubicTo(-cDistance,top.y,left.x,cDistance,left.x,left.y);
        mPath.cubicTo(left.x,-cDistance,-cDistance,bottom.y,bottom.x,bottom.y);
        mPath.cubicTo(cDistance,bottom.y,right.x,-cDistance,right.x,right.y);

        canvas.drawPath(mPath,mFillCirclePaint);


    }

    class VPoint {
        public float x;
        public float y;
        public PointF top = new PointF();
        public PointF bottom = new PointF();

        public void setY(float y) {
            this.y = y;
            top.y = y;
            bottom.y = y;
        }

        public void adjustY(float offset) {
            top.y -= offset;
            bottom.y += offset;
        }
    }

    class HPoint{
        public float x;
        public float y;
        public PointF left = new PointF();
        public PointF right = new PointF();

        public void setX(float x){
            this.x = x;
            left.x = x;
            right.x = x;
        }
    }

}
