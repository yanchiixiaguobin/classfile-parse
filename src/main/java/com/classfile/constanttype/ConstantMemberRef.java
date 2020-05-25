package com.classfile.constanttype;


import com.classfile.ConstantBase;
import com.classfile.type.U2;
import java.io.InputStream;

public class ConstantMemberRef extends ConstantBase {

    public int classIndex;

    public int nameAndTypeIndex;

    public short tag;

    public ConstantMemberRef(short tag) {
        this.tag = tag;
    }


    @Override
    public void read(InputStream inputStream) {
        classIndex= U2.read(inputStream);
        nameAndTypeIndex=U2.read(inputStream);
    }
}
