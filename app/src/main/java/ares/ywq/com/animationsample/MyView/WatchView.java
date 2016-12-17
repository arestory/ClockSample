package ares.ywq.com.animationsample.MyView;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.util.Calendar;

import ares.ywq.com.animationsample.R;
import ares.ywq.com.animationsample.SizeUtil;

/**
 * Created by ares on 2016/11/22.
 */
public class WatchView extends View {



    //控件真实长宽
    private int mRealWidth, mRealHeight;
    //外圈宽度
    private int widthOfRing;//为半径的十分一
    //中心小圆的半径
    private int innerCircleRadius;
    //时钟半径
    private int radius;
    //时钟的中心点
    private int centerX, centerY;
    //秒针颜色
    private int sColor;
    //秒针画笔
    private Paint sPaint;
    //分针颜色
    private int mColor;
    private Paint mPaint;
    //时针颜色
    private int hColor;
    private Paint hPaint;
    //外圈颜色
    private int outCircleColor;
    private Paint outPaint;
    //内圈颜色
    private int inCircleColor;
    private Paint inPaint;
    //刻数颜色
    private int numColor;
    private Paint numPaint;



    public WatchView(Context context) {
        super(context);
    }

    public WatchView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.WatchView);

        int sDefaultColor= Color.argb(0,121,111,111);
        sColor=array.getColor(R.styleable.WatchView_sColor,sDefaultColor);
        sPaint=getPaint(sColor);
        mColor=array.getColor(R.styleable.WatchView_mColor,sDefaultColor);
        mPaint=getPaint(mColor);
        hColor=array.getColor(R.styleable.WatchView_hColor,sDefaultColor);
        hPaint=getPaint(hColor);
        outCircleColor=array.getColor(R.styleable.WatchView_outCircleColor,sDefaultColor);
        outPaint=getPaint(outCircleColor);
        inCircleColor=array.getColor(R.styleable.WatchView_inCircleColor,sDefaultColor);
        inPaint=getPaint(inCircleColor);
        numColor=array.getColor(R.styleable.WatchView_numColor,sDefaultColor);
        numPaint=getPaint(numColor);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        // 获得该view真实的宽度和高度
        mRealWidth = getMeasuredWidth() - getPaddingLeft() - getPaddingRight();
        mRealHeight = getMeasuredHeight() - getPaddingTop() - getPaddingBottom();

        centerX=mRealWidth/2;
        centerY=mRealHeight/2;
        radius=mRealWidth/2;
        widthOfRing=radius/10;
        innerCircleRadius=radius/20;
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.save();
        //将画布锁定到该点
        canvas.translate(centerX,centerY);
        //画外圆
        drawOutCircle(canvas);
         canvas.drawCircle(0,0,radius,outPaint);
        //画内圆
        canvas.drawCircle(0,0,radius-widthOfRing, inPaint);


        drawScale(canvas);
      //  drawTimeText(canvas);
        drawClockwise(canvas);

        //画中心小圆
        canvas.drawCircle(0,0,innerCircleRadius, sPaint);

        //恢复画布中心点
         canvas.restore();
        postInvalidateDelayed(1000);

    }

    /**
     * 画内圆
     * @param canvas
     */
    private void drawOutCircle(Canvas canvas){
        canvas.drawCircle(0,0,radius,outPaint);
    }



    /**
     * 绘制时分秒针
     *
     * @param canvas
     */
    private void drawClockwise(Canvas canvas) {

        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
         int minute = calendar.get(Calendar.MINUTE);
        int second = calendar.get(Calendar.SECOND);
         if (timeChangeListener != null) {
            timeChangeListener.onTimeChange(hour, minute, second);
        }

        int angleHour = (hour % 12) * 360 / 12;//时针转过的角度
        int angleMinute = minute * 360 / 60;//分针转过的角度
        int angleSecond = second * 360 / 60;//秒针转过的角度
        //绘制时针
        canvas.save();
        canvas.rotate(angleHour);
        canvas.drawLine(0, -radius + 4 * widthOfRing + SizeUtil.dip2px(getContext(), 10), 0, innerCircleRadius + SizeUtil.dip2px(getContext(), 5), numPaint);
        canvas.restore();
        canvas.save();
        canvas.rotate(angleMinute);
        canvas.drawLine(0, -radius + 3 * widthOfRing + SizeUtil.dip2px(getContext(), 10), 0, innerCircleRadius + SizeUtil.dip2px(getContext(), 10), numPaint);
        canvas.restore();
        canvas.save();
        canvas.rotate(angleSecond);
        sPaint.setStrokeWidth(5);
        canvas.drawLine(0, -radius + 2 * widthOfRing + SizeUtil.dip2px(getContext(), 10), 0, innerCircleRadius + SizeUtil.dip2px(getContext(), 10), sPaint);

        canvas.restore();


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



    /**
     *
     * @param canvas
     */
    private void drawScale(Canvas canvas){

        Paint textPaint = getPaint(sColor);
        textPaint.setTextSize(20f);
        //设置刻度的宽度
          numPaint.setStrokeWidth(SizeUtil.dip2px(getContext(),1));
        int lineWidth=0;
        for(int i=0;i<60;i++){
            //整点
            if(i%5==0){
                numPaint.setStrokeWidth(SizeUtil.dip2px(getContext(),1.5f));
                lineWidth=25;
                canvas.drawLine(0,-radius+widthOfRing+SizeUtil.dip2px(getContext(),5),
                        0,-radius+widthOfRing+SizeUtil.dip2px(getContext(),5)+lineWidth,numPaint);
            }else{
                canvas.drawCircle(0,-radius+widthOfRing+SizeUtil.dip2px(getContext(),10),3,numPaint);
                //canvas.drawCircle(SizeUtil.dip2px(getContext(),10)/2,0,SizeUtil.dip2px(getContext(),10)/2,numPaint);
            }

            canvas.rotate(6);
        }
        //canvas.restore();

    }



    /**
     * 获得对应颜色的画笔
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



}
