package edu.buaa.beibeismart;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class RectView extends View {
    private Paint paint;
    private int left;
    private int top;
    private int right;
    private int bottom;
    private Rect rect;

    public RectView(Context context) {
        super(context);
    }

    public RectView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public RectView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public RectView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }


    public void setRect(int left,int top,int right,int bottom,int color){
        this.left=left;
        this.top=top;
        this.right=right;
        this.bottom=bottom;
        switch(color){
            case 4:
                paint.setColor(Color.parseColor("#FFBBFF"));//快乐
                break;
            case 0:
                paint.setColor(Color.RED);//愤怒
                break;
            case 2:
                paint.setColor(Color.parseColor("#1C86EE"));//悲伤
                break;
            case 3:
                paint.setColor(Color.parseColor("#32CD32"));//平静
                break;
            case 1:
                paint.setColor(Color.parseColor("#EEEE00"));//惊讶
                break;
                default:
                    paint.setColor(Color.BLACK);
        }
        invalidate();
    }

    public void initRect(){
        paint=new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(10);
    }

    @Override
    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);
        rect = new Rect(left,top,right,bottom);
        canvas.drawRect(rect,paint);
    }
}
