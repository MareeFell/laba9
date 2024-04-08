package com.example.lab9;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.SurfaceView;

import math.Arr;
import math.Interpolation;

public class Graphic extends SurfaceView {
    private float[] x, y;
    private int n;
    private Paint currentPaint;
    private Integer xMin;
    private Integer xMax;

    public Graphic(Context context, AttributeSet attrs) {
        super(context, attrs);
        setWillNotDraw(false);
    }

    public void update(float[] x, float[] y, int n, Paint paint, Integer xMin, Integer xMax) {
        this.x = x;
        this.n = n;
        this.currentPaint = paint;
        this.y = y;
        this.xMin = xMin;
        this.xMax = xMax;

        this.invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(Color.WHITE);
        int width = getWidth();
        int height = getHeight();

        float xMin = this.xMin == null ? Arr.min(x, n) : this.xMin,
                xMax = this.xMax == null ? Arr.max(x, n) : this.xMax,
                yMin = Arr.min(y, n),
                yMax = Arr.max(y, n);

        float x0 = 0.0f, y0 = 0.0f;
        for (int i = 0; i < n; i++) {
            float x1 = Interpolation.map(x[i], xMin, xMax, 0, width - 1);
            float y1 = Interpolation.map(y[i], yMin, yMax, height - 1, 0);

            if (i > 0) canvas.drawLine(x0, y0, x1, y1, currentPaint);

            x0 = x1;
            y0 = y1;
        }
    }
}


