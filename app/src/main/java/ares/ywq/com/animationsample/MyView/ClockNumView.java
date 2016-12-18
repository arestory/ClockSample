package ares.ywq.com.animationsample.MyView;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import ares.ywq.com.animationsample.R;

/**
 * Created by ares on 2016/11/27.
 */
public class ClockNumView extends View{

    private int numColor;
    //控件真实长宽
    private int mRealWidth, mRealHeight;
    private float centerX, centerY;
    private float lineWidth;//线的长度
    private float padding;//边距
    //当前数字,默认显示8
    private int num=8;
    private NumPaintUtil numPaintUtil;

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;

        invalidate();


    }

    public ClockNumView(Context context, AttributeSet attrs) {
        super(context, attrs);
        obtainStyledAttrs(attrs);

    }

    public ClockNumView(Context context) {
        super(context);
    }


    /**
     * 获取样式,出现异常时取默认值
     *
     * @param attrs
     */
    private void obtainStyledAttrs(AttributeSet attrs) {
        TypedArray array;

        try {
            array = getContext().obtainStyledAttributes(attrs, R.styleable.ClockNumView);
            numColor = array.getColor(R.styleable.ClockNumView_clockNumColor, Color.parseColor("#ffaacc"));
            num=array.getInteger(R.styleable.ClockNumView_num,8);
        } catch (Exception e) {
            numColor = Color.parseColor("#ffaacc");
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

        //如果0-9,设置长宽比为3:2
        if(num<10){
            int width=miniValue;
            int height=width * 3 / 2;
            setMeasuredDimension(width, height);
        }
        if(num>=10&&num<100){
            int width=miniValue;
            int height=width * 2;
            setMeasuredDimension(width, height);

        }

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        centerX = w / 2;
        centerY = h / 2;
        if(num<10){
            lineWidth = w / 2;
            padding = w / 6;

        }
        if(num>=10&&num<100){
            lineWidth=w/4;
            padding = w / 12;
        }

        numPaintUtil=new NumPaintUtil(lineWidth,numColor);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        numPaintUtil.setCanvas(canvas);
        canvas.save();

        if(num>=10&&num<100){
            int numOfTen = num/10;
            int numOfOne =num%10;
            canvas.translate(padding, centerY);
            numPaintUtil.drawNumber(num); 

        }else{
            canvas.translate(centerX, centerY);
            numPaintUtil.drawNumber(num);
        }


        canvas.restore();


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

}
