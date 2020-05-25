package com.classfile.constanttype;

import com.classfile.ConstantBase;
import com.classfile.type.U2;

import java.io.InputStream;

public class ConstantString extends ConstantBase {

    public int nameIndex;

    public short tag;

    public ConstantString(short tag) {
        this.tag = tag;
    }


    public void read(InputStream inputStream) {
        nameIndex = U2.read(inputStream);
    }
}
