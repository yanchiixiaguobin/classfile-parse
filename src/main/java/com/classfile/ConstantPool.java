package com.classfile;

import com.classfile.constant.Constant;
import com.classfile.type.U1;

import java.io.InputStream;

public class ConstantPool {

    public int constant_pool_count;

    public ConstantBase[] cpInfo;

    public ConstantPool(int count) {
        constant_pool_count = count;
        cpInfo = new ConstantBase[constant_pool_count];
    }

    public void read(InputStream inputStream) {

        for (int i = 1; i < constant_pool_count; i++) {
            short tag = U1.read(inputStream);
            ConstantBase constantBase = ConstantBase.getConstantInfo(tag);
            constantBase.read(inputStream);
            cpInfo[i] = constantBase;

            // double和long类型的常量会占用常量池的两项
            if (tag == Constant.CONSTANT_DOUBLE || tag == Constant.CONSTANT_LONG) {
                i++;
            }
        }

    }
}
