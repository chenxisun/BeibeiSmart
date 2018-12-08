package edu.buaa.beibeismart.Activity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
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
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.Size;
import android.view.Display;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import edu.buaa.beibeismart.View.AutoFitTextureView;
import edu.buaa.beibeismart.Net.FaceApi;
import edu.buaa.beibeismart.R;
import edu.buaa.beibeismart.RectView;

public class FaceRecActivity extends BaseActivity implements View.OnClickListener, ActivityCompat.OnRequestPermissionsResultCallback, Runnable {

    private static final int MAX_PREVIEW_WIDTH = 1080;
    private static final int MAX_PREVIEW_HEIGHT = 2280;

    private Size mPreviewSize;
    private String mCameraId;
    private int mSensorOrientation;
    private ImageReader mImageReader;
    private CameraDevice mCameraDevice;
    private CaptureRequest.Builder mCaptureRequestBuilder;
    private CaptureRequest mCaptureRequest;
    private CameraCaptureSession mPreviewSession;


    private RectView rectView1;
    private Paint paint;
    private Button btLogin;
    private Button btBack;
    private AutoFitTextureView surfaceView;
    private ImageView ivCamera;
    private TextView tvRes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        setListener();

    }

    protected void initView() {
        setContentView(R.layout.activity_face_rec);
        rectView1 = findViewById(R.id.faceRecView);
        surfaceView = findViewById(R.id.previewTextureView);
        surfaceView.setSurfaceTextureListener(surfaceTextureListener);
        btLogin = findViewById(R.id.login_bt_face_rec);
        btBack=findViewById(R.id.back_btn_face_rec);
        tvRes = findViewById(R.id.tvfaceRec);
        ivCamera = findViewById(R.id.iv_face_rec);
        rectView1.initRect();
        paint = new Paint();
        paint.setColor(Color.rgb(255, 0, 0));
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(5);

        Display display = this.getWindowManager().getDefaultDisplay();
        DisplayMetrics dm = new DisplayMetrics();
        display.getMetrics(dm);
        int mWidth = dm.widthPixels;
        int mHeight = dm.heightPixels;

        ViewGroup.LayoutParams lps = surfaceView.getLayoutParams();
        int fixedWidth = (mWidth * 3) / 4;
        int fixedHeight = (mHeight * 3) / 4;
        lps.width = fixedWidth;
        lps.height = fixedHeight;
        surfaceView.setLayoutParams(lps);

        ViewGroup.LayoutParams lp = ivCamera.getLayoutParams();
        lp.width = (mWidth * 1) / 4;
        lp.height = (mHeight * 1) / 4;
        ivCamera.setLayoutParams(lp);
    }

    private  void setListener(){
        btBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCameraDevice.close();
                startActivity(new Intent(FaceRecActivity.this, MainActivity.class));
            }
        });
        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Bitmap bitmap = surfaceView.getBitmap();
                if (!((bitmap == null) || bitmap.isRecycled())) {
                    ivCamera.setImageBitmap(bitmap);
                    alert_edit();
                    if (TextUtils.isEmpty(editTextIn)) {
                    }
                }
            }
        });

    }


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
            for (String cameraId : manager.getCameraIdList()) {
                CameraCharacteristics characteristics = manager.getCameraCharacteristics(cameraId);
                //默认打开后置摄像头
                if (characteristics.get(CameraCharacteristics.LENS_FACING) == CameraCharacteristics.LENS_FACING_FRONT)
//                if(characteristics.get(CameraCharacteristics.LENS_FACING))
                    continue;
                //获取StreamConfigurationMap，它是管理摄像头支持的所有输出格式和尺寸
                StreamConfigurationMap map = characteristics.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP);

                Size largest = Collections.max(
                        Arrays.asList(map.getOutputSizes(ImageFormat.JPEG)),
                        new EmotionActivity.CompareSizesByArea());
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
        } catch (CameraAccessException e) {
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
                    new Thread(FaceRecActivity.this).start();
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
    public void onClick(View view) {

    }

    @Override
    public void onVolleyFinish(int isSuccess, Object result) {

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
            return Collections.min(bigEnough, new EmotionActivity.CompareSizesByArea());
        } else if (notBigEnough.size() > 0) {
            return Collections.max(notBigEnough, new EmotionActivity.CompareSizesByArea());
        } else {
            //Log.e(TAG, "Couldn't find any suitable preview size");
            return choices[0];
        }
    }

    String face_token;

    private Handler mTipSetter = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            tvRes.setText((CharSequence) msg.obj);
        }
    };

    String editTextIn;

    public void alert_edit() {
        editTextIn = "";
        final EditText et = new EditText(this);

        new AlertDialog.Builder(this).setTitle("请输入消息")
                .setIcon(android.R.drawable.sym_def_app_icon)
                .setView(et)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //按下确定键后的事件
                        final String re = et.getText().toString();
                        if (!TextUtils.isEmpty(re)) {
//                            editTextIn=et.getText().toString();

                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    Log.e("face_token", face_token);
                                    try {
                                        FaceApi.addFace(face_token);
                                        FaceApi.setId(re, face_token);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            }).start();
                        } else {
                            alert_edit();
                        }
                    }
                }).setNegativeButton("取消", null).show();
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            final Bitmap bitmap = surfaceView.getBitmap();
            face_token = "";
            if (!((bitmap == null) || bitmap.isRecycled())) {
                Log.e("识别-----", "请求facetoken");
                String infoFace = null;
                try {
                    infoFace = FaceApi.detect(bitmap);
                    JSONObject jo = new JSONObject(infoFace);
                    JSONArray faces = jo.getJSONArray("faces");
                    face_token = faces.getJSONObject(0).getString("face_token");
                    final JSONObject faceRectjs = faces.getJSONObject(0).getJSONObject("face_rectangle");
                    int ratio = 3 / 4;
                    final int left = faceRectjs.getInt("left");
                    final int right = left + faceRectjs.getInt("width");
                    final int top = faceRectjs.getInt("top");
                    final int bottom = top + faceRectjs.getInt("height");

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //更新UI
                            if (!face_token.equals(null)) {
                                rectView1.setRect(left, top, right, bottom, 4);
                            }
                        }
                    });
                    Log.e("face_token", face_token);
                    Log.e("识别------", "请求图片比对");
                    Message msg = mTipSetter.obtainMessage();
                    CharSequence re = "";

                    try {
                        infoFace = FaceApi.search(face_token);
                        jo = new JSONObject(infoFace);
                        JSONArray persons = jo.getJSONArray("results");
                        for (int i = 0; i < persons.length(); i++) {
                            JSONObject temp = persons.getJSONObject(i);
                            re = re + temp.getString("user_id") + " 置信度：" + temp.getString("confidence") + "\n";
                        }
                        Log.e("face_token_res", re.toString());
//                                prepareResultBitmap(jo);
                    } catch (Exception e) {
                        e.printStackTrace();
                        re = "鉴别失败";
                    }
                    msg.obj = re;
                    mTipSetter.sendMessage(msg);

                    Thread.sleep(1000);

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }
    }

}
