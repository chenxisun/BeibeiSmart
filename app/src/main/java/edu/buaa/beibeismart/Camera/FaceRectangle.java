package edu.buaa.beibeismart.Camera;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.hardware.camera2.params.Face;
import android.util.Log;
import android.view.SurfaceView;

/**
 * Created by harsh singh on 31-08-2017.
 */

public class FaceRectangle implements Runnable {

    Thread thread;
    String ThreadName;
    SurfaceView surfaceview;
    int cameraWidth,cameraHeight;
    Rect rectangleFace;
    int orientation_offset;



    Boolean loop = true;

    Face detectedFace;


    public FaceRectangle(String ThreadName, SurfaceView surfaceview, int orientation_offset, Face[] faces, int cameraWidth, int cameraHeight) throws InterruptedException {

        this.ThreadName = ThreadName;

        thread = new Thread(this,ThreadName);

        this.orientation_offset = orientation_offset;

        if(faces != null) {

            if (faces.length > 0) {
                detectedFace = faces[0];
                rectangleFace = detectedFace.getBounds();
            }

        }

        this.surfaceview = surfaceview;
        this.cameraWidth = cameraWidth;
        this.cameraHeight = cameraHeight;


        thread.start();


    }

    @Override
    public void run() {

        Log.d("harsh__", "thread was here");

        Paint paint = new Paint();
        paint.setColor(Color.GREEN);
        paint.setStyle(Paint.Style.STROKE);

        Paint paint2 = new Paint();
        paint2.setColor(Color.YELLOW);
        paint2.setStyle(Paint.Style.STROKE);

        Canvas currentCanvas = surfaceview.getHolder().lockCanvas();
        if (currentCanvas != null) {

            currentCanvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);

//            if (detectedFace != null && rectangleFace.height() > 0) {
//
//                int canvasWidth = currentCanvas.getWidth();
//                int canvasHeight = currentCanvas.getHeight();
//                int faceWidthOffset =  rectangleFace.width()/8;
//                int faceHeightOffset = rectangleFace.height()/8;
//
//                currentCanvas.save();
//                currentCanvas.rotate(360 - orientation_offset, canvasWidth / 2,
//                        canvasHeight / 2);
//
//                int l = rectangleFace.right;
//                int t = rectangleFace.bottom;
//                int r = rectangleFace.left;
//                int b = rectangleFace.top;
//                int left = (canvasWidth - (canvasWidth*l)/cameraWidth)-(faceWidthOffset);
//                int top  = (canvasHeight*t)/cameraHeight - (faceHeightOffset);
//                int right = (canvasWidth - (canvasWidth*r)/cameraWidth) + (faceWidthOffset);
//                int bottom = (canvasHeight*b)/cameraHeight + (faceHeightOffset);
//                Log.d("harsh___","drawing rect");
//                currentCanvas.drawRect(left, top, right, bottom, paint);
//
//
                Rect rec = new Rect(300,700,1100,1500);

            Rect rec2 = new Rect(450,300,900,600);


            currentCanvas.drawRect(rec,paint);
            currentCanvas.drawRect(rec2,paint2);
//                currentCanvas.restore();
//            }
        }
        surfaceview.getHolder().unlockCanvasAndPost(currentCanvas);

    }





}
