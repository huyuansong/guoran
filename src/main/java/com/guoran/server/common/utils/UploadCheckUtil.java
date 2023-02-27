package com.guoran.server.common.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhangmengyu
 * @create 2020/9/27 11:15
 * @Modify By
 * 上传文件格式验证
 */
public class UploadCheckUtil {


    protected static final List FILECONTENTTYPELIST = Arrays.asList(
            ".jpg", ".jpeg", ".png", ".bmp", ".doc", ".docx", ".pdf", ".rar", ".zip", ".xlsx", ".xls");
    /**
     * 记录各个文件头信息及对应的文件类型
     */
    protected static final Map<String, String> MFILETYPES = new HashMap<String, String>();

    static {
        // images
        MFILETYPES.put("FFD8FFE0", ".jpg");
        MFILETYPES.put("FFD8FFE1", ".jpg");
        MFILETYPES.put("FFD8FFE8", ".jpg");
        MFILETYPES.put("89504E47", ".png");
        MFILETYPES.put("47494638", ".gif");
        MFILETYPES.put("49492A00", ".tif");
        MFILETYPES.put("424D", ".bmp");

        //PS和CAD
//        MFILETYPES.put("38425053", ".psd");
//        MFILETYPES.put("41433130", ".dwg");
//        MFILETYPES.put("252150532D41646F6265",".ps");

        //办公文档类
        MFILETYPES.put("D0CF11E0", ".doc");
        MFILETYPES.put("504B0304", ".docx");

//        MFILETYPES.put("7B5C727466", ".rtf");

        MFILETYPES.put("25504446", ".pdf");

        //视频或音频类
//        MFILETYPES.put("3026B275",".wma");
//        MFILETYPES.put("57415645", ".wav");
//        MFILETYPES.put("41564920", ".avi");
//        MFILETYPES.put("4D546864", ".mid");
//        MFILETYPES.put("2E524D46", ".rm");
//        MFILETYPES.put("000001BA", ".mpg");
//        MFILETYPES.put("000001B3", ".mpg");
//        MFILETYPES.put("6D6F6F76", ".mov");
//        MFILETYPES.put("3026B2758E66CF11", ".asf");

//        //程序文件
//        MFILETYPES.put("3C3F786D6C", ".xml");
//        MFILETYPES.put("68746D6C3E", ".html");
//        MFILETYPES.put("7061636B", ".java");
//        MFILETYPES.put("3C254020", ".jsp");
//        MFILETYPES.put("4D5A9000", ".exe");

        //压缩包
        MFILETYPES.put("52617221", ".rar");
//        MFILETYPES.put("1F8B08", ".gz");
//
//        MFILETYPES.put("44656C69766572792D646174653A", ".eml");
//        MFILETYPES.put("5374616E64617264204A", ".mdb");
//
//        MFILETYPES.put("46726F6D", ".mht");
//        MFILETYPES.put("4D494D45", ".mhtml");
    }

    public static String getFileType(InputStream is) throws IOException {
        byte[] b = new byte[4];
        if (is != null) {
            is.read(b, 0, b.length);
        }
        String fileHeader = getFileHeader(b);
        return MFILETYPES.get(getFileHeader(b));
    }

    /**
     * 根据文件转换成的字节数组获取文件头信息
     *
     * @param
     * @return 文件头信息
     */
    public static String getFileHeader(byte[] b) {
        String value = bytesToHexString(b);
        return value;
    }

    /**
     * 将要读取文件头信息的文件的byte数组转换成string类型表示
     * 下面这段代码就是用来对文件类型作验证的方法，
     * 将字节数组的前四位转换成16进制字符串，并且转换的时候，要先和0xFF做一次与运算。
     * 这是因为，整个文件流的字节数组中，有很多是负数，进行了与运算后，可以将前面的符号位都去掉，
     * 这样转换成的16进制字符串最多保留两位，如果是正数又小于10，那么转换后只有一位，
     * 需要在前面补0，这样做的目的是方便比较，取完前四位这个循环就可以终止了
     *
     * @param
     * @return 文件头信息
     */
    private static String bytesToHexString(byte[] src) {
        StringBuilder builder = new StringBuilder();
        if (src == null || src.length <= 0) {
            return null;
        }
        String hv;
        for (int i = 0; i < src.length; i++) {
            // 以十六进制（基数 16）无符号整数形式返回一个整数参数的字符串表示形式，并转换为大写
            hv = Integer.toHexString(src[i] & 0xFF).toUpperCase();
            if (hv.length() < 2) {
                builder.append(0);
            }
            builder.append(hv);
        }


        return builder.toString();
    }

    public Map<String, String> getMFILETYPES() {
        return MFILETYPES;
    }
}
