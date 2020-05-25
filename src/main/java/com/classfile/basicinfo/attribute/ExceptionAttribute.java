package com.classfile.basicinfo.attribute;

import com.classfile.ConstantPool;
import com.classfile.type.U2;
import com.classfile.type.U4;

import java.io.InputStream;

public class ExceptionAttribute extends AttributeInfo {

    public int numberOfExceptions;

    public int[] indexTable;

    public ExceptionAttribute(ConstantPool cp, int nameIndex) {
        super(cp, nameIndex);
    }

    public void read(InputStream inputStream) {
        //读取attribute长度，但是不用它
        length = (int) U4.read(inputStream);

        numberOfExceptions= U2.read(inputStream);
        indexTable = new int[numberOfExceptions];
        for (int i = 0; i < numberOfExceptions; i++) {
            indexTable[i] = U2.read(inputStream);
        }
    }
}
