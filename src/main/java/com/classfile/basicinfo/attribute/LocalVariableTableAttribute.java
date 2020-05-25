package com.classfile.basicinfo.attribute;

import com.classfile.ConstantPool;
import com.classfile.type.U2;
import com.classfile.type.U4;

import java.io.InputStream;

public class LocalVariableTableAttribute extends AttributeInfo {

    public int variableTableLength;

    public LocalVariableInfo[] localVariableInfos;

    public LocalVariableTableAttribute(ConstantPool cp, int nameIndex) {
        super(cp, nameIndex);
    }

    public void read(InputStream inputStream) {
        //读取attribute长度，但是不用它
        length = (int) U4.read(inputStream);

        variableTableLength = U2.read(inputStream);
        localVariableInfos = new LocalVariableInfo[variableTableLength];
        for (int i = 0; i < variableTableLength; i++) {
            localVariableInfos[i] = new LocalVariableInfo();
            localVariableInfos[i].read(inputStream);
        }
    }
}
