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

import ares.ywq.com.animationsample.R;

/**
 * 液晶数字时钟
 * Created by ares on 2016/11/27.
 */
public class NumClock extends View {

    //线的颜色
    private int lineColor;
    //中间两小点的颜色
    private int pointColor;
    //是否展示秒数
    private boolean showSeconds;
    //控件真实长宽
    private int mRealWidth, mRealHeight;
    private float centerX, centerY;
    private float lineWidth;//线的长度
    private float padding;//数字间的边距
    private float centerPadding;//中间的边距
    //中间点闪烁标志位
    private boolean isShow=false;
    private NumPaintUtil numPaintUtil;//绘制各个数字的工具
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

        numPaintUtil.setCanvas(canvas);
        //获取当前的时分秒
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        //锁定画布
        canvas.save();
        //为了减少坐标计算量,将坐标原点（0,0）移动到 view 的中心点
        canvas.translate(centerX,centerY);
        //绘制时
        drawHour(canvas,hour);
        //是否闪烁点
        if(isShow){
            drawPointBtHourNMinuter(canvas);
        }
        //更改标志位
        isShow=!isShow;
        //绘制分
        drawMinute(canvas,minute);
        //是否显示秒数
        if(showSeconds){
            int second = calendar.get(Calendar.SECOND);
            Paint textPaint = getPaint(lineColor);
            textPaint.setTextSize(25f);
            //绘制秒数
            if(second<10){
                canvas.drawText("0"+second,lineWidth/2+padding/2,lineWidth,textPaint);
            }else{
                canvas.drawText(second+"",lineWidth/2+padding/2,lineWidth,textPaint);
            }
        }
        canvas.restore();
        //每隔一秒刷新一次
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
        int miniValue = Math.min(mRealHeight, mRealWidth);
        Log.v("","onMeasure , mRealWidth="+mRealWidth+",mRealHeight="+mRealHeight);

        //设置长宽比为3:1
        int height=miniValue;
        int width=miniValue*3;
        setMeasuredDimension(width,height);



    }



    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        //TODO 设置各个参数
        centerX=w/2;
        centerY=h/2;
        mRealWidth=w;
        mRealHeight=h;
        lineWidth = w / 10;
        //数字间的间距
        padding=(mRealWidth-4*lineWidth)/8f;
        //时与分之间的间距
        centerPadding=2*padding;
        //由于 onDraw 调用比较频繁,故不在 onDraw 中实例化
        numPaintUtil=new NumPaintUtil(lineWidth,lineColor);


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
         float newCenterX=-(1.5f*lineWidth+padding+centerPadding/2);
         canvas.translate(newCenterX,0);
       //  drawNumber(canvas,tenOfHour);
        numPaintUtil.drawNumber(tenOfHour);
         canvas.translate(lineWidth+padding,0);
        numPaintUtil.drawNumber(oneOfHour);
        //drawNumber(canvas,oneOfHour);
    }

    /**
     * 绘制时与分之间闪烁的两个小点
     * @param canvas
     */
    public void drawPointBtHourNMinuter(Canvas canvas){
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
      //  drawNumber(canvas, tenOfMinute);
        numPaintUtil.drawNumber(tenOfMinute);
        canvas.translate(lineWidth+padding,0);
       // drawNumber(canvas, oneOfMinuter);
        numPaintUtil.drawNumber(oneOfMinuter);



    }







}
