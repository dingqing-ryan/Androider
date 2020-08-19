package com.ryan.core.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
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
    }

    @Override
    public void draw(Canvas canvas) {
//        leftTop = getTop() + getBottom();
//        width = getWidth();
//        height = getHeight();
//        Drawable drawable = getDrawable();
//        if (null != drawable) {
//            Bitmap bitmap = getBitmapFromDrawable(drawable);
//            Bitmap b = getRoundedCornerBitmap(bitmap);
//            canvas.drawBitmap(b,  0, 0, paint);
//        } else {
//            super.draw(canvas);
//        }
        super.draw(canvas);
        drawLeftTop(canvas);
        drawRightTop(canvas);
        drawLeftBottom(canvas);
        drawRightBottom(canvas);
    }

    /**
     * 把资源图片转换成Bitmap
     *
     * @param drawable 资源图片
     * @return 位图
     */
    private Bitmap getBitmapFromDrawable(Drawable drawable) {
        // 取 drawable 的长宽
        int w = drawable.getIntrinsicWidth();
        int h = drawable.getIntrinsicHeight();
        // 取 drawable 的颜色格式
        Bitmap.Config config = drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565;
        // 建立对应 bitmap
        Bitmap bitmap = Bitmap.createBitmap(w, h, config);
        // 建立对应 bitmap 的画布
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, width, height);
        // 把 drawable 内容画到画布中
        drawable.draw(canvas);
        return bitmap;
    }

    /**
     * to圆角Bitmap
     *
     * @param sourceBitmap
     * @return
     */
    private Bitmap getRoundedCornerBitmap(Bitmap sourceBitmap) {

        try {

            Bitmap targetBitmap = Bitmap.createBitmap(sourceBitmap.getWidth(), sourceBitmap.getHeight(), Bitmap.Config.ARGB_8888);
            // 得到画布
            Canvas canvas = new Canvas(targetBitmap);
            // 创建画笔
            Paint paint = new Paint();
            paint.setAntiAlias(true);
            // 绘制
            canvas.drawARGB(0, 0, 0, 0);
            Rect rect = new Rect(0, 0, sourceBitmap.getWidth(), sourceBitmap.getHeight());
            RectF rectF = new RectF(rect);
            canvas.drawRoundRect(new RectF(2.5f,2.5f,sourceBitmap.getWidth(),sourceBitmap.getHeight()), radius, radius, paint);
            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
            canvas.drawBitmap(sourceBitmap, rect, rect, paint);
            return targetBitmap;
        } catch (Exception | Error e) {
            e.printStackTrace();
        }
        return null;
    }

    private void drawLeftTop(Canvas canvas) {
        path.moveTo(0, radius);
        path.lineTo(0, 0);
        path.lineTo(radius, 0);
        path.arcTo(new RectF(0, 0, diameter, diameter), -90, -90);
        path.close();
        canvas.drawPath(path, paint);
    }

    private void drawRightTop(Canvas canvas) {
        path.moveTo(getWidth(), radius);
        path.lineTo(getWidth(), 0);
        path.lineTo(getWidth() - radius, 0);
        path.arcTo(new RectF(getWidth() - diameter, 0, getWidth(), diameter), -90, 90);
        path.close();
        canvas.drawPath(path, paint);
    }

    private void drawLeftBottom(Canvas canvas) {
        path.moveTo(0, getHeight() - radius);
        path.lineTo(0, getHeight());
        path.lineTo(radius, getHeight());
        path.arcTo(new RectF(0, getHeight() - diameter, diameter, getHeight()), 90, 90);
        path.close();
        canvas.drawPath(path, paint);
    }

    private void drawRightBottom(Canvas canvas) {
        path.moveTo(getWidth() - radius, getHeight());
        path.lineTo(getWidth(), getHeight());
        path.lineTo(getWidth(), getHeight() - radius);
        RectF oval = new RectF(getWidth() - diameter, getHeight() - diameter, getWidth(), getHeight());
        path.arcTo(oval, 0, 90);
        path.close();
        canvas.drawPath(path, paint);
    }
}
