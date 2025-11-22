package com.wishtoday.packetregister;

import com.wishtoday.Annotation.Codec;
import com.wishtoday.Annotation.Handler;
import com.wishtoday.Annotation.ID;
import com.wishtoday.Annotation.Packet;
import com.wishtoday.packetregister.Register.PayloadRegister;
import com.wishtoday.packetregister.Util.PacketState;
import com.wishtoday.packetregister.Visitors.ClassVisitor.PacketClassVisitor;
import io.github.classgraph.ClassGraph;
import io.github.classgraph.ClassInfo;
import io.github.classgraph.ClassInfoList;
import io.github.classgraph.ScanResult;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;
import org.objectweb.asm.ClassReader;

import java.io.IOException;

public class Packetregister implements ModInitializer {

    @Override
    public void onInitialize() {
        ScanResult scan = new ClassGraph()
                .acceptPackages("com.wishtoday")
                .enableAllInfo()
                .scan();
        ClassInfoList list = scan.getClassesWithAnnotation(Packet.class);
        scan.close();
        for (ClassInfo info : list) {
            System.out.println("Found Packet: " + info.getName());
            try {
                ClassReader reader = new ClassReader(info.getName());
                reader.accept(new PacketClassVisitor(), ClassReader.SKIP_CODE);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        PayloadRegister.register();
        ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> {

        });
    }
    @Packet(PacketState.S2C)
    public record TestPayload() implements CustomPayload {
        @ID
        public static final CustomPayload.Id<TestPayload> ID = new Id<>(Identifier.of("pctr", "test"));
        @Codec
        public static final PacketCodec<PacketByteBuf, TestPayload> CODEC = PacketCodec.unit(new TestPayload());

        @Handler
        public static void handler(TestPayload payload, ClientPlayNetworking.Context context) {

        }

        @Override
        public Id<? extends CustomPayload> getId() {
            return ID;
        }
    }
}
