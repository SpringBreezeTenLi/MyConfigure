package com.huading.configure.widget.itemdecoration;

import android.content.Context;
import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.huading.configure.utils.ScreenUtil;

/**
 * 文 件 名：RvSpaceItemDecoration
 * 描   述：Rv布局进行间隔设置
 */
public class RvSpaceItemDecoration extends RecyclerView.ItemDecoration {

    private String TAG = "RvSpaceItemDecoration";
    private int mSpacing;
    private boolean mIsSpaceFirstRowTop = true;//默认true

    public RvSpaceItemDecoration(Context context, int spacing) {
        this.mSpacing = new ScreenUtil().dip2px(context, spacing);
    }

    /**
     * @param context
     * @param spacing
     * @param isSpaceFirstRowTop 第一行头部是否有间距
     */
    public RvSpaceItemDecoration(Context context, int spacing, boolean isSpaceFirstRowTop) {
        this.mSpacing = new ScreenUtil().dip2px(context, spacing);
        mIsSpaceFirstRowTop = isSpaceFirstRowTop;
    }


    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        int itemPosition = parent.getChildAdapterPosition(view); // item position
        int spanCount = getSpanCount(parent);
        int childCount = parent.getAdapter().getItemCount();
        int column = itemPosition % spanCount + 1; // item column

        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            if (column == 1) {
                outRect.set(mSpacing, mSpacing, mSpacing / 2, 0);
            } else if (column == spanCount) {
                outRect.set(mSpacing / 2, mSpacing, mSpacing, 0);
            } else {
                outRect.set(mSpacing / 2, mSpacing, mSpacing / 2, 0);
            }
            if (isLastRaw(parent, itemPosition, spanCount, childCount)) {// 如果是最后一行，则不需要绘制底部
                outRect.bottom = mSpacing;
            }
        } else if (layoutManager instanceof LinearLayoutManager) {
            if (itemPosition == 0) {
                if (!mIsSpaceFirstRowTop) {//第一行头部不出现间距
                    outRect.set(mSpacing, 0, mSpacing, 0);
                } else {
                    outRect.set(mSpacing, mSpacing, mSpacing, 0);
                }
            } else {
                outRect.set(mSpacing, mSpacing, mSpacing, 0);
            }


            if (isLastRaw(parent, itemPosition, spanCount, childCount)) {// 如果是最后一行，则不需要绘制底部
                outRect.bottom = mSpacing;
            }
        }


    }

    //获取列数
    private int getSpanCount(RecyclerView parent) {
        // 列数
        int spanCount = -1;
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            spanCount = ((GridLayoutManager) layoutManager).getSpanCount();
        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
            spanCount = ((StaggeredGridLayoutManager) layoutManager).getSpanCount();
        } else if (layoutManager instanceof LinearLayoutManager) {
            spanCount = 1;
        }
        return spanCount;
    }

    // 判断是否是最后一行
    private boolean isLastRaw(RecyclerView parent, int pos, int spanCount, int childCount) {
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            int totalRawCount = childCount / spanCount + childCount % spanCount;//总行数
            //当前postion所在行数
            int currentPosRaw = (pos + 1) / spanCount + (pos + 1) % spanCount;

            if (currentPosRaw == totalRawCount) {//当前postiton的view所在行数等于总行数 则为最后一行
                return true;
            }
        } else if (layoutManager instanceof LinearLayoutManager) {
            if (pos == childCount - 1) {
                return true;
            }
        }
        return false;
    }
}
