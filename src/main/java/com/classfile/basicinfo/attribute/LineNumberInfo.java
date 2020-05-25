package com.classfile.basicinfo.attribute;

import com.classfile.type.U2;

import java.io.InputStream;

public class LineNumberInfo {

    public int startPc;

    public int lineNumber;

    public void read(InputStream inputStream) {
        startPc = U2.read(inputStream);
        lineNumber = U2.read(inputStream);
    }
}
