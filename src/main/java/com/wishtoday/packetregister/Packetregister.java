package com.wishtoday.packetregister;

import com.wishtoday.Annotation.*;
import com.wishtoday.packetregister.Util.IdentifierCreator;
import com.wishtoday.packetregister.Util.PacketState;
import com.wishtoday.packetregister.Visitors.ClassVisitor.PacketClassVisitor;
import io.github.classgraph.ClassGraph;
import io.github.classgraph.ClassInfo;
import io.github.classgraph.ClassInfoList;
import io.github.classgraph.ScanResult;
import lombok.extern.log4j.Log4j2;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.objectweb.asm.ClassReader;

import java.io.IOException;

@Log4j2
public class Packetregister implements ModInitializer {

    @Override
    public void onInitialize() {
        IdentifierCreator.setNameSpace("pctr");
        ScanResult scan = new ClassGraph()
                .acceptPackages("com.wishtoday")
                .enableAllInfo()
                .scan();
        ClassInfoList list = scan.getClassesWithAnnotation(Packet.class);
        ClassInfoList initList = scan.getClassesWithAnnotation(Initialize.class);
        for (ClassInfo info : list) {
            try {
                ClassReader reader = new ClassReader(info.getName());
                reader.accept(new PacketClassVisitor(), 0);
            } catch (IOException e) {
                log.error("asm exception {}", e.toString());
            }
        }
        initList.forEach(classInfo -> {
            try {
                Class.forName(classInfo.getName());
            } catch (ClassNotFoundException e) {
                log.error("class {} not found {}", classInfo.getName(), e.toString());
            }
        });
        scan.close();
        ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> {
            sender.sendPacket(new TestPayload2());
        });
    }

    /*@Packet(PacketState.S2C)
    public record TestPayload(int a) implements CustomPayload {
        @ID
        public static final CustomPayload.Id<TestPayload> ID = new Id<>(Identifier.of("pctr", "test"));
        @Codec
        public static final PacketCodec<PacketByteBuf, TestPayload> CODEC = PacketCodec.of((value, buf) -> buf.writeInt(value.a), buf -> new TestPayload(buf.readInt()));

        @Handler
        public static void handler(TestPayload payload
                , ClientPlayNetworking.Context context) {
            context.client().execute(() -> {
                context.player().sendMessage(Text.of("Hello" + payload.a));
            });
        }

        @Override
        public Id<? extends CustomPayload> getId() {
            return ID;
        }
    }*/
    @Packet(PacketState.S2C)
    @EmptyCodec
    public record TestPayload2() implements CustomPayload {
        @ID
        public static final CustomPayload.Id<TestPayload2> ID = new Id<>(Identifier.of("pctr", "test2"));

        @Handler
        public static void handler(TestPayload2 payload
                , ClientPlayNetworking.Context context) {
            context.client().execute(() -> {
                context.player().sendMessage(Text.of("Hello this is TestPayload2!"));
            });
        }

        @Override
        public Id<? extends CustomPayload> getId() {
            return ID;
        }
    }
}
