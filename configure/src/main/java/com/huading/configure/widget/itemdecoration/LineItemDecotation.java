package com.huading.configure.widget.itemdecoration;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;
import androidx.recyclerview.widget.RecyclerView;

import com.huading.configure.utils.ScreenUtil;

public class LineItemDecotation extends RecyclerView.ItemDecoration {

    Context con;
    private Paint mPaint;
    private float mDividerHeight;
    private float dividerTop;
    private float dividerLeft;
    private float dividerBottom;
    private float dividerRight;


    private float parentLeft = 0;
    private float parentRight = 0;
    private float lineHeight = 0;

    public LineItemDecotation(Context con) {
        this.con = con;
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.parseColor("#F8F8F8"));
    }

    public LineItemDecotation(Context con, float parentLeft, float parentRight, int lineHeight, int color) {
        this.con = con;
        this.parentLeft = parentLeft;
        this.parentRight = parentRight;
        this.lineHeight = lineHeight;
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(color);
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
        int childCount = parent.getChildCount();

        for (int i = 0; i < childCount; i++) {
            View view = parent.getChildAt(i);

            int index = parent.getChildAdapterPosition(view);
            //第一个ItemView不需要绘制
            if (index == 0) {
                continue;
            }

            dividerTop = view.getTop() - mDividerHeight;
            dividerLeft = parent.getPaddingLeft() + new ScreenUtil().dip2px(con, parentLeft);
            dividerBottom = view.getTop();
            dividerRight = parent.getWidth() - parent.getPaddingRight() - new ScreenUtil().dip2px(con, parentRight);

            c.drawRect(dividerLeft, dividerTop, dividerRight, dividerBottom, mPaint);
        }
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDrawOver(c, parent, state);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        if (parent.getChildAdapterPosition(view) != 0) {
            //这里直接硬编码为1px
            outRect.top = 15;
            outRect.left = 12;
            outRect.right = 12;
            mDividerHeight = lineHeight;

        }

    }
}
