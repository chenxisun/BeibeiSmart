package edu.buaa.beibeismart.View;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ItemDecoration;
import android.support.v7.widget.RecyclerView.State;
import android.view.View;
/**
 * Created by lu on 2017/8/29 16:07
 */
public class DividerItemLineDecoration extends ItemDecoration {
    private static final int[] ATTRS = new int[]{
            android.R.attr.listDivider
    };
    public static final int HORIZONTAL_LIST = LinearLayoutManager.HORIZONTAL;
    public static final int VERTICAL_LIST = LinearLayoutManager.VERTICAL;
    private Drawable mDivider;
    private int mOrientation;
    //public final static int START = 0x02;//第一个item顶部无偏移,没有实现第一个上方分割线
    public final static int INSIDE = 0x00;
    public final static int END = 0x01;
    int linePadding = 0;
    private int dividerMode = INSIDE;
    public DividerItemLineDecoration(Context context, int orientation) {
        final TypedArray a = context.obtainStyledAttributes(ATTRS);
        Drawable divider = a.getDrawable(0);
        init(orientation, divider);
        a.recycle();
    }
    public DividerItemLineDecoration(Context context, int orientation, int drawableId, int linePadding) {
        this.linePadding = linePadding;
        Drawable divider = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            divider = context.getDrawable(drawableId);
        } else {
            divider = context.getResources().getDrawable(drawableId);
        }
        init(orientation, divider);
    }
    public DividerItemLineDecoration(Context context, int orientation, Drawable divider) {
        init(orientation, divider);
    }
    private void init(int orientation, Drawable divider) {
        mDivider = divider;
        setOrientation(orientation);
    }
    public void setOrientation(int orientation) {
        if (orientation != HORIZONTAL_LIST && orientation != VERTICAL_LIST) {
            throw new IllegalArgumentException("invalid orientation");
        }
        mOrientation = orientation;
    }
    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        if (mOrientation == VERTICAL_LIST) {
            drawVertical(c, parent);
        } else {
            drawHorizontal(c, parent);
        }
    }
    public int getDividerMode() {
        return dividerMode;
    }
    public void setDividerMode(int dividerMode) {
        this.dividerMode = dividerMode;
    }
    public void drawVertical(Canvas c, RecyclerView parent) {
        final int left = parent.getPaddingLeft() + linePadding;
        final int right = parent.getWidth() - parent.getPaddingRight() - linePadding;
        final int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            RecyclerView v = new RecyclerView(parent.getContext());
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
// if (dividerMode == START) {
//final int bottom = child.getTop() + params.bottomMargin;
//final int top = bottom - mDivider.getIntrinsicHeight();
//;
//
//mDivider.setBounds(left, top, right, bottom);
//mDivider.draw(c);
            if (dividerMode == END) {
                final int top = child.getBottom() + params.bottomMargin;
                final int bottom = top + mDivider.getIntrinsicHeight();
                mDivider.setBounds(left, top, right, bottom);
                mDivider.draw(c);
            } else if (dividerMode == (INSIDE | END)) {
                if (i == 0) {
                    final int bottom = child.getTop() + params.bottomMargin;
                    final int top = bottom - mDivider.getIntrinsicHeight();
                    ;
                    mDivider.setBounds(left, top, right, bottom);
                    mDivider.draw(c);
                }
                final int top = child.getBottom() + params.bottomMargin;
                final int bottom = top + mDivider.getIntrinsicHeight();
                mDivider.setBounds(left, top, right, bottom);
                mDivider.draw(c);
            } else {//默认绘制
                if (i < childCount - 1) {
                    final int top = child.getBottom() + params.bottomMargin;
                    final int bottom = top + mDivider.getIntrinsicHeight();
                    mDivider.setBounds(left, top, right, bottom);
                    mDivider.draw(c);
                }
            }
        }
    }
    public void drawHorizontal(Canvas c, RecyclerView parent) {
        final int top = parent.getPaddingTop() + linePadding;
        final int bottom = parent.getHeight() - parent.getPaddingBottom() - linePadding;
        final int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                    .getLayoutParams();
// if (dividerMode == START) {
//final int right = child.getRight() + params.rightMargin;
//final int left = right - mDivider.getIntrinsicHeight();
//mDivider.setBounds(left, top, right, bottom);
//mDivider.draw(c);
            if (dividerMode == END) {
                final int left = child.getRight() + params.rightMargin;
                final int right = left + mDivider.getIntrinsicHeight();
                mDivider.setBounds(left, top, right, bottom);
                mDivider.draw(c);
            } else if (dividerMode == (INSIDE | END)) {
                if (i == 0) {
                    final int right = child.getRight() + params.rightMargin;
                    final int left = right - mDivider.getIntrinsicHeight();
                    mDivider.setBounds(left, top, right, bottom);
                    mDivider.draw(c);
                }
                final int left = child.getRight() + params.rightMargin;
                final int right = left + mDivider.getIntrinsicHeight();
                mDivider.setBounds(left, top, right, bottom);
                mDivider.draw(c);
            } else {//(dividerMode == INSIDE) 和其它值
                if (i < childCount - 1) {
                    final int left = child.getRight() + params.rightMargin;
                    final int right = left + mDivider.getIntrinsicHeight();
                    mDivider.setBounds(left, top, right, bottom);
                    mDivider.draw(c);
                }
            }
        }
    }
    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, State state) {
        super.getItemOffsets(outRect, view, parent, state);
        if (mOrientation == VERTICAL_LIST) {
            outRect.set(0, 0, 0, mDivider.getIntrinsicHeight());
        } else {
            outRect.set(0, 0, mDivider.getIntrinsicWidth(), 0);
        }
    }
}