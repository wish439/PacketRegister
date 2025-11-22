package com.wishtoday.packetregister.Visitors.ClassVisitor;

import com.wishtoday.packetregister.Data.PacketClassInfo;
import com.wishtoday.packetregister.Manager.PacketClassManager;
import com.wishtoday.packetregister.Util.ClassUtil;
import com.wishtoday.packetregister.Util.PacketState;
import com.wishtoday.packetregister.Visitors.FieldVisitor.PacketFieldVisitor;
import com.wishtoday.packetregister.Visitors.MethodVisitor.PacketHandlerVisitor;
import net.minecraft.network.packet.CustomPayload;
import org.objectweb.asm.*;

public class PacketClassVisitor extends ClassVisitor {
    public PacketClassVisitor() {
        super(Opcodes.ASM9);
    }

    private String classPath;
    private PacketState state;

    @Override
    public void visit(int version
            , int access
            , String name
            , String signature
            , String superName
            , String[] interfaces) {
        this.classPath = name.replace("/", ".");
        super.visit(version, access, name, signature, superName, interfaces);
    }

    @Override
    public FieldVisitor visitField(int access
            , String name
            , String descriptor
            , String signature
            , Object value) {
        return new PacketFieldVisitor(name, classPath);
    }

    @Override
    public AnnotationVisitor visitAnnotation(String descriptor, boolean visible) {
        if (descriptor.equals("Lcom/wishtoday/Annotation/Packet;")) {
            return new AnnotationVisitor(Opcodes.ASM9) {
                @Override
                public void visitEnum(String name
                        , String descriptor
                        , String value) {
                    if (!"value".equals(name)) return;
                    PacketClassVisitor.this.state = PacketState.valueOf(value);
                }
            };
        }
        return super.visitAnnotation(descriptor, visible);
    }

    @Override
    public MethodVisitor visitMethod(int access
            , String name
            , String descriptor
            , String signature
            , String[] exceptions) {
        return new PacketHandlerVisitor(this.classPath, name, this.state);
    }

    @Override
    public void visitEnd() {
        if (state == null) return;
        PacketClassInfo<CustomPayload> info = PacketClassManager.getInstance()
                .computeIfAbsent(this.classPath, s -> new PacketClassInfo<>());
        info.setState(this.state);
        info.setClazz((Class<CustomPayload>) ClassUtil.getClass(this.classPath));
        super.visitEnd();
    }
}
