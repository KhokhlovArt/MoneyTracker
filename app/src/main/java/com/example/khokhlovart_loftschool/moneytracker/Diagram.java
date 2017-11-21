package com.example.khokhlovart_loftschool.moneytracker;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Dom on 19.11.2017.
 */

public class Diagram extends View {
    private long exp;
    private long inc;
    private Paint expPaint = new Paint();
    private Paint incPaint = new Paint();
    public Diagram(Context context, AttributeSet attrs) {
        super(context, attrs);
        expPaint.setColor(getResources().getColor(R.color.balance_expense_color));
        incPaint.setColor(getResources().getColor(R.color.balance_income_color));
    }

    public void update(long exp, long inc) {
        this.exp = exp;
        this.inc = inc;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            drawPieDiagram(canvas);
        } else {
            drawRectDiagram(canvas);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        setMeasuredDimension(width, width);
    }

    private void drawRectDiagram(Canvas canvas) {
        if (exp + inc == 0)
            return;

        long max = Math.max(exp, inc);
        long expensesHeight = getHeight() * exp / max;
        long incomeHeight = getHeight() * inc / max;

        int w = getWidth() / 4;

        canvas.drawRect(w / 2, getHeight() - expensesHeight, w * 3 / 2, getHeight(), expPaint);
        canvas.drawRect(5 * w / 2, getHeight() - incomeHeight, w * 7 / 2, getHeight(), incPaint);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void drawPieDiagram(Canvas canvas) {
        if (exp + inc == 0)
            return;

        float expenseAngle = 360.f * exp / (exp + inc);
        float incomeAngle = 360.f * inc / (exp + inc);

        int space = 10; // space between income and expense
        int size = Math.min(getWidth(), getHeight()) - space * 2;
        final int xMargin = (getWidth() - size) / 2, yMargin = (getHeight() - size) / 2;

        canvas.drawArc(xMargin - space, yMargin, getWidth() - xMargin - space, getHeight() - yMargin, 180 - expenseAngle / 2, expenseAngle, true, expPaint);
        canvas.drawArc(xMargin + space, yMargin, getWidth() - xMargin + space, getHeight() - yMargin, 360 - incomeAngle / 2, incomeAngle, true, incPaint);
    }
}
