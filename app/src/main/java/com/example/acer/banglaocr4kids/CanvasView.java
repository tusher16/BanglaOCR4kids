package com.example.acer.banglaocr4kids;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Acer on 8/4/2017.
 */

public class CanvasView extends View {

    public int width;
    public int height;
    private Bitmap bmap;
    private Canvas canvas;
    private Path path;
    private Paint paint;
    private float nx, ny;
    private static final float TOL=5;
    Context context;


    public CanvasView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context= context;
        path=new Path();
        paint=new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeWidth(4f);

    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        bmap=Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        canvas=new Canvas(bmap);





    }
    public Bitmap getBitmap()
    {

        this.setDrawingCacheEnabled(true);
        this.buildDrawingCache();
        Bitmap bmp = Bitmap.createBitmap(this.getDrawingCache());
        this.setDrawingCacheEnabled(false);


        return bmp;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawPath(path, paint);
    }

    private void StartTouch(float x, float y){
        path.moveTo(x, y);
        nx=x;
        ny=y;
    }

    private void moveTouch(float x, float y){
        float dx= Math.abs(x - nx);
        float dy= Math.abs(y - ny);
        if(dx>=TOL || dy>=TOL){
            path.quadTo(nx, ny, (x+nx)/2,(y+ny)/2 );
            nx=x;
            ny=y;

        }

    }

    public void clearCanvas(){
        path.reset();
        invalidate();


    }

    private void upTouch(){
        path.lineTo(nx, ny);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x=event.getX();
        float y=event.getY();

        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                StartTouch(x,y);
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                moveTouch(x,y);
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                upTouch();
                invalidate();
                break;
        }



        return true;
    }


}
