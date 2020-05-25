# JAVA类文件解析  

## JAVA 类文件格式
Java类文件按照Java虚拟机规范定的格式存在，其格式大致如下
```c++
ClassFile {
    u4              magic;
    u2              minor_version;
    u2              major_version;
    u2              constant_pool_count;
    cp_info         constant_pool[constant_pool_count - 1];
    u2              access_flags;
    u2              this_class;
    u2              super_class;
    u2              interfaces_count;
    u2              interfaces[interfaces_count];
    u2              fields_count;
    field_info      fields[fields_count];
    u2              method_count;
    method_info     methods[methods_count];
    u2              attributes_count;
    attribute_info  attributes[attributes_count];
}
```  
>上述文件结构中，cp_info表示常量池项，有几个特征如下：
1. 常量池中项可以引用其他项内容
2. double和long类型常量占用两个常量池项
3. 常量池项被后续的字段表、方法表、属性表引用  

## 总结
1. LineNumberTable和LocalVariableTable在方法表的Code属性中
2. Exceptions属性和Code属性的异常表不是一个概念，它和Code属性平级。Code属性的异常表指定：代码中发生异常时代码的执行流程；Exceptions指明受检异常，如方法签名中使用throws关键字抛出的异常。
3. 对常量池的UTF8编码的字节流进行解码
```shell
1字节 0xxxxxxx 
2字节 110xxxxx 10xxxxxx 
3字节 1110xxxx 10xxxxxx 10xxxxxx 
```  
解码时将各个字节表示的值<b>按位或</b>到int变量中，最终转化为char类型，放在char数组中。

