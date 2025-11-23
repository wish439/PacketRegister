package com.wishtoday.packetregister.Visitors.MethodVisitor;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class IDInstanceMethodVisitor extends MethodVisitor {
    private final String id;

    public IDInstanceMethodVisitor(MethodVisitor mv
            , String id) {
        super(Opcodes.ASM9, mv);
        this.id = id;
    }
    /*public IDInstanceMethodVisitor(String id) {
        super(Opcodes.ASM9);
        this.id = id;
    }*/


    @Override
    public void visitCode() {
        super.visitCode();
        mv.visitLdcInsn(this.id);
        mv.visitMethodInsn(Opcodes.INVOKESTATIC
                , "com/wishtoday/packetregister/Util/CustomPayloadUtils"
                , "createCustomID"
                , "(Ljava/lang/String;)Lnet/minecraft/network/packet/CustomPayload$Id;"
                , false);
        mv.visitInsn(Opcodes.ARETURN);
        mv.visitMaxs(1, 1);
//      visitLdcInsn(this.id);
//      visitMethodInsn(Opcodes.INVOKESTATIC, "com/wishtoday/packetregister/Util/CustomPayloadUtils", "createCustomID", "(Ljava/lang/String;)Lnet/minecraft/network/packet/CustomPayload$Id;", false);
//      visitInsn(Opcodes.ARETURN);
//      visitMaxs(1, 1);
    }
}
