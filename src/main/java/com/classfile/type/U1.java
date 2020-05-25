package com.classfile.type;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;

public class U1 {

    private static final Logger LOG = LoggerFactory.getLogger(U1.class);

    public static short read(InputStream inputStream) {
        byte[] bytes = new byte[1];
        try {
            inputStream.read(bytes);
        } catch (IOException e) {
            LOG.debug("read failed:", e);
        }
        short value = (short) (bytes[0] & 0xFF);
        return value;
    }
}
