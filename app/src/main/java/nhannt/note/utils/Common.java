package nhannt.note.utils;


import android.content.Context;
import android.content.ContextWrapper;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import static android.content.Context.MODE_PRIVATE;


/**
 * A class contains some common methods which used in many other classes
 */

public class Common {
    private static final String TAG = "NhanNT";

    public static void writeLog(String tag, String content) {
        Log.d(TAG + ": " + tag, content);
    }

    /**
     * @return true when the caller API version is at least lollipop 21
     */
    public static boolean isMarshMallow() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
    }

    /**
     *  Get real path of a file from uri's format
     * @param mContext context calling this function
     * @param uri uri of file want to get real path
     * @return real path of the given uri
     */
    public static String getRealPathFromURI(Context mContext, Uri uri) {
        if (uri == null) {
            return null;
        }
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = mContext.getContentResolver().query(uri, projection, null, null, null);
        if (cursor != null) {
            int column_index = cursor
                    .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            cursor.close();
            return cursor.getString(column_index);
        }
        return uri.getPath();
    }

    /**
     * Save a bitmap to storage in jpg format
     * @param mContext context calling this function
     * @param bitmap Bitmap of image to save
     * @return file path of image just saved
     */
    public static String saveImage(Context mContext, Bitmap bitmap) {
        ContextWrapper wrapper = new ContextWrapper(mContext);
        File file = wrapper.getDir("Images", MODE_PRIVATE);
        file = new File(file, "Image-" + DateTimeUtils.getCurrentDateTimeInStr("yyyy-MM-dd$HH:mm:ss") + ".jpg");
        try {
            OutputStream stream;
            stream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            stream.flush();
            stream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file.getAbsolutePath();
    }
}
