package edu.buaa.beibeismart.Activity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.ImageFormat;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.SurfaceTexture;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.CaptureResult;
import android.hardware.camera2.params.StreamConfigurationMap;
import android.media.ImageReader;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.util.Size;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import edu.buaa.beibeismart.AutoFitTextureView;
import edu.buaa.beibeismart.Bean.facebean;
import edu.buaa.beibeismart.R;
import edu.buaa.beibeismart.RectView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class EmotionActivity extends BaseActivity implements View.OnClickListener, ActivityCompat.OnRequestPermissionsResultCallback,Runnable{
//    public static final String Action = "edu.buaa.beibeismart.intent.emotionActivity";
    private static final int MAX_PREVIEW_WIDTH = 1080;
    private static final int MAX_PREVIEW_HEIGHT = 2280;
    private AutoFitTextureView surfaceView;
    private Size mPreviewSize;
    private String mCameraId;
    private int mSensorOrientation;
    private ImageReader mImageReader;
    private CameraDevice mCameraDevice;
    private CaptureRequest.Builder mCaptureRequestBuilder;
    private CaptureRequest mCaptureRequest;
    private CameraCaptureSession mPreviewSession;
    private Activity activity;
    private String mImageType = "multipart/form-data";
    private String apikey = "k9ekvKztESZHXaTOrrLaqbKiS5gaAb0J";
    private String apiSecret = "Sv9JzlifyHxCOOVsD6hNnXSqOF2T4YR5";
    private String url = "https://api-cn.faceplusplus.com/facepp/v3/detect";
    private String emotion = "emotion";
    private File tfile;
    private TextView resultText1;
    private TextView resultText2;
    private TextView resultText3;
    private TextView resultText4;
    private TextView resultText5;
    private Button button;
    private Paint paint;
    private RectView rectView;
    private static int noFaceNumber = 0;

    private final TextureView.SurfaceTextureListener surfaceTextureListener
            = new TextureView.SurfaceTextureListener() {

        @Override
        public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
            //当SurefaceTexture可用的时候，设置相机参数并打开相机
            setupCamera(width, height);
            openCamera();
        }



        @Override
        public void onSurfaceTextureSizeChanged(SurfaceTexture texture, int width, int height) {
//            configureTransform(width, height);
        }

        @Override
        public boolean onSurfaceTextureDestroyed(SurfaceTexture texture) {
            return true;
        }

        @Override
        public void onSurfaceTextureUpdated(SurfaceTexture texture) {

        }

    };

    private void setupCamera(int width, int height) {
        //获取摄像头的管理者CameraManager
        CameraManager manager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
        try {
            //遍历所有摄像头
            for (String cameraId: manager.getCameraIdList()) {
                CameraCharacteristics characteristics = manager.getCameraCharacteristics(cameraId);
                //默认打开后置摄像头
                if (characteristics.get(CameraCharacteristics.LENS_FACING) == CameraCharacteristics.LENS_FACING_FRONT)
                    continue;
                //获取StreamConfigurationMap，它是管理摄像头支持的所有输出格式和尺寸
                StreamConfigurationMap map = characteristics.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP);

                Size largest = Collections.max(
                        Arrays.asList(map.getOutputSizes(ImageFormat.JPEG)),
                        new CompareSizesByArea());
                mImageReader = ImageReader.newInstance(largest.getWidth(), largest.getHeight(),
                        ImageFormat.JPEG, /*maxImages*/2);
//                mImageReader.setOnImageAvailableListener(mOnImageAvailableListener, mBackgroundHandler);

                // Find out if we need to swap dimension to get the preview size relative to sensor
                // coordinate.
                Activity activity = this;
                int displayRotation = activity.getWindowManager().getDefaultDisplay().getRotation();
                //noinspection ConstantConditions
                mSensorOrientation = characteristics.get(CameraCharacteristics.SENSOR_ORIENTATION);
                boolean swappedDimensions = false;
                switch (displayRotation) {
                    case Surface.ROTATION_0:
                    case Surface.ROTATION_180:
                        if (mSensorOrientation == 90 || mSensorOrientation == 270) {
                            swappedDimensions = true;
                        }
                        break;
                    case Surface.ROTATION_90:
                    case Surface.ROTATION_270:
                        if (mSensorOrientation == 0 || mSensorOrientation == 180) {
                            swappedDimensions = true;
                        }
                        break;
                    default:
                        Log.e(TAG, "Display rotation is invalid: " + displayRotation);
                }

                Point displaySize = new Point();
                activity.getWindowManager().getDefaultDisplay().getSize(displaySize);
                int rotatedPreviewWidth = width;
                int rotatedPreviewHeight = height;
                int maxPreviewWidth = displaySize.x;
                int maxPreviewHeight = displaySize.y;

                if (swappedDimensions) {
                    rotatedPreviewWidth = height;
                    rotatedPreviewHeight = width;
                    maxPreviewWidth = displaySize.y;
                    maxPreviewHeight = displaySize.x;
                }

                if (maxPreviewWidth > MAX_PREVIEW_WIDTH) {
                    maxPreviewWidth = MAX_PREVIEW_WIDTH;
                }

                if (maxPreviewHeight > MAX_PREVIEW_HEIGHT) {
                    maxPreviewHeight = MAX_PREVIEW_HEIGHT;
                }


                //根据TextureView的尺寸设置预览尺寸
                mPreviewSize = chooseOptimalSize(map.getOutputSizes(SurfaceTexture.class),
                        rotatedPreviewWidth, rotatedPreviewHeight, maxPreviewWidth,
                        maxPreviewHeight, largest);

                int orientation = getResources().getConfiguration().orientation;
                //下方代码使得前端展示控件长宽比固定防止照片拉伸
                if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
                    surfaceView.setAspectRatio(
                            mPreviewSize.getWidth(), mPreviewSize.getHeight());
                } else {
                    surfaceView.setAspectRatio(
                            mPreviewSize.getHeight(), mPreviewSize.getWidth());
                }

                mCameraId = cameraId;
                break;
            }
        }catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    private void openCamera() {
        //获取摄像头的管理者CameraManager
        CameraManager manager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
        //检查权限
        try {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            //打开相机，第一个参数指示打开哪个摄像头，第二个参数stateCallback为相机的状态回调接口，第三个参数用来确定Callback在哪个线程执行，为null的话就在当前线程执行
            manager.openCamera(mCameraId, stateCallback, null);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    private final CameraDevice.StateCallback stateCallback = new CameraDevice.StateCallback() {
        @Override
        public void onOpened(CameraDevice camera) {
            mCameraDevice = camera;
            //开启预览
            startPreview();
        }

        @Override
        public void onDisconnected(@NonNull CameraDevice camera) {

        }

        @Override
        public void onError(@NonNull CameraDevice camera, int error) {

        }
    };

    private void startPreview() {
        SurfaceTexture mSurfaceTexture = surfaceView.getSurfaceTexture();
        //设置TextureView的缓冲区大小
        mSurfaceTexture.setDefaultBufferSize(mPreviewSize.getWidth(), mPreviewSize.getHeight());
        //获取Surface显示预览数据
        Surface mSurface = new Surface(mSurfaceTexture);
        try {
            //创建CaptureRequestBuilder，TEMPLATE_PREVIEW比表示预览请求
            mCaptureRequestBuilder = mCameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW);
            //设置Surface作为预览数据的显示界面
            mCaptureRequestBuilder.addTarget(mSurface);
            //创建相机捕获会话，第一个参数是捕获数据的输出Surface列表，第二个参数是CameraCaptureSession的状态回调接口，当它创建好后会回调onConfigured方法，第三个参数用来确定Callback在哪个线程执行，为null的话就在当前线程执行
            mCameraDevice.createCaptureSession(Arrays.asList(mSurface), new CameraCaptureSession.StateCallback() {
                @Override
                public void onConfigured(CameraCaptureSession session) {
                    try {
                        //创建捕获请求
                        mCaptureRequest = mCaptureRequestBuilder.build();
                        mPreviewSession = session;
                        //设置反复捕获数据的请求，这样预览界面就会一直有数据显示
                        mPreviewSession.setRepeatingRequest(mCaptureRequest, mSessionCaptureCallback, null);
                    } catch (CameraAccessException e) {
                        e.printStackTrace();
                    }
                    new Thread(EmotionActivity.this).start();
                }

                @Override
                public void onConfigureFailed(CameraCaptureSession session) {

                }
            }, null);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    private CameraCaptureSession.CaptureCallback mSessionCaptureCallback
            = new CameraCaptureSession.CaptureCallback() {

        private void process(CaptureResult result) {
                //do nothing
            }
        };


    @Override
    protected void initView() {
        setContentView(R.layout.activity_emotion);
        surfaceView = (AutoFitTextureView)findViewById(R.id.surfaceText);
        surfaceView.setSurfaceTextureListener(surfaceTextureListener);
        resultText1 = (TextView)findViewById(R.id.resultText1);
        resultText2=(TextView)findViewById(R.id.resultText2);
        resultText3 = (TextView)findViewById(R.id.resultText3);
        resultText4=(TextView)findViewById(R.id.resultText4);
        resultText5=(TextView)findViewById(R.id.resultText5);
        rectView=(RectView)findViewById(R.id.rectView);
        button=(Button)findViewById(R.id.returnback);
        rectView.initRect();
        paint = new Paint();
        paint.setColor(Color.rgb(255, 0, 0));
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(5);
        findViewById(R.id.returnback).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(EmotionActivity.this,MainActivity.class));
            }
        });
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    @Override
    public void onVolleyFinish(int isSuccess, Object result) {

    }

    @Override
    public void onClick(View v) {

    }


    private static Size chooseOptimalSize(Size[] choices, int textureViewWidth,
                                          int textureViewHeight, int maxWidth, int maxHeight, Size aspectRatio) {

        // Collect the supported resolutions that are at least as big as the preview Surface
        List<Size> bigEnough = new ArrayList<>();
        // Collect the supported resolutions that are smaller than the preview Surface
        List<Size> notBigEnough = new ArrayList<>();
        int w = aspectRatio.getWidth();
        int h = aspectRatio.getHeight();
        for (Size option : choices) {
            if (option.getWidth() <= maxWidth && option.getHeight() <= maxHeight &&
                    option.getHeight() == option.getWidth() * h / w) {
                if (option.getWidth() >= textureViewWidth &&
                        option.getHeight() >= textureViewHeight) {
                    bigEnough.add(option);
                } else {
                    notBigEnough.add(option);
                }
            }
        }

        // Pick the smallest of those big enough. If there is no one big enough, pick the
        // largest of those not big enough.
        if (bigEnough.size() > 0) {
            return Collections.min(bigEnough, new CompareSizesByArea());
        } else if (notBigEnough.size() > 0) {
            return Collections.max(notBigEnough, new CompareSizesByArea());
        } else {
            //Log.e(TAG, "Couldn't find any suitable preview size");
            return choices[0];
        }
    }


    @Override
    public void run() {
        while(!Thread.currentThread().isInterrupted()) {
            final File mFile = new File(this.getExternalFilesDir(null), getDate() + ".jpg");
            final Bitmap bitmap = surfaceView.getBitmap();
//            final String filePath = getExternalFilesDir(null).getAbsolutePath();
//            final String fileName = getDate()+".jpg";
            FileOutputStream output = null;
            try {
                output = new FileOutputStream(mFile);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 15, output);
                output.flush();
                output.close();

                RequestBody fileBody = RequestBody.create(MediaType.parse("image/jpeg"), mFile);
                RequestBody requestBody = new MultipartBody.Builder()
                        .setType(MultipartBody.FORM)
                        .addFormDataPart("api_key", apikey)
                        .addFormDataPart("api_secret", apiSecret)
                        .addFormDataPart("image_file", "image_file.jpg", fileBody)
                        .addFormDataPart("imagetype", mImageType)
                        .addFormDataPart("return_attributes", emotion)
                        .build();
                final Request request = new Request.Builder()
                        .url(url)
                        .post(requestBody)
                        .build();
                OkHttpClient.Builder httpBuilder = new OkHttpClient.Builder();
                OkHttpClient okHttpClient = httpBuilder
                        .build();
                okHttpClient.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        bitmap.recycle();
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
//                        mFile.delete();
                        bitmap.recycle();
                        final String htmlStr = response.body().string();

                        try {
                            JSONObject js = new JSONObject(htmlStr);
                            js.get("faces");
                            Log.i(TAG, js.get("faces").toString());
                            if (new GsonBuilder().create().fromJson(htmlStr, facebean.class).getFaces().size() != 0) {
                                final facebean facebean1 = new GsonBuilder().create().fromJson(htmlStr, facebean.class);
                                int a = facebean1.getFaces().size();
                                if(a>1){a=1;}//多个人脸存在很多BUG，当前仅测试单个人脸的情况
                                for(int t=0;t<a;t++) {
                                    String result;
                                    result = "";
                                    facebean.FacesBean.AttributesBean.EmotionBean emotion = facebean1.getFaces().get(t).getAttributes().getEmotion();
                                    double[] sortData = {emotion.getAnger(), emotion.getSurprise(), emotion.getSadness(), emotion.getNeutral(), emotion.getHappiness(), emotion.getFear(), emotion.getDisgust()};
                                    String[] sortString = {"愤怒", "惊讶", "悲伤", "平静", "开心", "恐惧", "厌恶"};
//                                    for (int i = 0; i < 7; i++) {
                                        double max = 0;
                                        int maxIndex = 0;
                                        for (int j = 0; j < 7; j++) {
                                            if (sortData[j] > max) {
                                                max = sortData[j];
                                                maxIndex = j;
                                            }
                                        }
                                        result = result + sortString[maxIndex] + "    ";
                                        sortData[maxIndex] = -1;
//                                    }
                                    final int color = maxIndex;
                                    final String endResult = result;
                                    final int finger = t;
                                    final int left = facebean1.getFaces().get(0).getFace_rectangle().getLeft();
                                    final int right = (facebean1.getFaces().get(0).getFace_rectangle().getLeft()+facebean1.getFaces().get(0).getFace_rectangle().getWidth());
                                    final int top = facebean1.getFaces().get(0).getFace_rectangle().getTop();
                                    final int bottom = (facebean1.getFaces().get(0).getFace_rectangle().getTop()+facebean1.getFaces().get(0).getFace_rectangle().getHeight());
//                                deleteFile(filePath+fileName);
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            //更新UI
                                            switch(finger){
                                                case 0:
                                                    if (endResult != null) {
                                                    FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                                                    layoutParams.setMargins(right, top, 0, 0);
                                                    rectView.setRect(left,top,right,bottom,color);
                                                    resultText1.setLayoutParams(layoutParams);
//                                                    resultText1.setTextSize(40);
                                                    resultText1.setTextColor(Color.BLUE);
                                                    resultText1.setText(endResult);
                                                    }
                                                    break;
//                                                case 1:
//                                                    if (endResult != null) { resultText2.setText(endResult); }
//                                                    break;
//                                                case 2:
//                                                    if (endResult != null) { resultText3.setText(endResult); }
//                                                    break;
//                                                case 3:
//                                                    if (endResult != null) { resultText4.setText(endResult); }
//                                                    break;
//                                                case 4:
//                                                    if (endResult != null) { resultText5.setText(endResult); }
//                                                    break;
                                                //当前仅测试单个人脸，多个人脸存在bug
                                                    default:
                                                        break;
                                            }
                                        }
                                    });
                                }
                            }
                            else{
                                if(noFaceNumber == 4){
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        //更新UI
                                        rectView.setRect(0,0,0,0,0);
                                        resultText1.setText("");
                                    }
                                });
                                noFaceNumber=0;
                                }
                                else{noFaceNumber++;}
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static String getDate() {
        Calendar ca = Calendar.getInstance();
        int year = ca.get(Calendar.YEAR);           // 获取年份
        int month = ca.get(Calendar.MONTH);         // 获取月份
        int day = ca.get(Calendar.DATE);            // 获取日
        int minute = ca.get(Calendar.MINUTE);       // 分
        int hour = ca.get(Calendar.HOUR);           // 小时
        int second = ca.get(Calendar.SECOND);       // 秒

        String date = "" + year + (month + 1) + day + hour + minute + second;

        return date;
    }

    static class CompareSizesByArea implements Comparator<Size> {

        @Override
        public int compare(Size lhs, Size rhs) {
            // We cast here to ensure the multiplications won't overflow
            return Long.signum((long) lhs.getWidth() * lhs.getHeight() -
                    (long) rhs.getWidth() * rhs.getHeight());
        }

    }

}
