package com.classfile.basicinfo.attribute;

import com.classfile.ConstantPool;
import com.classfile.constant.Constant;
import com.classfile.type.U1;
import com.classfile.type.U2;
import com.classfile.type.U4;
import com.classfile.basicinfo.BasicInfo;
import com.classfile.constanttype.ConstantUTF8;

import java.io.InputStream;

public class AttributeInfo extends BasicInfo {

    public int nameIndex;

    protected int length;

    public short[] info;



    public AttributeInfo(ConstantPool cp, int nameIndex) {
        super(cp);
        this.nameIndex = nameIndex;
    }

    @Override
    public void read(InputStream inputStream) {
        length = (int) U4.read(inputStream);
        // info表示属性表的具体内容,这里其实是把其他的属性类型的内容都存起来，防止读取错位
        info = new short[length];
        for (int i = 0; i < length; i++) {
            info[i] = U1.read(inputStream);
        }
    }

    public static AttributeInfo getAttribute(ConstantPool cp, InputStream inputStream) {
        int nameIndex = U2.read(inputStream);
        String name = ((ConstantUTF8)cp.cpInfo[nameIndex]).value;

        // 目前支持的属性表类型
        switch(name) {
            case Constant.CODE:
                return new CodeAttribute(cp, nameIndex);
            case Constant.EXCEPTIONS:
                return new ExceptionAttribute(cp, nameIndex);
            case Constant.LINE_NUMBER_TABLE:
                return new LineNumberTableAttribute(cp ,nameIndex);
            case Constant.LOCAL_VARIABLE_TABLE:
                return new LocalVariableTableAttribute(cp, nameIndex);
        }
        // 暂不支持的类型,先用通用属性表存储
        return new AttributeInfo(cp, nameIndex);

    }
}
