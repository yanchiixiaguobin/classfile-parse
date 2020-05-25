package com.classfile.constanttype;

import com.classfile.ConstantBase;
import com.classfile.type.U4;

import java.io.InputStream;

public class ConstantInteger extends ConstantBase {

    public long value;

    public short tag;

    public ConstantInteger(short tag) {
        this.tag = tag;
    }

    public void read(InputStream inputStream) {
        value = U4.read(inputStream);
    }
}
