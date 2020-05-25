package com.classfile.basicinfo.attribute;

import com.classfile.ConstantPool;
import com.classfile.type.U2;
import com.classfile.type.U4;

import java.io.InputStream;

public class LineNumberTableAttribute extends AttributeInfo {

    public int lineNumberTableLength;

    public LineNumberInfo[] lineNumberTable;

    public LineNumberTableAttribute(ConstantPool cp, int nameIndex) {
        super(cp, nameIndex);
    }

    public void read(InputStream inputStream) {
        //读取attribute长度，但是不用它
        length = (int) U4.read(inputStream);

        lineNumberTableLength = U2.read(inputStream);
        lineNumberTable = new LineNumberInfo[lineNumberTableLength];
        for (int i = 0; i < lineNumberTableLength; i++) {
            lineNumberTable[i] = new LineNumberInfo();
            lineNumberTable[i].read(inputStream);
        }

    }

}
