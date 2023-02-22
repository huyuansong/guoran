package com.hisoft.pam.im.common.utils;

import sun.misc.BASE64Encoder;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
/**
 * @author zhangmengyu
 * @create 2020/8/31 9:30
 * @Modify By
 */
public class DownLoadMyFile {
    public static void downLoadMyFile1(File file, HttpServletResponse response,  String browserType) {
        try {
            if (file.exists()) {
                // 下载 代码 固定性!
                String filename = file.getName();// 文件名称含有中文信息 ...附件名乱码

                if (browserType.contains("Firefox")) {
                    filename = new BASE64Encoder().encode(filename.getBytes("utf-8"));
                    filename = "=?utf-8?B?" + filename + "?=";// 完成获取火狐编码
                } else {
                    filename = URLEncoder.encode(filename, "utf-8");// chrome ie 中文乱码解决!
                }
                // 1: 设置下载的响应头
                response.setHeader("Content-Disposition", "attachment;filename=" + filename);
                // 2: 第二个头 文件类型
//                String minmeType = context.getMimeType(filename);
//                response.setContentType(minmeType);

                // 3: 下载 服务器 文件内容 以 io 形式 输出给浏览器 ... 浏览器接受输出流 ...通过 本身 附件框完成下载!
                InputStream in = new FileInputStream(file);// 输入流 将文件内容 读取当前系统
                // 输入流获取数据 通过输出流 将文件数据 发送 浏览器 ...
                int len = 0;// 字节流的拷贝
                byte bytes[] = new byte[1024 * 4];
                while ((len = in.read(bytes)) != -1) {
                    response.getOutputStream().write(bytes, 0, len);
                }
                in.close();
            } else {
                throw new RuntimeException("文件不存在....");
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
