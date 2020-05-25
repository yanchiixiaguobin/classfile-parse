package com.classfile;


import com.classfile.constant.Constant;
import com.classfile.constanttype.*;

import java.io.InputStream;

public abstract class ConstantBase {



    public abstract void read(InputStream inputStream);

    public static ConstantBase getConstantInfo(short tag) {
        switch (tag) {
            case Constant.CONSTANT_CLASS:

                return new ConstantClass(tag);
            case Constant.CONSTANT_FIELDREF:
            case Constant.CONSTANT_METHODREF:
            case Constant.CONSTANT_INTERFACEMETHODREF:

                return new ConstantMemberRef(tag);
            case Constant.CONSTANT_LONG:

                return new ConstantLong(tag);
            case Constant.CONSTANT_DOUBLE:

                return new ConstantDouble(tag);
            case Constant.CONSTANT_STRING:

                return new ConstantString(tag);
            case Constant.CONSTANT_INTEGER:

                return new ConstantInteger(tag);
            case Constant.CONSTANT_FLOAT:

                return new ConstantFloat(tag);
            case Constant.CONSTANT_NAMEANDTYPE:

                return new ConstantNameAndType(tag);
            case Constant.CONSTANT_UTF8:

                return new ConstantUTF8(tag);
            case Constant.CONSTANT_METHODHANDLE:

                return new ConstantMethodHandle(tag);
            case Constant.CONSTANT_METHODTYPE:

                return new ConstantMethodType(tag);
            case Constant.CONSTANT_DYNAMICINFO:
            case Constant.CONSTANT_INVOKEDYNAMIC:

                return new ConstantInvokeDynamic(tag);
            case Constant.CONSTANT_MODULEINFO:
            case Constant.CONSTANT_PACKAGEINFO:

                return new ConstantModulePackageInfo(tag);
        }

        throw new RuntimeException("unrecognized type");
    }

}
