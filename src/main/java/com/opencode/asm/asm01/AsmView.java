package com.opencode.asm.asm01;

import javassist.bytecode.Opcode;
import jdk.internal.org.objectweb.asm.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class AsmView {

    public static void main(String[] args) throws IOException {

        byte[] generator = generator();

        FileOutputStream fos = new FileOutputStream(new File("d://nnn.class"));
        fos.write(generator);

    }

    private static byte[] generator() {

        ClassWriter classWriter = new ClassWriter(0);

        //定义类的头部信息   java版本、类修饰符、类的权限定名称    public class HelloWorld {}
        classWriter.visit(Opcodes.V1_8, Opcodes.ACC_PUBLIC, "HelloWorld", null, "java/lang/Object", null);

        FieldVisitor name = classWriter.visitField(Opcodes.ACC_PRIVATE + Opcodes.ACC_STATIC + Opcodes.ACC_FINAL, "name", "Ljava/lang/String", null, null);
        name.visitEnd();

        //定义构造方法
        MethodVisitor methodVisitor = classWriter.visitMethod(Opcodes.ACC_PUBLIC, "<init>", "()V", null, null);
        methodVisitor.visitCode();
        methodVisitor.visitVarInsn(Opcodes.ALOAD, 0);
        methodVisitor.visitMethodInsn(Opcodes.INVOKESPECIAL, "java/lang/Object", "<init>", "()V");
        methodVisitor.visitInsn(Opcodes.RETURN);
        methodVisitor.visitMaxs(1, 1);
        methodVisitor.visitEnd();

        //定义一个方法
//        MethodVisitor getName = classWriter.visitMethod(Opcodes.ACC_PUBLIC, "getName", "java/lang/String", null, null);
//        getName.visitCode();
//        getName.visitVarInsn(Opcodes.ALOAD, 0);
//        getName.visitMethodInsn(Opcodes.INVOKESPECIAL, "java/lang/Object", "getName", "java/lang/String");
//        getName.visitInsn(Opcodes.RETURN);
//        getName.visitMaxs(1, 1);
//        getName.visitEnd();

        classWriter.visitEnd();

        return classWriter.toByteArray();
    }

}
