package com.opencode.asm.asm01.objectweb;


import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static aj.org.objectweb.asm.Opcodes.ASM4;

public class TheStart {

    public static void main(String[] args) throws IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException, IOException {

        byte[] generator = generator();
        FileOutputStream fos = new FileOutputStream(new File("/Users/wty/project/play_code/src/main/java/com/opencode/asm/asm01/objectweb/nnn.class"));
        fos.write(generator);

        Class<?> aClass = CarrotByteClassLoader.defineClass(generator);
        Object o = aClass.newInstance();
        Method method = aClass.getMethod("code");
        Object invoke = method.invoke(o, null);
    }


    private static byte[] generator() {
        ClassWriter classWriter = new ClassWriter(0);
        ClassVisitor classVisitor = new ClassVisitor(ASM4, classWriter){};

        //定义类的头部信息   java版本、类修饰符、累的权限定名称    public class HelloAsm {}
        classWriter.visit(Opcodes.V1_8 , Opcodes.ACC_PUBLIC,"com/opencode/asm/asm01/HelloAsm",null,"java/lang/Object", null);

        //类的构造方法 public HelloAsm(){}
        MethodVisitor mv = classWriter.visitMethod(Opcodes.ACC_PUBLIC,"<init>","()V",null,null);
        mv.visitCode();
        mv.visitVarInsn(Opcodes.ALOAD,0);
        mv.visitMethodInsn(Opcodes.INVOKESPECIAL,"java/lang/Object","<init>","()V");
        mv.visitInsn(Opcodes.RETURN);
        mv.visitMaxs(1,1);
        mv.visitEnd();

        //定义一个方法 public void code(){}
        MethodVisitor methodVisitor = classWriter.visitMethod(Opcodes.ACC_PUBLIC, "code", "()V", null, null);
        methodVisitor.visitCode();
        methodVisitor.visitFieldInsn(Opcodes.GETSTATIC , "java/lang/System","out","Ljava/io/PrintStream;");
        methodVisitor.visitLdcInsn("select * from table where a = 1 and b = 2 and c = 3");
        methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL,"java/io/PrintStream","println","(Ljava/lang/String;)V");
        methodVisitor.visitInsn(Opcodes.RETURN);
        methodVisitor.visitMaxs(2,2);
        methodVisitor.visitEnd();
        classWriter.visitEnd();

        return classWriter.toByteArray();

    }

}
