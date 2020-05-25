package com.classfile;

import com.classfile.basicinfo.FieldMethodInfo;
import com.classfile.basicinfo.attribute.*;
import com.classfile.constanttype.*;
import com.classfile.type.U2;
import com.classfile.type.U4;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class Main {

    private static final Logger LOG = LoggerFactory.getLogger(Main.class);

    public static Map<Integer, String> codeMap = new HashMap();

    static {
        init();
    }

    public static void init() {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(getFile("bytecode.txt")));){
            int lineNumber = 0;
            String line;
            Integer bytecode = null;
            while ((line = bufferedReader.readLine()) != null) {
                if ((lineNumber & 0x1) == 0) {
                    bytecode = Integer.parseInt(line.substring(2,4), 16);
                } else {
                    codeMap.put(bytecode, line);
                }
                lineNumber++;
            }

        } catch(Exception e) {
            LOG.debug("file not exist!");
        }
    }

    public static String getFile(String fileName) {

        // 此方法可以获取当前类的class
        Class currentClass = new Object() { }.getClass().getEnclosingClass();
        ClassLoader classLoader = currentClass.getClassLoader();

        /**
         * getResource()方法会去classpath下找这个文件，获取到url resource, 得到这个资源后，调用url.getFile获取到 文件 的绝对路径
         */
        URL url = classLoader.getResource(fileName);
        // 获取文件的绝对路径
        return url.getFile();
    }



    public static void main(String[] args) throws Exception {
        String path = getFile("Init.class");
        FileInputStream inputStream = new FileInputStream(path);
        System.out.printf("magic : %x\n", U4.read(inputStream));
        System.out.println("minor version: " + U2.read(inputStream));
        System.out.println("major version: " + U2.read(inputStream));
        int cpCount = U2.read(inputStream);
        System.out.println("constantPool count: " + (cpCount - 1));

        ConstantPool cpPool = new ConstantPool(cpCount);
        cpPool.read(inputStream);
        printConstanPoolInfo(cpPool);


         int accessFlag = U2.read(inputStream);
         int classIndex = U2.read(inputStream);
         ConstantClass clazz = (ConstantClass) cpPool.cpInfo[classIndex];  //获取类名，并将其转化为ContantClass类
         ConstantUTF8 className = (ConstantUTF8) cpPool.cpInfo[clazz.nameIndex];

         System.out.print("classname:" + className.value + "\n");

        int superIndex = U2.read(inputStream);
        ConstantClass superClazz = (ConstantClass) cpPool.cpInfo[superIndex];
        ConstantUTF8 superclassName = (ConstantUTF8) cpPool.cpInfo[superClazz.nameIndex];
        System.out.print("superclass:" + superclassName.value + "\n");

        int interfaceCount = U2.read(inputStream);
        String[] interfaces = new String[interfaceCount];
        for (int i = 0; i < interfaceCount; i++) {
            int interfaceIndex = U2.read(inputStream);
            ConstantClass interfaceClazz = (ConstantClass) cpPool.cpInfo[interfaceIndex];
            ConstantUTF8 interfaceName = (ConstantUTF8) cpPool.cpInfo[interfaceClazz.nameIndex];
            interfaces[i] = interfaceName.value;
            System.out.print("interface:" + interfaceName.value + "\n");
        }
        System.out.print("\n");

        //字段信息

        int fieldCount = U2.read(inputStream);
        FieldMethodInfo[] fields = new FieldMethodInfo[fieldCount];
        for (int i = 0; i < fieldCount; i++) {
            FieldMethodInfo fieldInfo = new FieldMethodInfo(cpPool);
            fieldInfo.read(inputStream);
            System.out.print("field: " + ((ConstantUTF8) cpPool.cpInfo[fieldInfo.nameIndex]).value + ", ");
            System.out.print("desc: " + ((ConstantUTF8) cpPool.cpInfo[fieldInfo.descriptorIndex]).value + "\n");

        }
        System.out.print("\n");

        //方法信息
        int methodCount = U2.read(inputStream);
        FieldMethodInfo[] methods = new FieldMethodInfo[methodCount];
        for (int i = 0; i < methodCount; i++) {
            FieldMethodInfo methodInfo = new FieldMethodInfo(cpPool);
            methodInfo.read(inputStream);
            System.out.println("=============================================");
            System.out.print("method:" + ((ConstantUTF8) cpPool.cpInfo[methodInfo.nameIndex]).value + "(), ");
            System.out.print("desc:" + ((ConstantUTF8) cpPool.cpInfo[methodInfo.descriptorIndex]).value + "\n");
            System.out.println("---------------------------------------------");
            for (int j = 0; j < methodInfo.attributesCount; j++) {


                if (methodInfo.attributes[j] instanceof CodeAttribute) {
                    CodeAttribute codeAttribute = (CodeAttribute) methodInfo.attributes[j];
                    for (int m = 0; m < codeAttribute.codeLength; m++) {
                        short code = codeAttribute.code[m];
                        System.out.println("\t" + codeMap.get(Integer.valueOf(code)));
                    }
                    System.out.println("---------------------------------------------");
                    // 异常语句
                    System.out.println("Exception table:");
                    System.out.println("\tfrom\tto\ttarget\t\ttype");
                    for (int n = 0; n < codeAttribute.exceptionTableLength; n++) {
                        int catchTypeIndex = codeAttribute.exceptionTable[n].catchType;
                        String catchType = "any";
                        if (catchTypeIndex != 0) {
                            int nameIndex = ((ConstantClass)cpPool.cpInfo[catchTypeIndex]).nameIndex;
                            catchType = ((ConstantUTF8) cpPool.cpInfo[nameIndex]).value;
                        }

                        System.out.println("\t" + codeAttribute.exceptionTable[n].startPc
                        + "\t\t" + codeAttribute.exceptionTable[n].endPc
                        + "\t\t" + codeAttribute.exceptionTable[n].handlePc
                        + "\t\t" + catchType);
                    }
                    // lineNumberTable

                    for (int k = 0; k < codeAttribute.attributesCount; k++) {
                        if (codeAttribute.attributes[k] instanceof LineNumberTableAttribute) {
                            System.out.println("LineNumberTable:");
                            LineNumberTableAttribute lineNumberTableAttribute = (LineNumberTableAttribute) codeAttribute.attributes[k];
                            for (int l = 0; l < lineNumberTableAttribute.lineNumberTableLength; l++) {
                                System.out.println("\tline\t" + lineNumberTableAttribute.lineNumberTable[l].lineNumber
                                        + ": " + lineNumberTableAttribute.lineNumberTable[l].startPc);
                            }
                        } else if (codeAttribute.attributes[k] instanceof LocalVariableTableAttribute) {
                            System.out.println("LocalVariableTable:");
                            LocalVariableTableAttribute localVariableTableAttribute = (LocalVariableTableAttribute)codeAttribute.attributes[k];
                            for (int l = 0; l < localVariableTableAttribute.variableTableLength; l++) {
                                LocalVariableInfo localVariableInfo = localVariableTableAttribute.localVariableInfos[l];
                                String name = ((ConstantUTF8)cpPool.cpInfo[localVariableInfo.nameIndex]).value;
                                String descriptor = ((ConstantUTF8)cpPool.cpInfo[localVariableInfo.descriptorIndex]).value;

                                System.out.println("\t var:" + name + ", desc:" + descriptor + ", startoffset="
                                        + localVariableInfo.startPc + ", length=" + localVariableInfo.length);

                            }
                        }
                    }


                    System.out.println("=============================================");
                } else if (methodInfo.attributes[j] instanceof ExceptionAttribute) {
                    ExceptionAttribute exceptionAttribute = (ExceptionAttribute) methodInfo.attributes[j];
                    for (int m = 0; m < exceptionAttribute.numberOfExceptions; m++) {
                        int nameIndex = ((ConstantClass)cpPool.cpInfo[exceptionAttribute.indexTable[m]]).nameIndex;
                        System.out.println("checked exception type:"+ ((ConstantUTF8)cpPool.cpInfo[nameIndex]).value);
                    }
                    System.out.println("=============================================");
                }
            }

        }

        // 打印还剩多少数据
        System.out.println("remain bytes:" + inputStream.available());

        // 读出属性表的长度
        int attributeCount = U2.read(inputStream);
        System.out.println("attributeCount:" + attributeCount);
        inputStream.close();
    }

    public static void printConstanPoolInfo(ConstantPool cp){
        if(cp != null){

            for(int i=1;i<cp.constant_pool_count;i++){
                ConstantBase constantBase = cp.cpInfo[i];
                if(constantBase instanceof ConstantMemberRef){

                    ConstantMemberRef memberRef=(ConstantMemberRef) constantBase;
                    short tag = memberRef.tag;

                    switch(tag){
                        case 9:
                            System.out.println("#"+i+" = Fieldref       " + "#" + memberRef.classIndex + ".#" + memberRef.nameAndTypeIndex
                            + "");
                            continue;
                        case 10:
                            System.out.println("#"+i+" = Methodref      " + "#" + memberRef.classIndex + ".#" + memberRef.nameAndTypeIndex);
                            continue;
                        default :
                            continue;
                    }
                }else if(constantBase instanceof ConstantNameAndType){
                    ConstantNameAndType nameAndType_=(ConstantNameAndType) constantBase;
                    System.out.println("#"+i+" = NameAndType    #" + nameAndType_.nameIndex + ":#" + nameAndType_.descIndex);
                }else if(constantBase instanceof ConstantClass){
                    ConstantClass clazz=(ConstantClass) constantBase;
                    System.out.println("#"+i+" = Class          //"+((ConstantUTF8) cp.cpInfo[clazz.nameIndex]).value);
                }else if(constantBase instanceof ConstantUTF8){
                    ConstantUTF8 utf = (ConstantUTF8) constantBase;
                    System.out.println("#"+i+" = UTF-8          " + utf.value);
                }else if (constantBase instanceof ConstantString) {
                    ConstantString string = (ConstantString) constantBase;
                    System.out.println("#" + i + " = String" + "         #" + string.nameIndex + "\t//" + ((ConstantUTF8)cp.cpInfo[string.nameIndex]).value);
                }else if (constantBase instanceof ConstantLong) {
                    ConstantLong constantLong = (ConstantLong) constantBase;
                    long res = 0;
                    // 可能会显示为负数
                    res = constantLong.highValue << 32 | constantLong.lowValue;
                    System.out.println("#"+i+" = UTF-8          " + res);
                }
            }
            System.out.println("\n");
        }
    }

}
