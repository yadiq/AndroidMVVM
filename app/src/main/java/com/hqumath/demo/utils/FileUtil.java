package com.hqumath.demo.utils;

import android.content.Context;
import android.os.Environment;

import com.hqumath.demo.net.HandlerException;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.ResponseBody;

/**
 * ****************************************************************
 * 文件名称: FileUtils
 * 作    者: Created by gyd
 * 创建时间: 2019/3/1 14:35
 * 文件描述: 文件管理
 * 注意事项:
 * 版权声明:
 * ****************************************************************
 */
public class FileUtil {

    /**
     * 获取应用专属内部存储文件(无需权限) /data/user/0/pacakge/files
     *
     * @param dirName  父文件名
     * @param fileName 子文件名
     */
    public static File getFile(String dirName, String fileName) {
        //父文件目录
        String dirPath = CommonUtil.getContext().getFilesDir() + File.separator + dirName;
        File dir = new File(dirPath);
        if (!dir.exists())
            dir.mkdirs();
        //子文件目录
        String filePath = dirPath + File.separator + fileName;
        return new File(filePath);
    }

    /**
     * 获取应用专属内部存储文件-缓存(无需权限) /data/user/0/pacakge/cache
     *
     * @param fileName 文件名
     */
    public static File getCacheFile(String fileName) {
        String filePath = CommonUtil.getContext().getCacheDir() + File.separator + fileName;
        return new File(filePath);
    }

    /**
     * 获取应用专属外部存储空间文件(无需权限) /storage/emulated/0/Android/data/packname/files
     * 并检查sd卡是否可用
     *
     * @param dirName  父文件名
     * @param fileName 子文件名
     */
    public static File getExternalFile(String dirName, String fileName) {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {//SD卡是否可用
            String filePath = CommonUtil.getContext().getExternalFilesDir(dirName) + File.separator + fileName;
            return new File(filePath);
        } else {
            return getFile(dirName, fileName);
        }
    }

    /**
     * 获取应用专属外部存储空间文件-缓存(无需权限) /storage/emulated/0/Android/data/packname/cache
     * 并检查sd卡是否可用
     *
     * @param fileName 子文件名
     */
    public static File getExternalCacheFile(String fileName) {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {//SD卡是否可用
            String filePath = CommonUtil.getContext().getExternalCacheDir() + File.separator + fileName;
            return new File(filePath);
        } else {
            return getCacheFile(fileName);
        }

    }

    /**
     * 获取外部存储空间根目录(API29后废弃) /storage/emulated/0/dirname/filename
     *
     * @param dirName  父文件名
     * @param fileName 子文件名
     */
    public static File getExternalRootFile(String dirName, String fileName) {
        //父文件目录
        String dirPath = Environment.getExternalStorageDirectory() + File.separator + dirName;
        File dir = new File(dirPath);
        if (!dir.exists())
            dir.mkdirs();
        //子文件目录
        String filePath = dirPath + File.separator + fileName;
        return new File(filePath);
    }

    /**
     * 根据url下载文件，存储到应用专属外部存储空间
     *
     * @param url 文件地址
     */
    public static File getFileFromUrl(String url) {
        String fileName = url.substring(url.lastIndexOf("/") + 1);//文件名 a.mp4
        //String fileStyle = fileName.substring(fileName.lastIndexOf(".") + 1);//文件类型 mp4
        return getExternalCacheFile(fileName);
    }

    /**
     * 写文件
     *
     * @param responseBody 网络传输流
     */
    public static void writeFile(ResponseBody responseBody, File file) {
        BufferedInputStream is = new BufferedInputStream(responseBody.byteStream());
        BufferedOutputStream os = null;
        try {
            os = new BufferedOutputStream(new FileOutputStream(file));
            byte data[] = new byte[1024 * 8];
            int length = -1;
            while ((length = is.read(data)) != -1) {
                os.write(data, 0, length);
            }
            os.flush();
        } catch (Exception e) {
            if (file != null && file.exists()) {
                file.deleteOnExit();
            }
            e.printStackTrace();
            throw new HandlerException.ResponseThrowable("文件下载错误", "-1");
        } finally {
            closeStream(is);
            closeStream(os);
        }
    }

    public static void closeStream(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 读文件
     */
    public static void readFileTest() {
        String dir = Environment.getExternalStorageDirectory().getAbsolutePath();//外部存储 /storage/emulated/0
        String fileName = dir + "/Download/tempfiles/test.h265";
        try {
            File file = new File(fileName);
            FileInputStream fis = new FileInputStream(file);
            int length = fis.available();
            if (length > 8000)
                length = 8000;
            byte[] buffer = new byte[length];
            fis.read(buffer);
            fis.close();

            int type = (buffer[4] & 0x7e) >> 1;
            LogUtil.d("type = " + type);

            String msg = ByteUtil.bytesToHexWithSpace(buffer);
            msg = msg.replace("00 00 00 01 ", "\n");
            LogUtil.d(msg);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 复制assets文件夹到本地存储。若存在则跳过
     *
     * @param context
     * @param assetsDirName assets子文件夹名称
     */
    public static void copyAssetsDirToSDCard(Context context, String assetsDirName) {
        String filePath = context.getExternalFilesDir("").getAbsolutePath();
        copyAssetsDirToSDCard(context, assetsDirName, filePath);
    }

    /**
     * 复制assets文件夹到本地存储。若存在则跳过
     *
     * @param context
     * @param assetsDirName assets子文件夹名称
     * @param filePath      存储目录
     */
    public static void copyAssetsDirToSDCard(Context context, String assetsDirName, String filePath) {
        try {
            String list[] = context.getAssets().list(assetsDirName);
            if (list.length == 0) {
                InputStream inputStream = context.getAssets().open(assetsDirName);
                byte[] mByte = new byte[1024];
                int bt = 0;
                File file = new File(filePath + File.separator
                        + assetsDirName.substring(assetsDirName.lastIndexOf('/')));
                if (!file.exists()) {
                    file.createNewFile();
                } else {
                    return;
                }
                FileOutputStream fos = new FileOutputStream(file);
                while ((bt = inputStream.read(mByte)) != -1) {
                    fos.write(mByte, 0, bt);
                }
                fos.flush();
                inputStream.close();
                fos.close();
            } else {
                String subDirName = assetsDirName;
                if (assetsDirName.contains("/")) {
                    subDirName = assetsDirName.substring(assetsDirName.lastIndexOf('/') + 1);
                }
                filePath = filePath + File.separator + subDirName;
                File file = new File(filePath);
                if (!file.exists())
                    file.mkdirs();
                for (String s : list) {
                    copyAssetsDirToSDCard(context, assetsDirName + File.separator + s, filePath);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
