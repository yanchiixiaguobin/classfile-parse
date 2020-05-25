package com.classfile.basicinfo.attribute;

import com.classfile.ConstantPool;
import com.classfile.type.U1;
import com.classfile.type.U2;
import com.classfile.type.U4;

import java.io.InputStream;

public class CodeAttribute extends AttributeInfo {

    public int maxStack;

    public int maxLocals;

    public int codeLength;

    public short[] code;

    public int exceptionTableLength;

    public ExceptionTable[] exceptionTable;

    public int attributesCount;

    public AttributeInfo[] attributes;

    public CodeAttribute(ConstantPool cp, int nameIndex) {
        super(cp, nameIndex);
    }

    @Override
    public void read(InputStream inputStream) {
        //读取attribute长度，但是不用它
        length = length = (int) U4.read(inputStream);

        maxStack = U2.read(inputStream);
        maxLocals = U2.read(inputStream);
        codeLength = (int)U4.read(inputStream);
        code = new short[codeLength];
        for (int i = 0; i < codeLength; i++) {
            code[i] = U1.read(inputStream);
        }

        exceptionTableLength = U2.read(inputStream);
        exceptionTable = new ExceptionTable[exceptionTableLength];

        for (int i = 0; i < exceptionTableLength; i++) {
            ExceptionTable exceptTable = new ExceptionTable();
            exceptTable.read(inputStream);
            exceptionTable[i] = exceptTable;
        }

        attributesCount = U2.read(inputStream);
        attributes = new AttributeInfo[attributesCount];
        for (int i = 0; i < attributesCount; i++) {
            AttributeInfo attributeInfo = AttributeInfo.getAttribute(cp, inputStream);
            attributeInfo.read(inputStream);
            attributes[i] = attributeInfo;
        }
    }


}
