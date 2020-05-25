package com.classfile.constanttype;

import com.classfile.ConstantBase;
import com.classfile.type.U2;

import java.io.InputStream;

public class ConstantMethodType extends ConstantBase {

    public int descType;

    public short tag;

    public ConstantMethodType(short tag) {
        this.tag = tag;
    }


    public void read(InputStream inputStream) {
        descType = U2.read(inputStream);
    }
}
