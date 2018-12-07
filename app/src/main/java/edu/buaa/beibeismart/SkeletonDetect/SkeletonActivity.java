package edu.buaa.beibeismart.SkeletonDetect;

import android.graphics.Point;
import android.os.Bundle;
import android.Manifest;
import android.annotation.TargetApi;
import android.content.ContentUris;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import edu.buaa.beibeismart.Activity.MainActivity;
import edu.buaa.beibeismart.R;


public class SkeletonActivity extends AppCompatActivity implements View.OnClickListener{

    private  final String TAG=this.getClass().toString();
    public static final int TAKE_PHOTO=1;
    public static final int CHOOSE_PHOTO=2;
    public static final int DETECT=3;
    private ImageView pic;
    private Uri imageUri;
    private String imgPath;
    Bitmap bitMap;
    private Button take_photo,chooseFromAlbum,detect,backBtn;
    private int screenWidth;
    private int screenHeight;
    private bodyAnalysis bodyAnalyze;
    private BodyView bodyView;
    private TextView errorTextView;
    //String imgPath;
    //Bitmap bitMap;
    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.take_photo:
                takePhoto();
                break;
            case R.id.album_photo:
                choosePhoto();
                break;
            case R.id.detect:
                KeyPointDetect();
                break;
            case R.id.back_btn:
                Intent intent=new Intent(SkeletonActivity.this,MainActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.skeleton_layout);

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        screenWidth = size.x;
        screenHeight = size.y;
        errorTextView = (TextView) findViewById(R.id.errorTextView);
        backBtn=(Button) findViewById(R.id.back_btn);
        take_photo=(Button) findViewById(R.id.take_photo);
        detect=(Button) findViewById(R.id.detect);
        chooseFromAlbum=(Button) findViewById(R.id.album_photo);
        pic=(ImageView) findViewById(R.id.picture);
        bodyAnalyze= new bodyAnalysis();
        bodyAnalyze.init();
        bodyView = (BodyView) findViewById(R.id.bodyview);
        backBtn.setOnClickListener(this);
        take_photo.setOnClickListener(this);
        chooseFromAlbum.setOnClickListener(this);
        detect.setOnClickListener(this);
        if (!NetworkInit.isNetworkConnected(this)) {
            NetworkInit.showNoNetWorkDlg(this);
        }
    }

    public void takePhoto(){
        String status=Environment.getExternalStorageState();
        if (status.equals(Environment.MEDIA_MOUNTED)){
            File outputImg=new File(getExternalCacheDir(),"ouputImg.jpg");
            try{
                if (outputImg.exists()){
                    outputImg.delete();
                }
                outputImg.createNewFile();
            }
            catch (IOException e) {
                e.printStackTrace();
                Log.d(TAG,">>>>>>>>>>>无法创建图像");
            }
            if (Build.VERSION.SDK_INT>=24){
                imageUri =FileProvider.getUriForFile(this,"com.chao.activity.photoActivity",outputImg);

            }
            else{
                imageUri=Uri.fromFile(outputImg);
            }
            if(Build.VERSION.SDK_INT>=23){
                int checkCallCamera=ContextCompat.checkSelfPermission(SkeletonActivity.this,Manifest.permission.CAMERA);
                if(checkCallCamera != PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE},2);
                }}
            //n启动相机程序
            Intent intent=new Intent("android.media.action.IMAGE_CAPTURE");
            intent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);
            startActivityForResult(intent,TAKE_PHOTO);
        }
        else{
            Toast.makeText(SkeletonActivity.this,"没有存储卡",Toast.LENGTH_LONG).show();
        }
    }
    public void choosePhoto(){
        if (ContextCompat.checkSelfPermission(this,Manifest.permission.WRITE_EXTERNAL_STORAGE)
                !=PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this,
                    new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
        }
        else{
            openAlbum();}
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch(requestCode){
            case 1:
                if (grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED )
                {
                    openAlbum();
                }
                else{
                    Toast.makeText(this,"You denied the permission",Toast.LENGTH_SHORT).show();
                }
                break;
            case 2:
                if (grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED )
                {
                    //n启动相机程序
                    Intent intent=new Intent("android.media.action.IMAGE_CAPTURE");
                    intent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);
                    startActivityForResult(intent,TAKE_PHOTO);
                }
                else{
                    Toast.makeText(this,"You denied the camera permission",Toast.LENGTH_SHORT).show();
                }
            default:
        }
    }

    private void openAlbum(){
        Intent intent=new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
        startActivityForResult(intent,CHOOSE_PHOTO);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==RESULT_OK){
            switch (requestCode){
                case TAKE_PHOTO:
                    try{
                        //将照片显示出来
                        bitMap=BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));
                        imgScale();
                        pic.setImageBitmap(bitMap);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    break;
                case CHOOSE_PHOTO:
                    //判断手机版本号
                    if (Build.VERSION.SDK_INT>19)
                    {
                        //4.4及以上系统使用这个方法处理图片
                        //Toast.makeText(MainActivity.this,"failed to get image",Toast.LENGTH_SHORT).show();
                        handleImgeOnKitKat(data);
                    }
                    else {
                        handleImageBeforeKitKat(data);
                    }
                    break;
                default:
                    break;
            }
        }
    }
    private void handleImageBeforeKitKat(Intent data) {
        Uri uri = data.getData();
        String imagePath = getImagePath(uri,null);
        displayImage(imagePath);
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)//19
    private void handleImgeOnKitKat(Intent data) {
        String imagePath = null;
        Uri uri = data.getData();
        if (DocumentsContract.isDocumentUri(this,uri)) {
            //如果是document类型的uri，则通过document id处理
            String docId = DocumentsContract.getDocumentId(uri);
            if ("com.android.providers.media.documents".equals(uri.getAuthority())) {
                //解析出数字格式的id
                String id = docId.split(":")[1];
                String selection = MediaStore.Images.Media._ID + "=" + id;
                imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);
            } else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())) {
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(docId));
                imagePath = getImagePath(contentUri, null);
            }
        }
        else if ("content".equalsIgnoreCase(uri.getScheme()))
        {
            //如果是content类型的uri，则使用普通方式处理
            imagePath = getImagePath(uri,null);
        }
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            //如果是file类型的uri，直接获取图片路径即可
            imagePath = uri.getPath();
        }
        //根据图片路径显示图片
        displayImage(imagePath);

    }

    private void displayImage(String imagePath) {
        if (imagePath != null) {
            //imgPath=imagePath;
            bitMap = BitmapFactory.decodeFile(imagePath);
            imgScale();
            pic.setImageBitmap(bitMap);
        }else {
            Toast.makeText(SkeletonActivity.this,"failed to get image",Toast.LENGTH_SHORT).show();
        }
    }
    public void imgScale(){
        int w=bitMap.getWidth();
        int h=bitMap.getHeight();
        if(w<screenWidth)
        {
            bitMap = ThumbnailUtils.extractThumbnail(bitMap, w, h);
        }
        else{
            float scale=screenWidth*1.0f/w;
            w=screenWidth;
            h=(int)(h * scale);
            bitMap=ThumbnailUtils.extractThumbnail(bitMap, w, h);
        }
        if(h<screenHeight*0.8)
        {
            bitMap = ThumbnailUtils.extractThumbnail(bitMap, w, h);
        }
        else{
            float scale=screenHeight*0.8f/h;
            h=(int)(screenHeight*0.8);
            w=(int)(w * scale);
            bitMap=ThumbnailUtils.extractThumbnail(bitMap, w, h);
        }
    }
    public void KeyPointDetect(){
        Log.d(TAG,">>>>>>>>>开始对当前图像进行关键点检测");
        if(bitMap==null)
        {
            Toast.makeText(this,"还没加载图片>>>>>>拍照或者选择图像",Toast.LENGTH_SHORT);
        }

        final byte[] byteArrays=Bitmap2Bytes(bitMap);
        new Thread(new Runnable() {
            @Override
            public void run() {
                BodyKeyPoint bodyDetect = bodyAnalyze.doKeyPointDetect(byteArrays);

                if (bodyDetect.getError_msg() != null) {
                    errorTextView.setText("Error:" + bodyDetect.getError_msg());
                    return;
                }
                // 绘制命令
                bodyView.setBody(bodyDetect);
            }
        }).start();

    }
    public static byte[] Bitmap2Bytes(Bitmap bm){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
        return baos.toByteArray();
    }

    private String getImagePath(Uri uri,String selection) {
        String path = null;
        Cursor cursor = getContentResolver().query(uri,null,selection,null,null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }

}
