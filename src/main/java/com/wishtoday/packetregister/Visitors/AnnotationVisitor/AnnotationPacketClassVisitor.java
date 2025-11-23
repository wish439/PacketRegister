package com.wishtoday.packetregister.Visitors.AnnotationVisitor;

import com.wishtoday.packetregister.Visitors.ClassVisitor.PacketClassVisitor;

public abstract class AnnotationPacketClassVisitor extends AnnotationClassPathVisitor {
    protected PacketClassVisitor classVisitor;
    protected AnnotationPacketClassVisitor(String classPath,  PacketClassVisitor packetClassVisitor) {
        super(classPath);
        this.classVisitor = packetClassVisitor;
    }
}
