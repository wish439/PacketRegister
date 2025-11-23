package com.wishtoday.packetregister.Visitors.AnnotationVisitor.Field;

import com.wishtoday.packetregister.Data.PacketClassInfo;
import com.wishtoday.packetregister.Manager.PacketClassManager;
import com.wishtoday.packetregister.Visitors.AnnotationVisitor.AnnotationClassPathVisitor;
import net.minecraft.network.packet.CustomPayload;

public class IDVisitor extends AnnotationClassPathVisitor {
    private final CustomPayload.Id<CustomPayload> ID;
    public IDVisitor(String classPath,
            CustomPayload.Id<CustomPayload> id) {
        super(classPath);
        this.ID = id;
    }

    @Override
    public void visitEnd() {
        PacketClassManager
                .getInstance()
                .computeIfAbsent(classPath, s -> new PacketClassInfo())
                .setID(ID);
        super.visitEnd();
    }
}
