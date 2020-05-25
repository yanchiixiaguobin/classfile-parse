package com.classfile.constanttype;

import com.classfile.ConstantBase;
import com.classfile.type.U2;

import java.io.InputStream;

public class ConstantClass extends ConstantBase {

    public short tag;

    public int nameIndex;

    public ConstantClass(short tag) {
        this.tag = tag;
    }

    public void read(InputStream inputStream) {
        nameIndex = U2.read(inputStream);
    }
}
