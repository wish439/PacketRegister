package com.wishtoday.packetregister.Visitors.AnnotationVisitor.Field;

import com.wishtoday.packetregister.Data.PacketClassInfo;
import com.wishtoday.packetregister.Manager.PacketClassManager;
import com.wishtoday.packetregister.Visitors.AnnotationVisitor.AnnotationClassPathVisitor;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;

public class CodecVisitor extends AnnotationClassPathVisitor {
    private final PacketCodec<PacketByteBuf, CustomPayload> CODEC;

    public CodecVisitor(
            String path,
            PacketCodec<PacketByteBuf, CustomPayload> codec) {
        super(path);
        this.CODEC = codec;
    }

    @Override
    public void visitEnd() {
        PacketClassManager.getInstance()
                .computeIfAbsent(this.classPath, e -> new PacketClassInfo<>())
                .setCODEC(CODEC);
        super.visitEnd();
    }
}
