package com.classfile.constanttype;

import com.classfile.ConstantBase;
import com.classfile.type.U2;

import java.io.InputStream;

public class ConstantInvokeDynamic extends ConstantBase {

    public int bootstrapMethodAttrIndex;

    public int nameAndTypeIndex;

    public short tag;

    public ConstantInvokeDynamic(short tag) {
        this.tag = tag;
    }


    public void read(InputStream inputStream) {
        bootstrapMethodAttrIndex = U2.read(inputStream);
        nameAndTypeIndex = U2.read(inputStream);
    }
}
