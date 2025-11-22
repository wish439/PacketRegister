package com.wishtoday.packetregister.Visitors.FieldVisitor;

import com.wishtoday.packetregister.Util.FieldGetter;
import com.wishtoday.packetregister.Visitors.AnnotationVisitor.CodecVisitor;
import com.wishtoday.packetregister.Visitors.AnnotationVisitor.IDVisitor;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.Opcodes;

public class PacketFieldVisitor extends org.objectweb.asm.FieldVisitor {
    private final String fieldName;
    private final String className;

    public PacketFieldVisitor(String fieldName, String className) {
        super(Opcodes.ASM9);
        this.fieldName = fieldName;
        this.className = className;
    }

    @SuppressWarnings("unchecked")
    @Override
    public AnnotationVisitor visitAnnotation(
            String descriptor
            , boolean visible) {
        if (descriptor.equals("Lcom/wishtoday/Annotation/ID;")) {
            return new IDVisitor(this.className,
                    (CustomPayload.Id<CustomPayload>) new FieldGetter(className, fieldName).getStaticField());
        }
        if (descriptor.equals("Lcom/wishtoday/Annotation/Codec;")) {
            Object field = new FieldGetter(className, fieldName).getStaticField();
            if (!(field instanceof PacketCodec))
                throw new IllegalArgumentException(String.format("Invalid packet codec on %s Class %s field", this.className, this.fieldName)
                        + "\nPlease use PacketCodec<PacketByteBuf, ? extends CustomPayload>");
            try {
                return new CodecVisitor(this.className,
                        (PacketCodec<PacketByteBuf, CustomPayload>) field
                );
            } catch (ClassCastException e) {
                throw new IllegalArgumentException(
                        String.format("Invalid packet codec on %s Class %s field", this.className, this.fieldName)
                                + e.getMessage()
                                + "\nPlease use PacketCodec<PacketByteBuf, ? extends CustomPayload>");
            }
        }
        return super.visitAnnotation(descriptor, visible);
    }
}
