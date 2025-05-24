package io.github.pcl_community;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class GLFWClassVisitor extends ClassVisitor {
    public GLFWClassVisitor(ClassVisitor cv) {
        super(Opcodes.ASM9, cv);
    }

    public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
        MethodVisitor mv = super.visitMethod(access, name, descriptor, signature, exceptions);
        return "nglfwSetWindowIcon".equals(name) ? new MethodVisitor(this.api, mv) {
            public void visitMethodInsn(int opcode, String owner, String name, String descriptor, boolean isInterface) {
                if (!"invokePPV".equals(name)) {
                    super.visitMethodInsn(opcode, owner, name, descriptor, isInterface);
                }
            }
        } : mv;
    }
}
