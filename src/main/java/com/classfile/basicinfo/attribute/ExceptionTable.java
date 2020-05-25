package com.classfile.basicinfo.attribute;

import com.classfile.type.U2;

import java.io.InputStream;

public class ExceptionTable {

    public int startPc;

    public int endPc;

    public int handlePc;

    public int catchType;

    public void read(InputStream inputStream) {
        startPc = U2.read(inputStream);
        endPc = U2.read(inputStream);
        handlePc = U2.read(inputStream);
        catchType = U2.read(inputStream);
    }
}
