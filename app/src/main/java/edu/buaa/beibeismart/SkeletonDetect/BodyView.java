package edu.buaa.beibeismart.SkeletonDetect;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/*
人体框
 */
public class BodyView extends View {
    private final String TAG = this.getClass().toString();
    private BodyKeyPoint mBodyKeyPoint;
    private Paint mPointPaint;
    private Paint mLinePaint;
    private Paint mRectPaint;

    public BodyView(Context context) {
        super(context);
        this.init();
    }

    public BodyView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.init();
    }

    public BodyView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.init();
    }

    /*
    初始化
     */
    private void init(){
        this.mPointPaint = new Paint();
        this.mPointPaint.setStyle(Paint.Style.FILL);
        this.mPointPaint.setColor(Color.BLUE);
        this.mPointPaint.setStrokeWidth(10);

        this.mLinePaint = new Paint();
        this.mLinePaint.setStyle(Paint.Style.STROKE);
        this.mLinePaint.setColor(Color.GREEN);
        this.mLinePaint.setStrokeWidth(3);

        this.mRectPaint = new Paint();
        this.mRectPaint.setStyle(Paint.Style.STROKE);
        this.mRectPaint.setColor(Color.RED);
        this.mRectPaint.setStrokeWidth(1);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Rect bodyRect;
        int personNum;
        // 人体关键点 绘制内容--------------------------------------
        if (this.mBodyKeyPoint == null) {
            return;
            }
            // 人体区域
        bodyRect = new Rect();
        personNum = this.mBodyKeyPoint.getPerson_num();
        Log.d(TAG, ">>>>>>>>>识别人体数：" + personNum);

            // 遍历识别到的人体
        for (int i = 0; i < personNum; i++) {
                // 获取一个person_info
            BodyKeyPoint.Person_info person_info = this.mBodyKeyPoint.getPerson_info().get(i);

            // 获取人体区域坐标
            BodyKeyPoint.Location location = person_info.getLocation();
            bodyRect.set((int) location.getLeft(), (int) location.getTop(), (int) location.getLeft() + (int) location.getWidth(), (int) location.getTop() + (int) location.getHeight());
            Log.d(TAG, ">>>>>>>>>left:" + location.getLeft() +
                    ", top:" + location.getTop() +
                    ", width:" + location.getWidth() +
                    ", height:" + location.getHeight());
            canvas.drawRect(bodyRect, this.mRectPaint);

            // 获取人体骨骼点
            float[] points = new float[28];
            BodyKeyPoint.Body_parts parts = person_info.getBody_parts();
            // 颈部
            points[0] = (float) parts.getNeck().getX();
            points[1] = (float) parts.getNeck().getY();

            // 左肩
            points[2] = (float) parts.getLeft_shoulder().getX();
            points[3] = (float) parts.getLeft_shoulder().getY();
            // 左膝盖
            points[4] = (float) parts.getLeft_knee().getX();
            points[5] = (float) parts.getLeft_knee().getY();
            // 左脚踝
            points[6] = (float) parts.getLeft_ankle().getX();
            points[7] = (float) parts.getLeft_ankle().getY();
            // 左手肘
            points[8] = (float) parts.getLeft_elbow().getX();
            points[9] = (float) parts.getLeft_elbow().getY();
            // 左髋部
            points[10] = (float) parts.getLeft_hip().getX();
            points[11] = (float) parts.getLeft_hip().getY();
            // 左手腕
            points[12] = (float) parts.getLeft_wrist().getX();
            points[13] = (float) parts.getLeft_wrist().getY();

            // 右肩
            points[14] = (float) parts.getRight_shoulder().getX();
            points[15] = (float) parts.getRight_shoulder().getY();
            // 右膝盖
            points[16] = (float) parts.getRight_knee().getX();
            points[17] = (float) parts.getRight_knee().getY();
            // 右脚踝
            points[18] = (float) parts.getRight_ankle().getX();
            points[19] = (float) parts.getRight_ankle().getY();
            // 右手肘
            points[20] = (float) parts.getRight_elbow().getX();
            points[21] = (float) parts.getRight_elbow().getY();
            // 右髋部
            points[22] = (float) parts.getRight_hip().getX();
            points[23] = (float) parts.getRight_hip().getY();
            // 右手腕
            points[24] = (float) parts.getRight_wrist().getX();
            points[25] = (float) parts.getRight_wrist().getY();

            // 鼻子
            points[26] = (float) parts.getNose().getX();
            points[27] = (float) parts.getNose().getY();

            canvas.drawPoints(points, this.mPointPaint);

            // 连线(这里需要处理下，因为如果没有识别出来那么就为0点)
            //至少要包含鼻子或者颈部
            float[] linePoints = new float[52];
            // 鼻子-颈部
            linePoints[0] = (points[26]==0)?points[0]:points[26];
            linePoints[1] = (points[27]==0)?points[1]:points[27];
            linePoints[2] = (points[0]==0)?((points[2]==0)?points[14]:points[2]):points[0];
            linePoints[3] = (points[1]==0)?((points[3]==0)?points[15]:points[3]):points[1];
            // 颈部-右肩
            linePoints[4] = linePoints[2];
            linePoints[5] = linePoints[3];
            linePoints[6] = (points[14]==0)?points[0]:points[14];
            linePoints[7] = (points[15]==0)?points[1]:points[15];
            // 颈部-左肩
            linePoints[8] = points[0];
            linePoints[9] = points[1];
            linePoints[10] = (points[2]==0)?points[0]:points[2];
            linePoints[11] = (points[3]==0)?points[1]:points[3];
            // 左肩-左肘
            linePoints[12] = linePoints[10];
            linePoints[13] = linePoints[11];
            linePoints[14] = (points[8]==0)?linePoints[12]:points[8];
            linePoints[15] = (points[9]==0)?linePoints[13]:points[9];
            // 左肘-左腕
            linePoints[16] = linePoints[14];
            linePoints[17] = linePoints[15];
            linePoints[18] = (points[12]==0)?linePoints[16]:points[12];
            linePoints[19] = (points[13]==0)?linePoints[17]:points[13];
            // 右肩-右肘
            linePoints[20] = linePoints[6];
            linePoints[21] = linePoints[7];
            linePoints[22] = (points[20]==0)?linePoints[20]:points[20];
            linePoints[23] = (points[21]==0)?linePoints[21]:points[21];
            // 右肘-右腕
            linePoints[24] = linePoints[22];
            linePoints[25] = linePoints[23];
            linePoints[26] = (points[24]==0)?linePoints[24]:points[24];
            linePoints[27] = (points[25]==0)?linePoints[25]:points[25];
            // -----
            // 左肩-左髋
            linePoints[28] = linePoints[10];
            linePoints[29] = linePoints[11];
            linePoints[30] = (points[10]==0)?linePoints[28]:points[10];
            linePoints[31] = (points[11]==0)?linePoints[29]:points[11];
            //左髋-左膝盖
            linePoints[32] = linePoints[30];
            linePoints[33] = linePoints[31];
            linePoints[34] = (points[4]==0)?linePoints[32]:points[4];
            linePoints[35] = (points[5]==0)?linePoints[33]:points[5];
            // 左膝盖-左脚踝
            linePoints[36] = linePoints[34];
            linePoints[37] = linePoints[35];
            linePoints[38] = (points[6]==0)?linePoints[36]:points[6];
            linePoints[39] = (points[7]==0)?linePoints[37]:points[7];
            // 右肩-右髋
            linePoints[40] = linePoints[6];
            linePoints[41] = linePoints[7];
            linePoints[42] = (points[22]==0)?linePoints[40]:points[22];
            linePoints[43] = (points[23]==0)?linePoints[41]:points[23];
            //右髋-右膝盖
            linePoints[44] = linePoints[42];
            linePoints[45] = linePoints[43];
            linePoints[46] = (points[16]==0)?linePoints[44]:points[16];
            linePoints[47] = (points[17]==0)?linePoints[45]:points[17];
            // 右膝盖-右脚踝
            linePoints[48] = linePoints[46];
            linePoints[49] = linePoints[47];
            linePoints[50] = (points[18]==0)?linePoints[48]:points[18];
            linePoints[51] = (points[19]==0)?linePoints[49]:points[19];

            canvas.drawLines(linePoints, this.mLinePaint);
            }
            // 清理
            this.mBodyKeyPoint = null;
        }
    public void clear(){

    }

    /*
    设置Body
     */
    public void setBody(BodyKeyPoint body){
        mBodyKeyPoint = body;
        // 强制重绘
        invalidate();
    }
}
