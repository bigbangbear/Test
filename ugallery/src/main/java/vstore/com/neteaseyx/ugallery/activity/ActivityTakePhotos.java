package vstore.com.neteaseyx.ugallery.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import vstore.com.neteaseyx.ugallery.UGallery;

/**
 * 拍照的Activity
 * @author liangbin
 * @date 2016-05-05
 */
public class ActivityTakePhotos extends Activity{
    private static final String TAG = "ActivityTakePhotos";
    //Test
    private Uri mTakePhotoUri = null;
    private static boolean mIsCrop;
    public static void startActivityForTakePhoto(Context context, boolean isCrop ){
        mIsCrop = isCrop;
        Intent intent = new Intent(context, ActivityTakePhotos.class);
        ( (Activity)context).startActivityForResult(intent, UGallery.TAKE_PHOTO);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        takePhotoAction();
    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    /**
     * 拍照让MediaStore添加数据
     */
    private void noticTakePiture(){
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        intent.setData(mTakePhotoUri);
        sendBroadcast(intent);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode != RESULT_OK){
            return;
        }
        if (requestCode == UGallery.TAKE_PHOTO) {

            if (  mTakePhotoUri != null) {
                if (mIsCrop){
                    UGallery.cropImage(ActivityTakePhotos.this, mTakePhotoUri);
                }else {
                    returnSingleImage(mTakePhotoUri);
                }
            }
        }

        if (requestCode == UGallery.CROP_IMAGE){
            Uri uri = UGallery.getSingleImage(data);
            returnSingleImage(uri);
        }


    }

    private void returnSingleImage(Uri uri){
        final String path = uri.getPath();
        if (new File(path).exists()) {
            noticTakePiture();
            Intent intent = new Intent();
            intent.putExtra(UGallery.PATH, uri.getPath());
            setResult(RESULT_OK, intent);
            finish();
        }
    }

    private File mTakePhotoFolder = null;
    protected void takePhotoAction() {

        if (mTakePhotoFolder == null){
            mTakePhotoFolder = new File(Environment.getExternalStorageDirectory(), "/DCIM/");
        }

        String fileName = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.CHINA).format(new Date(System.currentTimeMillis()));

        if (mTakePhotoUri == null){
            File img = new File(mTakePhotoFolder, fileName+ ".jpg");
            mTakePhotoUri = Uri.fromFile(img);
        }
        Intent captureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        captureIntent.putExtra(MediaStore.EXTRA_OUTPUT, mTakePhotoUri);
        startActivityForResult(captureIntent, UGallery.TAKE_PHOTO);
    }


}
