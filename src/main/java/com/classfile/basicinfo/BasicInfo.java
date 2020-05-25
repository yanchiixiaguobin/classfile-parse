package com.classfile.basicinfo;

import com.classfile.ConstantPool;

import java.io.InputStream;

public abstract class BasicInfo {

    protected ConstantPool cp;

    public BasicInfo(ConstantPool cp) {
        this.cp = cp;
    }

    public abstract void read(InputStream inputStream);

}
