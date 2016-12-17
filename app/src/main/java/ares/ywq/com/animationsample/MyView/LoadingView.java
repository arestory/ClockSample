package ares.ywq.com.animationsample.MyView;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import ares.ywq.com.animationsample.R;

/**
 * Created by ares on 2016/11/21.
 */
public class LoadingView extends View {

    private double ringX,ringY;
    private double angle=0;


    //圆的半径
    private int radius;
    //最大半径
    private int rMax;
    //最小半径
    private int rMin;

    //圆的颜色
    private int circleColor;
    //圆的画笔
    private Paint circlePaint;

    //控件的中心点，作为所有圆的圆心
    private int centerX, centerY;

    //控件真实长宽
    private int mRealWidth, mRealHeight;


    //开始变大
    public static final int BEGIN_TO_BIGGER=0X001;
    //开始缩小
    public static final int BEGIN_TO_SMALLER=0X002;
    //开始圆周运动
    public static final int BEGIN_TO_RUN=0X003;

    public LoadingView(Context context) {
        super(context);
    }

    public LoadingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        WaveHandler handler = new WaveHandler();
        RoundHandler roundHandler = new RoundHandler();
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.LoadingView);
        radius=array.getInt(R.styleable.LoadingView_radius,20);
        circleColor=array.getColor(R.styleable.LoadingView_circleColor,getResources().getColor(R.color.colorAccent));

        circlePaint = new Paint();
        circlePaint.setAntiAlias(true);//抗锯齿
        circlePaint.setColor(circleColor);

        handler.sendEmptyMessage(BEGIN_TO_SMALLER);
        roundHandler.sendEmptyMessage(BEGIN_TO_RUN);
        //回收
        array.recycle();
    }

    public LoadingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.LoadingView);
        radius=array.getInt(R.styleable.LoadingView_radius,20);
        circleColor=array.getColor(R.styleable.LoadingView_circleColor,getResources().getColor(R.color.colorAccent));

        circlePaint = new Paint();
        circlePaint.setAntiAlias(true);//抗锯齿
        circlePaint.setColor(circleColor);



        //回收
        array.recycle();

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint blackPaint = new Paint();
        blackPaint.setAntiAlias(true);//抗锯齿
        blackPaint.setColor(getResources().getColor(R.color.black));

        canvas.drawCircle(centerX,centerY,radius,circlePaint);

        canvas.drawCircle((float)ringX,(float)ringY,rMin,circlePaint);

        canvas.drawCircle(100,400,50,circlePaint);
        canvas.drawLine((int)ringX,(int)ringY,centerX,centerY,blackPaint);

    }




    private class RoundHandler extends  Handler{

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            //外圆圆心到内圆圆心的距离
            int distance=rMax-rMin;
            angle+=(Math.PI/2)/100;
            if(angle>=0&&angle<=Math.PI/2){

                Log.v("test","angle="+angle+",dis="+distance);

                Log.v("test","ringX="+ringX+",ringY="+ringY);


            }
            sendEmptyMessageDelayed(BEGIN_TO_RUN,20);

            ringX= 2*rMax+centerX+distance*Math.sin(angle);
            ringY=centerY-distance*Math.cos(angle)-2*rMax;





        }
    }


    private class WaveHandler extends Handler{

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case BEGIN_TO_BIGGER:


                    if(radius<=rMax){
                        radius+=2;
                        sendEmptyMessageDelayed(BEGIN_TO_BIGGER,50);

                    }else{
                        sendEmptyMessageDelayed(BEGIN_TO_SMALLER,50);
                    }
                    invalidate();

                    break;
                case BEGIN_TO_SMALLER:

                    if(radius>=rMin){
                        radius-=2;
                        sendEmptyMessageDelayed(BEGIN_TO_SMALLER,50);
                    }else{
                        sendEmptyMessageDelayed(BEGIN_TO_BIGGER,50);
                    }

                    invalidate();




                    break;


            }


        }
    }




    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        // 获得该view真实的宽度和高度
        mRealWidth = getMeasuredWidth() - getPaddingLeft() - getPaddingRight();
        mRealHeight = getMeasuredHeight() - getPaddingTop() - getPaddingBottom();

        //初始化中心点
        centerX = mRealWidth / 2;
        centerY = mRealHeight / 2;


        //设置中心圆半径
        //radius = mRealHeight /3 ;
        rMax=radius;
        rMin=rMax/4;

        //外圆初始圆心
        ringX=mRealWidth / 2;
        ringY=rMin*2;



        Log.d("animation","初始ringX="+ringX+",ringY="+ringY);

        Log.d("animation","mRealWidth="+mRealWidth);
        Log.d("animation","mRealHeight="+mRealHeight);
        Log.d("animation","radius="+radius);
    }
}
