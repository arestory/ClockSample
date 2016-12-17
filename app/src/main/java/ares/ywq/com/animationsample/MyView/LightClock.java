package ares.ywq.com.animationsample.MyView;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.os.Handler;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.Toast;

import java.util.Calendar;

import ares.ywq.com.animationsample.R;
import ares.ywq.com.animationsample.SizeUtil;

/**
 * 荧光时钟
 * Created by ares on 2016/11/23.
 */
public class LightClock extends View {

    /**
     * 最外层颜色
     */
    private int outCircleColor;
    /**
     * 第二层颜色
     */
    private int secondCircleColor;
    /**
     * 表头颜色
     */
    private int clockColor;
    /**
     * 一刻钟的刻度的颜色
     */
    private int quarterColor;
    /**
     * 其他刻度的颜色
     */
    private int minuteColor;
    /**
     * 圆心小圆的颜色
     */
    private int centerCircleColor;
    /**
     * 指针的颜色
     */
    private int wiseColor;
    /**
     * 指针上正方形的颜色
     */
    private int squareColorOfWise;
    /**
     * 指针上圆形颜色
     */
    private int circleColorOfWise;
    /**
     * 指针上 三角形的颜色
     */
    private int triangleColorOfWise;

    /**
     * 时钟半径
     */
    private float radiusOfClock;
    /**
     *  中间小圆的半径
     */
    private float radiusOfCenterCircle;
    /**
     * 时钟的中心点
     */
    private float centerXofClock, centerYofClock;
    /**
     * 一刻钟的圆的半径
     */
    private float radiusOfquarter;
    /**
     * 其他刻度的圆的半径
     */
    private float radiusOfminute;

    /**
     * 正方形的宽度
     */
    private float squareWidth;
    /**
     * 三角形高度,该三角形的高=2*底边
     */
    private float triangleHeight;
    /**
     * 指针圆形半径
     */
    private float radiusOfWiseCircle;


    public LightClock(Context context) {
        super(context);
    }

    public LightClock(Context context, AttributeSet attrs) {
        super(context, attrs);
        obtainStyledAttrs(attrs);

    }


    /**
     * 获取样式,出现异常时取默认值
     *
     * @param attrs
     */
    private void obtainStyledAttrs(AttributeSet attrs) {
        TypedArray array;

        try {
            array = getContext().obtainStyledAttributes(attrs, R.styleable.LightClock);
            outCircleColor = getColor(array, R.styleable.LightClock_outsideCircleColor, Color.rgb(187, 184, 183));//#bbb8b7
            secondCircleColor = getColor(array, R.styleable.LightClock_secondCircleColor, Color.rgb(105, 104, 103));//#696867
            clockColor = getColor(array, R.styleable.LightClock_clockColor, Color.rgb(0, 0, 0));//#000000
            quarterColor = getColor(array, R.styleable.LightClock_quarterColor, Color.rgb(77, 255, 255));//4dffff
            minuteColor = getColor(array, R.styleable.LightClock_minuteColor, Color.rgb(105, 104, 103));//#696867
            centerCircleColor = getColor(array, R.styleable.LightClock_centerCircleColor, Color.rgb(255, 158, 11));//#ff9e0b
            wiseColor = getColor(array, R.styleable.LightClock_wiseColor, Color.rgb(105, 104, 103));//#696867
            squareColorOfWise = getColor(array, R.styleable.LightClock_squareColorOfWise, Color.rgb(0, 255, 0));//#00ff00
            circleColorOfWise = getColor(array, R.styleable.LightClock_circleColorOfWise, Color.rgb(255, 54, 54));//#ff3636
            triangleColorOfWise = getColor(array, R.styleable.LightClock_triangleColorOfWise, Color.rgb(255, 255, 49));//#ffff31


        } catch (Exception e) {

            outCircleColor=Color.rgb(187, 184, 183);
            secondCircleColor = Color.rgb(105, 104, 103);//#696867
            clockColor = Color.rgb(0, 0, 0);//#000000
            quarterColor = Color.rgb(77, 255, 255);//4dffff
            minuteColor = Color.rgb(105, 104, 103);//#696867
            centerCircleColor =Color.rgb(255, 158, 11);//#ff9e0b
            wiseColor = Color.rgb(105, 104, 103);//#696867
            squareColorOfWise = Color.rgb(0, 255, 0);//#00ff00
            circleColorOfWise =Color.rgb(255, 54, 54);//#ff3636
            triangleColorOfWise = Color.rgb(255, 255, 49);//#ffff31


        }
    }



    /**
     * 测量 view 的长宽
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        if (widthMode == MeasureSpec.AT_MOST || widthMode == MeasureSpec.UNSPECIFIED || heightMeasureSpec == MeasureSpec.AT_MOST || heightMeasureSpec == MeasureSpec.UNSPECIFIED) {
            try {
                throw new NoDetermineSizeException("宽度高度至少有一个确定的值,不能同时为wrap_content");
            } catch (NoDetermineSizeException e) {
                e.printStackTrace();
            }
        } else {
            // 获得该view真实的宽度和高度
            int mRealWidth = getMeasuredWidth() - getPaddingLeft() - getPaddingRight();
            int mRealHeight = getMeasuredHeight() - getPaddingTop() - getPaddingBottom();

            Log.v("","mRealHeight="+mRealHeight);
            //取最小值
            int width=Math.min(mRealHeight,mRealWidth);
            //设置长宽相等
            setMeasuredDimension(width,width);

        }


    }

    /**
     * 尺寸被修改后
     * @param w
     * @param h
     * @param oldw
     * @param oldh
     */
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        radiusOfClock = (Math.min(w, h)) / 2;
        Log.v("","radiusOfClock="+radiusOfClock);
        centerYofClock = radiusOfClock;
        centerXofClock = radiusOfClock;
        //整点刻度的圆半径为时钟半径的1/40
        radiusOfquarter=0.025f*radiusOfClock;
        //非整点刻度的圆半径为时钟半径的1/100
        radiusOfminute=0.01f*radiusOfClock;
        //时钟中心小圆的半径为时钟半径的3/100
        radiusOfCenterCircle=0.03f*radiusOfClock;
        //分针上的小圆半径为整点刻度小圆的1.6倍
        radiusOfWiseCircle=1.6f*radiusOfquarter;
        //时针上的正方形边长为时钟半径的1/20
        squareWidth=0.05f*radiusOfClock;
        //三角形高度=正方形边长
        triangleHeight=squareWidth;

    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //先锁定画布
        canvas.save();
        //将画布原点坐标从0,0转移到时钟中心的坐标点
        canvas.translate(centerXofClock, centerYofClock);
        drawTheOutCircle(canvas);
        drawSecondCircle(canvas);
        drawBoardCicle(canvas);
        //绘制刻度
        drawScale(canvas);
        //绘制时分秒针
        drawClockwise(canvas);
        //绘制中心小圆
        drawCenterCircle(canvas);
        //还原画布位置
        canvas.restore();
        //每隔一秒刷新一次
        postInvalidateDelayed(1000);


    }


    /**
     * 画最外圈
     * @param canvas
     */
    private void drawTheOutCircle(Canvas canvas) {

        Paint paint = getPaint(outCircleColor);

        canvas.drawCircle(0, 0, radiusOfClock, paint);
    }

    /**
     * 画第二个圈
     * @param canvas
     */
    private void drawSecondCircle(Canvas canvas){
        Paint paint = getPaint(secondCircleColor);

        canvas.drawCircle(0, 0, radiusOfClock*0.95f, paint);

    }

    /**
     * 画中间时钟的圈
     * @param canvas
     */
    private void drawBoardCicle(Canvas canvas){
        Paint paint = getPaint(clockColor);
        canvas.drawCircle(0, 0, radiusOfClock*0.92f, paint);
    }


    /**
     * 画刻度
     * @param canvas
     */
    private void drawScale(Canvas canvas){
        //共有60个刻度,循环画60次,每个刻度占6度
        for(int i=0;i<60;i++){
            //整点
            if(i%5==0){
                Paint scalePaint =getPaint(quarterColor);

                 canvas.drawCircle(0,-0.8f*radiusOfClock,radiusOfquarter,scalePaint);

            }else{
                Paint scalePaint =getPaint(minuteColor);
                canvas.drawCircle(0,-0.8f*radiusOfClock,radiusOfminute,scalePaint);


            }
            //每画一个刻度,将当前画布旋转6度
            canvas.rotate(6);
        }
    }

    /**
     * 画中间小圆
     * @param canvas
     */
    private void drawCenterCircle(Canvas canvas){
        Paint paint = getPaint(centerCircleColor);
        Paint paint2=getPaint(minuteColor);
        //再画一个阴影圆
        canvas.drawCircle(0,0,radiusOfCenterCircle*1.3f,paint2);
        canvas.drawCircle(0,0,radiusOfCenterCircle,paint);



    }

    /**时针占半径的比例*/
    public static final float HOUR_WISE_PERCENT=0.5F;
    /**分针占半径的比例*/
    public static final float MINUTE_WISE_PERCENT=0.65F;
    /**秒针占半径的比例*/
    public static final float SECOND_WISE_PERCENT=0.8F;
    /**
     * 用于绘制时分秒针
     * 绘制每一根针前都需要调用 canvas.save()去锁定当前画布,再根据时间旋转角度,画完需要调用 canvas.restore()还原画布
     * @param canvas
     */
    private void drawClockwise(Canvas canvas) {

        Paint squarePaint = getPaint(squareColorOfWise);
        Paint circlePaint = getPaint(circleColorOfWise);
        Paint trianglePaint=getPaint(triangleColorOfWise);
        Paint wisePaint = getPaint(wiseColor);
        //指针的宽度
        wisePaint.setStrokeWidth(4f);
        //获取当前时间的时分秒
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
         int minute = calendar.get(Calendar.MINUTE);
         int second = calendar.get(Calendar.SECOND);
         if (timeChangeListener != null) {
            timeChangeListener.onTimeChange(hour, minute, second);
        }
        //计算各条针应该旋转的角度
        int angleHour = (hour % 12) * 360 / 12;//时针转过的角度
        int angleMinute = minute * 360 / 60;//分针转过的角度
        int angleSecond = second * 360 / 60;//秒针转过的角度
        //绘制时针
        canvas.save();
        canvas.rotate(angleHour);
        canvas.drawLine(0, -HOUR_WISE_PERCENT*radiusOfClock, 0, 0, wisePaint);
        //画正方形
        canvas.drawRect(-squareWidth,-HOUR_WISE_PERCENT*radiusOfClock-squareWidth,squareWidth,squareWidth-HOUR_WISE_PERCENT*radiusOfClock,squarePaint);
        canvas.restore();
        //绘制分针
        canvas.save();
        canvas.rotate(angleMinute);
        canvas.drawLine(0, -MINUTE_WISE_PERCENT*radiusOfClock, 0, 0, wisePaint);
        canvas.drawCircle(0, -MINUTE_WISE_PERCENT*radiusOfClock,radiusOfWiseCircle,circlePaint);

         canvas.restore();

        //绘制秒针
        canvas.save();
        canvas.rotate(angleSecond);
        canvas.drawLine(0, - SECOND_WISE_PERCENT*radiusOfClock, 0, 0, wisePaint);
        Path trianglePath= new Path();
        //起点
        trianglePath.moveTo(0,- SECOND_WISE_PERCENT*radiusOfClock-triangleHeight);
        //移到这个点
        trianglePath.lineTo(-triangleHeight,- SECOND_WISE_PERCENT*radiusOfClock+triangleHeight);
        //移到这个点
        trianglePath.lineTo(triangleHeight,- SECOND_WISE_PERCENT*radiusOfClock+triangleHeight);
        canvas.drawPath(trianglePath,trianglePaint);
        // canvas.drawLine(0, -radius + 2 * widthOfRing + SizeUtil.dip2px(getContext(), 10), 0, innerCircleRadius + SizeUtil.dip2px(getContext(), 10), sPaint);

        canvas.restore();


    }




    public enum ColorBelong{

        outCircle,secondCircle,clock,quarter,minute,centerCircle,wise,square,circle,triangle,

    }

    /**
     * 设置对应的颜色
     * @param kind 种类
     * @param color 颜色
     */
    public void setColor(ColorBelong kind,int color){


         switch (kind){
             case outCircle:

                 outCircleColor=color;


                 break;
             case secondCircle:
                 secondCircleColor=color;
                 break;
             case clock:
                 clockColor=color;
                 break;
             case quarter:
                 quarterColor=color;
                 break;
             case minute:
                 minuteColor=color;
                 break;
             case centerCircle:
                 centerCircleColor=color;
                 break;
             case wise:
                 wiseColor=color;
                 break;
             case square:
                 squareColorOfWise=color;
                 break;
             case circle:
                 circleColorOfWise=color;
                 break;
             case triangle:
                 triangleColorOfWise=color;
                 break;
         }
        //刷新 view
        invalidate();

    }





    /**
     * 获得对应颜色的画笔
     *
     * @param color
     * @return
     */
    public Paint getPaint(int color) {
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(color);
        paint.setDither(true);
        return paint;
    }

    /**
     * 获取相应颜色
     *
     * @param array
     * @param colorId
     * @param defaultColor
     * @return
     */
    public int getColor(TypedArray array, int colorId, int defaultColor) {


        return array.getColor(colorId, defaultColor);

    }



    /**
     *
     */
    class NoDetermineSizeException extends Exception {
        public NoDetermineSizeException(String message) {
            super(message);
        }
    }


    public  TimeChangeListener timeChangeListener;

    /**
     * 设置时间变更监听
     * @param timeChangeListener
     */
    public void setTimeChangeListener( TimeChangeListener timeChangeListener){
        this.timeChangeListener=timeChangeListener;
    }

    /**
     * 时间监听接口
     */
    public interface TimeChangeListener{

        /**
         *
         * @param hour 当前时间的 时
         * @param minute 当前时间的 分
         * @param second 当前时间的 秒
         */
        void onTimeChange(int hour,int minute,int second);
    }



}
