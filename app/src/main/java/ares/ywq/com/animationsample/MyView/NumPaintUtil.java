package ares.ywq.com.animationsample.MyView;

import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * 用于 canvas 画液晶数字<BR/>
 * Created by ares on 2016/12/17.
 * <BR/>
 *     &nbsp&nbsp __ &nbsp&nbsp&nbsp __ <BR/>
 *      &nbsp&nbsp| &nbsp |  &nbsp&nbsp| &nbsp |<BR/>
 *         &nbsp&nbsp — &nbsp&nbsp&nbsp — <BR/>
 *      &nbsp&nbsp| &nbsp | &nbsp&nbsp&nbsp  &nbsp |<BR/>
 *       &nbsp&nbsp — &nbsp&nbsp&nbsp —<BR/>
 */
public class NumPaintUtil {


    private Canvas canvas;// view 的画布
    private float lineWidth;//线的长度
    private int lineColor;

    private float padding=10;//竖直间距,默认为10
    /**
     *
     * @param canvas 画布
     * @param lineWidth 线长
     * @param lineColor 线的颜色
     */
    public NumPaintUtil(Canvas canvas,float lineWidth,int lineColor){
        this.canvas=canvas;
        this.lineWidth=lineWidth;
        this.lineColor=lineColor;
    }

    /**
     *
     * @param lineWidth 线长
     * @param lineColor 线的颜色
     */
    public NumPaintUtil(float lineWidth,int lineColor){
        this.lineWidth=lineWidth;
        this.lineColor=lineColor;
    }

    public Canvas getCanvas() {
        return canvas;
    }

    /**
     *
     * 调用drawNumber之前需设置画布对象
     * @param canvas
     */
    public void setCanvas(Canvas canvas) {
        this.canvas = canvas;
    }

    /**
     *
     * 根据0-9的特性绘制线条从而实现对应的数字<BR/>
     * 使用前,需初始化设置canvas,并且请将 canvas 移动到合适的位置
     * @param num 数字
     */
    public void drawNumber(int num){
        if(canvas==null){

            try{
                throw new CanvasNullPointException("canvas is null,please init canvas");
            }catch (CanvasNullPointException e){
                e.printStackTrace();
            }
            return;
        }

            switch (num) {
                case 0:

                    /**
                     * 去掉中间线即可
                     *  ——
                     * |  |
                     * |  |
                     *  ——
                     */
                    drawTopLeftLine();
                    drawTopLine();
                    drawBottomLine();
                    drawTopRightLine();
                    drawBottomLeftLine();
                    drawBottomRightLine();
                    break;
                case 1:
                    /**
                     *   只画右上和右下的线
                     *
                     *   |
                     *   |
                     *
                     */
                    drawTopRightLine();
                    drawBottomRightLine();
                    break;
                case 2:
                    /**
                     *   去掉左上和右下
                     *
                     *   ——
                     *     |
                     *   ——
                     *  |
                     *   ——
                     *
                     */
                    drawCenterLine();
                    drawTopLine();
                    drawBottomLine();
                    drawTopRightLine();
                    drawBottomLeftLine();
                    break;
                case 3:
                    /**
                     *   去掉左上和左下
                     *
                     *   ——
                     *     |
                     *   ——
                     *     |
                     *   ——
                     *
                     */
                    drawCenterLine();
                    drawTopLine();
                    drawBottomLine();
                    drawTopRightLine();
                    drawBottomRightLine();
                    break;
                case 4:
                    /**
                     *   去掉顶部、底部和左下
                     *
                     *
                     *  |  |
                     *   ——
                     *     |
                     *
                     */
                    drawCenterLine();
                    drawTopLeftLine();
                    drawTopRightLine();
                    drawBottomRightLine();
                    break;
                case 5:
                    /**
                     *   去掉右上和左下
                     *
                     *   ——
                     *  |
                     *   ——
                     *     |
                     *   ——
                     *
                     */
                    drawCenterLine();
                    drawTopLeftLine();
                    drawTopLine();
                    drawBottomLine();
                    drawBottomRightLine();
                    break;
                case 6:
                    /**
                     *   去掉右上
                     *
                     *   ——
                     *  |
                     *   ——
                     *  |  |
                     *   ——
                     *
                     */
                    drawCenterLine();
                    drawTopLine();
                    drawBottomLine();
                    drawTopLeftLine();
                    drawBottomLeftLine();
                    drawBottomRightLine();
                    break;
                case 7:
                    /**
                     *   ——
                     *     |
                     *
                     *     |
                     */
                    drawTopLine();
                    drawTopRightLine();
                    drawBottomRightLine();
                    break;
                case 8:
                    /**
                     * 全保留
                     *    __
                     *   |  |
                     *    ——
                     *   |  |
                     *    ——
                     */
                    drawCenterLine();
                    drawTopLeftLine();
                    drawTopLine();
                    drawBottomLine();
                    drawTopRightLine();
                    drawBottomLeftLine();
                    drawBottomRightLine();
                    break;
                case 9:
                    /**
                     *  去掉左下
                     *    __
                     *   |  |
                     *    ——
                     *      |
                     *    ——
                     */
                    drawCenterLine();
                    drawTopLeftLine();
                    drawTopLine();
                    drawBottomLine();
                    drawTopRightLine();
                    drawBottomRightLine();
                    break;
            }





    }


    public class CanvasNullPointException extends NullPointerException{

          public CanvasNullPointException(String msg){
              super(msg);
          }
    }



    /**
     * 画中间线
     *
     */
    private void drawCenterLine() {

        Paint numPaint = getPaint(lineColor);
        canvas.drawLine(-lineWidth / 2, 0, lineWidth / 2, 0, numPaint);

    }

    /**
     * 画top 线
     *
     */
    private void drawTopLine() {
        Paint numPaint = getPaint(lineColor);
        canvas.drawLine(-lineWidth / 2, -lineWidth - padding, lineWidth / 2, -lineWidth - padding, numPaint);

    }

    /**
     * 画底部的线
     *
     */
    private void drawBottomLine() {
        Paint numPaint = getPaint(lineColor);
        canvas.drawLine(-lineWidth / 2, lineWidth + padding, lineWidth / 2, lineWidth + padding, numPaint);
    }

    /**
     * 画左上的线
     *
     */
    private void drawTopLeftLine() {
        Paint numPaint = getPaint(lineColor);
        canvas.drawLine(-lineWidth / 2, -padding/2, -lineWidth / 2, -padding/2 - lineWidth, numPaint);
    }

    /**
     * 画右上的线
     *
     */
    private void drawTopRightLine() {

        Paint numPaint = getPaint(lineColor);
        canvas.drawLine(lineWidth / 2, -padding/2, lineWidth / 2, -padding/2 - lineWidth, numPaint);
    }

    /**
     * 画左下
     *
     */
    private void drawBottomLeftLine() {

        Paint numPaint = getPaint(lineColor);
        canvas.drawLine(-lineWidth / 2, padding/2, -lineWidth / 2, padding/2 + lineWidth, numPaint);
    }


    /**
     * 画右下
     *
     */
    private void drawBottomRightLine() {
        Paint numPaint = getPaint(lineColor);
        canvas.drawLine(lineWidth / 2, padding/2, lineWidth / 2, padding/2 + lineWidth, numPaint);

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
