package com.example.demo02app.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PictureUtil {
    private static final String TAG = PictureUtil.class.getName();

    public static Bitmap createBitmapFromUri(@NonNull Context context,@NonNull Uri uri) throws FileNotFoundException {
        return BitmapFactory.decodeStream(context.getContentResolver().openInputStream(uri));
    }

    public static Bitmap compressBitmap(@NonNull Bitmap bitmap, int sizeLimitKB) {
        sizeLimitKB = sizeLimitKB <= 0 ? 100 : sizeLimitKB;
        Log.d(TAG, "compressBitmap: before" + bitmap.getByteCount() / 1024);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        int quality = 100;
        bitmap.compress(Bitmap.CompressFormat.JPEG, quality, byteArrayOutputStream);

        int count = 1;
        Log.d(TAG, "compressBitmap: array" + byteArrayOutputStream.toByteArray().length/1024);
        while (byteArrayOutputStream.toByteArray().length / 1024 > sizeLimitKB) {
            Log.d(TAG, "compressBitmap: called");
            byteArrayOutputStream.reset();
            quality -= 10;
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, byteArrayOutputStream);
            Log.d(TAG, "compressBitmap: do " + (count++));
            Log.d(TAG, "compressBitmap: byte " + byteArrayOutputStream.toByteArray().length / 1024);
        }
        Log.d(TAG, "compressBitmap: after bitmap:" + bitmap.getByteCount() / 1024);
        Bitmap result = BitmapFactory.decodeStream(new ByteArrayInputStream(byteArrayOutputStream.toByteArray()), null, null);
        Log.d(TAG, "compressBitmap: after result:" + result.getByteCount() / 1024);
        return result;
    }

    public static File createTempFileByBitmap(@NonNull Bitmap bitmap, int sizeLimitKB) {
        @SuppressLint("SimpleDateFormat")
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JEPG_" + timeStamp + "_";
        // 将bitmap写入临时文件
        File image = null;
        try {
            image = File.createTempFile(imageFileName, ".jpg");
            BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(image));
            Bitmap compress = compressBitmap(bitmap, sizeLimitKB);
            compress.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
            outputStream.flush();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }

}
