package com.classfile.type;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;

public class U2 {

    private static final Logger LOG = LoggerFactory.getLogger(U2.class);

    public static int read(InputStream inputStream) {
        //bytes作为缓冲数组存储两个字节
        //class文件中字符以U-16编码
        byte[] bytes = new byte[2];
        try {
            inputStream.read(bytes);
        } catch (IOException e) {
            LOG.debug("read failed:", e);
        }
        //将缓冲数组中的两个字节解析成字符。
        int num = 0;
        for (int i= 0; i < bytes.length; i++) {
            num <<= 8;
            num |= (bytes[i] & 0xff);
        }
        return num;
    }
}
