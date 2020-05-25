package com.classfile.constanttype;

import com.classfile.ConstantBase;
import com.classfile.type.U2;

import java.io.InputStream;

public class ConstantNameAndType extends ConstantBase {

    public int nameIndex;

    public int descIndex;

    public short tag;

    public ConstantNameAndType(short tag) {
        this.tag = tag;
    }

    public void read(InputStream inputStream) {
        nameIndex = U2.read(inputStream);
        descIndex = U2.read(inputStream);
    }
}
