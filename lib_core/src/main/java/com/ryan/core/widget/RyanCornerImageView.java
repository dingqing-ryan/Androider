package com.ryan.core.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatImageView;

/**
 * @Author: Ryan
 * @Date: 2020/7/14 15:21
 * @Description: 圆角图片
 */
public class RyanCornerImageView extends AppCompatImageView {
    private int radius = 20; // 圆角半径
    private int border = 4; //边框宽度
    private int diameter = radius << 1; // 圆角直径
    private Paint paint;
    private Path path;
    private int leftTop, rightTop, rightBottom, leftBottom, width, height;

    public RyanCornerImageView(Context context) {
        super(context);
        initPaint();
    }

    public RyanCornerImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initPaint();
    }

    public RyanCornerImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
    }

    private void initPaint() {
        path = new Path();
        paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setAntiAlias(true);// 消除锯齿
        setPadding(border, border, border, border);
    }

    @Override
    public void draw(Canvas canvas) {
        width = getWidth();
        height = getHeight();
        Drawable drawable = getDrawable();
        if (drawable != null) {
            Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
            assert bitmap != null;
            Bitmap roundBitmap = getRoundBitmap(bitmap, radius);
            drawBorder(canvas, border, "#F5F5F5");
            final Rect rectSrc = new Rect(0, 0, roundBitmap.getWidth(), roundBitmap.getHeight());
            final Rect rectDest = new Rect(0, 0, getWidth(), getHeight());
            // 重置画笔，不然会留下黑色区域
            paint.reset();
            canvas.drawBitmap(roundBitmap, rectSrc, rectDest, paint);
        } else {
            super.draw(canvas);
        }

//        super.draw(canvas);
//        drawLeftTop(canvas);
//        drawRightTop(canvas);
//        drawLeftBottom(canvas);
//        drawRightBottom(canvas);
    }

    /**
     * 画边框
     *
     * @param canvas
     */
    private void drawBorder(Canvas canvas, int border, String color) {
        Rect rect = canvas.getClipBounds();
        rect.bottom--;
        rect.right--;
        rect.left++;
        rect.top++;
        Paint paint = new Paint();
        paint.setColor(Color.parseColor(color));
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(border);
        canvas.drawRoundRect(new RectF(rect), radius, radius, paint);
    }

    /**
     * 裁剪图片
     *
     * @param bitmap
     * @param corner
     * @return Bitmap
     */
    private Bitmap getRoundBitmap(Bitmap bitmap, int corner) {
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
                bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        int left = getPaddingLeft();
        int top = getPaddingTop();
        int right = bitmap.getWidth() - getPaddingRight();
        int bottom = bitmap.getHeight() - getPaddingBottom();
        final Rect rect = new Rect(left, top, right, bottom);
        final RectF rectF = new RectF(rect);
        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        canvas.drawRoundRect(rectF, corner, corner, paint);
        // 设置图像混合模式为SRC_IN，裁剪出我们的圆角Bitmap
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        return output;
    }

    /**
     * dp转 px.
     *
     * @param value the value
     * @return the int
     */
    private int dp2px(Context context, float value) {
        final float scale = context.getResources().getDisplayMetrics().densityDpi;
        return (int) (value * (scale / 160) + 0.5f);
    }

    private void drawLeftTop(Canvas canvas) {
        path.moveTo(0, radius);
        path.lineTo(0, 0);
        path.lineTo(radius, 0);
        path.arcTo(new RectF(0, 0, diameter, diameter), -90, -90);
        path.close();
        // 重置画笔，不然会留下黑色区域
        paint.reset();
        canvas.drawPath(path, paint);
    }

    private void drawRightTop(Canvas canvas) {
        path.moveTo(getWidth(), radius);
        path.lineTo(getWidth(), 0);
        path.lineTo(getWidth() - radius, 0);
        path.arcTo(new RectF(getWidth() - diameter, 0, getWidth(), diameter), -90, 90);
        path.close();
        // 重置画笔，不然会留下黑色区域
        paint.reset();
        canvas.drawPath(path, paint);
    }

    private void drawLeftBottom(Canvas canvas) {
        path.moveTo(0, getHeight() - radius);
        path.lineTo(0, getHeight());
        path.lineTo(radius, getHeight());
        path.arcTo(new RectF(0, getHeight() - diameter, diameter, getHeight()), 90, 90);
        path.close();
        // 重置画笔，不然会留下黑色区域
        paint.reset();
        canvas.drawPath(path, paint);
    }

    private void drawRightBottom(Canvas canvas) {
        path.moveTo(getWidth() - radius, getHeight());
        path.lineTo(getWidth(), getHeight());
        path.lineTo(getWidth(), getHeight() - radius);
        RectF oval = new RectF(getWidth() - diameter, getHeight() - diameter, getWidth(), getHeight());
        path.arcTo(oval, 0, 90);
        path.close();
        // 重置画笔，不然会留下黑色区域
        paint.reset();
        canvas.drawPath(path, paint);
    }
}
