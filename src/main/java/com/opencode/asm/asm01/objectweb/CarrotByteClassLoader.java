package com.opencode.asm.asm01.objectweb;

public class CarrotByteClassLoader extends ClassLoader{

    private CarrotByteClassLoader() {
    }

    public static Class<?> defineClass(byte[] classBytes){
        return new CarrotByteClassLoader().defineClass(null,classBytes,0 , classBytes.length);
    }

}
