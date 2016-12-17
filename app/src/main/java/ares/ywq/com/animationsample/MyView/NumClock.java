package ares.ywq.com.animationsample.MyView;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.util.Calendar;

import ares.ywq.com.animationsample.MainActivity;
import ares.ywq.com.animationsample.R;

/**
 * Created by ares on 2016/11/27.
 */
public class NumClock extends View {

    private boolean isShow=false;
    //线的颜色
    private int lineColor;
    private int pointColor;
    //是否展示秒数
    private boolean showSeconds;
    //控件真实长宽
    private int mRealWidth, mRealHeight;
    private float centerX, centerY;
    private float lineWidth;//线的长度
    private float padding;//数字间的边距
    private float centerPadding;//中间的边距

    public NumClock(Context context) {
        super(context);
    }

    public NumClock(Context context, AttributeSet attrs) {
        super(context, attrs);
        obtainStyledAttrs(attrs);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        int second = calendar.get(Calendar.SECOND);

        if(showSeconds){
            canvas.save();
            canvas.translate(centerX,centerY);
            drawHour(canvas,hour);
            if(isShow){
                drawPointBtHourNminuter(canvas);
            }
            isShow=!isShow;
            drawMinute(canvas,minute);
            Paint textPaint = getPaint(lineColor);
            textPaint.setTextSize(25f);
            if(second<10){
                canvas.drawText("0"+second,lineWidth/2+padding/2,lineWidth,textPaint);
            }else{
                canvas.drawText(second+"",lineWidth/2+padding/2,lineWidth,textPaint);

            }
            canvas.restore();
        }else{
            canvas.save();
            canvas.translate(centerX,centerY);
            drawHour(canvas,hour);
            if(isShow){
                drawPointBtHourNminuter(canvas);
            }
            isShow=!isShow;
            drawMinute(canvas,minute);
            canvas.restore();
        }
        postInvalidateDelayed(1000);
    }


    /**
     * 获取样式,出现异常时取默认值
     *
     * @param attrs
     */
    private void obtainStyledAttrs(AttributeSet attrs) {
        TypedArray array ;

        try {
            array = getContext().obtainStyledAttributes(attrs, R.styleable.NumClock);
            lineColor = array.getColor(R.styleable.NumClock_lineColor, Color.parseColor("#ffaacc"));
            showSeconds=array.getBoolean(R.styleable.NumClock_showSecond,false);
            pointColor=array.getColor(R.styleable.NumClock_pointColor, Color.parseColor("#ffaacc"));
        } catch (Exception e) {
            lineColor = Color.parseColor("#ffaacc");
            pointColor=Color.parseColor("#ffaacc");
            showSeconds=false;
        }


    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        // 获得该view真实的宽度和高度
        mRealWidth = getMeasuredWidth() - getPaddingLeft() - getPaddingRight();
        mRealHeight = getMeasuredHeight() - getPaddingTop() - getPaddingBottom();
        //取最小值
        int width = Math.min(mRealHeight, mRealWidth);
        Log.v("","onMeasure , mRealWidth="+mRealWidth+",mRealHeight="+mRealHeight);
        //判断是否显示秒数,重新设置长宽
        if(showSeconds){
            //设置长宽比为9:2
            setMeasuredDimension( width * 6 / 2,width);
        }else{
            //设置长宽比为6:2
            setMeasuredDimension( width * 4 / 2,width);
        }


    }



    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        centerX=w/2;
        centerY=h/2;
        mRealWidth=w;
        mRealHeight=h;
        Log.v("","onSizeChanged , mRealWidth="+mRealWidth+",mRealHeight="+mRealHeight);

        if(showSeconds){
            lineWidth = w / 10;
            padding=(mRealWidth-4*lineWidth)/8f;
            centerPadding=2*padding;
        }else{
            lineWidth = w / 10;
            padding=(mRealWidth-4*lineWidth)/8f;
            centerPadding=2*padding;
        }

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
        paint.setStrokeWidth(5f);
        paint.setDither(true);
        return paint;
    }


    /**
     * 绘制 时
     * @param canvas
     * @param  hour
     */
    public void drawHour(Canvas canvas,int hour){
        int tenOfHour=hour/10;//十位
        int oneOfHour=hour%10;//个位
        //再次平移
        //间隙
         float newCenterX=-(1.5f*lineWidth+padding+centerPadding/2);
         canvas.translate(newCenterX,0);
         drawNumber(canvas,tenOfHour);
         canvas.translate(lineWidth+padding,0);
        drawNumber(canvas,oneOfHour);
    }

    public void drawPointBtHourNminuter(Canvas canvas){
        Paint pointPaint =getPaint(pointColor);
        canvas.drawCircle(lineWidth/2+centerPadding/2,lineWidth/2,5,pointPaint);
        canvas.drawCircle(lineWidth/2+centerPadding/2,-lineWidth/2,5,pointPaint);

    }

    /**
     * 绘制 分
     * @param canvas
     * @param minute
     */
    public void drawMinute(Canvas canvas,int minute){
        int tenOfMinute=minute/10;//十位
        int oneOfMinuter=minute%10;//个位
        canvas.translate(lineWidth+centerPadding,0);
        drawNumber(canvas, tenOfMinute);
        canvas.translate(lineWidth+padding,0);
        drawNumber(canvas, oneOfMinuter);



    }


    /**
     * 绘制 秒
     * @param canvas
     * @param seconds
     */
    public void drawSeconds(Canvas canvas,int seconds){
        int tenOfMinute=seconds/10;//十位
        int oneOfMinuter=seconds%10;//个位
        canvas.translate(lineWidth+padding,0);
        drawNumber(canvas, tenOfMinute);
        canvas.translate(lineWidth+padding,0);
        drawNumber(canvas, oneOfMinuter);


    }



    /**
     * 绘制数字
     * @param canvas
     * @param num
     */
    private void drawNumber(Canvas canvas,int num){

        switch (num) {
            case 0:
                drawTopLeftLine(canvas);
                drawTopLine(canvas);
                drawBottomLine(canvas);
                drawTopRightLine(canvas);
                drawBottomLeftLine(canvas);
                drawBottomRigthLine(canvas);
                break;
            case 1:
                drawTopRightLine(canvas);
                drawBottomRigthLine(canvas);
                break;
            case 2:
                drawCenterLine(canvas);
                drawTopLine(canvas);
                drawBottomLine(canvas);
                drawTopRightLine(canvas);
                drawBottomLeftLine(canvas);
                break;
            case 3:
                drawCenterLine(canvas);
                drawTopLine(canvas);
                drawBottomLine(canvas);
                drawTopRightLine(canvas);
                drawBottomRigthLine(canvas);
                break;
            case 4:
                drawCenterLine(canvas);
                drawTopLeftLine(canvas);
                drawTopRightLine(canvas);
                drawBottomRigthLine(canvas);
                break;
            case 5:
                drawCenterLine(canvas);
                drawTopLeftLine(canvas);
                drawTopLine(canvas);
                drawBottomLine(canvas);
                drawBottomRigthLine(canvas);
                break;
            case 6:
                drawCenterLine(canvas);
                drawTopLine(canvas);
                drawBottomLine(canvas);
                drawTopLeftLine(canvas);
                drawBottomLeftLine(canvas);
                drawBottomRigthLine(canvas);
                break;
            case 7:
                drawTopLine(canvas);
                drawTopRightLine(canvas);
                drawBottomRigthLine(canvas);
                break;
            case 8:
                drawCenterLine(canvas);
                drawTopLeftLine(canvas);
                drawTopLine(canvas);
                drawBottomLine(canvas);
                drawTopRightLine(canvas);
                drawBottomLeftLine(canvas);
                drawBottomRigthLine(canvas);
                break;
            case 9:
                drawCenterLine(canvas);
                drawTopLeftLine(canvas);
                drawTopLine(canvas);
                drawBottomLine(canvas);
                drawTopRightLine(canvas);
                drawBottomRigthLine(canvas);
                break;
        }

    }



    /**
     * 画中间线
     *
     * @param canvas
     */
    private void drawCenterLine(Canvas canvas) {

        Paint numPaint = getPaint(lineColor);
        canvas.drawLine(-lineWidth / 2, 0, lineWidth / 2, 0, numPaint);

    }

    /**
     * 画top 线
     *
     * @param canvas
     */
    private void drawTopLine(Canvas canvas) {
        Paint numPaint = getPaint(lineColor);
        canvas.drawLine(-lineWidth / 2, -lineWidth - 10, lineWidth / 2, -lineWidth - 10, numPaint);

    }

    /**
     * 画底部的线
     *
     * @param canvas
     */
    private void drawBottomLine(Canvas canvas) {
        Paint numPaint = getPaint(lineColor);
        canvas.drawLine(-lineWidth / 2, lineWidth + 10, lineWidth / 2, lineWidth + 10, numPaint);
    }


    /**
     * 画左上的线
     *
     * @param canvas
     */
    private void drawTopLeftLine(Canvas canvas) {
        Paint numPaint = getPaint(lineColor);
        canvas.drawLine(-lineWidth / 2, -5, -lineWidth / 2, -5 - lineWidth, numPaint);
    }

    /**
     * 画右上的线
     *
     * @param canvas
     */
    private void drawTopRightLine(Canvas canvas) {

        Paint numPaint = getPaint(lineColor);
        canvas.drawLine(lineWidth / 2, -5, lineWidth / 2, -5 - lineWidth, numPaint);
    }

    /**
     * 画左下
     *
     * @param canvas
     */
    private void drawBottomLeftLine(Canvas canvas) {

        Paint numPaint = getPaint(lineColor);
        canvas.drawLine(-lineWidth / 2, 5, -lineWidth / 2, 5 + lineWidth, numPaint);
    }


    /**
     * 画右下
     *
     * @param canvas
     */
    private void drawBottomRigthLine(Canvas canvas) {
        Paint numPaint = getPaint(lineColor);
        canvas.drawLine(lineWidth / 2, 5, lineWidth / 2, 5 + lineWidth, numPaint);

    }




}
