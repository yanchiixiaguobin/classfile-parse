package com.classfile.basicinfo.attribute;

import com.classfile.type.U2;

import java.io.InputStream;

public class LocalVariableInfo {

    public int startPc;

    public int length;

    public int nameIndex;

    public int descriptorIndex;

    public int index;

    public void read(InputStream inputStream) {

        startPc = U2.read(inputStream);
        length = U2.read(inputStream);
        nameIndex = U2.read(inputStream);
        descriptorIndex = U2.read(inputStream);
        index = U2.read(inputStream);

    }
}
