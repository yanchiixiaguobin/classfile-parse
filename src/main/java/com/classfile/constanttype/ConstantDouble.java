package com.classfile.constanttype;

import com.classfile.ConstantBase;
import com.classfile.type.U4;

import java.io.InputStream;

public class ConstantDouble extends ConstantBase {

    public long highValue;

    public long lowValue;

    public short tag;

    public ConstantDouble (short tag) {
        this.tag = tag;
    }

    public void read(InputStream inputStream) {
        highValue = U4.read(inputStream);
        lowValue = U4.read(inputStream);
    }
}
