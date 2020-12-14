package miis.mpda.woundanalysis;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.media.ExifInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.TextureView;
import android.view.View;
import android.widget.Button;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;

public class MainActivity extends Activity implements TextureView.SurfaceTextureListener, Camera.PictureCallback {

    /** 相機 */
    private Camera camera;

    /** 拍照按鈕 */
    private Button buttonTake;

    /** TextureView */
    private TextureView textureView;

    /** Surface */
    private SurfaceTexture surface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonTake = (Button) findViewById(R.id.button1);
        // 按鈕點擊事件
        buttonTake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                camera.takePicture(null, null, MainActivity.this);
            }
        });

        textureView = (TextureView) findViewById(R.id.textureView);
        textureView.setSurfaceTextureListener(this);
        surface = textureView.getSurfaceTexture();
    }

    @Override
    protected void onStop() {
        super.onStop();

    }

    @Override
    public void onPictureTaken(byte[] data, Camera camera) {
        try {

            ByteArrayInputStream stream = new ByteArrayInputStream(data);
            Bitmap bitmap = BitmapFactory.decodeStream(stream);

            stream.close();
            stream = null;

            FileOutputStream fileOutputStream = new FileOutputStream(
                    new File("/storage/emulated/0/Download/temp/" + new Date().getTime() + ".jpg"));
            rotateBitmap(bitmap,270).compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
            bitmap = null;

            // 寫入檔案
            fileOutputStream.flush();
            fileOutputStream.close();
            fileOutputStream = null;
            camera.startPreview();

        } catch (IOException e) {
            Log.i("Tack_IOException", e.getMessage());
        }
    }

    @Override
    public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
        try {
            camera = Camera.open();
            // 取得相機參數
            Camera.Parameters parameters = camera.getParameters();

            // 關閉閃光燈
            parameters.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);

            // 設定最佳預覽尺寸
            List<Camera.Size> listPreview = parameters.getSupportedPreviewSizes();
            parameters.setPreviewSize(listPreview.get(0).width, listPreview.get(0).height);
            listPreview = null;

            // 設定最佳照片尺寸
            List<Camera.Size> listPicture = parameters.getSupportedPictureSizes();
            parameters.setPictureSize(listPicture.get(0).width, listPicture.get(0).height);
            listPicture = null;

            // 設定照片輸出為90度
            parameters.setRotation(90);

            //parameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);

            // 設定預覽畫面為90度
            camera.setDisplayOrientation(270);

            // 設定相機參數
            camera.setParameters(parameters);

            // 設定顯示的Surface
            camera.setPreviewTexture(surface);
            // 開始顯示
            camera.startPreview();
        } catch (IOException e) {
        }

    }

    @Override
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
        if (camera != null) {
            camera.stopPreview();
            camera.release();
        }
        return true;
    }

    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onSurfaceTextureUpdated(SurfaceTexture surface) {

    }

    public Bitmap rotateBitmap(Bitmap original, float degrees) {
        Matrix matrix = new Matrix();
        matrix.preRotate(degrees);
        Bitmap rotatedBitmap = Bitmap.createBitmap(original, 0, 0, original.getWidth(), original.getHeight(), matrix, true);
        original.recycle();
        return rotatedBitmap;
    }

}
