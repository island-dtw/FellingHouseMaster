package com.dtw.fellinghousemaster.Utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by Administrator on 2017/6/29 0029.
 */

public class UriUtil {
    public static Bitmap getBitmapFormUri(Context context, Uri uri)throws FileNotFoundException,IOException{
        return getCompressBitmap(context,getNoZipedBitmap(context,uri));//再进行质量压缩
    }
    public static File getFileFormUri(Activity context, Uri uri) throws FileNotFoundException, IOException {
        return getCompressImageFile(context,getNoZipedBitmap(context,uri));//再进行质量压缩
    }
    private static Bitmap getNoZipedBitmap(Context context,Uri uri)throws FileNotFoundException,IOException{
        int ww=1080;
        InputStream input = context.getContentResolver().openInputStream(uri);
        BitmapFactory.Options onlyBoundsOptions = new BitmapFactory.Options();
        onlyBoundsOptions.inJustDecodeBounds = true;
        onlyBoundsOptions.inDither = true;//optional
        onlyBoundsOptions.inPreferredConfig = Bitmap.Config.RGB_565;//optional
        BitmapFactory.decodeStream(input, null, onlyBoundsOptions);
        input.close();
        int originalWidth = onlyBoundsOptions.outWidth;
        if (originalWidth == -1) {
            return null;
        }
        //图片分辨率以480x800为标准
        //缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        int be = 1;//be=1表示不缩放
        if(originalWidth>ww){
            be=originalWidth/ww;
        }
        //比例压缩
        BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
        bitmapOptions.inSampleSize = be;//设置缩放比例
        bitmapOptions.inDither = true;//optional
        bitmapOptions.inPreferredConfig = Bitmap.Config.ARGB_8888;//optional
        input = context.getContentResolver().openInputStream(uri);
        Bitmap bitmap = BitmapFactory.decodeStream(input, null, bitmapOptions);
        input.close();
        return bitmap;
    }
    private static File getCompressImageFile(Activity context,Bitmap image) throws IOException {
//        Log.v("Dtw",options+"");
//        File file=new File(ac.getCacheDir()+"/temp.png");
        OutputStream outputStream=new FileOutputStream(context.getCacheDir()+"/temp.png");
        getBitmatByteOutPutStream(context,image).writeTo(outputStream);
//        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());//把压缩后的数据baos存放到ByteArrayInputStream中
//        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);//把ByteArrayInputStream数据生成图片
        return new File(context.getCacheDir()+"/temp.png");
    }
    private static Bitmap getCompressBitmap(Context activity,Bitmap bitmap)throws  IOException{
        ByteArrayInputStream isBm = new ByteArrayInputStream(getBitmatByteOutPutStream(activity,bitmap).toByteArray());//把压缩后的数据baos存放到ByteArrayInputStream中
        return BitmapFactory.decodeStream(isBm, null, null);//把ByteArrayInputStream数据生成图片
    }
    private static ByteArrayOutputStream getBitmatByteOutPutStream(Context context,Bitmap bitmap){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        Log.v("dtw",baos.size()+"");
        int options = 100;
        if(baos.toByteArray().length>1000*1024){
            options=100000*1024/baos.toByteArray().length;
        }
//        while (baos.toByteArray().length / 1024 > 1000) {  //循环判断如果压缩后图片是否大于100kb,大于继续压缩
        baos.reset();//重置baos即清空baos
        //第一个参数 ：图片格式 ，第二个参数： 图片质量，100为最高，0为最差  ，第三个参数：保存压缩后的数据的流
        bitmap.compress(Bitmap.CompressFormat.PNG, options, baos);//这里压缩options%，把压缩后的数据存放到baos中
//            options -= 10;//每次都减少10
//        }
        return baos;
    }
}
