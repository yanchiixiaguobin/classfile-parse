package com.classfile.constanttype;

import com.classfile.ConstantBase;
import com.classfile.type.U1;
import com.classfile.type.U2;

import java.io.InputStream;

public class ConstantMethodHandle extends ConstantBase {

    public short referenceKind;

    public int referenceIndex;

    public short tag;

    public ConstantMethodHandle(short tag) {
        this.tag = tag;
    }

    public void read(InputStream inputStream) {
        referenceKind = U1.read(inputStream);
        referenceIndex = U2.read(inputStream);
    }
}
