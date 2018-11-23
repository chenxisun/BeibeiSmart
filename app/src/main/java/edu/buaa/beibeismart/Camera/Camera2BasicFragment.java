/*
 * Copyright 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package edu.buaa.beibeismart.Camera;


import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ImageFormat;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CaptureRequest;
import android.media.ExifInterface;
import android.media.Image;
import android.media.ImageReader;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.SparseIntArray;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

//import com.facepp.error.FaceppParseException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Arrays;

import edu.buaa.beibeismart.R;

import static android.os.Looper.getMainLooper;

public class Camera2BasicFragment extends Fragment {

    public static final int REQUEST_CAMERA_CODE = 100;
    public static final String PACKAGE = "package:";

    View root;

    private static final SparseIntArray ORIENTATIONS = new SparseIntArray();

    ///为了使照片竖直显示
    static {
        ORIENTATIONS.append(Surface.ROTATION_0, 90);
        ORIENTATIONS.append(Surface.ROTATION_90, 0);
        ORIENTATIONS.append(Surface.ROTATION_180, 270);
        ORIENTATIONS.append(Surface.ROTATION_270, 180);
    }

    private SurfaceView mSurfaceView;
    private SurfaceHolder mSurfaceHolder;
    private ImageView iv_show;
    private TextView mTip;
    private Button mCatture, mLogin, mDetect;
    private CameraManager mCameraManager;//摄像头管理器
    private Handler childHandler, mainHandler;
    private String mCameraID;//摄像头Id 0 为后  1 为前
    private ImageReader mImageReader;
    private CameraCaptureSession mCameraCaptureSession;
    private CameraDevice mCameraDevice;
    private View mWaiting;

    private Bitmap mBitmapPhoto;

    /**
     * 摄像头创建监听
     */
    private CameraDevice.StateCallback stateCallback = new CameraDevice.StateCallback() {
        @Override
        public void onOpened(CameraDevice camera) {//打开摄像头
            mCameraDevice = camera;
            //开启预览
            takePreview();
        }

        @Override
        public void onDisconnected(CameraDevice camera) {//关闭摄像头
            if (null != mCameraDevice) {
                mCameraDevice.close();
                Camera2BasicFragment.this.mCameraDevice = null;
            }
        }

        @Override
        public void onError(CameraDevice camera, int error) {//发生错误
            Toast.makeText(getContext(), "摄像头开启失败", Toast.LENGTH_SHORT).show();
        }
    };

    public static Camera2BasicFragment newInstance() {
        return new Camera2BasicFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_camera2_basic, container, false);
        initVIew(root);
        initListener();
        return root;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    /**
     * 初始化
     */
    private void initVIew(View root) {
        iv_show = (ImageView) root.findViewById(R.id.iv_show);
        //mSurfaceView
        mSurfaceView = (SurfaceView) root.findViewById(R.id.surfaceView);
        mCatture = (Button) root.findViewById(R.id.mCatture);
        mLogin = root.findViewById(R.id.id_login);
        mDetect = root.findViewById(R.id.id_detect);
        mSurfaceHolder = mSurfaceView.getHolder();
        mWaiting = root.findViewById(R.id.id_waiting);
        mSurfaceHolder.setKeepScreenOn(true);
        mTip = root.findViewById(R.id.id_tip);
        // mSurfaceView添加回调
        mSurfaceHolder.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) { //SurfaceView创建
                // 初始化Camera
                initCamera2();
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) { //SurfaceView销毁
                // 释放Camera资源
                if (null != mCameraDevice) {
                    mCameraDevice.close();
                    Camera2BasicFragment.this.mCameraDevice = null;
                }
            }
        });

        Display display = getActivity().getWindowManager().getDefaultDisplay();
        DisplayMetrics dm = new DisplayMetrics();
        display.getMetrics(dm);
        int mWidth = dm.widthPixels;
        int mHeight = dm.heightPixels;

        ViewGroup.LayoutParams lp = (ViewGroup.LayoutParams) iv_show.getLayoutParams();
        lp.width = (mWidth * 3) / 4;
        lp.height = (mHeight * 3) / 4;
        iv_show.requestLayout();


        ViewGroup.LayoutParams lps = mSurfaceView.getLayoutParams();
        int fixedWidth = (mWidth) / 4;
        int fixedHeight = (mHeight) / 4;
        lps.width = fixedWidth;
        lps.height = fixedHeight;
        mSurfaceView.getHolder().setFixedSize(fixedWidth, fixedHeight);
        mSurfaceView.setLayoutParams(lps);

//        iv_show.setLayoutParams(new  android.widget.AbsListView.LayoutParams((mWidth * 3) / 4,  (mHeight * 3) / 4));

//        iv_show.setLayoutParams(lp);

    }

    /**
     * 初始化Camera2
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void initCamera2() {
        HandlerThread handlerThread = new HandlerThread("Camera2");
        handlerThread.start();
        childHandler = new Handler(handlerThread.getLooper());
        mainHandler = new Handler(getMainLooper());
        mCameraID = "" + CameraCharacteristics.LENS_FACING_FRONT;//后摄像头
        mImageReader = ImageReader.newInstance(1080, 1920, ImageFormat.JPEG, 1);
        mImageReader.setOnImageAvailableListener(new ImageReader.OnImageAvailableListener() { //可以在这里处理拍照得到的临时照片 例如，写入本地
            @Override
            public void onImageAvailable(ImageReader reader) {
                //mCameraDevice.close();
                // 拿到拍照照片数据
                Image image = reader.acquireNextImage();
                ByteBuffer buffer = image.getPlanes()[0].getBuffer();
                byte[] bytes = new byte[buffer.remaining()];
                buffer.get(bytes);//由缓冲区存入字节数组
                image.close();
                mBitmapPhoto = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);

                if (mBitmapPhoto != null) {
//                    resizePhoto();

//                    mBitmapPhoto = rotateBitmapByDegree(mBitmapPhoto, 180);

                    iv_show.setImageBitmap(mBitmapPhoto);

//                    mBitmapPhoto=compressImage(mBitmapPhoto);

//                    detectPic();
                }
            }
        }, mainHandler);
        //获取摄像头管理
        mCameraManager = (CameraManager) getContext().getSystemService(Context.CAMERA_SERVICE);
        try {
            if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                //申请WRITE_EXTERNAL_STORAGE权限
                requestPermissions(new String[]{Manifest.permission.CAMERA},
                        REQUEST_CAMERA_CODE);
                //return;
            } else {
                //打开摄像头
                mCameraManager.openCamera(mCameraID, stateCallback, mainHandler);
            }
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    private boolean editTextIsNULL;
    String editTextIn;

    public void alert_edit(final View view) {
        editTextIn = "";
        editTextIsNULL = true;
        final EditText et = new EditText(getContext());

        new AlertDialog.Builder(getContext()).setTitle("请输入消息")
                .setIcon(android.R.drawable.sym_def_app_icon)
                .setView(et)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //按下确定键后的事件
//                        Toast.makeText(getContext(), et.getText().toString(),Toast.LENGTH_LONG).show();
                        final String re = et.getText().toString();
                        if (!TextUtils.isEmpty(re)) {
                            editTextIsNULL = false;
//                            editTextIn=et.getText().toString();

                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    Log.e("face_token", face_token);
                                    try {
                                        FaceApi.addFace(face_token);
                                        FaceApi.setId(re,face_token);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            }).start();
                        } else {
                            alert_edit(view);
                        }
                    }
                }).setNegativeButton("取消", null).show();

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CAMERA_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission Granted
                try {
                    mCameraManager.openCamera(mCameraID, stateCallback, mainHandler);
                } catch (CameraAccessException e) {
                    e.printStackTrace();
                } catch (SecurityException e) {
                    e.printStackTrace();
                }
            } else {
                // Permission Denied
            }
        }
    }

    /**
     * 开始预览
     */
    private void takePreview() {
        try {
            // 创建预览需要的CaptureRequest.Builder
            final CaptureRequest.Builder previewRequestBuilder = mCameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW);
            // 将SurfaceView的surface作为CaptureRequest.Builder的目标
            previewRequestBuilder.addTarget(mSurfaceHolder.getSurface());
            // 创建CameraCaptureSession，该对象负责管理处理预览请求和拍照请求
            mCameraDevice.createCaptureSession(Arrays.asList(mSurfaceHolder.getSurface(), mImageReader.getSurface()), new CameraCaptureSession.StateCallback() // ③
            {
                @Override
                public void onConfigured(CameraCaptureSession cameraCaptureSession) {
                    if (null == mCameraDevice) return;
                    // 当摄像头已经准备好时，开始显示预览
                    mCameraCaptureSession = cameraCaptureSession;
                    try {
                        // 自动对焦
                        previewRequestBuilder.set(CaptureRequest.CONTROL_AF_MODE, CaptureRequest.CONTROL_AF_MODE_CONTINUOUS_PICTURE);
                        // 打开闪光灯
                        previewRequestBuilder.set(CaptureRequest.CONTROL_AE_MODE, CaptureRequest.CONTROL_AE_MODE_ON_AUTO_FLASH);
                        // 显示预览
                        CaptureRequest previewRequest = previewRequestBuilder.build();
                        mCameraCaptureSession.setRepeatingRequest(previewRequest, null, childHandler);
                    } catch (CameraAccessException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onConfigureFailed(CameraCaptureSession cameraCaptureSession) {
                    Toast.makeText(getContext(), "配置失败", Toast.LENGTH_SHORT).show();
                }
            }, childHandler);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * 拍照
     */
    private void takePicture() {
        if (mCameraDevice == null) return;
        // 创建拍照需要的CaptureRequest.Builder
        final CaptureRequest.Builder captureRequestBuilder;
        try {
            captureRequestBuilder = mCameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_STILL_CAPTURE);
            // 将imageReader的surface作为CaptureRequest.Builder的目标
            captureRequestBuilder.addTarget(mImageReader.getSurface());
            // 自动对焦
            captureRequestBuilder.set(CaptureRequest.CONTROL_AF_MODE, CaptureRequest.CONTROL_AF_MODE_CONTINUOUS_PICTURE);
            // 自动曝光
            captureRequestBuilder.set(CaptureRequest.CONTROL_AE_MODE, CaptureRequest.CONTROL_AE_MODE_ON_AUTO_FLASH);
            // 获取手机方向
            int rotation = getActivity().getWindowManager().getDefaultDisplay().getRotation();
            // 根据设备方向计算设置照片的方向
            Log.e("rotation", String.valueOf((ORIENTATIONS.get(rotation)) + String.valueOf(CaptureRequest.JPEG_ORIENTATION)));
            captureRequestBuilder.set(CaptureRequest.JPEG_ORIENTATION, (ORIENTATIONS.get(rotation)));
            //拍照
            CaptureRequest mCaptureRequest = captureRequestBuilder.build();
            mCameraCaptureSession.capture(mCaptureRequest, null, childHandler);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    String face_token;

    private void initListener() {
        mCatture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                face_token = "";
                takePicture();
                if (!((mBitmapPhoto == null) || (mBitmapPhoto.isRecycled()))) {

                    Log.e("login", "发出请求");

                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                String infoFace = FaceApi.detect(mBitmapPhoto);
                                JSONObject jo = new JSONObject(infoFace);
                                JSONArray faces = jo.getJSONArray("faces");
                                face_token = faces.getJSONObject(0).getString("face_token");
                                Log.e("face_token_res", face_token);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();
                }
            }
        });
        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!((mBitmapPhoto == null) || (mBitmapPhoto.isRecycled()))) {
                    Log.e("login", "发出请求");
                    alert_edit(root);
                    if (TextUtils.isEmpty(editTextIn)) {

                    }
                }
            }
        });
        mDetect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!((mBitmapPhoto == null) || (mBitmapPhoto.isRecycled()))) {
                    Log.e("detect", "发出请求");

                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            Message msg = mTipSetter.obtainMessage();
                            CharSequence re="";
                            try {
                                String infoFace = FaceApi.search(mBitmapPhoto);
                                JSONObject jo = new JSONObject(infoFace);
                                JSONArray persons = jo.getJSONArray("results");
                                re=re+"有"+persons.length()+"张人脸\n";
                                for (int i=0;i<persons.length();i++){
                                    JSONObject temp=persons.getJSONObject(i);
                                    re=re+temp.getString("user_id")+" 置信度："+temp.getString("confidence")+"\n";
                                }
                                Log.e("face_token_res", re.toString());
//                                prepareResultBitmap(jo);
                            } catch (Exception e) {
                                e.printStackTrace();
                                re="鉴别失败";
                            }
                            msg.obj = re;
                            mTipSetter.sendMessage(msg);
                        }
                    }).start();
                }
            }
        });
    }

    private Handler mTipSetter=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            mTip.setText((CharSequence) msg.obj);
        }
    };

    private Canvas canvas;
    private Paint mPaint;

    private void prepareResultBitmap(JSONObject rs) {
        // TODO Auto-generated method stub
        Bitmap bitmap = Bitmap.createBitmap(mBitmapPhoto.getWidth(), mBitmapPhoto.getHeight(),
                mBitmapPhoto.getConfig());
        canvas = new Canvas(bitmap);
        canvas.drawBitmap(mBitmapPhoto, 0, 0, null);

        try {
            JSONArray faces = rs.getJSONArray("face");
            int faceCount = faces.length();
//            mTip.setText("find " + faceCount);

            for (int i = 0; i < faceCount; i++) {
                // 拿到单独face对象
                JSONObject face = faces.getJSONObject(i);
                JSONObject positonObj = face.getJSONObject("position");
                // get face position
                float x = (float) positonObj.getJSONObject("center").getDouble("x");
                float y = (float) positonObj.getJSONObject("center").getDouble("y");
                float w = (float) positonObj.getDouble("width");
                float h = (float) positonObj.getDouble("height");

                x = x / 100 * bitmap.getWidth();
                y = y / 100 * bitmap.getHeight();
                w = w / 100 * bitmap.getWidth();
                h = h / 100 * bitmap.getHeight();

                mPaint.setColor(0xffffffff);
                mPaint.setStrokeWidth(3);
                // draw box
                canvas.drawLine(x - w / 2, y - h / 2, x - w / 2, y + h / 2, mPaint);// 左
                canvas.drawLine(x - w / 2, y + h / 2, x + w / 2, y + h / 2, mPaint);// 下
                canvas.drawLine(x + w / 2, y + h / 2, x + w / 2, y - h / 2, mPaint);// 右
                canvas.drawLine(x - w / 2, y - h / 2, x + w / 2, y - h / 2, mPaint);// 上

                // get age and gender
                int age = face.getJSONObject("attribute").getJSONObject("age").getInt("value");
                String gender = face.getJSONObject("attribute").getJSONObject("gender").getString("value");

                Bitmap ageBitmap = buildAgeBitmap(age, gender.equals("Male"));

                int ageWidth = ageBitmap.getWidth();
                int ageHeight = ageBitmap.getHeight();
                if (bitmap.getWidth() <= iv_show.getWidth() && bitmap.getHeight() <= iv_show.getHeight()) {
                    float ratio = Math.max(bitmap.getWidth() * 1.0f / iv_show.getWidth(),
                            bitmap.getHeight() * 1.0f / iv_show.getHeight());

                    ageBitmap = Bitmap.createScaledBitmap(ageBitmap, (int) (ageWidth * ratio),
                            (int) (ageHeight * ratio), false);
                    canvas.drawBitmap(ageBitmap, x - ageBitmap.getWidth() / 2, y - h / 2 - ageBitmap.getHeight(), null);
                }
                mBitmapPhoto = bitmap;
                iv_show.setImageBitmap(mBitmapPhoto);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    ;
TextView tv;
    private Bitmap buildAgeBitmap(int age, boolean isMale) {
        tv = (TextView) mWaiting.findViewById(R.id.id_age_and_gender);
        tv.setText(age + "");
        if (isMale) {
            tv.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.male), null, null, null);
        } else {
            tv.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.female), null, null, null);
        }
        tv.setDrawingCacheEnabled(true);
        Bitmap bitmap = Bitmap.createBitmap(tv.getDrawingCache());
        tv.destroyDrawingCache();
        return bitmap;
    }

    private Bitmap scaleBitmap(Bitmap origin, float ratio) {
        if (origin == null) {
            return null;
        }
        int width = origin.getWidth();
        int height = origin.getHeight();
        Matrix matrix = new Matrix();
        matrix.preScale(ratio, ratio);
        Bitmap newBM = Bitmap.createBitmap(origin, 0, 0, width, height, matrix, false);
        if (newBM.equals(origin)) {
            return newBM;
        }
        origin.recycle();
        return newBM;
    }

    /**
     * 质量压缩方法
     *
     * @param image
     * @return
     */
    public static Bitmap compressImage(Bitmap image) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);// 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = 90;

        while (baos.toByteArray().length / 1024 > 100) { // 循环判断如果压缩后图片是否大于100kb,大于继续压缩
            baos.reset(); // 重置baos即清空baos
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);// 这里压缩options%，把压缩后的数据存放到baos中
            options -= 10;// 每次都减少10
        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());// 把压缩后的数据baos存放到ByteArrayInputStream中
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);// 把ByteArrayInputStream数据生成图片
        return bitmap;
    }


    /**
     * 将图片按照某个角度进行旋转
     *
     * @param bm     需要旋转的图片
     * @param degree 旋转角度
     * @return 旋转后的图片
     */
    public static Bitmap rotateBitmapByDegree(Bitmap bm, int degree) {
        Bitmap returnBm = null;

        // 根据旋转角度，生成旋转矩阵
        Matrix matrix = new Matrix();
        matrix.postRotate(degree);
        try {
            // 将原始图片按照旋转矩阵进行旋转，并得到新的图片
            returnBm = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(), bm.getHeight(), matrix, true);
        } catch (OutOfMemoryError | Exception e) {
            e.printStackTrace();
        }
        if (returnBm == null) {
            returnBm = bm;
        }
        if (bm != returnBm) {
            bm.recycle();
        }
        return returnBm;
    }

}
