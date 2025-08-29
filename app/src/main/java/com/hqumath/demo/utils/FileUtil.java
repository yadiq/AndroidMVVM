package com.hqumath.demo.utils;

import com.hqumath.demo.net.HandlerException;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

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
     * 根据url生成文件
     */
    public static File getFileFromUrl(String url) {
        //获取文件名称类型
        String fileNameFromUrl1 = url.substring(url.lastIndexOf("/") + 1);
        //String fileName = fileNameFromUrl1.substring(0, fileNameFromUrl1.lastIndexOf("."));//文件名
        String fileStyle = fileNameFromUrl1.substring(fileNameFromUrl1.lastIndexOf(".") + 1);//文件类型
        //生成文件目录
        File fileDir = CommonUtil.getContext().getExternalFilesDir(fileStyle);
        if (!fileDir.exists())
            fileDir.mkdirs();
        String filePath = fileDir.getAbsolutePath() + "/" + fileNameFromUrl1;
        return new File(filePath);
    }

    /**
     * 根据app版本号生成文件
     */
    public static File getFileFromVersionName(String version) {
        //获取文件名称类型
        String fileNameFromUrl1 = CommonUtil.getContext().getPackageName() + "-" + version;
        String fileStyle = "apk";//文件类型
        //生成文件目录
        File fileDir = CommonUtil.getContext().getExternalFilesDir(fileStyle);
        if (!fileDir.exists())
            fileDir.mkdirs();
        String filePath = fileDir.getAbsolutePath() + "/" + fileNameFromUrl1;
        return new File(filePath);
    }

    /**
     * 写文件
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

    private static void closeStream(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
