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
    private int num;

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
        if(num>=10){
            this.num=0;
        }
       // invalidate();


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
        TypedArray array = null;

        try {
            array = getContext().obtainStyledAttributes(attrs, R.styleable.ClockNumView);
            numColor = array.getColor(R.styleable.ClockNumView_clockNumColor, Color.parseColor("#ffaacc"));
            num=array.getInteger(R.styleable.ClockNumView_num,0);
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
        int width = Math.min(mRealHeight, mRealWidth);
        //设置长宽比为3:2
        setMeasuredDimension(width, width * 3 / 2);

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        centerX = w / 2;
        centerY = h / 2;
        lineWidth = w / 2;
        padding = w / 6;


    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.save();
        canvas.translate(centerX, centerY);

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


        canvas.restore();


    }






    /**
     * 画中间线
     *
     * @param canvas
     */
    private void drawCenterLine(Canvas canvas) {

        Paint numPaint = getPaint(numColor);
        canvas.drawLine(-lineWidth / 2, 0, lineWidth / 2, 0, numPaint);

    }

    /**
     * 画top 线
     *
     * @param canvas
     */
    private void drawTopLine(Canvas canvas) {
        Paint numPaint = getPaint(numColor);
        canvas.drawLine(-lineWidth / 2, -lineWidth - 10, lineWidth / 2, -lineWidth - 10, numPaint);

    }

    /**
     * 画底部的线
     *
     * @param canvas
     */
    private void drawBottomLine(Canvas canvas) {
        Paint numPaint = getPaint(numColor);
        canvas.drawLine(-lineWidth / 2, lineWidth + 10, lineWidth / 2, lineWidth + 10, numPaint);
    }


    /**
     * 画左上的线
     *
     * @param canvas
     */
    private void drawTopLeftLine(Canvas canvas) {
        Paint numPaint = getPaint(numColor);
        canvas.drawLine(-lineWidth / 2, -5, -lineWidth / 2, -5 - lineWidth, numPaint);
    }

    /**
     * 画右上的线
     *
     * @param canvas
     */
    private void drawTopRightLine(Canvas canvas) {

        Paint numPaint = getPaint(numColor);
        canvas.drawLine(lineWidth / 2, -5, lineWidth / 2, -5 - lineWidth, numPaint);
    }

    /**
     * 画左下
     *
     * @param canvas
     */
    private void drawBottomLeftLine(Canvas canvas) {

        Paint numPaint = getPaint(numColor);
        canvas.drawLine(-lineWidth / 2, 5, -lineWidth / 2, 5 + lineWidth, numPaint);
    }


    /**
     * 画右下
     *
     * @param canvas
     */
    private void drawBottomRigthLine(Canvas canvas) {
        Paint numPaint = getPaint(numColor);
        canvas.drawLine(lineWidth / 2, 5, lineWidth / 2, 5 + lineWidth, numPaint);

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
