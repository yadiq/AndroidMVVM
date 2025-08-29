package com.hqumath.demo.utils;

/**
 * Zxing二维码工具
 * 降级的引入，要求API14+
 * implementation 'com.google.zxing:core:3.3.3'
 */
public class ZxingUtil {

    /**
     * @param url
     * @param qrWidth     300
     * @param qrHeight    300
     * @param deleteWhite 是否去除白边
     * @return 二维码
     */
    /*public static Bitmap createQRCode(String url, int qrWidth, int qrHeight, boolean deleteWhite) {
        if (TextUtils.isEmpty(url)) return null;
        try {
            // 生成二维矩阵,编码时指定大小,不要生成了图片以后再进行缩放,这样会模糊导致识别失败
            Hashtable<EncodeHintType, String> hints = new Hashtable<>();
            hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
            BitMatrix bitMatrix = new QRCodeWriter().encode(url, BarcodeFormat.QR_CODE, qrWidth, qrHeight, hints);
            //去白边
            if (deleteWhite) bitMatrix = deleteWhite(bitMatrix);
            int width = bitMatrix.getWidth();
            int height = bitMatrix.getHeight();
            //二维矩阵转为一维像素数组
            int[] pixels = new int[width * height];
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    if (bitMatrix.get(x, y)) {
                        pixels[y * width + x] = 0xff000000;
                    } else {
                        pixels[y * width + x] = 0xffffffff;
                    }
                }
            }
            Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
            return bitmap;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }*/

    /**
     * 去除白边
     *
     * @param matrix
     * @return
     */
    /*private static BitMatrix deleteWhite(BitMatrix matrix) {
        int[] rec = matrix.getEnclosingRectangle();
        int resWidth = rec[2] + 1;
        int resHeight = rec[3] + 1;

        BitMatrix resMatrix = new BitMatrix(resWidth, resHeight);
        resMatrix.clear();
        for (int i = 0; i < resWidth; i++) {
            for (int j = 0; j < resHeight; j++) {
                if (matrix.get(i + rec[0], j + rec[1]))
                    resMatrix.set(i, j);
            }
        }
        return resMatrix;
    }*/
}
